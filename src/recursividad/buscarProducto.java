package recursividad;

import java.util.List;

import domain.Producto;

public class buscarProducto {
	
	public Producto buscarProductoRec(String nombre) {
	    return buscarProductoRecHelper(nombre, 0);
	}

	private List<Producto> productos;
	
	private Producto buscarProductoRecHelper(String nombre, int index) {
	    if (index >= productos.size()) {
	        return null;
	    }

	    Producto p = productos.get(index);

	    if (p.getNombre().equalsIgnoreCase(nombre)) {
	        return p;
	    }

	    return buscarProductoRecHelper(nombre, index + 1);
	}

}
