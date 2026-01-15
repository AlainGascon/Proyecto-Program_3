package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import domain.Producto;
// IMPORTACIÓN DE LAS CLASES DE RECURSIVIDAD
import recursividad.CombinacionesExactasRec;
import recursividad.OrdenarPorPrecioRec;

public class VentanaResultadosRecursividad extends JDialog {

    private static final long serialVersionUID = 1L;
    
    // PALETA DE COLORES UNIFICADA
    private static final Color COLOR_PRIMARIO = new Color(41, 128, 185); 
    private static final Color COLOR_ACENTO = new Color(46, 204, 113); 
    private static final Color COLOR_PRECIO = new Color(231, 76, 60); 
    private static final Color COLOR_FONDO_CLARO = new Color(236, 240, 241);
    private static final Color COLOR_TEXTO_PRINCIPAL = new Color(51, 51, 51);
    private static final Color COLOR_FONDO_OSCURO = new Color(33, 37, 41);
    
    // FUENTES ESTANDARIZADAS
    private static final Font FUENTE_TITULO = new Font("Arial", Font.BOLD, 28);
    private static final Font FUENTE_SUBTITULO = new Font("Arial", Font.BOLD, 22);
    private static final Font FUENTE_DESTACADO = new Font("Arial", Font.BOLD, 16);
    private static final Font FUENTE_NORMAL = new Font("Arial", Font.PLAIN, 14);
    
