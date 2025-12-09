package recursividad;

import java.util.ArrayList;
import java.util.List;

import domain.Producto;

public class encontrarCombinacionesRecursivo {

	
	public static List<List<Producto>> encontrarCombinacionesUnicas(double presupuesto, List<Producto> productos) {
	    List<List<Producto>> combinaciones = new ArrayList<>();
	    List<Producto> combinacionActual = new ArrayList<>();
	    
	    buscarCombinacionesUnicasRecursivo(presupuesto, productos, 0, combinacionActual, combinaciones, 0.01);
	    return combinaciones;
	}

	private static void buscarCombinacionesUnicasRecursivo(
	        double presupuestoRestante,
	        List<Producto> productos,
	        int indice,
	        List<Producto> combinacionActual,
	        List<List<Producto>> combinaciones,
	        double tolerancia) {
	    
	    // Si tenemos una combinación válida, la guardamos
	    if (!combinacionActual.isEmpty() && presupuestoRestante >= -tolerancia) {
	        combinaciones.add(new ArrayList<>(combinacionActual));
	    }
	    
	    // Explorar productos restantes
	    for (int i = indice; i < productos.size(); i++) {
	        Producto producto = productos.get(i);
	        
	        if (producto.getPrecio() <= presupuestoRestante + tolerancia) {
	            combinacionActual.add(producto);
	            buscarCombinacionesUnicasRecursivo(
	                presupuestoRestante - producto.getPrecio(),
	                productos,
	                i + 1, // i+1 para evitar repetir productos
	                combinacionActual,
	                combinaciones,
	                tolerancia
	            );
	            combinacionActual.remove(combinacionActual.size() - 1);
	        }
	    }
	}
	
	
}
