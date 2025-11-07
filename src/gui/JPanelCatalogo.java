package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

public class JPanelCatalogo extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel panelTarjetasProductos;
	private JPanel panelDetallesProducto;
	private JPanel panelContenidoDetalle; 
	
	private JTextField txtFiltro;
	private JButton btnAnadirCarrito;
	
	private ProductCardPanel productoSeleccionado = null; 

	private static final Color COLOR_PRIMARIO = new Color(30, 144, 255);
	private static final Color COLOR_FONDO_CLARO = Color.WHITE;
	private static final Color COLOR_FONDO_OSCURO = new Color(245, 245, 245);
	private static final Color COLOR_HOVER = new Color(173, 216, 230); 

	public JPanelCatalogo() {
		setTitle("🛍️ Tienda de Ropa - Catálogo");
		setSize(1200, 750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(15, 15));
		getContentPane().setBackground(COLOR_FONDO_OSCURO);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		btnAnadirCarrito = new JButton("🛒 Añadir al Carrito");
		btnAnadirCarrito.setFont(new Font("SansSerif", Font.BOLD, 16));
		btnAnadirCarrito.setBackground(new Color(250, 179, 113));
		btnAnadirCarrito.setForeground(Color.BLACK);
		btnAnadirCarrito.setFocusPainted(false);
		btnAnadirCarrito.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
		btnAnadirCarrito.setEnabled(false);
		
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
		TitledBorder bordeInfo = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.GRAY, 1), "🛒 Cesta / Filtros Avanzados");
		bordeInfo.setTitleFont(new Font("SansSerif", Font.BOLD, 14));
		bordeInfo.setTitleColor(Color.DARK_GRAY);
		panelDerechaVacio.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 0, 10, 10), bordeInfo));
		panelDerechaVacio.setPreferredSize(new Dimension(350, getHeight()));
		
		JLabel lblInfoAd = new JLabel("<html><div style='text-align: center; color: gray;'>Aquí irían los filtros de talla, color o el resumen de la cesta de compra.</div></html>", JLabel.CENTER);
		lblInfoAd.setFont(new Font("SansSerif", Font.ITALIC, 14));
		panelDerechaVacio.add(lblInfoAd, BorderLayout.CENTER);
		
		add(panelDerechaVacio, BorderLayout.EAST);
	}
	
	public JPanel panelProductos() {

		panelTarjetasProductos = new JPanel(new GridLayout(0, 4, 15, 15)); 
		panelTarjetasProductos.setBackground(COLOR_FONDO_CLARO);
		panelTarjetasProductos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		cargarDatosDeEjemplo(); 

		JScrollPane scrollPaneProductos = new JScrollPane(panelTarjetasProductos);
		scrollPaneProductos.setBorder(BorderFactory.createEmptyBorder());
		
		this.txtFiltro = new JTextField(20);
		this.txtFiltro.setFont(new Font("SansSerif", Font.PLAIN, 14));
		this.txtFiltro.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARIO, 1));

		JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		panelFiltro.setBackground(COLOR_FONDO_CLARO);
		JLabel lblFiltro = new JLabel("🔍 Buscar Artículo: ");
		lblFiltro.setFont(new Font("SansSerif", Font.BOLD, 14));
		panelFiltro.add(lblFiltro);
		panelFiltro.add(txtFiltro);

		JPanel panelProductosContenedor = new JPanel(new BorderLayout(5, 5));
		
		TitledBorder bordeProductos = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(COLOR_PRIMARIO, 2), "🛒 PRODUCTOS EN CATÁLOGO");
		bordeProductos.setTitleFont(new Font("SansSerif", Font.BOLD, 16));
		bordeProductos.setTitleColor(COLOR_PRIMARIO);
		
		panelProductosContenedor.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 0, 10), bordeProductos));
		panelProductosContenedor.setBackground(COLOR_FONDO_CLARO);
		
		panelProductosContenedor.add(scrollPaneProductos, BorderLayout.CENTER);
		panelProductosContenedor.add(panelFiltro, BorderLayout.NORTH);

		return panelProductosContenedor;
	}
	
	public JPanel panelDetallesProducto() {
		
		JPanel panelDetalle = new JPanel(new BorderLayout(10, 10));
		TitledBorder bordeCaracteristicas = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.GRAY, 1), "📝 DETALLES Y COMPRA DEL ARTÍCULO");
		bordeCaracteristicas.setTitleFont(new Font("SansSerif", Font.BOLD, 14));
		bordeCaracteristicas.setTitleColor(Color.DARK_GRAY);
		panelDetalle.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(0, 10, 0, 10), bordeCaracteristicas));
		panelDetalle.setBackground(COLOR_FONDO_CLARO);
		
		panelContenidoDetalle = new JPanel(new BorderLayout());
		panelContenidoDetalle.setBackground(COLOR_FONDO_CLARO);
		
		JLabel lblInfo = new JLabel("<html><div style='text-align: center; color: #555; padding-top:20px;'>Haga clic en un producto para ver la **Descripción**, las **Tallas disponibles** y la **Galería de fotos**.</div></html>", JLabel.CENTER);
		lblInfo.setFont(new Font("SansSerif", Font.ITALIC, 14));
		panelContenidoDetalle.add(lblInfo, BorderLayout.CENTER);
		
		JPanel panelBoton = new JPanel((LayoutManager) new FlowLayout(FlowLayout.RIGHT));
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
		
		public ProductCardPanel(String nombre, String imagen, double precio) {
			this.nombreProducto = nombre;
			this.archivoImagen = imagen;
			this.precio = precio;
			
			setLayout(new BorderLayout());
			setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			setBackground(COLOR_FONDO_CLARO);
			
			JLabel lblImagen = new JLabel();
			try {
				ImageIcon iconOriginal = new ImageIcon(getClass().getResource("/images/" + archivoImagen));
				Image img = iconOriginal.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
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
				
				// 1. ESCALADO A 250x250
				Image img = iconOriginal.getImage().getScaledInstance(300, 250, Image.SCALE_SMOOTH); 
				
				lblDetalleImagen.setIcon(new ImageIcon(img));
			} catch (Exception e) {
				lblDetalleImagen.setText("[IMG]");
			}
			lblDetalleImagen.setHorizontalAlignment(JLabel.CENTER);
			lblDetalleImagen.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			
			// 2. DARLE UN ANCHO PREFERIDO DE 280 (para el hueco)
			lblDetalleImagen.setPreferredSize(new Dimension(280, 250)); 
			
			String descripcionDetallada = String.format(
					"<html><div style='padding: 10px; font-family: SansSerif;'>"
					+ "<h3>%s</h3>"
					+ "<p><b>Precio:</b> <span style='color: %s; font-weight: bold;'>%.2f €</span></p>"
					+ "<p><b>Descripción:</b> Material de alta calidad, corte moderno y tallaje disponible en S, M, L y XL. ¡Envío gratuito en 24h!</p>"
					+ "<p><b>Opciones:</b> <span style='color: blue;'>[Tallas] [Colores]</span></p>"
					+ "</div></html>", 
					nombreProducto,
					String.format("#%06x", COLOR_PRIMARIO.darker().getRGB() & 0xFFFFFF),
					precio
			);
			
			JLabel lblDetalleInfo = new JLabel(descripcionDetallada);
			lblDetalleInfo.setVerticalAlignment(JLabel.TOP);
			
			JPanel panelDetalleContenido = new JPanel(new BorderLayout(10, 10));
			panelDetalleContenido.setBackground(COLOR_FONDO_CLARO);
			
			// La imagen se añade en WEST, utilizando el ancho preferido de 280.
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
				{"Camiseta Basica Blanca", "camiseta_blanca.png", 14.95},
				{"Camiseta Honda NSX-R", "camiseta_honda.png", 19.95},
				{"Camiseta Tom & Jerry", "camiseta_tom-jerry.png", 16.95},
				{"Camiseta Mickey Mouse", "camiseta_mickey.png", 16.95},
				{"Camiseta KTM", "camiseta_ktm.png", 19.95},
				{"Top Básico \"Luxe\"", "top_luxe.png", 14.95},
				{"Sudadera Capucha \"Chill\"", "hoodie.png", 49.99},
				{"Jersey Trenzado \"Nordic\"", "jersey_n.png", 55.00},
				{"Chaqueta Bomber \"Pilot\"", "bomber.png", 69.95},
				{"Vestido Midi Floral", "vest_flo.png", 54.99},
				{"Falda Plisada \"School\"", "falda_pli.png", 39.90},
				{"Zapatillas Running \"Sprint\"", "zapa_run.png", 79.99},
				{"Abrigo Lana \"Classic Fit\"", "abrigo_lana.png", 129.99},
				{"Botines Cuero \"Chelsea\"", "botines_c.png", 89.90},
				{"Gorra Béisbol Logo", "gorra_logo.png", 17.50},
				{"Bufanda Cachemira", "bufanda_c.png", 35.95},
				{"Camisa Oxford \"Business\"", "camisa_ox.png", 49.99},
				{"Bikini Top \"Tropical\"", "bikini_top.png", 22.00},
				{"Sudadera Cuello Redondo", "sud_cuello.png", 38.99},
				{"Pantalón Cargo Negro", "pant_cargo.png", 55.00},
				{"Vestido Noche Asimétrico", "vest_noche.png", 79.95},
				{"Mocasines Ante Marrón", "mocasines.png", 65.00},
				{"Calcetines Pack 3", "calcetines.png", 9.99},
				{"Chaleco Acolchado", "chaleco.png", 59.90}
		};

		for (Object[] row : data) {
			String nombre = (String) row[0];
			String imagen = (String) row[1];
			double precio = (double) row[2];
			panelTarjetasProductos.add(new ProductCardPanel(nombre, imagen, precio));
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JPanelCatalogo frame = new JPanelCatalogo();
			frame.setVisible(true);
		});
	}
}