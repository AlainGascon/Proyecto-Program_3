package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent; 

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame; 
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import domain.Producto; 

public class JPanelCatalogo extends JPanel { 

	private static final long serialVersionUID = 1L;
	
	private JPanel panelTarjetasProductos;
	private JPanel panelDetallesProducto;
	private JPanel panelContenidoDetalle; 
	
	private JTextField txtFiltro;
	private JButton btnAnadirCarrito;
	
	private ProductCardPanel productoSeleccionado = null; 
    private int cantidad = 1; 

	private static final Color COLOR_PRIMARIO = new Color(30, 144, 255);
	private static final Color COLOR_FONDO_CLARO = Color.WHITE;
	private static final Color COLOR_FONDO_OSCURO = new Color(245, 245, 245);
	private static final Color COLOR_HOVER = new Color(173, 216, 230); 

	public JPanelCatalogo() {
		
		this.setLayout(new BorderLayout()); 
		
		btnAnadirCarrito = new JButton("🛒 Añadir al Carrito");
		btnAnadirCarrito.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnAnadirCarrito.setBackground(new Color(250, 179, 113));
		btnAnadirCarrito.setForeground(Color.BLACK);
		btnAnadirCarrito.setFocusPainted(false);
		btnAnadirCarrito.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
		btnAnadirCarrito.setEnabled(false);
		
		
		
		JPanel pNorte = new JPanel(new BorderLayout(10, 0));
		pNorte.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		JLabel lblTitulo = new JLabel("Catálogo de Productos", JLabel.LEFT);
		lblTitulo.setBackground(new Color(250, 179, 113)); 
		lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
		
		pNorte.add(lblTitulo, BorderLayout.WEST);
		
		
		this.add(pNorte, BorderLayout.NORTH); 
		
		JPanel panelIzquierda = new JPanel(new BorderLayout(0, 15));
		panelIzquierda.setBackground(COLOR_FONDO_OSCURO);
		panelIzquierda.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
		
		panelIzquierda.add(panelProductos(), BorderLayout.CENTER);
		
		
		panelDetallesProducto = panelDetallesProducto();
		panelDetallesProducto.setPreferredSize(new Dimension(0, 250));
		panelIzquierda.add(panelDetallesProducto, BorderLayout.SOUTH);

		add(panelIzquierda, BorderLayout.CENTER); 

		JPanel panelDerechaVacio = new JPanel(new BorderLayout());
		panelDerechaVacio.setBackground(COLOR_FONDO_CLARO);
		TitledBorder bordeInfo = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1), "🛒 Cesta / Filtros Avanzados");
		bordeInfo.setTitleFont(new Font("SansSerif", Font.BOLD, 14));
		bordeInfo.setTitleColor(Color.DARK_GRAY);
		panelDerechaVacio.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10), bordeInfo));
		panelDerechaVacio.setPreferredSize(new Dimension(350, 0)); 

		
		JLabel lblInfoAd = new JLabel("<html><div style='text-align: center; color: gray;'>Aquí irían los filtros de talla, color o el resumen de la cesta de compra.</div></html>", JLabel.CENTER);
		lblInfoAd.setFont(new Font("SansSerif", Font.ITALIC, 14));
		panelDerechaVacio.add(lblInfoAd, BorderLayout.CENTER);
		
		add(panelDerechaVacio, BorderLayout.EAST);
		
        
        btnAnadirCarrito.addActionListener(e -> {
			if (productoSeleccionado != null) {
				Producto p = new Producto(
                    productoSeleccionado.hashCode(), 
                    productoSeleccionado.nombreProducto, 
                    "Descripción por defecto", 
                    productoSeleccionado.precio, 
                    "M", "Negro", 1, "Ropa", "Marca", true
                );
				JFramePrincipal.agregarItemAlCarrito(p, cantidad); 
				System.out.println("Producto añadido: " + productoSeleccionado.nombreProducto);
			}
		});
	}
	
	public JPanel panelProductos() {

	    panelTarjetasProductos = new JPanel(new GridLayout(0, 4, 15, 15)); 
	    panelTarjetasProductos.setBackground(COLOR_FONDO_CLARO);
	    panelTarjetasProductos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    cargarDatosDeEjemplo(); 

	    JScrollPane scrollPaneProductos = new JScrollPane(panelTarjetasProductos);
	    scrollPaneProductos.setBorder(BorderFactory.createEmptyBorder());
	    
	    this.txtFiltro = new JTextField(15); 
	    this.txtFiltro.setFont(new Font("SansSerif", Font.PLAIN, 14));
	    this.txtFiltro.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARIO, 1));

	    
	    JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
	    panelFiltro.setBackground(COLOR_FONDO_CLARO);
	    
	    
	    JLabel lblFiltro = new JLabel("🔍 Buscar: ");
	    lblFiltro.setFont(new Font("SansSerif", Font.BOLD, 14));
	    panelFiltro.add(lblFiltro);
	    panelFiltro.add(txtFiltro);
	    
	    
	    JLabel lblTalla = new JLabel("Talla:");
	    lblTalla.setFont(new Font("SansSerif", Font.BOLD, 14));
	    String[] tallas = {"-", "XS", "S", "M", "L", "XL", "XXL"};
	    JComboBox<String> comboTalla = new JComboBox<>(tallas);
	    comboTalla.setFont(new Font("SansSerif", Font.PLAIN, 14));
	    
	    panelFiltro.add(lblTalla);
	    panelFiltro.add(comboTalla);

	    
	    JLabel lblTipo = new JLabel("Tipo:");
	    lblTipo.setFont(new Font("SansSerif", Font.BOLD, 14));
	    String[] tipos = {"-", "Pantalones", "Camisetas", "Abrigos", "Calzado", "Accesorios"};
	    JComboBox<String> comboTipo = new JComboBox<>(tipos);
	    comboTipo.setFont(new Font("SansSerif", Font.PLAIN, 14));

	    panelFiltro.add(lblTipo);
	    panelFiltro.add(comboTipo);

	    
	    JPanel panelProductosContenedor = new JPanel(new BorderLayout(5, 5));
	    
	    TitledBorder bordeProductos = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(COLOR_PRIMARIO, 2), "🛒 PRODUCTOS EN CATÁLOGO");
	    bordeProductos.setTitleFont(new Font("SansSerif", Font.BOLD, 16));
	    bordeProductos.setTitleColor(COLOR_PRIMARIO); 
	    
	    panelProductosContenedor.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10), bordeProductos));
	    panelProductosContenedor.setBackground(COLOR_FONDO_CLARO);
	    
	    panelProductosContenedor.add(scrollPaneProductos, BorderLayout.CENTER);
	    panelProductosContenedor.add(panelFiltro, BorderLayout.NORTH); 
	    
	    return panelProductosContenedor;
	}


	
	public JPanel panelDetallesProducto() {
		
		JPanel panelDetalle = new JPanel(new BorderLayout(10, 10));
		TitledBorder bordeCaracteristicas = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1), "📝 DETALLES Y COMPRA DEL ARTÍCULO");
		bordeCaracteristicas.setTitleFont(new Font("SansSerif", Font.BOLD, 14));
		bordeCaracteristicas.setTitleColor(Color.DARK_GRAY);
		panelDetalle.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10), bordeCaracteristicas));
		panelDetalle.setBackground(COLOR_FONDO_CLARO);
		
		panelContenidoDetalle = new JPanel(new BorderLayout());
		panelContenidoDetalle.setBackground(COLOR_FONDO_CLARO);
		
		JLabel lblInfo = new JLabel("<html><div style='text-align: center; color: #555; padding-top:20px;'>Haga clic en un producto para ver la **Descripción**, las **Tallas disponibles** y la **Galería de fotos**.</div></html>", JLabel.CENTER);
		lblInfo.setFont(new Font("SansSerif", Font.ITALIC, 14));
		panelContenidoDetalle.add(lblInfo, BorderLayout.CENTER);
		
		// 💡 CORRECCIÓN: Se elimina el casting innecesario
		JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelBoton.setBackground(COLOR_FONDO_CLARO);
		panelBoton.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		panelBoton.add(btnAnadirCarrito);
		
		panelDetalle.add(panelContenidoDetalle, BorderLayout.CENTER);
		panelDetalle.add(panelBoton, BorderLayout.SOUTH);
		
		return panelDetalle;
	}
	
	private class ProductCardPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private String nombreProducto;
		private String archivoImagen;
		private double precio;
		private String descripcion;
		
		@SuppressWarnings("unused")
		public ProductCardPanel(String nombre, String imagen, double precio, String descripcion) {
			this.nombreProducto = nombre;
			this.archivoImagen = imagen;
			this.precio = precio;
			this.descripcion = descripcion;
			
			setLayout(new BorderLayout());
			setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			setBackground(COLOR_FONDO_CLARO);
			
			JLabel lblImagen = new JLabel();
			try {
				ImageIcon iconOriginal = new ImageIcon(getClass().getResource("/images/" + archivoImagen));
				
				
				Image img = iconOriginal.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH); 
				lblImagen.setIcon(new ImageIcon(img));
			} catch (Exception e) {
				lblImagen.setText("[IMG] " + imagen);
				lblImagen.setHorizontalAlignment(JLabel.CENTER);
				lblImagen.setPreferredSize(new Dimension(150, 150));
			}
			lblImagen.setHorizontalAlignment(JLabel.CENTER);
			lblImagen.setVerticalAlignment(JLabel.CENTER);
			add(lblImagen, BorderLayout.CENTER);
			
			JPanel panelInfo = new JPanel(new GridLayout(2, 1));
			panelInfo.setBackground(COLOR_FONDO_OSCURO);
			panelInfo.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
			
			JLabel lblNombre = new JLabel("<html><b>" + nombre + "</b></html>");
			lblNombre.setFont(new Font("SansSerif", Font.PLAIN, 14));
			lblNombre.setHorizontalAlignment(JLabel.CENTER);
			
			JLabel lblPrecio = new JLabel(String.format("%.2f €", precio));
			lblPrecio.setFont(new Font("SansSerif", Font.BOLD, 16));
			lblPrecio.setForeground(COLOR_PRIMARIO.darker());
			lblPrecio.setHorizontalAlignment(JLabel.CENTER);
			
			panelInfo.add(lblNombre);
			panelInfo.add(lblPrecio);
			
			add(panelInfo, BorderLayout.SOUTH);
			
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (productoSeleccionado != null) {
						productoSeleccionado.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
						productoSeleccionado.setBackground(COLOR_FONDO_CLARO);
					}
					productoSeleccionado = ProductCardPanel.this;
					
					setBorder(BorderFactory.createLineBorder(COLOR_PRIMARIO, 2));
					setBackground(COLOR_HOVER);
					
					mostrarDetallesProducto();
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					if (productoSeleccionado != ProductCardPanel.this) {
						setBorder(BorderFactory.createLineBorder(COLOR_PRIMARIO.brighter(), 1));
					}
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					if (productoSeleccionado != ProductCardPanel.this) {
						setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
					}
				}
			});
		}
		
		private void mostrarDetallesProducto() {
			panelContenidoDetalle.removeAll();
			panelContenidoDetalle.setLayout(new BorderLayout(10, 10));
			
			
			
			JLabel lblDetalleImagen = new JLabel();
			try {
				ImageIcon iconOriginal = new ImageIcon(getClass().getResource("/images/" + archivoImagen));
				
				
				Image img = iconOriginal.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH); 
				
				lblDetalleImagen.setIcon(new ImageIcon(img));
			} catch (Exception e) {
				lblDetalleImagen.setText("[IMG]");
			}
		
			
			lblDetalleImagen.setHorizontalAlignment(JLabel.CENTER);
			lblDetalleImagen.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			
			
			// IAG
			lblDetalleImagen.setPreferredSize(new Dimension(280, 250)); 
			
			

			    String descripcionDetallada = String.format(
			        "<html><div style='padding: 10px; font-family: SansSerif;'>"
			        + "<h3>%s</h3>"
			        + "<p><b>Precio:</b> <span style='color: %s; font-weight: bold;'>%.2f €</span></p>"
			        + "<p><b>Descripción:</b> %s</p>" 
			        + "</div></html>", 
			        nombreProducto,
			        String.format("#%06x", COLOR_PRIMARIO.darker().getRGB() & 0xFFFFFF),
			        precio,
			        descripcion 
			    );

			
			
			JLabel lblDetalleInfo = new JLabel(descripcionDetallada);
			lblDetalleInfo.setVerticalAlignment(JLabel.TOP);
			
			JPanel panelDetalleContenido = new JPanel(new BorderLayout(10, 10));
			panelDetalleContenido.setBackground(COLOR_FONDO_CLARO);
			
			
			panelDetalleContenido.add(lblDetalleImagen, BorderLayout.WEST); 
			panelDetalleContenido.add(lblDetalleInfo, BorderLayout.CENTER);
			
			panelContenidoDetalle.add(panelDetalleContenido, BorderLayout.CENTER);
			
			btnAnadirCarrito.setEnabled(true);
			
			panelDetallesProducto.revalidate();
			panelDetallesProducto.repaint();
			
			TitledBorder borde = (TitledBorder) panelDetallesProducto.getBorder();
			borde.setTitle("📝 DETALLES Y COMPRA: " + nombreProducto.toUpperCase());
		}

		
	}
	
	private void cargarDatosDeEjemplo() {
	    
	    Object[][] data = {
	        
	        {"Camiset Basica Blanca", "camiseta_blanca.png", 15.95, "Algodón 100% orgánico, corte clásico y duradero. Ideal para el día a día."},
	        {"Camiseta Honda NSX-R", "camiseta_honda.png", 19.95, "Diseño exclusivo de edición limitada, cuello reforzado y estampado de alta calidad."},
	        {"Camiseta Mickey Mouse", "camiseta_mickey.png", 17.95, "Estampado retro del famoso ratón. Tacto suave y ajuste regular."},
	        {"Camiseta Tom & Jerry", "camiseta_tom-jerry.png", 17.99, "Divertida camiseta con los personajes clásicos."},
	        {"Camieta KTM", "camiseta_ktm.png", 19.95, "Estilo de competición. Tejido transpirable y ligero, perfecto para fans del motor."},
	        {"Camiseta Racing Team", "camiseta_racing.png", 13.95, "Inspirada en las carreras. Ajuste cómodo y tejido de fácil cuidado."},
	        {"Camiseta Friday Gaming Club", "camiseta_friday.png", 21.95, "Para tus noches de juego. Diseño moderno y tejido fresco."},
	        {"Pantalon Cargo Baggy", "pantalon_cargo.png", 55.00, "Estilo 'Baggy' con múltiples bolsillos. Máxima comodidad y tendencia."},
	        {"Pantalon Tailoring Wide Leg", "pantalon_tailoring.png", 69.95, "Elegancia y corte ancho. Ideal para ocasiones formales e informales."},
	        {"Pantalon Jogger Relaxed Fit", "pantalon_jogger.png", 54.99, "Cintura elástica y bajos ajustados. Perfecto para deporte o relax."},
	        {"Pantalon Chino Skinny Fit", "pantalon_chino.png", 39.90, "Corte ajustado y tejido elástico que se adapta a tu cuerpo."},
	        {"Sudadera Capucha Clasica", "sudadera_clasica.png", 79.99, "Suela de amortiguación avanzada y malla transpirable. Ligereza en cada pisada."},
	        {"Abrigo Lana \"Classic Fit\"", "abrigo_lana.png", 129.99, "Composición de lana virgen, corte clásico y botones ocultos. Muy cálido."},
	        {"Botines Cuero \"Chelsea\"", "botines_c.png", 89.90, "Piel auténtica, suela antideslizante y elástico lateral. Durabilidad y estilo."},
	        {"Gorra Béisbol Logo", "gorra_logo.png", 17.50, "Ajustable, con visera curva y logo bordado. 100% algodón."},
	    };

	    for (Object[] row : data) {
	        String nombre = (String) row[0];
	        String imagen = (String) row[1];
	        double precio = (double) row[2];
	        String descripcion = (String) row[3]; // **NUEVA LÍNEA**
	        
	        
	        panelTarjetasProductos.add(new ProductCardPanel(nombre, imagen, precio, descripcion)); 
	    }
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame tempFrame = new JFrame("Catálogo de Prueba");
            tempFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            tempFrame.add(new JPanelCatalogo());
            tempFrame.setSize(1200, 800);
            tempFrame.setLocationRelativeTo(null);
			tempFrame.setVisible(true);
		});
	}
}