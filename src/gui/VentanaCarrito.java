package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import domain.ItemCarrito;
import domain.Producto;

public class VentanaCarrito extends JFrame {

    private static final long serialVersionUID = 1L;
    
    // PALETA DE COLORES UNIFICADA
    private static final Color COLOR_PRIMARIO = new Color(41, 128, 185); 
    private static final Color COLOR_ACENTO = new Color(46, 204, 113); 
    private static final Color COLOR_PRECIO = new Color(231, 76, 60); 
    private static final Color COLOR_FONDO_CLARO = new Color(236, 240, 241);
    private static final Color COLOR_TEXTO_PRINCIPAL = new Color(51, 51, 51);
    private static final Color COLOR_FONDO_PANELES = Color.WHITE;
    
    // FUENTES ESTANDARIZADAS
    private static final Font FUENTE_TITULO = new Font("Arial", Font.BOLD, 28);
    private static final Font FUENTE_SUBTITULO = new Font("Arial", Font.BOLD, 22);
    private static final Font FUENTE_DESTACADO = new Font("Arial", Font.BOLD, 16);
    private static final Font FUENTE_NORMAL = new Font("Arial", Font.PLAIN, 14);
    private static final Font FUENTE_DESCUENTO = new Font("Arial", Font.BOLD, 15);

    private JPanel pNorte = new JPanel(new GridBagLayout()); 
    private JPanel pSur, pNorteDerechaContenedor, pIzqAbajo; 
    private JTable tabla;
    private JScrollPane scrollTabla;
    private ModeloTablaCompras modeloTabla;
    private List<ItemCarrito> listaItems;
    private JLabel lblTotal, lblDescuento;
    private JButton btnEliminar;
    private JButton btnVaciar;
    private JButton btnPagar;
    private JButton btnSalir;
    public static boolean descuentoAplicado=false;

    public VentanaCarrito(List<ItemCarrito> lista) {
        super(" Carrito de Compras");
        this.listaItems = lista;

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(this);

        getContentPane().setBackground(COLOR_FONDO_CLARO); 
        setBounds(300, 200, 900, 600); 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        pNorte.setBackground(COLOR_FONDO_CLARO); 
        pNorte.setBorder(new EmptyBorder(15, 20, 15, 20)); 
        
        pSur = new JPanel(new BorderLayout(20, 10));
        pSur.setBackground(COLOR_FONDO_PANELES);
        pSur.setBorder(new EmptyBorder(10, 10, 10, 10));

        pIzqAbajo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pIzqAbajo.setBackground(COLOR_FONDO_PANELES);
        
        pNorteDerechaContenedor = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pNorteDerechaContenedor.setBackground(COLOR_FONDO_CLARO);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 0, 5);
        
        getContentPane().add(pNorte, BorderLayout.NORTH);
        getContentPane().add(pSur, BorderLayout.SOUTH);

        // TÍTULO
        JLabel lblTitulo = new JLabel("Mi Carrito  ", SwingConstants.LEFT);
        lblTitulo.setFont(FUENTE_TITULO); 
        lblTitulo.setForeground(COLOR_TEXTO_PRINCIPAL);
        
        gbc.gridx = 0;
        gbc.weightx = 0.5; 
        gbc.anchor = GridBagConstraints.WEST;
        pNorte.add(lblTitulo, gbc);
        
        // ETIQUETA DESCUENTO
        lblDescuento = new JLabel("  ¡20% de descuento aplicado! ");
        lblDescuento.setFont(FUENTE_DESCUENTO); 
        lblDescuento.setForeground(COLOR_ACENTO);
        lblDescuento.setVisible(false);
        
        gbc.gridx = 1;
        gbc.weightx = 0.5; 
        gbc.anchor = GridBagConstraints.CENTER;
        pNorte.add(lblDescuento, gbc);

        // TABLA
        modeloTabla = new ModeloTablaCompras(listaItems);
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(40); 
        tabla.getTableHeader().setFont(FUENTE_NORMAL);
        tabla.getTableHeader().setBackground(new Color(230, 230, 230)); 
        tabla.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        tabla.setGridColor(new Color(230, 230, 230));
        tabla.setIntercellSpacing(new Dimension(0, 1)); 
        
        configurarRenderersYEditors(); 
        
        scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        getContentPane().add(scrollTabla, BorderLayout.CENTER);
        
        tabla.getModel().addTableModelListener(e -> {
            actualizarTotal();
            JFramePrincipal.actualizarContadorCarritoGlobal(); 
        });
        
        // BOTONES ESTANDARIZADOS
        btnEliminar = crearBotonSecundario("  Eliminar Producto");
        btnVaciar = crearBotonSecundario("  Vaciar Carrito");
        btnPagar = crearBotonPrimario("  Proceder al Pago");
        btnSalir = crearBotonTerciario("  Volver al Catálogo");
        
