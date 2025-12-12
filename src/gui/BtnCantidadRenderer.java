package gui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import domain.ItemCarrito;

public class BtnCantidadRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor{
	
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JButton btnMas, btnMenos;
	private int row;
	
	
	
	public BtnCantidadRenderer(JTable tabla, List<ItemCarrito> lista, JLabel lblTotal, ModeloTablaCompras modelo) {
		super();
		panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnMenos= new JButton("-");
		btnMas= new JButton("+");
		
		btnMenos.setMargin(new Insets(1, 5, 1, 5));
		btnMas.setMargin(new Insets(1, 5, 1, 5));
		
	btnMenos.addActionListener((e)->{
		ItemCarrito item=lista.get(row);
		if(item.getCantidad()>1) {
			item.setCantidad(item.getCantidad()-1);
		}else {
			lista.remove(row);
		}
		modelo.fireTableDataChanged();
		lblTotal.setText("Total: "+calcularTotal(lista)+"€");
	});	
	
	btnMas.addActionListener((e)->{
		ItemCarrito item=lista.get(row);
		item.setCantidad(item.getCantidad()+1);
		modelo.fireTableDataChanged();
		lblTotal.setText("Total: "+ calcularTotal(lista)+"€");
	});
	
	panel.add(btnMas);
	panel.add(btnMenos);
		
	}
	
	 private double calcularTotal(List<ItemCarrito> lista) {
	        double total = 0;
	        for (ItemCarrito item : lista) {
	            total += item.getCantidad() * item.getProducto().getPrecio();
	        }
	        return total;
	    }
	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub
		this.row=row;
		return panel;
	}
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		this.row=row;
		return panel;
	}
	
	
}