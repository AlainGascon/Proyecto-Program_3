package recursividad;

import java.util.ArrayList;
import java.util.List;
import domain.Producto;

public class OrdenarPorPrecioRec {

    public static List<Producto> mergeSortPorPrecio(List<Producto> productos) {
        if (productos == null) return new ArrayList<>();
        if (productos.size() <= 1) return new ArrayList<>(productos);

        int mid = productos.size() / 2;
        List<Producto> izq = mergeSortPorPrecio(productos.subList(0, mid));
        List<Producto> der = mergeSortPorPrecio(productos.subList(mid, productos.size()));

        return merge(izq, der);
    }

    private static List<Producto> merge(List<Producto> a, List<Producto> b) {
        List<Producto> res = new ArrayList<>();
        int i = 0, j = 0;

        while (i < a.size() && j < b.size()) {
            Producto pa = a.get(i);
            Producto pb = b.get(j);

            double precioA = (pa != null) ? pa.getPrecio() : Double.POSITIVE_INFINITY;
            double precioB = (pb != null) ? pb.getPrecio() : Double.POSITIVE_INFINITY;

            if (precioA <= precioB) {
                res.add(pa);
                i++;
            } else {
                res.add(pb);
                j++;
            }
        }

        while (i < a.size()) res.add(a.get(i++));
        while (j < b.size()) res.add(b.get(j++));

        return res;
    }
}