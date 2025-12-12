package gui;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.DefaultTableModel;

import domain.ItemCarrito;

public class ModeloTablaCompras extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;
	private List<String> titulos = Arrays.asList("Producto", "Cantidad", "Precio Unitario", "Total","Acciones");
    private List<ItemCarrito> listaItems;

    public ModeloTablaCompras(List<ItemCarrito> lista) {
        listaItems = lista;
    }

    @Override
    public int getRowCount() {
        if (listaItems == null) return 0;
        return listaItems.size();
    }

    @Override
    public int getColumnCount() {
        return titulos.size();
    }

    @Override
    public String getColumnName(int column) {
        return titulos.get(column);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column==4;
    }

    @Override
    public Object getValueAt(int row, int column) {
        ItemCarrito item = listaItems.get(row);
        switch (column) {
            case 0: return item.getProducto().getNombre();
            case 1: return item.getCantidad();
            case 2: return item.getProducto().getPrecio();
            case 3: double subtotal= item.getCantidad() * item.getProducto().getPrecio();
            		return String.format("%.2f", subtotal)+"€";
            case 4: return "Modificar";
            default: return null;
        }
    }

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		if(column==1) {
			int nuevaCantidad= Integer.parseInt(aValue.toString());
			if(nuevaCantidad ==0) {
				listaItems.remove(row);
				fireTableDataChanged();
			}else {
				listaItems.get(row).setCantidad(nuevaCantidad);
				fireTableRowsUpdated(row, row);
			}
		}
	}
    
}