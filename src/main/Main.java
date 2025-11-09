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

    private static final Object[][] RAW_DATA = {
        {"CAMISETA BASICA BLANCA", "camiseta_blanca.png", 15.95, "Algodón 100% orgánico, corte clásico y duradero. Ideal para el día a día."},
        {"CAMISETA HONDA NSX-R", "camiseta_honda.png", 19.95, "Diseño exclusivo de edición limitada, cuello reforzado y estampado de alta calidad."},
        {"CAMISETA MICKEY MOUSE", "camiseta_mickey.png", 17.95, "Estampado retro del famoso ratón. Tacto suave y ajuste regular."},
        {"CAMISETA TOM & JERRY", "camiseta_tom-jerry.png", 17.99, "Divertida camiseta con los personajes clásicos."},
        {"CAMISETA KTM", "camiseta_ktm.png", 19.95, "Estilo Fde competición. Tejido transpirable y ligero, perfecto para fans del motor."},
        {"CAMISETA RACING TEAM", "camiseta_racing.png", 13.95, "Inspirada en las carreras. Ajuste cómodo y tejido de fácil cuidado."},
        {"CAMISETA FRIDAY GAMING CLUB", "camiseta_friday.png", 21.95, "Para tus noches de juego. Diseño moderno y tejido fresco."},
        {"PANTALON CARGO BAGGY", "pantalon_cargo.png", 55.00, "Estilo 'Baggy' con múltiples bolsillos. Máxima comodidad y tendencia."},
        {"PANTALON TAILORING WIDE LEG", "pantalon_tailoring.png", 45.00, "Elegancia y corte ancho. Ideal para ocasiones formales e informales."},
        {"PANTALON JOGGER RELAXED FIT", "pantalon_jogger.png", 54.99, "Cintura elástica y bajos ajustados. Perfecto para deporte o relax."},
        {"PANTALON CHINO SKINNY FIT", "pantalon_chino.png", 39.90, "Corte ajustado y tejido elástico que se adapta a tu cuerpo."},
        {"SUDADERA CAPUCHA CLASICA", "sudadera_clasica.png", 29.95, "Suela de amortiguación avanzada y malla transpirable. Ligereza en cada pisada."},
        {"SUDADERA POLO CUELLO CONTRASTE", "sudadera_contraste.png", 29.95, "Sudadera polo regular fit. Cuello solapa con cierre frontal de botonadura oculta por solapa."},
        {"SUDADERA PARCHES BANDAS CONTRASTE", "sudadera_parches.png", 39.95, "Sudadera hoodie relaxed fit. Cuello con capucha y manga larga. Bolsillo frontal tipo canguro."},
        {"CAZADORA ACOLCHADA LIGERA WATER REPELLENT", "cazadora_acolchada.png", 39.95, "Cazadora acolchada regular fit confeccionada en tejido técnico que repele el agua al contacto."},
        {"CHAQUETÓN REGULAR FIT BOLSILLOS", "chaqueta_regularfit.png", 53.95, "Cazadora acolchada regular fit confeccionada en tejido técnico que repele el agua al contacto."},
    };


    private static List<Producto> listaProductosGlobal = new ArrayList<>();


    private static void inicializarProductos() {
        
        Random rand = new Random();
        String[] tallasRopa = {"XS", "S", "M", "L", "XL"};

        int id = 1;

        for (Object[] item : RAW_DATA) {
            String nombre = (String) item[0];
            double precio = (double) item[2];
            String descripcion = (String) item[3];


            Map<String, Integer> inventarioPorTalla = new HashMap<>();

            String tallaDefecto = "M";
            int totalStock = 0;


            for (String talla : tallasRopa) {

                int stock;
                int numeroAleatorio = rand.nextInt(10);

                if (numeroAleatorio == 0) {
                    stock = 0;
                } else {
                    stock = rand.nextInt(10) + 1;
                }

                inventarioPorTalla.put(talla, stock);
                totalStock = totalStock + stock;
            }


            Producto p = new Producto(
                id++,
                nombre,
                descripcion,
                precio,
                tallaDefecto,
                totalStock,
                new ArrayList<Opinion>(), 
                inventarioPorTalla
            );

            listaProductosGlobal.add(p);
        }
    }


    private static List<ItemCarrito> inicializarCarrito() {
        return new ArrayList<>();
    }


    public static void main(String[] args) {
        inicializarProductos();
        List<ItemCarrito> carritoInicial = inicializarCarrito();

        SwingUtilities.invokeLater(() -> {

            
            JFramePrincipal principal = new JFramePrincipal(listaProductosGlobal, carritoInicial);
            principal.setVisible(false);

            
            VentanaCarga ventanaCarga = new VentanaCarga(principal);
            ventanaCarga.setVisible(true);

           
            ventanaCarga.iniciarCarga(() -> {
                principal.setVisible(true);
            });
        });
    }
}