package main;

import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import domain.ItemCarrito;
import domain.Opinion; 
import domain.Producto; 
import gui.JFramePrincipal;
import gui.VentanaCarga; 

public class Main {
    
    // Matriz de datos de productos
    private static final Object[][] RAW_DATA = {
        {"Camiset Basica Blanca", "camiseta_blanca.png", 15.95, "Algodón 100% orgánico, corte clásico y duradero. Ideal para el día a día."},
        {"Camiseta Honda NSX-R", "camiseta_honda.png", 19.95, "Diseño exclusivo de edición limitada, cuello reforzado y estampado de alta calidad."},
        {"Camiseta Mickey Mouse", "camiseta_mickey.png", 17.95, "Estampado retro del famoso ratón. Tacto suave y ajuste regular."},
        {"Camiseta Tom & Jerry", "camiseta_tom-jerry.png", 17.99, "Divertida camiseta con los personajes clásicos."},
        {"Camieta KTM", "camiseta_ktm.png", 19.95, "Estilo Fde competición. Tejido transpirable y ligero, perfecto para fans del motor."},
        {"Camiseta Racing Team", "camiseta_racing.png", 13.95, "Inspirada en las carreras. Ajuste cómodo y tejido de fácil cuidado."},
        {"Camiseta Friday Gaming Club", "camiseta_friday.png", 21.95, "Para tus noches de juego. Diseño moderno y tejido fresco."},
        {"Pantalon Cargo Baggy", "pantalon_cargo.png", 55.00, "Estilo 'Baggy' con múltiples bolsillos. Máxima comodidad y tendencia."},
        {"Pantalon Tailoring Wide Leg", "pantalon_tailoring.png", 69.95, "Elegancia y corte ancho. Ideal para ocasiones formales e informales."},
        {"Pantalon Jogger Relaxed Fit", "pantalon_jogger.png", 54.99, "Cintura elástica y bajos ajustados. Perfecto para deporte o relax."},
        {"Pantalon Chino Skinny Fit", "pantalon_chino.png", 39.90, "Corte ajustado y tejido elástico que se adapta a tu cuerpo."},
        {"Sudadera Capucha Clasica", "sudadera_clasica.png", 79.99, "Suela de amortiguación avanzada y malla transpirable. Ligereza en cada pisada."},
        {"Abrigo Lana \"Classic Fit\"", "abrigo_lana.png", 129.99, "Composición de lana virgen, corte clásico y botones ocultos. Muy cálido."},
        {"Botines Cuero \"Chelsea\"", "botines_c.png", 89.90, "Piel auténtica, suela antideslizante y elástico lateral. Durabilidad y estilo."},
        {"Gorra Béisbol Logo", "gorra_logo.png", 17.50, "Ajustable, con visera curva y logo bordado. 100% algodón."},
    };
    
    // Lista global para mantener todos los productos creados
    private static List<Producto> listaProductosGlobal = new ArrayList<>();
    
    
    /**
     * Crea la lista de objetos Producto a partir de la matriz de datos, 
     * generando ID y stock por talla.
     */
    private static void inicializarProductos() {
        Random rand = new Random();
        String[] tallasRopa = {"XS", "S", "M", "L", "XL"};
        
        int id = 1;
        
        for (Object[] item : RAW_DATA) {
            String nombre = (String) item[0];
            double precio = (double) item[2];
            String descripcion = (String) item[3];
            
            // 1. Generar el inventario por talla y stock total
            Map<String, Integer> inventarioPorTalla = new HashMap<>();
            String tallaDefecto = "M";
            int totalStock = 0;
            
            for (String talla : tallasRopa) {
                // Stock entre 0 y 15
                int stock = (rand.nextInt(10) == 0) ? 0 : (rand.nextInt(15) + 1);
                inventarioPorTalla.put(talla, stock);
                totalStock += stock;
            }
            
            // 2. Crear el objeto Producto con los 8 argumentos
            Producto p = new Producto(
                id++, 
                nombre, 
                descripcion, 
                precio, 
                tallaDefecto,              // String talla (Talla por defecto)
                totalStock,                // int stock (Stock total)
                new ArrayList<Opinion>(),  // List<Opinion> opiniones (vacía)
                inventarioPorTalla         // Map<String, Integer> inventarioPorTalla
            );
            
            listaProductosGlobal.add(p);
        }
    }
    
    /**
     * Crea la lista inicial del carrito.
     */
    private static List<ItemCarrito> inicializarCarrito() {
        List<ItemCarrito> carritoInicial = new ArrayList<>();
        
        // Añadir los dos primeros productos al carrito para empezar
        if (listaProductosGlobal.size() >= 2) {
            Producto p1 = listaProductosGlobal.get(0);
            Producto p2 = listaProductosGlobal.get(1);
            
            carritoInicial.add(new ItemCarrito(p1, 2, "M")); 
            carritoInicial.add(new ItemCarrito(p2, 1, "L")); 
        }
        
        return carritoInicial;
    }
    
    
    /**
     * Punto de entrada de la aplicación.
     */
    public static void main(String[] args) {
        
        // 1. Inicializar la lista global de Productos
        inicializarProductos();
        
        // 2. Preparar los datos iniciales del Carrito
        List<ItemCarrito> carritoInicial = inicializarCarrito();
        
        // 3. Ejecuta la lógica de interfaz en el EDT
        SwingUtilities.invokeLater(() -> {
            
            // CORRECCIÓN: Llamamos al constructor de JFramePrincipal con ambos parámetros
            JFramePrincipal principal = new JFramePrincipal(listaProductosGlobal, carritoInicial); 
            principal.setVisible(false); 

            // Crear y mostrar la ventana de carga (Asumiendo que VentanaCarga está disponible)
            VentanaCarga ventanaCarga = new VentanaCarga(principal);
            ventanaCarga.setVisible(true);

            // Iniciar la simulación de carga.
            ventanaCarga.iniciarCarga(() -> {
                principal.setVisible(true); 
            });
        });
    }
}