package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

import domain.ItemCarrito;
import domain.Producto;

public class VentanaCarrito extends JFrame {

    private static final long serialVersionUID = 1L;
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
        this.listaItems = lista;

        setBounds(300, 200, 800, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
        
        pNorte = new JPanel(new BorderLayout(10,10));
        pSur = new JPanel(new BorderLayout(10,10));
        pDerecha = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
        pDerechaAbajo = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
        pIzqAbajo= new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));

        getContentPane().add(pNorte, BorderLayout.NORTH);
        getContentPane().add(pSur, BorderLayout.SOUTH);

        JLabel lblTitulo = new JLabel("Carrito 🛒", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        pNorte.add(lblTitulo, BorderLayout.WEST);
        
        modeloTabla = new ModeloTablaCompras(listaItems);
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(30);
        configurarRenderersYEditors(); 
        
        scrollTabla = new JScrollPane(tabla);
        getContentPane().add(scrollTabla, BorderLayout.CENTER);
        
        tabla.getModel().addTableModelListener(e -> {
            actualizarTotal();
            JFramePrincipal.actualizarContadorCarritoGlobal(); 
        });
        
       btnEliminar = new JButton("Eliminar producto");
        btnVaciar = new JButton("Vaciar carrito");
        btnPagar = new JButton("Proceder al pago");
        btnPagar.setFont(new Font("Arial",Font.BOLD,14));
        btnPagar.setForeground(Color.WHITE);
        btnPagar.setBackground(new Color(46, 204, 113));
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

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila != -1) {
                listaItems.remove(fila);
                modeloTabla.fireTableDataChanged();
                actualizarTotal();
                JFramePrincipal.actualizarContadorCarritoGlobal();
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un producto para eliminar");
            }
        });
        
        btnVaciar.addActionListener(e -> vaciarCarrito());
        
        btnPagar.addActionListener(e -> {
            if (listaItems.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El carrito está vacío.");
            } else {
                new VentanaPago(calcularTotal(), this, listaItems);
            }
        });
        
        btnSalir.addActionListener(e -> dispose());

        btnEliminar.setForeground(Color.RED);
        btnVaciar.setBackground(Color.LIGHT_GRAY);
        btnVaciar.setForeground(Color.RED);
        
        setVisible(true);
    }

    private void configurarRenderersYEditors() {
        BtnCantidadRenderer rendererEditor = new BtnCantidadRenderer(tabla, listaItems, lblTotal, modeloTabla);
        tabla.getColumn("Acciones").setCellRenderer(rendererEditor);
        tabla.getColumn("Acciones").setCellEditor(rendererEditor);
    }
    
    private double calcularTotal() {
        double total = 0;
        for (ItemCarrito item : listaItems) {
            total += item.getCantidad() * item.getProducto().getPrecio();
        }
        return total;
    }

    private void vaciarCarrito() {
        if (listaItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El carrito ya está vacío.");
            return;
        }
        listaItems.clear();
        modeloTabla = new ModeloTablaCompras(listaItems);
        tabla.setModel(modeloTabla);
        configurarRenderersYEditors(); 
        actualizarTotal();
        JFramePrincipal.actualizarContadorCarritoGlobal();
    }

    public void actualizarTabla() {
        modeloTabla = new ModeloTablaCompras(listaItems);
        tabla.setModel(modeloTabla);
        configurarRenderersYEditors(); 
    }

    public void actualizarTotal() {
        lblTotal.setText("Total: "+String.format("%.2f", calcularTotal())+"€");
    }

    public void agregarProducto(Producto producto, int cantidad) {
        boolean encontrado = false;
        for (ItemCarrito item : listaItems) {
            if (item.getProducto().getId() == producto.getId()) {
                item.setCantidad(item.getCantidad() + cantidad);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            listaItems.add(new ItemCarrito(producto, cantidad, "M")); 
        }
        modeloTabla.fireTableDataChanged();
    }

}