    private List<List<Producto>> resultadosOriginales;
    private DefaultTableModel modelo;
    private JTable tabla;
    private double presupuesto;

    
    public VentanaResultadosRecursividad(JFrame padre, List<List<Producto>> resultados, double presupuesto) {
        super(padre, "Sugerencias de Compra - Deusto Fashion", true);
        this.resultadosOriginales = new ArrayList<>(resultados);
        this.presupuesto = presupuesto;
        
        setSize(1000, 750);
        setLocationRelativeTo(padre);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // PANEL NORTE
        JPanel pnlNorte = new JPanel(new BorderLayout());
        pnlNorte.setBackground(COLOR_FONDO_CLARO);
        pnlNorte.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JPanel pnlTextos = new JPanel(new GridLayout(2, 1));
        pnlTextos.setBackground(COLOR_FONDO_CLARO);
        
        JLabel lblT = new JLabel("COMBINACIONES DISPONIBLES");
        lblT.setFont(FUENTE_TITULO);
        lblT.setForeground(COLOR_TEXTO_PRINCIPAL);
        
        JLabel lblS = new JLabel("<html>Opciones encontradas: " + resultados.size() + 
                                 " | Presupuesto: <span style='color: rgb(231,76,60); font-weight: bold;'>" + 
                                 String.format("%.2f", presupuesto) + "€</span></html>");
        lblS.setFont(FUENTE_NORMAL);
        lblS.setForeground(COLOR_TEXTO_PRINCIPAL);
        
        pnlTextos.add(lblT); 
        pnlTextos.add(lblS);

        JPanel pnlFiltro = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        pnlFiltro.setBackground(COLOR_FONDO_CLARO);
        
        JLabel lblFiltro = new JLabel("ORDENAR/FILTRAR: ");
        lblFiltro.setFont(FUENTE_DESTACADO);
        lblFiltro.setForeground(COLOR_TEXTO_PRINCIPAL);
        
        String[] opciones = {"Sin orden", "Precio: Menor a Mayor", "Precio: Mayor a Menor", "Aprovechamiento Máximo (>90%)"};
        JComboBox<String> comboOrden = new JComboBox<>(opciones);
        comboOrden.setFont(FUENTE_NORMAL);
        comboOrden.setPreferredSize(new Dimension(260, 35));
        comboOrden.addActionListener(e -> procesarFiltro(comboOrden.getSelectedIndex()));

        pnlFiltro.add(lblFiltro);
        pnlFiltro.add(comboOrden);

        pnlNorte.add(pnlTextos, BorderLayout.WEST);
        pnlNorte.add(pnlFiltro, BorderLayout.EAST);
        add(pnlNorte, BorderLayout.NORTH);

        // TABLA
        modelo = new DefaultTableModel(new String[]{"ID", "Prendas Sugeridas", "Total", "Sobrante"}, 0) {
            private static final long serialVersionUID = 1L;
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        
        tabla = new JTable(modelo);
        tabla.setFont(FUENTE_NORMAL);
        tabla.getTableHeader().setFont(FUENTE_DESTACADO);
        tabla.setRowHeight(35);
        configurarTabla(tabla);
        actualizarTabla(this.resultadosOriginales);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scroll.getViewport().setBackground(Color.WHITE);
        add(scroll, BorderLayout.CENTER);

        // PANEL SUR CON BOTONES ESTANDARIZADOS
        JPanel pnlSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        pnlSur.setBackground(Color.WHITE);
        pnlSur.setBorder(BorderFactory.createEmptyBorder(15, 0, 25, 0));

        JButton btnExportar = crearBotonPrimario("EXPORTAR A TXT", COLOR_ACENTO);
        btnExportar.addActionListener(e -> exportarResultados());

        JButton btnCerrar = crearBotonPrimario("VOLVER", COLOR_PRIMARIO);
        btnCerrar.addActionListener(e -> dispose());

        pnlSur.add(btnExportar);
        pnlSur.add(btnCerrar);
        add(pnlSur, BorderLayout.SOUTH);
    }
    
    private JButton crearBotonPrimario(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(250, 50));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(FUENTE_DESTACADO);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void procesarFiltro(int index) {
        List<List<Producto>> listaProcesada = new ArrayList<>(resultadosOriginales);
        switch (index) {
            case 1:
                listaOrdenada(listaProcesada, true);
                break;
            case 2:
                listaOrdenada(listaProcesada, false);
                break;
            case 3:
                listaProcesada = listaProcesada.stream()
                        .filter(l -> sumarPrecios(l) >= (presupuesto * 0.9))
                        .sorted((l1, l2) -> Double.compare(sumarPrecios(l2), sumarPrecios(l1)))
                        .collect(Collectors.toList());
                break;
        }
        actualizarTabla(listaProcesada);
    }

    private void listaOrdenada(List<List<Producto>> lista, boolean ascendente) {
        lista.sort((l1, l2) -> ascendente ? 
            Double.compare(sumarPrecios(l1), sumarPrecios(l2)) : 
            Double.compare(sumarPrecios(l2), sumarPrecios(l1)));
    }

    private double sumarPrecios(List<Producto> lista) {
        return lista.stream().mapToDouble(Producto::getPrecio).sum();
    }

    private void actualizarTabla(List<List<Producto>> lista) {
        modelo.setRowCount(0);
        for (int i = 0; i < lista.size(); i++) {
            List<Producto> comboOriginal = lista.get(i);
            
            // INTEGRACIÓN: Se utiliza el algoritmo recursivo Merge Sort (OrdenarPorPrecioRec)
            // para ordenar los productos dentro de cada combinación sugerida antes de mostrarlos.
            List<Producto> combo = OrdenarPorPrecioRec.mergeSortPorPrecio(comboOriginal);
            
            double total = sumarPrecios(combo);
            double sobrante = presupuesto - total;
            
            StringBuilder sb = new StringBuilder("<html><body style='padding:15px; font-family: Arial; font-size: 11pt;'>");
            for(Producto p : combo) {
                sb.append("• ").append(p.getNombre())
                  .append(" (<span style='color: rgb(231,76,60); font-weight: bold;'>")
                  .append(String.format("%.2f", p.getPrecio()))
                  .append("€</span>)<br>");
            }
            sb.append("</body></html>");
            
            String totalStr = "<html><span style='color: rgb(231,76,60); font-weight: bold;'>" + 
                             String.format("%.2f€", total) + "</span></html>";
            String sobranteStr = "<html><span style='color: rgb(231,76,60); font-weight: bold;'>" + 
                                String.format("%.2f€", sobrante) + "</span></html>";
            
            modelo.addRow(new Object[]{"#"+(i+1), sb.toString(), totalStr, sobranteStr});
        }
        ajustarAlturas();
    }

    private void configurarTabla(JTable tabla) {
        tabla.getColumnModel().getColumn(1).setCellRenderer(new WordWrapCellRenderer());
        tabla.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override public void componentResized(java.awt.event.ComponentEvent e) { ajustarAlturas(); }
        });
    }
    
    private void ajustarAlturas() {
        for (int r = 0; r < tabla.getRowCount(); r++) {
            TableCellRenderer renderer = tabla.getCellRenderer(r, 1);
            Component c = tabla.prepareRenderer(renderer, r, 1);
            int height = c.getPreferredSize().height + 25; 
            if (tabla.getRowHeight(r) != height) tabla.setRowHeight(r, height);
        }
    }

    private void exportarResultados() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Sugerencias de Compra");
        fileChooser.setSelectedFile(new java.io.File("Sugerencias_Compra_Deusto.txt"));
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            java.io.File archivo = fileChooser.getSelectedFile();
            try (PrintWriter out = new PrintWriter(new FileWriter(archivo))) {
                out.println("DEUSTO FASHION STORE - SUGERENCIAS DE COMPRA");
                out.println("Fecha: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                out.println("Presupuesto máximo: " + presupuesto + "€");
                out.println("============================================");
                for (int i = 0; i < resultadosOriginales.size(); i++) {
                    // Aquí también podríamos usar OrdenarPorPrecioRec si queremos el TXT ordenado
                    List<Producto> combo = OrdenarPorPrecioRec.mergeSortPorPrecio(resultadosOriginales.get(i));
                    double total = sumarPrecios(combo);
                    out.println("\nOPCION #" + (i + 1));
                    for (Producto p : combo) {
                        out.println(" - " + p.getNombre() + " (ID: " + p.getId() + "): " + p.getPrecio() + "€");
                    }
                    out.println("TOTAL: " + String.format("%.2f", total) + "€ | SOBRANTE: " + String.format("%.2f", presupuesto - total) + "€");
                    out.println("--------------------------------------------");
                }
                JOptionPane.showMessageDialog(this, "Archivo guardado correctamente en:\n" + archivo.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al escribir el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    static class WordWrapCellRenderer extends JEditorPane implements TableCellRenderer {
        private static final long serialVersionUID = 1L;
        public WordWrapCellRenderer() { 
            setEditable(false); 
            setContentType("text/html"); 
        }
        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
            setText(v.toString());
            setSize(t.getColumnModel().getColumn(c).getWidth(), 1000);
            setBackground(s ? t.getSelectionBackground() : t.getBackground());
            return this;
        }
    }
}