        // LABEL TOTAL CON PRECIO EN ROJO
        lblTotal = new JLabel("Total: ");
        lblTotal.setFont(FUENTE_SUBTITULO); 
        lblTotal.setForeground(COLOR_TEXTO_PRINCIPAL);
        actualizarTotal(); // Esto aplicará el formato con precio en rojo

        pNorteDerechaContenedor.add(lblTotal);
        pNorteDerechaContenedor.add(btnPagar);

        gbc.gridx = 2;
        gbc.weightx = 0.5; 
        gbc.anchor = GridBagConstraints.EAST;
        pNorte.add(pNorteDerechaContenedor, gbc);

        pIzqAbajo.add(btnEliminar);
        pIzqAbajo.add(btnVaciar);
        
        pSur.add(pIzqAbajo, BorderLayout.WEST);
        
        JPanel pSalirContenedor = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pSalirContenedor.setBackground(COLOR_FONDO_PANELES);
        pSalirContenedor.add(btnSalir);
        pSur.add(pSalirContenedor, BorderLayout.EAST);
        
        // LISTENERS
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila != -1) {
                listaItems.remove(fila);
                modeloTabla.fireTableDataChanged();
                actualizarTotal();
                JFramePrincipal.actualizarContadorCarritoGlobal();
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un producto para eliminar");
            }
        });
        
        btnVaciar.addActionListener(e -> vaciarCarrito());
        
        btnPagar.addActionListener(e -> {
            if (listaItems.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El carrito está vacío.");
            } else {
                new VentanaPago(calcularTotal(), this, listaItems); 
            }
        });
        
        btnSalir.addActionListener(e -> dispose());

        setVisible(true);
    }

    // MÉTODOS PARA CREAR BOTONES ESTANDARIZADOS
    private JButton crearBotonPrimario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(FUENTE_DESTACADO);
        btn.setForeground(Color.WHITE);
        btn.setBackground(COLOR_PRIMARIO);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    private JButton crearBotonSecundario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(FUENTE_NORMAL);
        btn.setForeground(COLOR_PRECIO);
        btn.setBackground(COLOR_FONDO_PANELES);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(COLOR_PRECIO, 1));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    private JButton crearBotonTerciario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(FUENTE_NORMAL);
        btn.setForeground(COLOR_TEXTO_PRINCIPAL);
        btn.setBackground(new Color(240, 240, 240));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void configurarRenderersYEditors() {
        BtnCantidadRenderer rendererEditor = new BtnCantidadRenderer(tabla, listaItems, lblTotal, modeloTabla);
        tabla.getColumn("Acciones").setCellRenderer(rendererEditor);
        tabla.getColumn("Acciones").setCellEditor(rendererEditor);
    }
    
    private double calcularTotal() {
        double total = 0;
        for (ItemCarrito item : listaItems) {
            total += item.getCantidad() * item.getProducto().getPrecio();
        }
        if(descuentoAplicado) {
            total = total * 0.80;
            lblDescuento.setVisible(true);
        }
        return total;
    }

    private void vaciarCarrito() {
        if (listaItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El carrito ya está vacío.");
            return;
        }
        listaItems.clear();
        modeloTabla = new ModeloTablaCompras(listaItems);
        tabla.setModel(modeloTabla);
        configurarRenderersYEditors(); 
        actualizarTotal();
        JFramePrincipal.actualizarContadorCarritoGlobal();
    }

    public void actualizarTabla() {
        modeloTabla = new ModeloTablaCompras(listaItems);
        tabla.setModel(modeloTabla);
        configurarRenderersYEditors(); 
    }

    public void actualizarTotal() {
        double total = calcularTotal();
        // Formato con precio en rojo
        lblTotal.setText("<html>Total: <span style='color: rgb(231,76,60); font-weight: bold;'>" + 
                        String.format("%.2f", total) + "€</span></html>");
        if(descuentoAplicado) {
            lblDescuento.setVisible(true);
        } else {
            lblDescuento.setVisible(false);
        }
    }

    public void agregarProducto(Producto producto, int cantidad) {
        boolean encontrado = false;
        for (ItemCarrito item : listaItems) {
            if (item.getProducto().getId() == producto.getId()) {
                item.setCantidad(item.getCantidad() + cantidad);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            listaItems.add(new ItemCarrito(producto, cantidad, "M")); 
        }
        modeloTabla.fireTableDataChanged();
    }
    
    public void setDescuento(boolean valor) {
        descuentoAplicado = valor;
        actualizarTotal();
    }
}
