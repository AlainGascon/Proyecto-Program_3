package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import domain.Evento;
import domain.ItemCarrito;
import domain.Producto;

public class JFramePrincipal extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private static boolean isLoggedIn = false;
    private static List<ItemCarrito> carritoActual = new ArrayList<>();
    private static JButton btnCarritoGlobalEstatico;

    private static final Color COLOR_FONDO_NAV = new Color(33, 37, 41);
    private static final Color COLOR_ACCENTO = new Color(52, 152, 219); 
    private static final Color COLOR_TEXTO_BLANCO = new Color(248, 249, 250);
    private static final Color COLOR_BOTON_CARRITO = new Color(46, 204, 113); 

    private static List<Producto> listaProductosEstatica; 
    private final List<Producto> listaProductos;
    private final List<Evento> listaEventos; 

    private CardLayout cardLayout;
    private JPanel panelContenidoDinamico;

    private static final String CARD_CATALOGO = "CATALOGO";
    private static final String CARD_EVENTOS = "EVENTOS";
    private static final String CARD_JUEGO_DESCUENTO = "JUEGO_DESCUENTO"; 
    private static final String CARD_RECURSIVIDAD = "RECURSIVIDAD";

    private static final String COMMAND_LOGIN = "LOGIN";

    public JFramePrincipal(List<Producto> listaProductos, List<ItemCarrito> carritoInicial) {
        this.listaProductos = listaProductos;
        JFramePrincipal.carritoActual = carritoInicial;
        JFramePrincipal.listaProductosEstatica = listaProductos;
        this.listaEventos = generarDatosEventos(); 

        this.setTitle("DEUSTO FASHION STORE");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setMinimumSize(new Dimension(1200, 800));
        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        cambiarIconoVentana();

        JPanel pNorte = new JPanel(new BorderLayout());
        pNorte.setBackground(Color.WHITE);
        pNorte.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)), 
            BorderFactory.createEmptyBorder(10, 20, 10, 20) 
        ));
        
        JLabel lblTitulo = new JLabel("  DEUSTO FASHION");
        lblTitulo.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 24));
        lblTitulo.setForeground(COLOR_FONDO_NAV);
        pNorte.add(lblTitulo, BorderLayout.WEST);

        JPanel pControlesCarrito = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        pControlesCarrito.setOpaque(false);
        btnCarritoGlobalEstatico = crearBotonCarrito();
        pControlesCarrito.add(btnCarritoGlobalEstatico);
        pNorte.add(pControlesCarrito, BorderLayout.EAST);
        
        this.add(pNorte, BorderLayout.NORTH);

        JPanel panelNavegacion = new JPanel();
        panelNavegacion.setLayout(new BoxLayout(panelNavegacion, BoxLayout.Y_AXIS));
        panelNavegacion.setPreferredSize(new Dimension(240, 0));
        panelNavegacion.setBackground(COLOR_FONDO_NAV);
        panelNavegacion.setBorder(new EmptyBorder(30, 0, 30, 0));

        panelNavegacion.add(crearBotonNavegacion("     Catálogo", CARD_CATALOGO));
        panelNavegacion.add(Box.createRigidArea(new Dimension(0, 5)));
        panelNavegacion.add(crearBotonNavegacion("     Eventos", CARD_EVENTOS));
        panelNavegacion.add(Box.createRigidArea(new Dimension(0, 5)));
        panelNavegacion.add(crearBotonNavegacion("     Descuento", CARD_JUEGO_DESCUENTO));
        panelNavegacion.add(Box.createRigidArea(new Dimension(0, 5)));
        panelNavegacion.add(crearBotonNavegacion("     Posibles Compras", CARD_RECURSIVIDAD));
        
        panelNavegacion.add(Box.createVerticalGlue()); 

        JButton btnLogin = crearBotonNavegacion("  🔑  Iniciar Sesión", COMMAND_LOGIN);
        btnLogin.setBackground(new Color(44, 62, 80));
        panelNavegacion.add(btnLogin);

        this.add(panelNavegacion, BorderLayout.WEST);

        cardLayout = new CardLayout();
        panelContenidoDinamico = new JPanel(cardLayout);
        panelContenidoDinamico.setBackground(new Color(245, 246, 250));
        
        panelContenidoDinamico.add(new JPanelCatalogo(listaProductos), CARD_CATALOGO);
        panelContenidoDinamico.add(new VentanaEventos(this.listaEventos), CARD_EVENTOS); 
        panelContenidoDinamico.add(new JuegoDescuento(), CARD_JUEGO_DESCUENTO); 

        this.add(panelContenidoDinamico, BorderLayout.CENTER);

        cardLayout.show(panelContenidoDinamico, CARD_CATALOGO);
        this.setVisible(true);
    }

    private JButton crearBotonNavegacion(String texto, String comando) {
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(240, 50));
        btn.setPreferredSize(new Dimension(240, 50));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setForeground(COLOR_TEXTO_BLANCO);
        btn.setBackground(COLOR_FONDO_NAV);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setActionCommand(comando);
        btn.addActionListener(this);
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(COLOR_ACCENTO); }
            public void mouseExited(MouseEvent e) { btn.setBackground(COLOR_FONDO_NAV); }
        });
        return btn;
    }

    public JButton crearBotonCarrito() {
        int cantidadItems = calcularTotalArticulos();
        JButton btn = new JButton("🛒 CARRITO (" + cantidadItems + ")");
        btn.setFont(new Font("Segoe UI Bold", Font.BOLD, 12));
        btn.setBackground(COLOR_BOTON_CARRITO);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> {
            VentanaCarrito ventana = new VentanaCarrito(carritoActual);
            ventana.setVisible(true);
        });
        return btn;
    }

    private void cambiarIconoVentana() {
        try {
            Image icono = new ImageIcon(getClass().getResource("/images/iconoP.png")).getImage();
            this.setIconImage(icono);
        } catch (Exception e) {
            System.err.println("Icono no encontrado");
        }
    }

    private static int calcularTotalArticulos() {
        int total = 0;
        for (ItemCarrito item : carritoActual) total += item.getCantidad();
        return total;
    }

    public static void actualizarContadorCarritoGlobal() {
        if (btnCarritoGlobalEstatico != null) {
            SwingUtilities.invokeLater(() -> {
                btnCarritoGlobalEstatico.setText("🛒" + "CARRITO (" + calcularTotalArticulos() + ")");
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(CARD_CATALOGO) || command.equals(CARD_EVENTOS) || command.equals(CARD_JUEGO_DESCUENTO)) {
            cardLayout.show(panelContenidoDinamico, command);
        } else if (command.equals(CARD_RECURSIVIDAD)) {
            ejecutarAlgoritmoRecursivo();
        } else if (command.equals(COMMAND_LOGIN)) {
            new JFrameAutenticacion(null).setVisible(true);
        }
    }

    private void ejecutarAlgoritmoRecursivo() {
        DialogoFiltroRecursivo diag = new DialogoFiltroRecursivo(this);
        diag.setVisible(true);

        String talla = diag.getTalla();
        Double importe = diag.getPresupuesto();

        if (talla != null && importe != null) {
            List<Producto> productosConStock = listaProductos.stream()
                    .filter(p -> p.getInventarioPorTalla().getOrDefault(talla, 0) > 0)
                    .toList();

            List<List<Producto>> resultados = new ArrayList<>();
            calcularComprasPosibles(resultados, productosConStock, importe, 0, new ArrayList<>());

            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay combinaciones para talla " + talla + " con ese presupuesto.");
            } else {
                new VentanaResultadosRecursividad(this, resultados, importe).setVisible(true);
            }
        }
    }

    private void calcularComprasPosibles(List<List<Producto>> result, List<Producto> elementos, double disponible, int indice, List<Producto> temp) {
        if (!temp.isEmpty()) {
            result.add(new ArrayList<>(temp));
        }

        for (int i = indice; i < elementos.size(); i++) {
            Producto p = elementos.get(i);

            if (p.getPrecio() <= disponible + 0.01) {
                temp.add(p);
                calcularComprasPosibles(result, elementos, disponible - p.getPrecio(), i + 1, temp);
                temp.remove(temp.size() - 1);
            }
        }
    }

    public static boolean isLoggedIn() { 
    	return isLoggedIn; 
    	}
    public static void setLoggedIn(boolean status) { 
    	isLoggedIn = status; 
    	}
    public List<Evento> generarDatosEventos() {
    	return new ArrayList<>(); 
    	}
    
    public static void agregarItemAlCarrito(Producto p, int cantidad, String talla) {
        decrementarStock(p, cantidad, talla);
        boolean encontrado= false;
        for(ItemCarrito item: carritoActual) {
        	if(item.getProducto().getNombre().equals(p.getNombre()) && item.getTalla().equals(talla)) {
        		item.setCantidad(item.getCantidad() + cantidad);
        		encontrado=true;
        		break;
        	}
        }
        if(!encontrado) {
            carritoActual.add(new ItemCarrito(p, cantidad, talla));

        }
        actualizarContadorCarritoGlobal();
    }
    
    public static boolean decrementarStock(Producto producto, int cantidad, String talla) {
        if (listaProductosEstatica != null) {
            Optional<Producto> pStock = listaProductosEstatica.stream().filter(p -> p.getNombre().equals(producto.getNombre())).findFirst();
            if (pStock.isPresent()) { pStock.get().decrementarStock(talla, cantidad); return true; }
        }
        return false;
    }
}