package gui;

import domain.Producto;
import domain.Opinion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class JPanelCatalogo extends JPanel {

	private static final long serialVersionUID = 1L;
    
	private List<Producto> listaProductosCompleta;
    
	private JPanel panelTarjetasProductos;
	private JPanel panelDetallesProducto;
	private JPanel panelContenidoDetalle;
    
	private JComboBox<String> cbxFiltroTalla;
	private JTextField txtFiltro; 
    
	private JButton btnAnadirCarrito;
	private JComboBox<String> comboTallaDetalle;
    private JTextField txtCantidadDetalle;
    private JLabel lblStockDetalle;
    
    private Producto productoDetalleSeleccionado = null;
    
	private static final Color COLOR_PRIMARIO = new Color(30, 144, 255);
	private static final Color COLOR_FONDO_CLARO = Color.WHITE;
	private static final Color COLOR_FONDO_OSCURO = new Color(245, 245, 245);
    
    private final String[] TALLAS_VALIDAS_FILTRO = {"TODAS", "XS", "S", "M", "L", "XL"};
    private final String[] TALLAS_ORDENADAS = {"XS", "S", "M", "L", "XL"};

	public JPanelCatalogo(List<Producto> productos) {
        this.listaProductosCompleta = productos;
        
		this.setLayout(new BorderLayout(10, 10)); 
		this.setBackground(COLOR_FONDO_OSCURO);
        
        panelTarjetasProductos = new JPanel(new GridLayout(0, 3, 20, 20));
        panelTarjetasProductos.setBackground(COLOR_FONDO_OSCURO);
        
        JScrollPane scrollCatalogo = new JScrollPane(panelTarjetasProductos);
        scrollCatalogo.getVerticalScrollBar().setUnitIncrement(16);
        scrollCatalogo.setBorder(null);
        
        JPanel pSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pSuperior.setBackground(COLOR_FONDO_OSCURO);
        
        cbxFiltroTalla = new JComboBox<>(TALLAS_VALIDAS_FILTRO);
        
        pSuperior.add(new JLabel("Filtrar por Talla:"));
        pSuperior.add(cbxFiltroTalla);
        cbxFiltroTalla.addActionListener(e -> filtrarPorTalla());
        
        this.add(pSuperior, BorderLayout.NORTH);
        this.add(scrollCatalogo, BorderLayout.CENTER);
        
        inicializarPanelDetalles();
        this.add(panelDetallesProducto, BorderLayout.WEST);
        
        cargarProductos(listaProductosCompleta);
	}
    
    private void inicializarPanelDetalles() {
        panelDetallesProducto = new JPanel(new BorderLayout());
        panelDetallesProducto.setPreferredSize(new Dimension(300, 0));
        panelDetallesProducto.setBackground(COLOR_FONDO_CLARO);
        panelDetallesProducto.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_PRIMARIO, 2), "Detalles del Producto", 
            TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("SansSerif", Font.BOLD, 20), COLOR_PRIMARIO));
        
        panelContenidoDetalle = new JPanel(new BorderLayout());
        panelContenidoDetalle.setBackground(COLOR_FONDO_CLARO);
        panelDetallesProducto.add(panelContenidoDetalle, BorderLayout.CENTER);
        
        comboTallaDetalle = new JComboBox<>();
        txtCantidadDetalle = new JTextField("1", 3);
        txtCantidadDetalle.setHorizontalAlignment(SwingConstants.CENTER);
        lblStockDetalle = new JLabel("Stock: --");
        
        btnAnadirCarrito = new JButton("Añadir al Carrito");
        btnAnadirCarrito.setBackground(COLOR_PRIMARIO);
        btnAnadirCarrito.setForeground(Color.WHITE);
        
        btnAnadirCarrito.addActionListener(e -> {
            if (productoDetalleSeleccionado != null) {
                try {
                    String talla = (String) comboTallaDetalle.getSelectedItem();
                    int cantidad = Integer.parseInt(txtCantidadDetalle.getText());
                    
                    if (talla == null || cantidad <= 0) {
                        return; 
                    }
                    
                    if (cantidad > productoDetalleSeleccionado.getStock(talla)) {
                        System.out.println("Error: Cantidad excede el stock disponible para la talla " + talla);
                        return;
                    }
                    
                    JFramePrincipal.agregarItemAlCarrito(productoDetalleSeleccionado, cantidad, talla); 
                    System.out.println("Añadido: " + productoDetalleSeleccionado.getNombre() + " (Talla: " + talla + ", Cant: " + cantidad + ")");
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
            JLabel lblSinResultados = new JLabel("No se encontraron productos.", SwingConstants.CENTER);
            lblSinResultados.setFont(new Font("SansSerif", Font.ITALIC, 16));
            
            panelTarjetasProductos.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));
            panelTarjetasProductos.add(lblSinResultados);
        } else {
            panelTarjetasProductos.setLayout(new GridLayout(0, 4, 15, 15));
            
            for (Producto p : listaParaMostrar) {
                String archivoImagen = obtenerNombreImagen(p.getNombre());
                panelTarjetasProductos.add(new ProductCardPanel(p, archivoImagen)); 
            }
        }
        
        panelTarjetasProductos.revalidate();
        panelTarjetasProductos.repaint();
    }
    
    private String obtenerNombreImagen(String nombreProducto) {
        if (nombreProducto.contains("Basica Blanca")) return "camiseta_blanca.png";
        if (nombreProducto.contains("Honda NSX-R")) return "camiseta_honda.png"; 
        if (nombreProducto.contains("Mickey Mouse")) return "camiseta_mickey.png";
        if (nombreProducto.contains("Tom & Jerry")) return "camiseta_tom-jerry.png";
        if (nombreProducto.contains("KTM")) return "camiseta_ktm.png";
        if (nombreProducto.contains("Racing Team")) return "camiseta_racing.png";
        if (nombreProducto.contains("Friday Gaming Club")) return "camiseta_friday.png";
        if (nombreProducto.contains("Cargo Baggy")) return "pantalon_cargo.png";
        if (nombreProducto.contains("Tailoring Wide Leg")) return "pantalon_tailoring.png";
        if (nombreProducto.contains("Jogger Relaxed Fit")) return "pantalon_jogger.png";
        if (nombreProducto.contains("Chino Skinny Fit")) return "pantalon_chino.png";
        if (nombreProducto.contains("Sudadera Capucha Clasica")) return "sudadera_clasica.png";
        if (nombreProducto.contains("Abrigo Lana")) return "abrigo_lana.png";
        if (nombreProducto.contains("Botines Cuero")) return "botines_c.png";
        if (nombreProducto.contains("Gorra Béisbol")) return "gorra_logo.png";
        
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
			setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			setBackground(COLOR_FONDO_CLARO);
			
			JPanel panelImagen = new JPanel(new BorderLayout());
			panelImagen.setPreferredSize(new Dimension(0, 150));
			panelImagen.setBackground(COLOR_FONDO_CLARO);
            
            JLabel lblImagen = new JLabel();
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource("/images/" + archivoImagen));
                Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                lblImagen.setIcon(new ImageIcon(img));
                lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
            } catch (Exception e) {
                lblImagen.setText("No Image");
            }
            panelImagen.add(lblImagen, BorderLayout.CENTER);
            
			JPanel panelInfo = new JPanel(new GridLayout(2, 1));
			panelInfo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            
			JLabel lblNombre = new JLabel("<html><b>" + p.getNombre() + "</b></html>");
			lblNombre.setFont(new Font("SansSerif", Font.PLAIN, 14));
            
			JLabel lblPrecio = new JLabel(String.format("%.2f €", p.getPrecio()));
			lblPrecio.setFont(new Font("SansSerif", Font.BOLD, 16));
			lblPrecio.setForeground(new Color(255, 69, 0));
            
			panelInfo.add(lblNombre);
			panelInfo.add(lblPrecio);
			
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
                    setBorder(BorderFactory.createLineBorder(COLOR_PRIMARIO, 2));
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                }
            });
		}
		
		private void mostrarDetallesProducto() {
            panelContenidoDetalle.removeAll();
            panelContenidoDetalle.setLayout(new BorderLayout(10, 10));
            
            JPanel pInfo = new JPanel(new GridLayout(0, 1, 0, 5));
            pInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            pInfo.setBackground(COLOR_FONDO_CLARO);
            
            JLabel lblNombreDetalle = new JLabel("<html><h2>" + producto.getNombre() + "</h2></html>");
            JLabel lblPrecioDetalle = new JLabel(String.format("<html><b>Precio: <font color='rgb(255, 69, 0)'>%.2f €</font></b></html>", producto.getPrecio()));
            JLabel lblDescripcionDetalle = new JLabel("<html><p>" + producto.getDescripcion() + "</p></html>");
            
            pInfo.add(lblNombreDetalle);
            pInfo.add(lblPrecioDetalle);
            pInfo.add(new JSeparator(SwingConstants.HORIZONTAL)); 
            pInfo.add(lblDescripcionDetalle);
            
            JPanel pControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
            pControles.setBackground(COLOR_FONDO_CLARO);
            pControles.setBorder(BorderFactory.createTitledBorder("Selección"));
            
            comboTallaDetalle.removeAllItems();
            
            for (String talla : TALLAS_ORDENADAS) {
                int stock = producto.getStock(talla);
                if (stock > 0) {
                    comboTallaDetalle.addItem(talla);
                }
            }
            
            comboTallaDetalle.addActionListener(e -> {
                String talla = (String) comboTallaDetalle.getSelectedItem();
                int stock = (talla != null) ? producto.getStock(talla) : 0;
                
                lblStockDetalle.setText("Stock: " + stock);
                btnAnadirCarrito.setEnabled(stock > 0);
                txtCantidadDetalle.setText("1");
            });
            
            pControles.add(new JLabel("Talla:"));
            pControles.add(comboTallaDetalle);
            pControles.add(new JLabel("Cantidad:"));
            pControles.add(txtCantidadDetalle);
            pControles.add(lblStockDetalle);
            
            if (comboTallaDetalle.getItemCount() > 0) {
                comboTallaDetalle.setSelectedIndex(0);
            } else {
                lblStockDetalle.setText("Stock: AGOTADO");
                btnAnadirCarrito.setEnabled(false);
            }
            
            JPanel pBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pBoton.setBackground(COLOR_FONDO_CLARO);
            pBoton.add(btnAnadirCarrito);
            
            panelContenidoDetalle.add(pInfo, BorderLayout.NORTH);
            panelContenidoDetalle.add(pControles, BorderLayout.CENTER);
            panelContenidoDetalle.add(pBoton, BorderLayout.SOUTH);
            
            TitledBorder border = (TitledBorder) panelDetallesProducto.getBorder();
            border.setTitle(producto.getNombre());
            
            panelDetallesProducto.revalidate();
			panelDetallesProducto.repaint();
		}
	}
}