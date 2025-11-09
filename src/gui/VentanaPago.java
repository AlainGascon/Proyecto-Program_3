package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import gui.JFramePrincipal;
import domain.ItemCarrito;
import domain.Pago;

public class VentanaPago extends JFrame{ 
	private JLabel lblTitulo, lblTotal, lblDireccion, lblTitular,lblTarjeta, lblMetodo;
	private JPanel pNorte, pCentro, pSur;
	private JButton btnConfirmar, btnCancelar;
	private JTextField txtDireccion, txtTitular, txtTarjeta;
	private JComboBox<String> cbMetodoPago;
	private VentanaCarrito ventanaCarrito;;
	private List<ItemCarrito> listaItems;
	private double total;
	
	public VentanaPago(double total, VentanaCarrito ventanaCarrito, List<ItemCarrito> listaItems) {
		super();
		this.total= total;
		this.ventanaCarrito=ventanaCarrito;
		this.listaItems=listaItems;
		
		
		setBounds(300, 200, 800, 400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pNorte= new JPanel(new BorderLayout(15,15));
		pCentro= new JPanel(new GridLayout(4,2,10,15));
		pSur= new JPanel(new FlowLayout(FlowLayout.RIGHT,15,10));

		ImageIcon im= new ImageIcon("imagenes/pago.png");
		lblTitulo= new JLabel("Pago del Pedido",im, SwingConstants.LEFT);
		lblTotal= new JLabel("Total: "+String.format("%.2f", total)+"€");	
		lblDireccion= new JLabel("Dirección de envío");
		lblTitular= new JLabel("Títular de la tarjeta");
		lblTarjeta= new JLabel("Número de la tarjeta");
		lblMetodo= new JLabel("Método de pago");
		
		txtDireccion= new JTextField(20);
		txtTitular = new JTextField(20);
		txtTarjeta= new JTextField(20);
		
		cbMetodoPago= new JComboBox<>(new String[] {"Tarjeta","PayPal","Bizum"});
		
		btnCancelar= new JButton("Cancelar");
		btnCancelar.setForeground(Color.RED);
		btnConfirmar= new JButton("Confirmar pago");
		btnConfirmar.setForeground(Color.GREEN);
		btnConfirmar.setBackground(Color.LIGHT_GRAY);
		
		pNorte.add(lblTitulo, BorderLayout.WEST);
		pNorte.add(lblTotal,BorderLayout.EAST);
		pSur.add(btnCancelar);
		pSur.add(btnConfirmar);
		pCentro.add(lblDireccion);
		pCentro.add(txtDireccion);
		pCentro.add(lblTitular);
		pCentro.add(txtTitular);
		pCentro.add(lblTarjeta);
		pCentro.add(txtTarjeta);
		pCentro.add(lblMetodo);
		pCentro.add(cbMetodoPago);

		
		add(pNorte, BorderLayout.NORTH);
		add(pSur,BorderLayout.SOUTH);
		add(pCentro, BorderLayout.CENTER);
		
		//listeners
		btnCancelar.addActionListener((e)->{
			dispose();
		});
		
		btnConfirmar.addActionListener((e)->{
			String dirrecion= txtDireccion.getText();
			String titular= txtTitular.getText();
			String tarjeta= txtTarjeta.getText();
			String metodo= cbMetodoPago.getSelectedItem().toString();
			
			if(dirrecion.isEmpty() || titular.isEmpty() || tarjeta.isEmpty() ) {
				JOptionPane.showMessageDialog(this, "Completa todos los campos.");
				return;
			}
			int idPago= new Random().nextInt(100000);
			String transaccion= "TXN"+ System.currentTimeMillis();
			
			Pago pago= new Pago(idPago, total, metodo, "Pago Completado", new Date(), transaccion, tarjeta, titular);
			
			JOptionPane.showMessageDialog(this, "✅Pago realizado con éxito.\n\n"+"Transacción: "+pago.getNumTransaccion()+"\n");
			
			listaItems.clear();
			ventanaCarrito.actualizarTabla();
			ventanaCarrito.actualizarTotal();
			dispose();
		});
		
		setVisible(true);
	}
	/*public static void main(String[] args) {
		new VentanaPago(177.37);
	}*/
}











