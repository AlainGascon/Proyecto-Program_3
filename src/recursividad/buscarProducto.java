package recursividad;

import java.util.List;
import domain.Producto;

public class buscarProducto {
    
    // 1. La lista debe ser inicializada o pasada por constructor
    private List<Producto> productos;

    public buscarProducto(List<Producto> productos) {
        this.productos = productos;
    }

    public Producto buscarProductoRec(String nombre) {
        // Validación de seguridad: si la lista es nula, no buscamos
        if (productos == null) return null;
        return buscarProductoRecHelper(nombre, 0);
    }

    private Producto buscarProductoRecHelper(String nombre, int index) {
        // Caso Base 1: Se acabó la lista y no lo encontramos
        if (index >= productos.size()) {
            return null;
        }

        // Caso Base 2: Lo encontramos (usamos equalsIgnoreCase por seguridad)
        Producto p = productos.get(index);
        if (p.getNombre() != null && p.getNombre().equalsIgnoreCase(nombre)) {
            return p;
        }

        // Paso Recursivo: Buscamos en la siguiente posición
        return buscarProductoRecHelper(nombre, index + 1);
    }
}