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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel; // CAMBIO CLAVE: Ahora extiende JPanel
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

// Importar clases de dominio necesarias (asumiendo que existen)
import domain.Producto; 
// Importar la clase principal para acceder al método del carrito
import gui.JFramePrincipal;

// ----------------------------------------------------------------------------------
// CAMBIO CLAVE: EXTENDER JPanel
// ----------------------------------------------------------------------------------
public class JPanelCatalogo extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JPanel panelTarjetasProductos;
	private JPanel panelDetallesProducto;
	private JPanel panelContenidoDetalle; 
	
	private JTextField txtFiltro;
	private JButton btnAnadirCarrito;
	
	private ProductCardPanel productoSeleccionado = null; 

	private static final Color COLOR_PRIMARIO = new Color(30, 144, 255);
	private static final Color COLOR_FONDO_CLARO = Color.WHITE;
	private static final Color COLOR_FONDO_OSCURO = new Color(240, 248, 255);
	
	// Datos de prueba (simplificados de tu snippet original)
	private Object[][] datosProductos = {
		{"Pantalón Vaquero Clásico", "pantalon_vaquero.png", 49.99},
		{"Camiseta Racing Team", "camiseta_racing.png", 13.95},
		{"Jersey Trenzado \"Nordic\"", "jersey_n.png", 55.00},
		{"Chaqueta Bomber \"Pilot\"", "bomber.png", 69.95},
		// ... (Añadir el resto de tus productos aquí)
	};
	
	public JPanelCatalogo() {
		// Ya no se llama super(); y no se establecen propiedades de JFrame
		
		this.setLayout(new BorderLayout(10,10));
		this.setBackground(COLOR_FONDO_OSCURO);
		
		// 1. Panel Norte (Filtros y Búsqueda)
		JPanel pNorteFiltros = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		pNorteFiltros.setBackground(COLOR_FONDO_CLARO);
		pNorteFiltros.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(COLOR_PRIMARIO, 1), 
				"Filtros de Búsqueda", TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 12), COLOR_PRIMARIO));
		
		txtFiltro = new JTextField(20);
		JButton btnBuscar = new JButton("Buscar");
		
		pNorteFiltros.add(new JLabel("Buscar Producto:"));
		pNorteFiltros.add(txtFiltro);
		pNorteFiltros.add(btnBuscar);
		
		this.add(pNorteFiltros, BorderLayout.NORTH);

		// 2. Panel Central (Listado de Productos)
		panelTarjetasProductos = new JPanel(new GridLayout(0, 4, 15, 15)); 
		panelTarjetasProductos.setBackground(COLOR_FONDO_OSCURO);
		
		JScrollPane scroll = new JScrollPane(panelTarjetasProductos);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().setUnitIncrement(16);

		this.add(scroll, BorderLayout.CENTER);

		// 3. Panel Este (Detalles del Producto)
		panelDetallesProducto = new JPanel(new BorderLayout());
		panelDetallesProducto.setPreferredSize(new Dimension(300, 0));
		panelDetallesProducto.setBackground(COLOR_FONDO_CLARO);
		panelDetallesProducto.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), 
				"Detalle del Producto", TitledBorder.CENTER, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 14)));

		// Contenido que se actualiza al seleccionar una tarjeta
		panelContenidoDetalle = new JPanel(new GridLayout(6, 1, 5, 5));
		panelContenidoDetalle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelDetallesProducto.add(panelContenidoDetalle, BorderLayout.CENTER);
		
		// Botón de Añadir al Carrito (Parte inferior del panel de detalles)
		btnAnadirCarrito = new JButton("➕ Añadir al Carrito");
		btnAnadirCarrito.setBackground(COLOR_PRIMARIO);
		btnAnadirCarrito.setForeground(Color.WHITE);
		btnAnadirCarrito.setEnabled(false); // Deshabilitado hasta seleccionar un producto
		
		JPanel pSurDetalles = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pSurDetalles.add(btnAnadirCarrito);
		panelDetallesProducto.add(pSurDetalles, BorderLayout.SOUTH);

		this.add(panelDetallesProducto, BorderLayout.EAST);
		
		// Inicializar Productos
		cargarProductos(datosProductos);
		
		// 4. Listener del botón Añadir al Carrito
		btnAnadirCarrito.addActionListener(e -> {
			if (productoSeleccionado != null) {
				// ----------------------------------------------------------------------------------
				// LLAMADA CLAVE: Usar el método estático de JFramePrincipal
				// NOTA: Se asume que el objeto Producto real está en 'productoSeleccionado.producto'
				// Y que la cantidad es 1 para este ejemplo simple.
				// ----------------------------------------------------------------------------------
				// Si necesitas la cantidad, deberías obtenerla de un JSpinner o JTextField.
				Producto productoDummy = new Producto(1, productoSeleccionado.getNombre(), productoSeleccionado.getPrecio());
				JFramePrincipal.agregarItemAlCarrito(productoDummy, 1);
				
				System.out.println("Producto añadido: " + productoSeleccionado.getNombre());
			}
		});
	}

	private void cargarProductos(Object[][] datos) {
		panelTarjetasProductos.removeAll();
		for (Object[] data : datos) {
			String nombre = (String) data[0];
			String imagen = (String) data[1];
			double precio = (double) data[2];
			
			// Nota: La clase ProductoCardPanel está definida a continuación
			ProductCardPanel card = new ProductCardPanel(nombre, imagen, precio);
			
			card.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					mostrarDetalle(card);
				}
			});
			panelTarjetasProductos.add(card);
		}
		panelTarjetasProductos.revalidate();
		panelTarjetasProductos.repaint();
	}
	
	private void mostrarDetalle(ProductCardPanel card) {
		// Lógica para resaltar la tarjeta y mostrar detalles
		if (productoSeleccionado != null) {
			productoSeleccionado.setBorder(null);
		}
		productoSeleccionado = card;
		productoSeleccionado.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARIO, 3));
		
		// Actualizar panel de detalles
		panelContenidoDetalle.removeAll();
		panelContenidoDetalle.add(new JLabel("<html><h2>" + card.getNombre() + "</h2></html>"));
		panelContenidoDetalle.add(new JLabel("Precio: " + String.format("%.2f€", card.getPrecio())));
		panelContenidoDetalle.add(new JLabel("Descripción: ..."));
		panelContenidoDetalle.add(new JLabel("Tallas Disponibles: S, M, L"));
		
		// Se asume que tienes un JLabel para la imagen aquí
		panelContenidoDetalle.add(new JLabel("[Imagen de " + card.getNombre() + "]")); 
		
		btnAnadirCarrito.setEnabled(true);
		panelContenidoDetalle.revalidate();
		panelContenidoDetalle.repaint();
	}
	
	// Clase Interna simplificada (se asume que existe en tu código)
	private class ProductCardPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private String nombre;
		private double precio;

		public ProductCardPanel(String nombre, String imagenPath, double precio) {
			this.nombre = nombre;
			this.precio = precio;
			this.setLayout(new BorderLayout());
			this.setBackground(Color.WHITE);
			this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

			JLabel lblNombre = new JLabel(nombre, SwingConstants.CENTER);
			lblNombre.setFont(new Font("SansSerif", Font.BOLD, 14));
			
			JLabel lblPrecio = new JLabel(String.format("%.2f€", precio), SwingConstants.CENTER);
			lblPrecio.setForeground(new Color(231, 76, 60)); // Rojo
			
			// Placeholder para imagen (asumiendo que las imágenes no están disponibles aquí)
			JLabel lblImagen = new JLabel("[IMG]");
			lblImagen.setPreferredSize(new Dimension(150, 150));
			lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
			
			this.add(lblImagen, BorderLayout.CENTER);
			JPanel pSur = new JPanel(new GridLayout(2, 1));
			pSur.add(lblNombre);
			pSur.add(lblPrecio);
			this.add(pSur, BorderLayout.SOUTH);
		}

		public String getNombre() { return nombre; }
		public double getPrecio() { return precio; }
	}
}