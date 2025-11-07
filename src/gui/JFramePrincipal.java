package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;

import domain.ItemCarrito;
import domain.Producto;
import gui.VentanaCarrito;

public class JFramePrincipal extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private static List<ItemCarrito> carritoActual = new ArrayList<>();
    
	private CardLayout cardLayout;
	private JPanel panelContenidoDinamico;
	private JButton btnCatalogo;
	private JButton btnLogin;
	private JButton btnEventos;
	private static JButton btnCarritoGlobal;

    
	private static final String CARD_CATALOGO = "CATALOGO";
	private static final String CARD_EVENTOS = "EVENTOS";
	
	public JFramePrincipal() {
		this.setTitle("Tienda de Ropa DEUSTO - Menú Principal");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(1200, 800);
		this.setLocationRelativeTo(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		JPanel panelNavegacion = new JPanel();
		panelNavegacion.setLayout(new GridLayout(6, 1, 10, 10));
		panelNavegacion.setPreferredSize(new Dimension(200, 0));
		panelNavegacion.setBackground(new Color(44, 62, 80));
		panelNavegacion.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		

		btnCatalogo = crearBotonNavegacion("🛍️ Catálogo", CARD_CATALOGO);
		btnEventos = crearBotonNavegacion("🗓️ Eventos", CARD_EVENTOS);
		btnLogin = crearBotonNavegacion("🔑 Iniciar Sesión", "LOGIN");

		panelNavegacion.add(btnCatalogo);
		panelNavegacion.add(btnEventos);
		panelNavegacion.add(new JLabel(""));
		panelNavegacion.add(new JLabel(""));
		panelNavegacion.add(btnLogin);

		this.add(panelNavegacion, BorderLayout.WEST);

		cardLayout = new CardLayout();
		panelContenidoDinamico = new JPanel(cardLayout);
		panelContenidoDinamico.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		panelContenidoDinamico.add(crearPlaceholderPanel("Catálogo de Productos"), CARD_CATALOGO);
		panelContenidoDinamico.add(crearPlaceholderPanel("Eventos Pendientes"), CARD_EVENTOS);
		
		this.add(panelContenidoDinamico, BorderLayout.CENTER);
		
		JPanel pNorte = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
		btnCarritoGlobal = crearBotonCarrito();
		pNorte.add(btnCarritoGlobal);
		this.add(pNorte, BorderLayout.NORTH);
		
		cardLayout.show(panelContenidoDinamico, CARD_CATALOGO);
		
		this.setVisible(true);
	}

	private JButton crearBotonNavegacion(String texto, String comando) {
	    JButton btn = new JButton(texto);
	    btn.setFont(new Font("SansSerif", Font.BOLD, 14));
	    btn.setForeground(Color.WHITE);
	    btn.setBackground(new Color(52, 73, 94));
	    btn.setHorizontalAlignment(SwingConstants.LEFT);
	    btn.setFocusPainted(false);
	    btn.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
	    btn.setActionCommand(comando);
	    btn.addActionListener(this);
	    return btn;
	}
    
	private JPanel crearPlaceholderPanel(String titulo) {
	    JPanel panel = new JPanel(new GridBagLayout());
	    JLabel lbl = new JLabel("<html><h1 style='color: #95A5A6;'>Sección: " + titulo + "</h1>"
	        + "<p>Contenido en construcción...</p></html>", SwingConstants.CENTER);
	    lbl.setFont(new Font("SansSerif", Font.PLAIN, 24));
	    panel.add(lbl);
	    return panel;
	}

	public JButton crearBotonCarrito() {
	    int cantidadItems = carritoActual.size();
	    JButton btnCarrito = new JButton("🛒 Carrito (" + cantidadItems + ")");
	    btnCarrito.setToolTipText("Ver Carrito de Compras");
	    btnCarrito.setPreferredSize(new Dimension(150, 30));
	    
	    btnCarrito.addActionListener(e -> {
	        new VentanaCarrito(carritoActual).setVisible(true);
	    });
	    return btnCarrito;
	}
    
	public static void agregarItemAlCarrito(Producto p, int cantidad) {
	    boolean encontrado = false;
	    for (ItemCarrito item : carritoActual) {
	        if (item.getProducto().equals(p)) {
	            item.setCantidad(item.getCantidad() + cantidad);
	            encontrado = true;
	            break;
	        }
	    }
	    if (!encontrado) {
	        carritoActual.add(new ItemCarrito(p, cantidad));
	    }
	    actualizarBotonCarrito();
	}

	
	private static void actualizarBotonCarrito() {
	    if (btnCarritoGlobal != null) {
	        btnCarritoGlobal.setText("🛒 Carrito (" + carritoActual.size() + ")");
	    }
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
	    String command = e.getActionCommand();
	    
	    if (command.equals(CARD_CATALOGO) || command.equals(CARD_EVENTOS)) {
	        cardLayout.show(panelContenidoDinamico, command);
	    } 
	    else if (command.equals("LOGIN")) {
	        new JFrameAutenticacion().setVisible(true);
	    }
	}
	
	public static void main(String[] args) {
	    new JFramePrincipal();
	}
}
