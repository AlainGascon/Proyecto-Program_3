package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import domain.Evento;
import domain.ItemCarrito;
import domain.Producto;



public class JFramePrincipal extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static boolean isLoggedIn = false;
	
	private static List<ItemCarrito> carritoActual = new ArrayList<>();
	private static JButton btnCarritoGlobalEstatico;

    private static final Color COLOR_FONDO_NAV = new Color(52, 73, 94);
    
    // Lista de productos debe ser accesible estáticamente para decrementar stock
    private static List<Producto> listaProductosEstatica; 

	private final List<Producto> listaProductos;
    private final List<Evento> listaEventos; 

	private CardLayout cardLayout;
	private JPanel panelContenidoDinamico;

	private static final String CARD_CATALOGO = "CATALOGO";
	private static final String CARD_EVENTOS = "EVENTOS";
	private static final String CARD_JUEGO_DESCUENTO = "JUEGO_DESCUENTO"; 

	private static final String COMMAND_LOGIN = "LOGIN";
	private static final String COMMAND_PAGAR = "PAGAR";


	public JFramePrincipal(List<Producto> listaProductos, List<ItemCarrito> carritoInicial) {

		this.listaProductos = listaProductos;
		JFramePrincipal.carritoActual = carritoInicial;
        JFramePrincipal.listaProductosEstatica = listaProductos; // Inicialización estática
        
        this.listaEventos = generarDatosEventos(); 

		this.setTitle("Tienda de Ropa DEUSTO - Menú Principal");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(1200, 800);
		this.setLocationRelativeTo(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);


		JPanel pNorte = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
		btnCarritoGlobalEstatico = crearBotonCarrito();
		pNorte.add(btnCarritoGlobalEstatico);
		this.add(pNorte, BorderLayout.NORTH);


		JPanel panelNavegacion = new JPanel();
		panelNavegacion.setLayout(new GridLayout(6, 1, 10, 10));
		panelNavegacion.setPreferredSize(new Dimension(200, 0));
		panelNavegacion.setBackground(COLOR_FONDO_NAV);
		panelNavegacion.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));


		JButton btnCatalogo = crearBotonNavegacion("🛍️ Catálogo", CARD_CATALOGO);
		JButton btnEventos = crearBotonNavegacion("🗓️ Eventos", CARD_EVENTOS);
		JButton btnJuego = crearBotonNavegacion("🎰 Descuento", CARD_JUEGO_DESCUENTO); 
		JButton btnLogin = crearBotonNavegacion("🔑 Iniciar Sesión", COMMAND_LOGIN);

		panelNavegacion.add(btnCatalogo);
		panelNavegacion.add(btnEventos);
		panelNavegacion.add(btnJuego); 
		panelNavegacion.add(new JLabel(""));
		panelNavegacion.add(new JLabel(""));
		panelNavegacion.add(btnLogin);

		this.add(panelNavegacion, BorderLayout.WEST);


		cardLayout = new CardLayout();
		panelContenidoDinamico = new JPanel(cardLayout);
		panelContenidoDinamico.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelContenidoDinamico.add(new JPanelCatalogo(listaProductos), CARD_CATALOGO);
		panelContenidoDinamico.add(new VentanaEventos(this.listaEventos), CARD_EVENTOS); 
		panelContenidoDinamico.add(new JuegoDescuento(), CARD_JUEGO_DESCUENTO); 

		this.add(panelContenidoDinamico, BorderLayout.CENTER);


		cardLayout.show(panelContenidoDinamico, CARD_CATALOGO);

		this.setVisible(true);
	}
    
    // Método estático para decrementar el stock del producto principal
    public static boolean decrementarStock(Producto producto, int cantidad, String talla) {
        if (listaProductosEstatica != null) {
            Optional<Producto> productoEnStock = listaProductosEstatica.stream()
                .filter(p -> p.getNombre().equals(producto.getNombre()))
                .findFirst();

            if (productoEnStock.isPresent()) {
                // Se asume que la clase Producto tiene este método
                productoEnStock.get().decrementarStock(talla, cantidad); 
                return true;
            }
        }
        return false;
    }


	public List<Evento> generarDatosEventos() {
	    List<Evento> eventos = new ArrayList<>();
	    

	    // IAG
	    
	    eventos.add(new Evento(LocalDate.of(2025, 11, 15), "Desfile Colección Invierno", "Gran Vía, Bilbao", 120));
	    eventos.add(new Evento(LocalDate.of(2025, 11, 18), "Lanzamiento Cápsula 'Urban'", "Passeig de Gràcia, Barcelona", 95));
	    eventos.add(new Evento(LocalDate.of(2025, 11, 21), "Venta Privada Invierno", "Showroom Exclusivo, Madrid", 70));
	    eventos.add(new Evento(LocalDate.of(2025, 11, 25), "Desfile Colección Fiesta", "Sala de Eventos, Valencia", 150));
	    eventos.add(new Evento(LocalDate.of(2025, 11, 29), "Lanzamiento Zapatillas 'Retro'", "Centro Comercial, Sevilla", 220));
	    eventos.add(new Evento(LocalDate.of(2025, 12, 3), "Desfile Jóvenes Diseñadores", "Galería de Arte, Málaga", 85));
	    eventos.add(new Evento(LocalDate.of(2025, 12, 7), "Lanzamiento Ropa Deportiva", "Plaza Central, Zaragoza", 300));
	    eventos.add(new Evento(LocalDate.of(2025, 12, 11), "Venta Privada de Navidad", "Tienda Principal, Murcia", 110));
	    eventos.add(new Evento(LocalDate.of(2025, 12, 15), "Desfile de Tendencias", "Showroom Exclusivo, Palma", 65));
	    eventos.add(new Evento(LocalDate.of(2025, 12, 19), "Lanzamiento de Bisutería", "Tienda Principal, Alicante", 90));
	    eventos.add(new Evento(LocalDate.of(2025, 12, 23), "Masterclass Maquillaje de Pasarela", "Taller Auxiliar, Bilbao", 30));
	    eventos.add(new Evento(LocalDate.of(2025, 12, 27), "Taller de Customización Denim", "Taller Creativo, Madrid", 25));
	    eventos.add(new Evento(LocalDate.of(2025, 12, 30), "Cierre de año por todo lo alto", "Passeig de Gràcia, Barcelona", 3500)); 
	    eventos.add(new Evento(LocalDate.of(2026, 1, 3), "Masterclass Personal Shopper", "Showroom Exclusivo, Valencia", 40));
	    eventos.add(new Evento(LocalDate.of(2026, 1, 7), "Taller 'Crea tu Propio Estilo'", "Sala de Eventos, Sevilla", 50));
	    eventos.add(new Evento(LocalDate.of(2026, 1, 11), "Masterclass Fotografía de Moda", "Galería de Arte, Málaga", 35));
	    eventos.add(new Evento(LocalDate.of(2026, 1, 15), "Taller de Reciclaje Textil", "Taller Creativo, Zaragoza", 28));
	    eventos.add(new Evento(LocalDate.of(2026, 1, 19), "Masterclass Tendencias '26", "Showroom Exclusivo, Murcia", 45));
	    eventos.add(new Evento(LocalDate.of(2026, 1, 23), "Taller de Complementos", "Taller Auxiliar, Palma", 20));
	    eventos.add(new Evento(LocalDate.of(2026, 1, 27), "Masterclass de Street Style", "Sala de Eventos, Alicante", 55));
	    eventos.add(new Evento(LocalDate.of(2026, 1, 31), "Liquidación Total Invierno", "Plaza Central, Bilbao", 500));
	    eventos.add(new Evento(LocalDate.of(2026, 2, 4), "Rebajas de Final de Temporada", "Centro Comercial, Madrid", 750));
	    eventos.add(new Evento(LocalDate.of(2026, 2, 8), "Venta Flash Zapatillas", "Tienda Principal, Barcelona", 400));
	    eventos.add(new Evento(LocalDate.of(2026, 2, 12), "Liquidación Accesorios", "Sala de Eventos, Valencia", 320));
	    eventos.add(new Evento(LocalDate.of(2026, 2, 16), "Venta Final de Stock", "Centro Comercial, Sevilla", 800));
	    eventos.add(new Evento(LocalDate.of(2026, 2, 20), "Rebajas de Invierno Finales", "Tienda Principal, Málaga", 550));
	    eventos.add(new Evento(LocalDate.of(2026, 2, 24), "Liquidación Total Zapatos", "Plaza Central, Zaragoza", 600));
	    eventos.add(new Evento(LocalDate.of(2026, 2, 28), "Venta Flash 'Only Dresses'", "Tienda Principal, Murcia", 380));
	    eventos.add(new Evento(LocalDate.of(2026, 3, 4), "Rebajas Extraordinarias", "Centro Comercial, Palma", 700));
	    eventos.add(new Evento(LocalDate.of(2026, 3, 8), "Liquidación Ropa de Abrigo", "Tienda Principal, Alicante", 450));
	    eventos.add(new Evento(LocalDate.of(2026, 3, 12), "Desfile Colección Verano", "Sala de Eventos, Córdoba", 130));
	    eventos.add(new Evento(LocalDate.of(2026, 3, 16), "Lanzamiento Colección 'Beach'", "Showroom Exclusivo, Valladolid", 100));
	    eventos.add(new Evento(LocalDate.of(2026, 3, 20), "Venta Privada Verano", "Tienda Principal, Vigo", 80));
	    eventos.add(new Evento(LocalDate.of(2026, 3, 24), "Desfile de Bañadores", "Centro Comercial, Gijón", 180));
	    eventos.add(new Evento(LocalDate.of(2026, 3, 28), "Lanzamiento Zapatillas 'Future'", "Plaza Mayor, Vitoria", 250));
	    eventos.add(new Evento(LocalDate.of(2026, 4, 1), "Desfile de Tendencias Primavera", "Galería de Arte, A Coruña", 90));
	    eventos.add(new Evento(LocalDate.of(2026, 4, 5), "Lanzamiento Ropa Casual", "Tienda Principal, Granada", 350));
	    eventos.add(new Evento(LocalDate.of(2026, 4, 9), "Venta Privada de Pascua", "Showroom Exclusivo, Oviedo", 120));
	    eventos.add(new Evento(LocalDate.of(2026, 4, 13), "Desfile de Primavera", "Sala de Eventos, Santa Cruz", 75));
	    eventos.add(new Evento(LocalDate.of(2026, 4, 17), "Lanzamiento de Gafas de Sol", "Tienda Principal, Las Palmas", 105));
	    eventos.add(new Evento(LocalDate.of(2026, 5, 31), "Liquidación Total Primavera", "Plaza Central, Córdoba", 520));
	    eventos.add(new Evento(LocalDate.of(2026, 6, 4), "Rebajas de Final de Temporada", "Centro Comercial, Valladolid", 770));
	    eventos.add(new Evento(LocalDate.of(2026, 6, 8), "Venta Flash Vestidos", "Tienda Principal, Vigo", 420));
	    eventos.add(new Evento(LocalDate.of(2026, 6, 12), "Liquidación de Joyería", "Sala de Eventos, Gijón", 340));
	    eventos.add(new Evento(LocalDate.of(2026, 6, 16), "Venta Final de Stock Verano", "Centro Comercial, Vitoria", 820));
	    eventos.add(new Evento(LocalDate.of(2026, 6, 20), "Rebajas de Verano Inicio", "Tienda Principal, A Coruña", 570));
	    eventos.add(new Evento(LocalDate.of(2026, 6, 24), "Liquidación Total Sandalias", "Plaza Central, Granada", 620));
	    eventos.add(new Evento(LocalDate.of(2026, 6, 28), "Venta Flash 'Only Shorts'", "Tienda Principal, Oviedo", 400));
	    eventos.add(new Evento(LocalDate.of(2026, 7, 2), "Rebajas de Mitad de Temporada", "Centro Comercial, Santa Cruz", 720));
	    eventos.add(new Evento(LocalDate.of(2026, 7, 6), "Liquidación Ropa de Fiesta", "Tienda Principal, Las Palmas", 470));
	
	    return eventos;
	}


	public static boolean isLoggedIn() {
		return isLoggedIn;
	}

	public static void setLoggedIn(boolean status) {
		isLoggedIn = status;
	}


	private JButton crearBotonNavegacion(String texto, String comando) {
		JButton btn = new JButton(texto);
		btn.setFont(new Font("SansSerif", Font.BOLD, 14));
		btn.setForeground(Color.WHITE);
		btn.setBackground(COLOR_FONDO_NAV.brighter());
		btn.setHorizontalAlignment(SwingConstants.LEFT);
		btn.setFocusPainted(false);
		btn.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
		btn.setActionCommand(comando);
		btn.addActionListener(this);
		return btn;
	}


	public JButton crearBotonCarrito() {
		int cantidadItems = calcularTotalArticulos();
		JButton btnCarrito = new JButton("🛒 Carrito (" + cantidadItems + ")");
		btnCarrito.setToolTipText("Ver Carrito de Compras");
		btnCarrito.setPreferredSize(new Dimension(150, 30));
		btnCarrito.setFont(new Font("SanSerif", Font.BOLD, 15));
		btnCarrito.setBackground(new Color(250, 179, 113));
		btnCarrito.setForeground(Color.BLACK);

		btnCarrito.addActionListener(e -> {
			
			VentanaCarrito ventana = new VentanaCarrito(carritoActual);
			ventana.setVisible(true);
		});
		return btnCarrito;
	}

	private static int calcularTotalArticulos() {
		int totalArticulos = 0;

		for (ItemCarrito item : carritoActual) {
			totalArticulos += item.getCantidad();
		}
		return totalArticulos;
	}

	public static void agregarItemAlCarrito(Producto p, int cantidad, String talla) {
        // Decrementamos el stock del producto en la lista principal
        decrementarStock(p, cantidad, talla);
        
		carritoActual.add(new ItemCarrito(p, cantidad, talla));
		actualizarContadorCarritoGlobal();
	}
    
   
    public static void actualizarContadorCarritoGlobal() {
        if (btnCarritoGlobalEstatico != null) {
            SwingUtilities.invokeLater(() -> {
                int nuevoTotal = calcularTotalArticulos();
                btnCarritoGlobalEstatico.setText("🛒 Carrito (" + nuevoTotal + ")");
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals(CARD_CATALOGO) || command.equals(CARD_EVENTOS) || command.equals(CARD_JUEGO_DESCUENTO)) {
            cardLayout.show(panelContenidoDinamico, command);
            panelContenidoDinamico.revalidate();
            panelContenidoDinamico.repaint();
        } 
        
        else if (command.equals(COMMAND_LOGIN)) {
            
            JFrameAutenticacion loginFrame = new JFrameAutenticacion(null);
            loginFrame.setVisible(true);
        }
        
        else if (command.equals(COMMAND_PAGAR)) {
            if (JFramePrincipal.isLoggedIn()) {
                JOptionPane.showMessageDialog(this,
                    "✅ Usuario autenticado: Procediendo a la pasarela de pago...",
                    "Pago Permitido",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "🔒 Debe iniciar sesión para proceder con el pago. Abra la ventana de inicio de sesión.",
                    "Autenticación Requerida",
                    JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}