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
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import domain.ItemCarrito;
import domain.Producto;


public class JFramePrincipal extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private static boolean isLoggedIn = false;
    
	private static List<ItemCarrito> carritoActual = new ArrayList<>();
	private static JButton btnCarritoGlobalEstatico;
	
    private final List<Producto> listaProductos; 

	private CardLayout cardLayout;
	private JPanel panelContenidoDinamico;

	private static final String CARD_CATALOGO = "CATALOGO";
	private static final String CARD_EVENTOS = "EVENTOS";
    
    private static final String COMMAND_LOGIN = "LOGIN"; 
    private static final String COMMAND_PAGAR = "PAGAR"; 
 

	public JFramePrincipal(List<Producto> listaProductos, List<ItemCarrito> carritoInicial) {
        
        this.listaProductos = listaProductos;
        JFramePrincipal.carritoActual = carritoInicial;
		
		this.setTitle("Tienda de Ropa DEUSTO - Menú Principal");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(1200,800);
		this.setLocationRelativeTo(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		
		JPanel pNorte = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
		btnCarritoGlobalEstatico = crearBotonCarrito();
		pNorte.add(btnCarritoGlobalEstatico);
		this.add(pNorte, BorderLayout.NORTH);
		
		
		JPanel panelNavegacion = new JPanel();
		panelNavegacion.setLayout(new GridLayout(6, 1, 10, 10));
		panelNavegacion.setPreferredSize(new Dimension(200, 0));
		panelNavegacion.setBackground(new Color(44, 62, 80));
		panelNavegacion.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

		
		JButton btnCatalogo = crearBotonNavegacion("🛍️ Catálogo", CARD_CATALOGO);
		JButton btnEventos = crearBotonNavegacion("🗓️ Eventos", CARD_EVENTOS);
		JButton btnLogin = crearBotonNavegacion("🔑 Iniciar Sesión", COMMAND_LOGIN);

		panelNavegacion.add(btnCatalogo);
		panelNavegacion.add(btnEventos);
		panelNavegacion.add(new JLabel(""));
		panelNavegacion.add(new JLabel(""));
		panelNavegacion.add(new JLabel(""));
		panelNavegacion.add(btnLogin);

		this.add(panelNavegacion, BorderLayout.WEST);

		
		cardLayout = new CardLayout();
		panelContenidoDinamico = new JPanel(cardLayout);
		panelContenidoDinamico.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		panelContenidoDinamico.add(new JPanelCatalogo(listaProductos), CARD_CATALOGO);
		panelContenidoDinamico.add(crearPlaceholderPanel("Eventos Pendientes"), CARD_EVENTOS);
		
		this.add(panelContenidoDinamico, BorderLayout.CENTER);
		
		
		cardLayout.show(panelContenidoDinamico, CARD_CATALOGO);
		
		this.setVisible(true);
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
	    
	    
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0; 
	    gbc.gridy = 0; 
	    gbc.weightx = 1.0; 
	    gbc.weighty = 1.0; 
	    gbc.fill = GridBagConstraints.BOTH; 
	    gbc.anchor = GridBagConstraints.CENTER; 
	    
	    JLabel lbl = new JLabel("<html><h1 style='color: #95A5A6;'>Sección: " + titulo + "</h1><p>Contenido en construcción...</p></html>", SwingConstants.CENTER);
	    lbl.setFont(new Font("SansSerif", Font.PLAIN, 24));
	    
	    panel.add(lbl, gbc); 
	    return panel;
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

	    
	    carritoActual.add(new ItemCarrito(p, cantidad, talla));

	    
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
		
		if (command.equals(CARD_CATALOGO) || command.equals(CARD_EVENTOS)) {
			cardLayout.show(panelContenidoDinamico, command);
			panelContenidoDinamico.revalidate(); 
	        panelContenidoDinamico.repaint();
		}
		else if (command.equals(COMMAND_LOGIN)) {
			new JFrameAutenticacion().setVisible(true);
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