package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import domain.ItemCarrito;
import domain.Producto;

public class VentanaCarrito extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private static final Color COLOR_PRIMARIO = new Color(41, 128, 185); 
    private static final Color COLOR_ACENTO = new Color(46, 204, 113); 
    private static final Color COLOR_PRECIO = new Color(231, 76, 60); 
    private static final Color COLOR_FONDO_CLARO = new Color(236, 240, 241);

    private JPanel pNorte = new JPanel(new GridBagLayout()); 
    private JPanel pSur, pNorteDerechaContenedor, pIzqAbajo; 
    private JTable tabla;
    private JScrollPane scrollTabla;
    private ModeloTablaCompras modeloTabla;
    private List<ItemCarrito> listaItems;
    private JLabel lblTotal, lblDescuento;
    private JButton btnEliminar;
    private JButton btnVaciar;
    private JButton btnPagar;
    private JButton btnSalir;
    public static boolean descuentoAplicado=false;

    public VentanaCarrito(List<ItemCarrito> lista) {
        super(" Carrito de Compras");
        this.listaItems = lista;

        
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(this);


        getContentPane().setBackground(COLOR_FONDO_CLARO); 

        setBounds(300, 200, 900, 600); 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        
        pNorte.setBackground(COLOR_FONDO_CLARO); 
        pNorte.setBorder(new EmptyBorder(15, 20, 15, 20)); 
        
        pSur = new JPanel(new BorderLayout(20, 10));
        pSur.setBackground(Color.WHITE);
        pSur.setBorder(new EmptyBorder(10, 10, 10, 10));

        pIzqAbajo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pIzqAbajo.setBackground(Color.WHITE);
        
        
        pNorteDerechaContenedor = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pNorteDerechaContenedor.setBackground(COLOR_FONDO_CLARO);
        
        
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 0, 5);
        
        getContentPane().add(pNorte, BorderLayout.NORTH);
        getContentPane().add(pSur, BorderLayout.SOUTH);

        
        
        JLabel lblTitulo = new JLabel("Mi Carrito  ", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28)); 
        lblTitulo.setForeground(new Color(51, 51, 51));
        
        gbc.gridx = 0;
        gbc.weightx = 0.5; 
        gbc.anchor = GridBagConstraints.WEST;
        pNorte.add(lblTitulo, gbc);
        
        lblDescuento = new JLabel("  ¡20% de descuento aplicado! ");
        lblDescuento.setFont(new Font("Arial", Font.BOLD, 15)); 
        lblDescuento.setForeground(COLOR_ACENTO);
        lblDescuento.setVisible(false);
        
        gbc.gridx = 1;
        gbc.weightx = 0.5; 
        gbc.anchor = GridBagConstraints.CENTER;
        pNorte.add(lblDescuento, gbc);

        
        modeloTabla = new ModeloTablaCompras(listaItems);
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(40); 
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabla.getTableHeader().setBackground(new Color(230, 230, 230)); 
        tabla.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        tabla.setGridColor(new Color(230, 230, 230));
        tabla.setIntercellSpacing(new Dimension(0, 1)); 
        
        configurarRenderersYEditors(); 
        
        scrollTabla = new JScrollPane(tabla);
        
        scrollTabla.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        getContentPane().add(scrollTabla, BorderLayout.CENTER);
        
        tabla.getModel().addTableModelListener(e -> {
            actualizarTotal();
            JFramePrincipal.actualizarContadorCarritoGlobal(); 
        });
        
        
        
        btnEliminar = new JButton("  Eliminar Producto");
        btnVaciar = new JButton("  Vaciar Carrito");
        btnPagar = new JButton("  Proceder al Pago");
        btnSalir = new JButton("  Volver al Catálogo");
        
        
        btnPagar.setFont(new Font("Arial", Font.BOLD, 16));
        btnPagar.setForeground(Color.WHITE);
        btnPagar.setBackground(COLOR_PRIMARIO); 
        btnPagar.setFocusPainted(false);
        btnPagar.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        
        
        btnSalir.setFont(new Font("Arial", Font.PLAIN, 14));
        btnSalir.setForeground(new Color(51, 51, 51));
        btnSalir.setBackground(new Color(240, 240, 240));
        btnSalir.setFocusPainted(false);
        btnSalir.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        
        btnEliminar.setForeground(COLOR_PRECIO); 
        btnEliminar.setBackground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBorder(BorderFactory.createLineBorder(COLOR_PRECIO, 1)); 
        
        btnVaciar.setForeground(COLOR_PRECIO); 
        btnVaciar.setBackground(Color.WHITE); 
        btnVaciar.setFocusPainted(false);
        btnVaciar.setBorder(BorderFactory.createLineBorder(COLOR_PRECIO, 1));
        
        
        lblTotal = new JLabel("Total: " + String.format("%.2f", calcularTotal())+"€");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 22)); 
        lblTotal.setForeground(new Color(51, 51, 51));

        
        pNorteDerechaContenedor.add(lblTotal);
        pNorteDerechaContenedor.add(btnPagar);

        gbc.gridx = 2;
        gbc.weightx = 0.5; 
        gbc.anchor = GridBagConstraints.EAST;
        pNorte.add(pNorteDerechaContenedor, gbc);

       
        pIzqAbajo.add(btnEliminar);
        pIzqAbajo.add(btnVaciar);
        
        pSur.add(pIzqAbajo, BorderLayout.WEST);
        
        
        JPanel pSalirContenedor = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pSalirContenedor.setBackground(Color.WHITE);
        pSalirContenedor.add(btnSalir);
        pSur.add(pSalirContenedor, BorderLayout.EAST);
        
        
        
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
        if(descuentoAplicado) {
            total=total * 0.80;
            lblDescuento.setVisible(true);
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
        if(descuentoAplicado) {
            lblDescuento.setVisible(true);
        }else {
            lblDescuento.setVisible(false);
        }
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
    
    public void setDescuento(boolean valor) {
        descuentoAplicado = valor;
        lblTotal.setText("Total: "+String.format("%.2f", calcularTotal())+"€");
    }
}
