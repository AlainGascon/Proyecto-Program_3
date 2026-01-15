package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import domain.ItemCarrito;
import domain.Pago;

public class VentanaPago extends JFrame { 
    
    private static final long serialVersionUID = 1L;
    
    // PALETA DE COLORES UNIFICADA
    private static final Color COLOR_PRIMARIO = new Color(41, 128, 185);
    private static final Color COLOR_ACENTO = new Color(46, 204, 113);
    private static final Color COLOR_PRECIO = new Color(231, 76, 60);
    private static final Color COLOR_FONDO_CLARO = new Color(236, 240, 241);
    private static final Color COLOR_TEXTO_PRINCIPAL = new Color(51, 51, 51);
    private static final Color COLOR_FONDO_PANELES = Color.WHITE;
    
    // FUENTES ESTANDARIZADAS
    private static final Font FUENTE_TITULO = new Font("Arial", Font.BOLD, 28);
    private static final Font FUENTE_SUBTITULO = new Font("Arial", Font.BOLD, 22);
    private static final Font FUENTE_DESTACADO = new Font("Arial", Font.BOLD, 16);
    private static final Font FUENTE_NORMAL = new Font("Arial", Font.PLAIN, 14);
    
    private JLabel lblTitulo, lblTotal, lblDireccion, lblTitular, lblTarjeta, lblMetodo;
    private JPanel pNorte, pCentro, pSur;
    private JButton btnConfirmar, btnCancelar;
    private JTextField txtDireccion, txtTitular, txtTarjeta;
    private JComboBox<String> cbMetodoPago;
    private VentanaCarrito ventanaCarrito;
    private List<ItemCarrito> listaItems;
    private double total;
    
    public VentanaPago(double total, VentanaCarrito ventanaCarrito, List<ItemCarrito> listaItems) {
        super("Procesar Pago - Deusto Fashion");
        this.total = total;
        this.ventanaCarrito = ventanaCarrito;
        this.listaItems = listaItems;
        
        setBounds(300, 200, 800, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // CONFIGURACIÓN DE PANELES
        pNorte = new JPanel(new BorderLayout(15, 15));
        pCentro = new JPanel(new GridLayout(4, 2, 10, 15));
        pSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        
        pNorte.setBackground(COLOR_FONDO_CLARO);
        pNorte.setBorder(new EmptyBorder(20, 20, 20, 20));
        pCentro.setBackground(COLOR_FONDO_PANELES);
        pCentro.setBorder(new EmptyBorder(20, 30, 20, 30));
        pSur.setBackground(COLOR_FONDO_CLARO);
        pSur.setBorder(new EmptyBorder(10, 20, 20, 20));
        
        // TÍTULO CON ICONO
        ImageIcon im = new ImageIcon("imagenes/pago.png");
        lblTitulo = new JLabel("Pago del Pedido", im, SwingConstants.LEFT);
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_TEXTO_PRINCIPAL);
        
        // LABEL TOTAL CON PRECIO EN ROJO
        lblTotal = new JLabel("<html>Total: <span style='color: rgb(231,76,60); font-weight: bold;'>" + 
                             String.format("%.2f", total) + "€</span></html>");
        lblTotal.setFont(FUENTE_SUBTITULO);
        lblTotal.setForeground(COLOR_TEXTO_PRINCIPAL);
        
        // LABELS DE CAMPOS
        lblDireccion = new JLabel("Dirección de envío:");
        lblTitular = new JLabel("Titular de la tarjeta:");
        lblTarjeta = new JLabel("Número de la tarjeta:");
        lblMetodo = new JLabel("Método de pago:");
        
        // Estilo consistente para labels
        Font fontLabel = FUENTE_NORMAL;
        lblDireccion.setFont(fontLabel);
        lblTitular.setFont(fontLabel);
        lblTarjeta.setFont(fontLabel);
        lblMetodo.setFont(fontLabel);
        
        lblDireccion.setForeground(COLOR_TEXTO_PRINCIPAL);
        lblTitular.setForeground(COLOR_TEXTO_PRINCIPAL);
        lblTarjeta.setForeground(COLOR_TEXTO_PRINCIPAL);
        lblMetodo.setForeground(COLOR_TEXTO_PRINCIPAL);
        
        // CAMPOS DE TEXTO ESTANDARIZADOS
        txtDireccion = crearCampoTexto();
        txtTitular = crearCampoTexto();
        txtTarjeta = crearCampoTexto();
        
        // COMBOBOX
        cbMetodoPago = new JComboBox<>(new String[] {"Tarjeta", "PayPal", "Bizum"});
        cbMetodoPago.setFont(FUENTE_NORMAL);
        cbMetodoPago.setBackground(Color.WHITE);
        
        // BOTONES ESTANDARIZADOS
        btnCancelar = crearBotonSecundario("Cancelar");
        btnConfirmar = crearBotonPrimario("Confirmar pago");
        
        // AÑADIR COMPONENTES
        pNorte.add(lblTitulo, BorderLayout.WEST);
        pNorte.add(lblTotal, BorderLayout.EAST);
        
        pCentro.add(lblDireccion);
        pCentro.add(txtDireccion);
        pCentro.add(lblTitular);
        pCentro.add(txtTitular);
        pCentro.add(lblTarjeta);
        pCentro.add(txtTarjeta);
        pCentro.add(lblMetodo);
        pCentro.add(cbMetodoPago);
        
        pSur.add(btnCancelar);
        pSur.add(btnConfirmar);
        
        add(pNorte, BorderLayout.NORTH);
        add(pCentro, BorderLayout.CENTER);
        add(pSur, BorderLayout.SOUTH);
        
        // LISTENERS
        btnCancelar.addActionListener((e) -> {
            dispose();
        });
        
        btnConfirmar.addActionListener((e) -> {
            String direccion = txtDireccion.getText();
            String titular = txtTitular.getText();
            String tarjeta = txtTarjeta.getText();
            String metodo = cbMetodoPago.getSelectedItem().toString();
            
            if (direccion.isEmpty() || titular.isEmpty() || tarjeta.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa todos los campos.");
                return;
            }
            
            int idPago = new Random().nextInt(100000);
            String transaccion = "TXN" + System.currentTimeMillis();
            
            Pago pago = new Pago(idPago, total, metodo, "Pago Completado", new Date(), transaccion, tarjeta, titular);
            
            JOptionPane.showMessageDialog(this, 
                "✅ Pago realizado con éxito.\n\n" + 
                "Transacción: " + pago.getNumTransaccion() + "\n");
            
            listaItems.clear();
            ventanaCarrito.actualizarTabla();
            ventanaCarrito.actualizarTotal();
            dispose();
        });
        
        setVisible(true);
    }
    
    // MÉTODOS PARA CREAR COMPONENTES ESTANDARIZADOS
    private JTextField crearCampoTexto() {
        JTextField campo = new JTextField(20);
        campo.setFont(FUENTE_NORMAL);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return campo;
    }
    
    private JButton crearBotonPrimario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(FUENTE_DESTACADO);
        btn.setForeground(Color.WHITE);
        btn.setBackground(COLOR_PRIMARIO);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    private JButton crearBotonSecundario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(FUENTE_NORMAL);
        btn.setForeground(COLOR_PRECIO);
        btn.setBackground(COLOR_FONDO_PANELES);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(COLOR_PRECIO, 1));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}