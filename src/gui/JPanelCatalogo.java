package gui;

import domain.Producto;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class JPanelCatalogo extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private List<Producto> listaProductosCompleta;
    private JPanel panelTarjetasProductos;
    private JPanel panelDetallesProducto;
    private JPanel panelContenidoDetalle;
    private JComboBox<String> cbxFiltroTalla;
    private JButton btnAnadirCarrito;
    private JComboBox<String> comboTallaDetalle;
    private JSpinner spinnerCantidad;
    private JLabel lblStockDetalle;
    private Producto productoDetalleSeleccionado = null;
    
    // Paleta de colores moderna
    private static final Color COLOR_PRIMARIO = new Color(41, 128, 185);
    private static final Color COLOR_SECUNDARIO = new Color(52, 152, 219);
    private static final Color COLOR_ACENTO = new Color(46, 204, 113);
    private static final Color COLOR_PRECIO = new Color(231, 76, 60);
    private static final Color COLOR_FONDO_CLARO = new Color(236, 240, 241);
    private static final Color COLOR_FONDO_OSCURO = Color.WHITE;
    
    private final String[] TALLAS_VALIDAS_FILTRO = {"TODAS", "XS", "S", "M", "L", "XL"};
    private final String[] TALLAS_ORDENADAS = {"XS", "S", "M", "L", "XL"};
    
    public JPanelCatalogo(List<Producto> productos) {
        this.listaProductosCompleta = productos;
        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(COLOR_FONDO_CLARO);
        
        panelTarjetasProductos = new JPanel(new GridLayout(0, 3, 25, 25));
        panelTarjetasProductos.setBackground(COLOR_FONDO_CLARO);
        panelTarjetasProductos.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JScrollPane scrollCatalogo = new JScrollPane(panelTarjetasProductos);
        scrollCatalogo.getVerticalScrollBar().setUnitIncrement(16);
        scrollCatalogo.setBorder(null);
        
        // Panel superior con gradiente
        JPanel pSuperior = crearPanelSuperior();
        
        this.add(pSuperior, BorderLayout.NORTH);
        this.add(scrollCatalogo, BorderLayout.CENTER);
        
        inicializarPanelDetalles();
        this.add(panelDetallesProducto, BorderLayout.EAST);
        
        cargarProductos(listaProductosCompleta);
    }
    
    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, COLOR_PRIMARIO, getWidth(), 0, COLOR_SECUNDARIO);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(0, 70));
        
        JLabel lblTitulo = new JLabel("  📚 CATÁLOGO DE PRODUCTOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        panelFiltros.setOpaque(false);
        
        JLabel lblFiltro = new JLabel("Filtrar por Talla:");
        lblFiltro.setFont(new Font("Arial", Font.BOLD, 14));
        lblFiltro.setForeground(Color.WHITE);
        
        cbxFiltroTalla = new JComboBox<>(TALLAS_VALIDAS_FILTRO);
        cbxFiltroTalla.setFont(new Font("Arial", Font.PLAIN, 13));
        cbxFiltroTalla.setPreferredSize(new Dimension(100, 35));
        cbxFiltroTalla.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cbxFiltroTalla.addActionListener(e -> filtrarPorTalla());
        
        panelFiltros.add(lblFiltro);
        panelFiltros.add(cbxFiltroTalla);
        
        panel.add(lblTitulo, BorderLayout.WEST);
        panel.add(panelFiltros, BorderLayout.EAST);
        
        return panel;
    }
    
    private void inicializarPanelDetalles() {
        panelDetallesProducto = new JPanel(new BorderLayout());
        panelDetallesProducto.setPreferredSize(new Dimension(350, 0));
        panelDetallesProducto.setBackground(COLOR_FONDO_OSCURO);
        panelDetallesProducto.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 2, 0, 0, COLOR_PRIMARIO),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel lblTituloDetalle = new JLabel("DETALLES DEL PRODUCTO");
        lblTituloDetalle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTituloDetalle.setForeground(COLOR_PRIMARIO);
        lblTituloDetalle.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        panelContenidoDetalle = new JPanel(new BorderLayout());
        panelContenidoDetalle.setBackground(COLOR_FONDO_OSCURO);
        
        JLabel lblMensajeInicial = new JLabel(
            "<html><div style='text-align: center; padding: 20px;'>" +
            "👆<br><br>Haz clic en un producto<br>para ver sus detalles</div></html>"
        );
        lblMensajeInicial.setFont(new Font("Arial", Font.PLAIN, 14));
        lblMensajeInicial.setForeground(new Color(149, 165, 166));
        lblMensajeInicial.setHorizontalAlignment(SwingConstants.CENTER);
        panelContenidoDetalle.add(lblMensajeInicial, BorderLayout.CENTER);
        
        panelDetallesProducto.add(lblTituloDetalle, BorderLayout.NORTH);
        panelDetallesProducto.add(panelContenidoDetalle, BorderLayout.CENTER);
        
        comboTallaDetalle = new JComboBox<>();
        comboTallaDetalle.setFont(new Font("Arial", Font.PLAIN, 13));
        
        spinnerCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        spinnerCantidad.setFont(new Font("Arial", Font.PLAIN, 13));
        ((JSpinner.DefaultEditor) spinnerCantidad.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
        
        lblStockDetalle = new JLabel("Stock: --");
        lblStockDetalle.setFont(new Font("Arial", Font.BOLD, 12));
        
        btnAnadirCarrito = new JButton("🛒 AÑADIR AL CARRITO");
        btnAnadirCarrito.setFont(new Font("Arial", Font.BOLD, 14));
        btnAnadirCarrito.setBackground(COLOR_ACENTO);
        btnAnadirCarrito.setForeground(Color.WHITE);
        btnAnadirCarrito.setFocusPainted(false);
        btnAnadirCarrito.setBorderPainted(false);
        btnAnadirCarrito.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAnadirCarrito.setPreferredSize(new Dimension(0, 45));
        
        btnAnadirCarrito.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (btnAnadirCarrito.isEnabled()) {
                    btnAnadirCarrito.setBackground(new Color(39, 174, 96));
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btnAnadirCarrito.setBackground(COLOR_ACENTO);
            }
        });
        
        btnAnadirCarrito.addActionListener(e -> {
            if (productoDetalleSeleccionado != null) {
                try {
                    String talla = (String) comboTallaDetalle.getSelectedItem();
                    int cantidad = (Integer) spinnerCantidad.getValue();
                    if (talla == null || cantidad <= 0) {
                        return;
                    }
                    if (cantidad > productoDetalleSeleccionado.getStock(talla)) {
                        System.out.println("Error: Cantidad excede el stock disponible para la talla " + talla);
                        return;
                    }
                    
                    // Llama al método estático que también decrementa el stock en JFramePrincipal
                    JFramePrincipal.agregarItemAlCarrito(productoDetalleSeleccionado, cantidad, talla);
                    
                    System.out.println("Añadido: " + productoDetalleSeleccionado.getNombre() + 
                                     " (Talla: " + talla + ", Cant: " + cantidad + ")");
                                     
                    
                    
                } catch (NumberFormatException ex) {
                }
            }
        });
    }
    

	private void filtrarPorTalla() {
        String tallaSeleccionada = (String) cbxFiltroTalla.getSelectedItem();
        List<Producto> productosFiltrados;
        if (tallaSeleccionada == null || tallaSeleccionada.equals("TODAS")) {
            productosFiltrados = listaProductosCompleta;
        } else {
            productosFiltrados = listaProductosCompleta.stream()
                .filter(p -> p.getStock(tallaSeleccionada) > 0)
                .collect(Collectors.toList());
        }
        cargarProductos(productosFiltrados);
    }
    
    private void cargarProductos(List<Producto> listaParaMostrar) {
        panelTarjetasProductos.removeAll();
        if (listaParaMostrar.isEmpty()) {
            JLabel lblSinResultados = new JLabel(
                "<html><div style='text-align: center;'>😔<br><br>No se encontraron productos<br>" +
                "con los filtros seleccionados</div></html>", SwingConstants.CENTER
            );
            lblSinResultados.setFont(new Font("Arial", Font.PLAIN, 16));
            lblSinResultados.setForeground(new Color(149, 165, 166));
            panelTarjetasProductos.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));
            panelTarjetasProductos.add(lblSinResultados);
        } else {
            panelTarjetasProductos.setLayout(new GridLayout(0, 3, 25, 25));
            for (Producto p : listaParaMostrar) {
                String archivoImagen = obtenerNombreImagen(p.getNombre());
                panelTarjetasProductos.add(new ProductCardPanel(p, archivoImagen));
            }
        }
        panelTarjetasProductos.revalidate();
        panelTarjetasProductos.repaint();
    }
    
    private String obtenerNombreImagen(String nombreProducto) {
        if (nombreProducto.contains("BASICA BLANCA")) return "camiseta_blanca.png";
        if (nombreProducto.contains("HONDA NSX-R")) return "camiseta_honda.png";
        if (nombreProducto.contains("MICKEY MOUSE")) return "camiseta_mickey.png";
        if (nombreProducto.contains("TOM & JERRY")) return "camiseta_tom-jerry.png";
        if (nombreProducto.contains("KTM")) return "camiseta_ktm.png";
        if (nombreProducto.contains("RACING TEAM")) return "camiseta_racing.png";
        if (nombreProducto.contains("FRIDAY GAMING CLUB")) return "camiseta_friday.png";
        if (nombreProducto.contains("CARGO BAGGY")) return "pantalon_cargo.png";
        if (nombreProducto.contains("TAILORING WIDE LEG")) return "pantalon_tailoring.png";
        if (nombreProducto.contains("JOGGER RELAXED FIT")) return "pantalon_jogger.png";
        if (nombreProducto.contains("CHINO SKINNY FIT")) return "pantalon_chino.png";
        if (nombreProducto.contains("SUDADERA CAPUCHA CLASICA")) return "sudadera_clasica.png";
        if (nombreProducto.contains("POLO CUELLO CONTRASTE")) return "sudadera_contraste.png";
        if (nombreProducto.contains("PARCHES BANDAS")) return "sudadera_parches.png";
        if (nombreProducto.contains("CAZADORA ACOLCHADA")) return "cazadora_acolchada.png";
        if (nombreProducto.contains("CHAQUETÓN REGULAR")) return "chaqueta_regularfit.png";
        return "default.png";
    }
    
    private class ProductCardPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private Producto producto;
        private String archivoImagen;
        
        public ProductCardPanel(Producto p, String archivoImagen) {
            this.producto = p;
            this.archivoImagen = archivoImagen;
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(0, 0, 0, 0)
            ));
            setBackground(COLOR_FONDO_OSCURO);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            JPanel panelImagen = new JPanel(new BorderLayout());
            panelImagen.setPreferredSize(new Dimension(0, 200));
            panelImagen.setBackground(new Color(250, 250, 250));
            panelImagen.setBorder(new EmptyBorder(15, 15, 15, 15));
            
            JLabel lblImagen = new JLabel();
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource("/images/" + archivoImagen));
                Image img = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                lblImagen.setIcon(new ImageIcon(img));
                lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
            } catch (Exception e) {
                lblImagen.setText("📷");
                lblImagen.setFont(new Font("Arial", Font.PLAIN, 48));
                lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
                lblImagen.setForeground(new Color(189, 195, 199));
            }
            
            panelImagen.add(lblImagen, BorderLayout.CENTER);
            
            JPanel panelInfo = new JPanel();
            panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
            panelInfo.setBackground(COLOR_FONDO_OSCURO);
            panelInfo.setBorder(new EmptyBorder(12, 15, 15, 15));
            
            JLabel lblNombre = new JLabel("<html><div style='width: 200px;'>" + p.getNombre() + "</div></html>");
            lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
            lblNombre.setForeground(new Color(44, 62, 80));
            lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JPanel separador = new JPanel();
            separador.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
            separador.setBackground(new Color(230, 230, 230));
            separador.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JLabel lblPrecio = new JLabel(String.format("%.2f €", p.getPrecio()));
            lblPrecio.setFont(new Font("Arial", Font.BOLD, 20));
            lblPrecio.setForeground(COLOR_PRECIO);
            lblPrecio.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JLabel lblDisponible = new JLabel("✓ Disponible");
            lblDisponible.setFont(new Font("Arial", Font.PLAIN, 11));
            lblDisponible.setForeground(COLOR_ACENTO);
            lblDisponible.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            panelInfo.add(lblNombre);
            panelInfo.add(Box.createVerticalStrut(8));
            panelInfo.add(separador);
            panelInfo.add(Box.createVerticalStrut(8));
            panelInfo.add(lblPrecio);
            panelInfo.add(Box.createVerticalStrut(3));
            panelInfo.add(lblDisponible);
            
            add(panelImagen, BorderLayout.NORTH);
            add(panelInfo, BorderLayout.CENTER);
            
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    productoDetalleSeleccionado = producto;
                    mostrarDetallesProducto();
                }
                
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_SECUNDARIO, 2),
                        new EmptyBorder(0, 0, 0, 0)
                    ));
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                        new EmptyBorder(0, 0, 0, 0)
                    ));
                }
            });
        }
        
        
        private void mostrarDetallesProducto() {
            panelContenidoDetalle.removeAll();
            panelContenidoDetalle.setLayout(new BorderLayout(0, 10)); 
            
            
            ActionListener tallaChangeListener = e -> {
                String talla = (String) comboTallaDetalle.getSelectedItem();
                int stock = (talla != null) ? producto.getStock(talla) : 0;
                
                if (stock > 0) {
                     lblStockDetalle.setText("Stock: " + stock + " unid.");
                     lblStockDetalle.setForeground(new Color(127, 140, 141));
                } else {
                     lblStockDetalle.setText("AGOTADO");
                     lblStockDetalle.setForeground(COLOR_PRECIO);
                }
                
                btnAnadirCarrito.setEnabled(stock > 0);
                spinnerCantidad.setValue(1);
                ((SpinnerNumberModel) spinnerCantidad.getModel()).setMaximum(stock);
            };
            
            
            String tallaPreseleccionada = (String) comboTallaDetalle.getSelectedItem();
            
            
            JPanel pInfo = new JPanel();
            pInfo.setLayout(new BoxLayout(pInfo, BoxLayout.Y_AXIS));
            pInfo.setBackground(COLOR_FONDO_OSCURO);
            pInfo.setBorder(new EmptyBorder(0, 0, 5, 0));
            
            JLabel lblNombreDetalle = new JLabel("<html><div style='width: 280px;'><b>" + 
                                                 producto.getNombre() + "</b></div></html>");
            lblNombreDetalle.setFont(new Font("Arial", Font.BOLD, 16));
            lblNombreDetalle.setForeground(new Color(44, 62, 80));
            lblNombreDetalle.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JLabel lblPrecioDetalle = new JLabel(String.format("%.2f €", producto.getPrecio()));
            lblPrecioDetalle.setFont(new Font("Arial", Font.BOLD, 24));
            lblPrecioDetalle.setForeground(COLOR_PRECIO);
            lblPrecioDetalle.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JLabel lblDescripcionDetalle = new JLabel(
                "<html><div style='width: 280px; margin-top: 10px;'>" + 
                producto.getDescripcion() + "</div></html>"
            );
            lblDescripcionDetalle.setFont(new Font("Arial", Font.PLAIN, 12));
            lblDescripcionDetalle.setForeground(new Color(127, 140, 141));
            lblDescripcionDetalle.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            pInfo.add(lblNombreDetalle);
            pInfo.add(Box.createVerticalStrut(10));
            pInfo.add(lblPrecioDetalle);
            pInfo.add(Box.createVerticalStrut(15));
            pInfo.add(lblDescripcionDetalle);
            
            
            
            JPanel pControles = new JPanel();
            pControles.setLayout(new BoxLayout(pControles, BoxLayout.Y_AXIS));
            pControles.setBackground(COLOR_FONDO_CLARO);
            pControles.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(8, 15, 8, 15)
            ));
            
            
            JPanel pTalla = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            pTalla.setBackground(COLOR_FONDO_CLARO);
            
            JLabel lblTalla = new JLabel("Talla:");
            lblTalla.setPreferredSize(new Dimension(70, 30)); 
            lblTalla.setFont(new Font("Arial", Font.BOLD, 13));
            
           
            comboTallaDetalle.removeAllItems();
            
            boolean hayStockTotal = false;
            for (String talla : TALLAS_ORDENADAS) {
                int stock = producto.getStock(talla);
                if (stock > 0) {
                    comboTallaDetalle.addItem(talla);
                    hayStockTotal = true;
                }
            }
            
            comboTallaDetalle.setPreferredSize(new Dimension(140, 30));
            
            
            if (tallaPreseleccionada != null && producto.getStock(tallaPreseleccionada) > 0) {
                comboTallaDetalle.setSelectedItem(tallaPreseleccionada);
            } else if (comboTallaDetalle.getItemCount() > 0) {
                comboTallaDetalle.setSelectedIndex(0);
            }
            
            
            for (ActionListener al : comboTallaDetalle.getActionListeners()) {
                comboTallaDetalle.removeActionListener(al);
            }
            comboTallaDetalle.addActionListener(tallaChangeListener);
            
            pTalla.add(lblTalla);
            pTalla.add(comboTallaDetalle);
            
           
            JPanel pCantidad = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            pCantidad.setBackground(COLOR_FONDO_CLARO);
            
            JLabel lblCantidad = new JLabel("Cantidad:");
            lblCantidad.setPreferredSize(new Dimension(70, 30)); 
            lblCantidad.setFont(new Font("Arial", Font.BOLD, 13));
            
           
            spinnerCantidad.setPreferredSize(new Dimension(100, 40));
            
            pCantidad.add(lblCantidad);
            pCantidad.add(spinnerCantidad);
            
            lblStockDetalle.setFont(new Font("Arial", Font.ITALIC, 11));
            lblStockDetalle.setBorder(new EmptyBorder(0, 10, 0, 0)); 
            pCantidad.add(lblStockDetalle); 
            
            pControles.add(pTalla);
            pControles.add(Box.createVerticalStrut(4));
            pControles.add(pCantidad);
            
            
            if (hayStockTotal) {
                
                tallaChangeListener.actionPerformed(null);
            } else {
                lblStockDetalle.setText("AGOTADO");
                lblStockDetalle.setForeground(COLOR_PRECIO);
                btnAnadirCarrito.setEnabled(false);
                ((SpinnerNumberModel) spinnerCantidad.getModel()).setMaximum(0);
            }
            
            
            JPanel pBoton = new JPanel(new BorderLayout());
            pBoton.setBackground(COLOR_FONDO_OSCURO);
            pBoton.add(btnAnadirCarrito, BorderLayout.CENTER);
            
            panelContenidoDetalle.add(pInfo, BorderLayout.NORTH);
            panelContenidoDetalle.add(pControles, BorderLayout.CENTER);
            panelContenidoDetalle.add(pBoton, BorderLayout.SOUTH);
            
            panelDetallesProducto.revalidate();
            panelDetallesProducto.repaint();
        }
    }
}