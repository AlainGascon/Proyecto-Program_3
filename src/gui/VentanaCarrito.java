package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import domain.ItemCarrito;
import domain.Producto;

public class VentanaCarrito extends JFrame {

	private JPanel pNorte, pSur, pDerecha, pDerechaAbajo, pIzqAbajo;
	private JTable tabla;
	private JScrollPane scrollTabla;
	private ModeloTablaCompras modeloTabla; // Asumimos que existe ModeloTablaCompras
	private List<ItemCarrito> listaItems;
	private JLabel lblTotal;
	private JButton btnEliminar;	
	private JButton btnVaciar;
	private JButton btnPagar;
	private JButton btnSalir;
	

	public VentanaCarrito(List<ItemCarrito> lista) {
		super();
		listaItems = lista;

		setBounds(300, 200, 800, 400);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		pNorte = new JPanel(new BorderLayout(10,10));
		pSur = new JPanel(new BorderLayout(10,10));
		pDerecha = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
		pDerechaAbajo = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
		pIzqAbajo= new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));

		getContentPane().add(pNorte, BorderLayout.NORTH);
		getContentPane().add(pSur, BorderLayout.SOUTH);

		ImageIcon im2= new ImageIcon("imagenes/carrito.png");
		JLabel lblTitulo = new JLabel("Carrito",im2, SwingConstants.LEFT);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
		pNorte.add(lblTitulo, BorderLayout.WEST);
		
		// Tabla
		modeloTabla = new ModeloTablaCompras(listaItems);
		tabla = new JTable(modeloTabla);
		scrollTabla = new JScrollPane(tabla);
		getContentPane().add(scrollTabla, BorderLayout.CENTER);
		tabla.getModel().addTableModelListener(e->{
			lblTotal.setText("Total: " + String.format("%.2f", calcularTotal()) + "€");
		});

		//Botones
		btnEliminar = new JButton("Eliminar producto");
		btnVaciar = new JButton("Vaciar carrito");
		btnPagar = new JButton("Proceder al pago");
		btnPagar.setFont(new Font("Arial",Font.BOLD,14));
		btnPagar.setForeground(Color.WHITE);
		btnPagar.setBackground(new Color(46, 204, 113)	);
		ImageIcon im= new ImageIcon("imagenes/salir.png");
		btnSalir = new JButton(im);

		pIzqAbajo.add(btnEliminar);
		pIzqAbajo.add(btnVaciar);
		pDerecha.add(btnPagar);
		pDerechaAbajo.add(btnSalir);

		lblTotal = new JLabel("Total: " + String.format("%.2f", calcularTotal()) + "€");
		lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
		pDerecha.add(lblTotal, BorderLayout.EAST);

		pNorte.add(pDerecha, BorderLayout.EAST);
		pSur.add(pIzqAbajo, BorderLayout.WEST);
		pSur.add(pDerechaAbajo,BorderLayout.EAST);
		
		//Listeners
		btnEliminar.addActionListener((e)->{
			 int fila = tabla.getSelectedRow();
			 if (fila != -1) {
				 // Aseguramos que se actualicen las listas y la tabla
				 ItemCarrito itemRemovido = listaItems.remove(fila);
				 // Necesitamos notificar al JFramePrincipal para actualizar su lista estática global
				 // Si JFramePrincipal gestiona la lista estática, DEBE implementarse un método de remoción.
				 // Aquí asumiremos que solo actualizamos la tabla localmente:
				 modeloTabla = new ModeloTablaCompras(listaItems);
				 tabla.setModel(modeloTabla);
				 lblTotal.setText("Total: " + String.format("%.2f", calcularTotal()) + "€");
			 } else {
				 JOptionPane.showMessageDialog(this, "Selecciona un producto para eliminar");
			 }
		});
		
		btnVaciar.addActionListener(e -> vaciarCarrito());
		
		// 🔑 LISTENER DE PAGO MODIFICADO
		btnPagar.addActionListener((e)->{
			 if (listaItems.isEmpty()) {
				 JOptionPane.showMessageDialog(this, "El carrito está vacío.");
			 } else {
				 // 1. VERIFICACIÓN DE SESIÓN
				 if (JFramePrincipal.isLoggedIn()) {
					 // Está logueado: Proceder al pago
					 JOptionPane.showMessageDialog(this, "Procesando pago... ");
					 // Asumimos que VentanaPago existe y recibe el total
					 new VentanaPago(calcularTotal()).setVisible(true); 
					 vaciarCarrito();
					 
					 dispose(); 
				 } else {
					 
					 int respuesta = JOptionPane.showConfirmDialog(this, 
							 "Debe iniciar sesión para proceder con el pago.\n¿Desea iniciar sesión ahora?", 
							 "Autenticación Requerida", 
							 JOptionPane.YES_NO_OPTION, 
							 JOptionPane.WARNING_MESSAGE);
					 
					 if (respuesta == JOptionPane.YES_OPTION) {
						 new JFrameAutenticacion().setVisible(true);
						 
					 }
				 }
			 }
		});
		
		btnSalir.addActionListener((e)->{
			JOptionPane.showMessageDialog(null, "Se va a cerrar la aplicacion", "Cerrando...", JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		});

		btnEliminar.setForeground(Color.RED);
		btnVaciar.setBackground(Color.LIGHT_GRAY);
		btnVaciar.setForeground(Color.RED);
		
		setVisible(true);
	}

	private double calcularTotal() {
		double total = 0;
		for (ItemCarrito item : listaItems) {
			
			total += item.getCantidad() * item.getProducto().getPrecio();
		}
		return total;
	}

	private void vaciarCarrito() {
		listaItems.clear();
		modeloTabla = new ModeloTablaCompras(listaItems);
		tabla.setModel(modeloTabla);
		lblTotal.setText("Total: 0.00€");
		
	}

	
}