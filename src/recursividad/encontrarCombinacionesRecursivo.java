package recursividad;

import java.util.ArrayList;
import java.util.List;
import domain.Producto;

public class encontrarCombinacionesRecursivo {

    public static List<List<Producto>> encontrarCombinacionesUnicas(double presupuesto, List<Producto> productos) {
        List<List<Producto>> resultado = new ArrayList<>();
        buscar(presupuesto, productos, 0, new ArrayList<>(), resultado);
        return resultado;
    }

    private static void buscar(double restante, List<Producto> productos, int indice, 
                               List<Producto> actual, List<List<Producto>> resultado) {
        
        if (!actual.isEmpty()) {
            resultado.add(new ArrayList<>(actual));
        }

        for (int i = indice; i < productos.size(); i++) {
            Producto p = productos.get(i);
            // Tolerancia de 0.001 para manejar céntimos (double precision)
            if (p.getPrecio() <= restante + 0.001) {
                actual.add(p);
                // i + 1 garantiza que no se repita el mismo producto físico
                buscar(restante - p.getPrecio(), productos, i + 1, actual, resultado);
                actual.remove(actual.size() - 1); // Backtracking
            }
        }
    }
}