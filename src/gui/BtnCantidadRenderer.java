package gui;

import java.awt.Component;
import java.awt.FlowLayout;
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
	private JPanel panel;
	private JButton btnMas, btnMenos;
	private int row;
	
	
	
	public BtnCantidadRenderer(JTable tabla, List<ItemCarrito> lista, JLabel lblTotal, ModeloTablaCompras modelo) {
		super();
		panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnMenos= new JButton("-");
		btnMas= new JButton("+");
		
		
		
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
		return null;
	}
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
