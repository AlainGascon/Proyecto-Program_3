package recursividad;

import java.util.ArrayList;
import java.util.List;
import domain.Producto;

public class CombinacionesExactasRec {

    private static final double EPS = 0.001;

    public static List<List<Producto>> combinacionesExactas(double presupuesto, List<Producto> productos) {
        List<List<Producto>> res = new ArrayList<>();
        if (productos == null) return res;
        backtrack(presupuesto, productos, 0, new ArrayList<>(), res);
        return res;
    }

    private static void backtrack(double restante, List<Producto> productos, int i,
                                  List<Producto> actual, List<List<Producto>> res) {

        // Caso base: llegamos a 0 (o muy cerca)
        if (Math.abs(restante) <= EPS) {
            res.add(new ArrayList<>(actual));
            return;
        }

        // Si nos pasamos o terminamos la lista
        if (restante < -EPS || i >= productos.size()) {
            return;
        }

        // Opción 1: incluir productos[i]
        Producto p = productos.get(i);
        if (p != null) {
            actual.add(p);
            backtrack(restante - p.getPrecio(), productos, i + 1, actual, res);
            actual.remove(actual.size() - 1);
        }

        // Opción 2: no incluirlo
        backtrack(restante, productos, i + 1, actual, res);
    }
}
