package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border; 
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer; 
import java.awt.Font; 

import javax.swing.JTable;

public class JPanelCatalogo extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private int filaTablaProductos = -1;
	public JTextField display;
	
	
	private JTable tablaProductos;
	private DefaultTableModel modeloDatosProductos;
	private JTextField txtFiltro;
	private JTable tablaExplicacion;
	private DefaultTableModel modeloDatosExplicacion;
	
	private JButton btnAnadirCarrito;
	
	private static final Color COLOR_PRIMARIO = new Color(30, 144, 255); 
	private static final Color COLOR_FONDO_CLARO = Color.WHITE;
	private static final Color COLOR_FONDO_OSCURO = new Color(245, 245, 245); 
	private static final Color COLOR_HOVER = new Color(173, 216, 230); 

	
	public JPanelCatalogo() {
        setTitle("📦 Catálogo de Productos"); 
        setSize(1200, 750); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15)); 
        getContentPane().setBackground(COLOR_FONDO_OSCURO); 
        
        tablaProductos = new JTable();
        tablaExplicacion = new JTable();
        initTables();

        
        JPanel panelIzquierda = panelProductos();
        add(panelIzquierda, BorderLayout.WEST);

        
        JPanel panelDerecha = panelDetalles();
        add(panelDerecha, BorderLayout.CENTER);
        
        panelIzquierda.setPreferredSize(new Dimension(450, getHeight())); 
    }
	
    
	public JPanel panelDetalles() {
		
		JPanel panelDerecha = new JPanel(new GridLayout(2, 1, 15, 15)); 
		panelDerecha.setBackground(COLOR_FONDO_OSCURO);
		panelDerecha.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10)); 

		
        JPanel panelSuperior = new JPanel(new BorderLayout()); 
		
		TitledBorder bordeCaracteristicas = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1), "📝 CARACTERÍSTICAS DEL ARTÍCULO SELECCIONADO");
		bordeCaracteristicas.setTitleFont(new Font("SansSerif", Font.BOLD, 14));
		bordeCaracteristicas.setTitleColor(Color.DARK_GRAY);
		panelSuperior.setBorder(bordeCaracteristicas);
		panelSuperior.setBackground(COLOR_FONDO_CLARO);
		
		JLabel lblInfo = new JLabel("<html><div style='text-align: center; color: gray;'>TALLA, STOCK, DESCRIPCIÓN... <br>(Panel de detalles)</div></html>", JLabel.CENTER);
		lblInfo.setFont(new Font("SansSerif", Font.ITALIC, 14));
		panelSuperior.add(lblInfo, BorderLayout.CENTER); 

		
		btnAnadirCarrito = new JButton("🛒 Añadir al Carrito");
        btnAnadirCarrito.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnAnadirCarrito.setBackground(new Color(250, 179, 113));
        btnAnadirCarrito.setForeground(Color.BLACK);
        btnAnadirCarrito.setFocusPainted(false);
        btnAnadirCarrito.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnAnadirCarrito.setEnabled(true);
        
        JPanel panelBoton = new JPanel((LayoutManager) new FlowLayout(FlowLayout.RIGHT)); 
        panelBoton.setBackground(COLOR_FONDO_CLARO);
        panelBoton.setBorder(BorderFactory.createEmptyBorder(60, 10, 10, 10));
        panelBoton.add(btnAnadirCarrito);
        
        panelSuperior.add(panelBoton, BorderLayout.SOUTH);
        
        
		JPanel panelInferior = new JPanel(new BorderLayout());
		TitledBorder bordeExplicacion = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1), "📊 INFORMACIÓN ADICIONAL");
		bordeExplicacion.setTitleFont(new Font("SansSerif", Font.BOLD, 14));
		bordeExplicacion.setTitleColor(Color.DARK_GRAY);
		panelInferior.setBorder(bordeExplicacion);
		panelInferior.setBackground(COLOR_FONDO_CLARO);
		
		JLabel lblExplicacion = new JLabel("<html><div style='text-align: center; color: gray;'>Aquí iría la tablaExplicacion <br>o gráficos de datos.</div></html>", JLabel.CENTER);
		lblExplicacion.setFont(new Font("SansSerif", Font.ITALIC, 14));
		panelInferior.add(lblExplicacion, BorderLayout.CENTER); 

        panelDerecha.add(panelSuperior);
        panelDerecha.add(panelInferior);
		
		return panelDerecha;
	}

	JPanel panelCatalogo = new JPanel(new BorderLayout());
	
	
	public JPanel panelProductos() {
		
		
		JScrollPane scrollPaneProductos = new JScrollPane(this.tablaProductos); 
		
		scrollPaneProductos.setBorder(BorderFactory.createEmptyBorder()); 
		this.tablaProductos.setFillsViewportHeight(true);
		
		this.txtFiltro = new JTextField(30); 
		this.txtFiltro.setFont(new Font("SansSerif", Font.PLAIN, 14)); 
		this.txtFiltro.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARIO, 1)); 
		
		JPanel panelFiltro = new JPanel();
		panelFiltro.setBackground(COLOR_FONDO_CLARO); 
		panelFiltro.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10)); 
	    JLabel lblFiltro = new JLabel("🔍 Filtrar Artículo: ");
	    lblFiltro.setFont(new Font("SansSerif", Font.BOLD, 14));
	    panelFiltro.add(lblFiltro);
	    panelFiltro.add(txtFiltro);
	
	    JPanel panelNorteContenedor = new JPanel(new BorderLayout(0, 0));
	    panelNorteContenedor.add(panelFiltro, BorderLayout.CENTER);
	    
		JPanel panelProductos = new JPanel(new BorderLayout(5, 5));
		
		TitledBorder bordeProductos = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(COLOR_PRIMARIO, 2), "🛒 Productos de la Tienda");
		bordeProductos.setTitleFont(new Font("SansSerif", Font.BOLD, 16));
		bordeProductos.setTitleColor(COLOR_PRIMARIO);
		panelProductos.setBorder(BorderFactory.createCompoundBorder(
		BorderFactory.createEmptyBorder(10, 10, 10, 10), bordeProductos));
        panelProductos.setBackground(COLOR_FONDO_CLARO);
        panelProductos.add(BorderLayout.CENTER, scrollPaneProductos);
        panelProductos.add(BorderLayout.NORTH, panelFiltro);
		
		
		MouseMotionAdapter miMouseMotionListener = new MouseMotionAdapter() {
			
				public void mouseMoved(MouseEvent e) {
				
				Point puntoRaton = new Point(e.getX(), e.getY());
				int nuevaFila = tablaProductos.rowAtPoint(puntoRaton); 
				if (filaTablaProductos != nuevaFila) {
					filaTablaProductos = nuevaFila;
					tablaProductos.repaint();
				}				
			
				}
			};
				
			MouseAdapter miMouseAdapter = new MouseAdapter() {
				
				@Override
				public void mouseExited(MouseEvent e) {
					if (filaTablaProductos != -1) { 
						filaTablaProductos = -1;
						tablaProductos.repaint();
					}
				}
			};
		
		
		this.tablaProductos.addMouseMotionListener(miMouseMotionListener);
		this.tablaProductos.addMouseListener(miMouseAdapter);
		
		
		return panelProductos; 
		
	}
	
	private void cargarDatosDeEjemplo() {
		
		// La columna de IMAGEN se deja como String simple por ahora, pero meter imagen mas adelante. 
		
		Object[][] data = {
				{"Pantalón Denim \"Explorer\"", "vaquero.png", 59.99},
				{"Chino Algodón \"Urban\"", "chino.png", 45.50},
				{"Jogger Técnico \"Velocity\"", "jogger.png", 39.95},
				{"Leggings Térmicos \"Comfy\"", "legging.png", 24.99},
				{"Short de Baño \"Maui\"", "short.png", 29.90},
				{"Bermuda Cargo \"Outback\"", "bermuda.png", 34.95},
				{"Pantalón Sastre \"Elegance\"", "sastre.png", 79.90},
				{"Camiseta Estampada \"Galaxy\"", "cam_gala.png", 19.99},
				{"Polo Piqué \"Classic\"", "polo_clas.png", 29.50},
				{"Top Básico \"Luxe\"", "top_luxe.png", 14.95},
				{"Camiseta Oversize \"Retro\"", "cam_retro.png", 22.99},
				{"Tank Top \"Gym Beast\"", "tank_gym.png", 17.50},
				{"Blusa Seda \"Aurora\"", "blusa_aur.png", 49.90},
				{"Crop Top Asimétrico", "crop_as.png", 16.99},
				{"Sudadera Capucha \"Chill\"", "hoodie.png", 49.99},
				{"Jersey Trenzado \"Nordic\"", "jersey_n.png", 55.00},
				{"Sudadera Cremallera \"Track\"", "sud_track.png", 42.95},
				{"Cárdigan Fino \"Layer\"", "cardigan.png", 38.50},
				{"Sudadera Cuello Redondo \"Minimal\"", "sud_min.png", 36.99},
				{"Jersey Cashmere \"Luxury\"", "jersey_lux.png", 99.90},
				{"Chaqueta Bomber \"Pilot\"", "bomber.png", 69.95},
				{"Trench Coat \"Detective\"", "trench.png", 119.99},
				{"Cazadora Denim \"Vintage\"", "caz_den.png", 59.90},
				{"Parka Acolchada \"Arctic\"", "parka.png", 139.50},
				{"Blazer Lino \"Mediterranean\"", "blazer.png", 85.00},
				{"Abrigo Lana \"Chesterfield\"", "abrig_lan.png", 159.99},
				{"Chubasquero \"Raindrop\"", "chubas.png", 49.95},
				{"Vestido Midi Floral", "vest_flo.png", 54.99},
				{"Falda Plisada \"School\"", "falda_pli.png", 39.90},
				{"Vestido Cóctel \"Gala\"", "vest_cock.png", 89.95},
				{"Falda Tubo \"Business\"", "falda_tub.png", 44.50},
				{"Vestido Lencero \"Night\"", "vest_len.png", 65.00},
				{"Zapatillas Running \"Sprint\"", "zapa_run.png", 79.99},
				{"Botines Piel \"Rocker\"", "botines.png", 95.00},
				{"Sandalias Tiras \"Summer\"", "sanda_sum.png", 35.95},
				{"Mocasines Serraje \"Gentry\"", "moca_gen.png", 69.90},
				{"Zapatos Oxford \"Formal\"", "oxford.png", 89.99},
				{"Gorra Béisbol \"Sport\"", "gorra_sp.png", 15.99},
				{"Gorro Lana \"Beanie\"", "gorro_lan.png", 19.50},
				{"Sombrero Fedora \"Jazz\"", "fedora.png", 39.90},
				{"Visera \"Tennis Pro\"", "visera.png", 12.95},
				{"Guantes Piel \"Driver\"", "guan_dri.png", 49.99},
				{"Manoplas Nieve \"Powder\"", "manoplas.png", 25.50},
				{"Bufanda Punto \"Infinity\"", "bufa_inf.png", 29.90},
				{"Pañuelo Seda \"Art\"", "panuelo.png", 18.99},
				{"Cinturón Cuero \"Tough\"", "cint_tou.png", 35.00},
				{"Mochila Lona \"Hiker\"", "mochila.png", 59.95},
				{"Gafas Sol Polarizadas \"Neo\"", "gafas.png", 29.99},
				{"Calcetines Dibujos \"Fun\"", "calcetin.png", 8.95},
				{"Pijama Algodón \"Sweet\"", "pijama.png", 39.90},
				{"Corbata Seda \"Regal\"", "corbata.png", 34.50},
				{"Reloj Deportivo \"Pulse\"", "reloj.png", 69.99},
				{"Tirantes \"Vintage\"", "tirantes.png", 24.95},
				{"Funda Móvil \"Clear\"", "funda.png", 10.00},
				{"Cartera Billetera \"Slim\"", "cartera.png", 29.99},
				{"Llavero \"Leather\"", "llavero.png", 12.50},
				{"Set de 3 Mascarillas \"Safe\"", "mascaras.png", 19.99},
				{"Bolsa de Tela \"Eco\"", "bolsa_eco.png", 9.90},
				{"Botella Reutilizable \"Hydrate\"", "botella.png", 14.99},
				{"Paraguas Plegable \"Storm\"", "paraguas.png", 22.95}
		};

		for (Object[] row : data) { 
			this.modeloDatosProductos.addRow(row);
		}
	}
	
	
	private void initTables() { 
		
		Vector<String> cabeceraProductos = new Vector<String>(Arrays.asList( "ARTICULO", "IMAGEN", "PRECIO (€)")); 
		
		this.modeloDatosProductos = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceraProductos);
		this.tablaProductos.setModel(this.modeloDatosProductos); 
		
		cargarDatosDeEjemplo();
		
		TableCellRenderer cellRenderer = (table, value, isSelected, hasFocus, row, column) -> {
			
		
			
			JLabel result = new JLabel(value.toString());
			result.setFont(new Font("SansSerif", Font.PLAIN, 12)); 
			result.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); 
			
			
			if (isSelected || (table.equals(tablaProductos) && filaTablaProductos == row)) {
				result.setBackground(COLOR_HOVER); 
				result.setForeground(Color.BLACK);
			} else {
				
				if (row % 2 == 0) {
					result.setBackground(COLOR_FONDO_CLARO);
				} else {
					result.setBackground(COLOR_FONDO_OSCURO);
				}
				result.setForeground(Color.BLACK);
			}
			
			result.setOpaque(true);
			return result;
		};
		
		
		tablaProductos.setDefaultRenderer(Object.class, cellRenderer);
		
		this.tablaProductos.setRowHeight(30); 
		this.tablaExplicacion.setRowHeight(30); 
		
		
		this.tablaProductos.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
		this.tablaProductos.getTableHeader().setBackground(COLOR_PRIMARIO);
		this.tablaProductos.getTableHeader().setForeground(Color.WHITE);
		this.tablaProductos.setGridColor(new Color(220, 220, 220)); 
		
		this.tablaProductos.getTableHeader().setReorderingAllowed(false);
		this.tablaProductos.setAutoCreateRowSorter(true);
		
		
		
	
	}
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JPanelCatalogo frame = new JPanelCatalogo();
            frame.setVisible(true);
        });
    }
}
