package gui;



import javax.swing.table.AbstractTableModel;

import domain.Evento;

import java.util.List;

public class ModeloEventos extends AbstractTableModel {

    private List<Evento> eventos;
    private final String[] columnas = {"Fecha", "Descripción", "Lugar", "Capacidad"};

    public ModeloEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    @Override
    public int getRowCount() {
        return eventos.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public Object getValueAt(int row, int column) {
        Evento e = eventos.get(row);
        return switch (column) {
            case 0 -> e.getFecha();
            case 1 -> e.getDescripcion();
            case 2 -> e.getLugar();
            case 3 -> e.getCapacidad();
            default -> null;
        };
    }

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
    
    
}
