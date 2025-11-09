/*public class VentanaCarrito extends JFrame{
	private JPanel pNorte, pSur, pEste,pOeste,pCentro, pBotones;
	private JButton btnEliminar, btnVaciar, btnPagar;
	private JTable tablaCarrito;
	
}*/
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
    private ModeloTablaCompras modeloTabla;
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
        JLabel lblTitulo = new JLabel("Carrito🛒",im2, SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        pNorte.add(lblTitulo, BorderLayout.WEST);
        
        //JLabel lblCarro= new JLabel(im2);
        //pNorte.add(lblCarro,BorderLayout.WEST);

        // Tabla
        modeloTabla = new ModeloTablaCompras(listaItems);
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(30);
        scrollTabla = new JScrollPane(tabla);
        getContentPane().add(scrollTabla, BorderLayout.CENTER);
        tabla.getModel().addTableModelListener(e->{
        	lblTotal.setText("Total: " + String.format("%.2f", calcularTotal())+"€");
        });
        tabla.getColumn("Acciones").setCellRenderer(new BtnCantidadRenderer(tabla, lista, lblTotal, modeloTabla));
        tabla.getColumn("Acciones").setCellEditor(new BtnCantidadRenderer(tabla, lista, lblTotal, modeloTabla));


        //Botones
        btnEliminar = new JButton("Eliminar producto");
        btnVaciar = new JButton("Vaciar carrito");
        btnPagar = new JButton("Proceder al pago");
        btnPagar.setFont(new Font("Arial",Font.BOLD,14));
        btnPagar.setForeground(Color.WHITE);
        btnPagar.setBackground(new Color(46, 204, 113)	);
        //ImageIcon im= new ImageIcon("imagenes/salir.png");
        btnSalir = new JButton("Volver al catálogo");
        btnSalir.setForeground(Color.BLACK);
        btnSalir.setBackground(Color.LIGHT_GRAY);

        pIzqAbajo.add(btnEliminar);
        pIzqAbajo.add(btnVaciar);
        pDerecha.add(btnPagar);
        pDerechaAbajo.add(btnSalir);

        lblTotal = new JLabel("Total: " + String.format("%.2f", calcularTotal())+"€");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        pDerecha.add(lblTotal, BorderLayout.EAST);

        pNorte.add(pDerecha, BorderLayout.EAST);
        pSur.add(pIzqAbajo, BorderLayout.WEST);
        pSur.add(pDerechaAbajo,BorderLayout.EAST);
        //Listeners
        btnEliminar.addActionListener((e)->{
        	 int fila = tabla.getSelectedRow();
             if (fila != -1) {
                 listaItems.remove(fila);
                 modeloTabla.fireTableDataChanged();
                 lblTotal.setText("Total: " + String.format("%.2f", calcularTotal())+"€");
             } else {
                 JOptionPane.showMessageDialog(this, "Selecciona un producto para eliminar");
             }
        });
        
        /*btnVaciar.addActionListener((e)->{
        	listaItems.clear();
            modeloTabla = new ModeloTablaCompras(listaItems);
            tabla.setModel(modeloTabla);
            lblTotal.setText("Total: 0.00$");
        });*/
        btnVaciar.addActionListener(e -> vaciarCarrito());
        
        btnPagar.addActionListener((e)->{
        	 if (listaItems.isEmpty()) {
                 JOptionPane.showMessageDialog(this, "El carrito está vacío.");
             } else {
                 JOptionPane.showMessageDialog(this, "Procesando pago... ");
                 new VentanaPago(calcularTotal(),this,listaItems);
                // vaciarCarrito();
             }
        });
        
        btnSalir.addActionListener((e)->{
			//JOptionPane.showMessageDialog(null, "Se va a cerrar la aplicacion", "Cerrando...", JOptionPane.WARNING_MESSAGE);
			//new JPanelCatalogo(null);
			dispose();
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

   public void actualizarTabla() {
	   modeloTabla= new ModeloTablaCompras(listaItems);
	   tabla.setModel(modeloTabla);
   }
   
   public void actualizarTotal() {
	   lblTotal.setText("Total: "+String.format("%.2f", calcularTotal())+"€");
   }
   
   public void agregarProducto(Producto producto, int cantidad) {
	   boolean encontrado= false;
	   for(ItemCarrito item: listaItems) {
		   if(item.getProducto().getId()==producto.getId()) {
			   item.setCantidad(item.getCantidad()+cantidad);
			   encontrado=true;
			   break;
		   }
	   }
	   if(!encontrado) {
		   listaItems.add(new ItemCarrito(producto, cantidad, getName()));
	   }
	   modeloTabla.fireTableDataChanged();
	   lblTotal.setText("Total: "+String.format("%.2f", calcularTotal())+"€");
	   
   }
/*<<<<<<< HEAD
    public static void main(String[] args) {
        Producto p1 = new Producto(1, "Camiseta básica", "Camiseta de algodón 100%", 15.99, "M", "Blanco", 50, "Ropa", "H&M", true);
        Producto p2 = new Producto(2, "Pantalón vaquero", "Jeans azul oscuro ajustado", 39.99, "L", "Azul", 30, "Ropa", "Levi’s", true);
        Producto p3 = new Producto(3, "Zapatillas deportivas", "Zapatillas ligeras para correr", 59.90, "42", "Negro", 20, "Calzado", "Nike", true);
        Producto p4 = new Producto(4, "Sudadera con capucha", "Sudadera cómoda para invierno", 45.50, "M", "Gris", 15, "Ropa", "Adidas", true);

        List<ItemCarrito> lista = new ArrayList<>();
        lista.add(new ItemCarrito(p1, 2)); 
        lista.add(new ItemCarrito(p2, 1)); 
        lista.add(new ItemCarrito(p3, 1));  
        lista.add(new ItemCarrito(p4, 1)); 
        
       

        new VentanaCarrito(lista);
    }
=======
    
>>>>>>> branch 'main' of git@github.com:AlainGascon/Proyecto-Program_3.git
*/
   
}

