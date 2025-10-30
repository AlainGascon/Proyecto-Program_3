package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer; 

import javax.swing.JTable;

public class JPanelCatalogo extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private int filaTablaProductos = -1;
	public JTextField display;
	
	
	private JTable tablaProductos;
	private DefaultTableModel modeloDatosProductos;
	private JScrollPane scrollPaneProductos;
	private JTextField txtFiltro;
	private JTable tablaExplicacion;
	private DefaultTableModel modeloDatosExplicacion;
	private JScrollPane scrollPaneExplicacion;
	
	public JPanelCatalogo() {
        setTitle("Catálogo de Productos");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); 
        getContentPane().setBackground(Color.WHITE);
        
        tablaProductos = new JTable();
        tablaExplicacion = new JTable();
        initTables();

        
        JPanel panelIzquierda = panelProductos();
        add(panelIzquierda, BorderLayout.WEST);

        
        JPanel panelDerecha = new JPanel(new GridLayout(2, 1, 10, 10));

        JPanel panelSuperior = new JPanel();
        panelSuperior.setBorder(new TitledBorder("CARACTERISTICAS"));
        panelSuperior.add(new JLabel("TALLA,STOCK,..."));

        JPanel panelInferior = new JPanel();
        panelInferior.setBorder(new TitledBorder("????"));
        panelInferior.add(new JLabel());

        panelDerecha.add(panelSuperior);
        panelDerecha.add(panelInferior);

        add(panelDerecha, BorderLayout.CENTER);
    }
	

	JPanel panelCatalogo = new JPanel(new BorderLayout());
	
	
	public JPanel panelProductos() {
		
		
		JScrollPane scrollPaneProductos = new JScrollPane(this.tablaProductos);
		scrollPaneProductos.setBorder(new TitledBorder("Productos: "));
		this.tablaProductos.setFillsViewportHeight(true);
		
		this.txtFiltro = new JTextField(20);
		
		JPanel panelFiltro = new JPanel();
        panelFiltro.add(new JLabel("Filtro: "));
        panelFiltro.add(txtFiltro);
	
		JPanel panelProductos = new JPanel(new BorderLayout());
        panelProductos.setBorder(new TitledBorder("Productos de la tienda"));
        panelProductos.add(BorderLayout.CENTER, scrollPaneProductos);
        panelProductos.add(BorderLayout.NORTH, panelFiltro);
		
		
		MouseMotionAdapter miMouseMotionListener = new MouseMotionAdapter() {
			
				public void mouseMoved(MouseEvent e) {
				
				Point puntoRaton = new Point(e.getX(), e.getY());
				filaTablaProductos = tablaProductos.rowAtPoint(puntoRaton);
				tablaProductos.repaint();				
			
				}
			};
				
			MouseAdapter miMouseAdapter = new MouseAdapter() {
				@Override
			
				public void mouseExited(MouseEvent e) {
					filaTablaProductos = -1;
					tablaProductos.repaint();
				}
			};
		
		
		this.tablaProductos.addMouseMotionListener(miMouseMotionListener);
		this.tablaProductos.addMouseListener(miMouseAdapter);
		
		
		return panelProductos; 
		
	}
	
	
	private void initTables() { 
		
		Vector<String> cabeceraComics = new Vector<String>(Arrays.asList( "ARTICULO", "IMAGEN", "PRECIO(€) "));
		//Se crea el modelo de datos para la tabla de comics sólo con la cabecera
		this.modeloDatosProductos = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceraComics);
		
		TableCellRenderer cellRenderer = (table, value, isSelected, hasFocus, row, column) -> {
			
			
			
			JLabel result = new JLabel(value.toString());
			
			
			
			if (isSelected || (table.equals(tablaProductos) && filaTablaProductos == row)) {
				result.setBackground(table.getSelectionBackground());
				result.setForeground(table.getSelectionForeground());
			}
			
			if (table.equals(tablaProductos)) {
				if (row % 2 == 0) {
					result.setBackground(new Color(250, 250, 250));
				} else {
					result.setBackground(new Color(230, 255, 230));
				}
			}
			
			result.setOpaque(true);
			return result;
		};
		
		tablaProductos.setDefaultRenderer(Object.class, cellRenderer);
		
		this.tablaProductos.setRowHeight(26);
		this.tablaExplicacion.setRowHeight(26);
		
		
		this.tablaProductos.getTableHeader().setReorderingAllowed(false);
		this.tablaProductos.setAutoCreateRowSorter(true);
		
		
		
	
	}
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JPanelCatalogo frame = new JPanelCatalogo();
            frame.setVisible(true);
        });
    }
}
