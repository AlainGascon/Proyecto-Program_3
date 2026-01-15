package gui;

import javax.swing.*;
import domain.Evento;
import domain.ModeloEventos;
import java.awt.*;
import java.time.LocalDate; 
import java.util.ArrayList;
import java.util.List;

public class VentanaEventos extends JPanel { 
	
	private static final long serialVersionUID = 1L;
    
    // PALETA DE COLORES UNIFICADA
    private static final Color COLOR_PRIMARIO = new Color(41, 128, 185); 
    private static final Color COLOR_ACENTO = new Color(46, 204, 113); 
    private static final Color COLOR_PRECIO = new Color(231, 76, 60); 
    private static final Color COLOR_FONDO_CLARO = new Color(236, 240, 241);
    private static final Color COLOR_TEXTO_PRINCIPAL = new Color(51, 51, 51);
    
    // FUENTES ESTANDARIZADAS
    private static final Font FUENTE_TITULO = new Font("Arial", Font.BOLD, 28);
    private static final Font FUENTE_NORMAL = new Font("Arial", Font.PLAIN, 14);
    private static final Font FUENTE_TABLA_HEADER = new Font("Arial", Font.BOLD, 14);
    
    private ModeloEventos modelo;
    private JTable tabla;
    private JScrollPane scrollTabla;
    private JTextArea txtMensaje;
    private List<Evento> listaEventos;

    public VentanaEventos(List<Evento> listaEventos) { 
        
        listaEventos = new ArrayList<>(); 
        
        listaEventos.add(new Evento(LocalDate.of(2025, 11, 15), "Desfile de invierno", "Gran Vía, Bilbao",120));
        listaEventos.add(new Evento(LocalDate.of(2025, 12, 5), "Rebajas de Navidad", "Gran Vía, Madrid", 200));
        listaEventos.add(new Evento(LocalDate.of(2025, 12, 30), "Cierre de año por todo lo alto", "Passeig de Gràcia, Barcelona", 3500));
        listaEventos.add(new Evento(LocalDate.of(2025, 11, 25), "Desfile Colección Fiesta", "Sala de Eventos, Valencia", 150));
        listaEventos.add(new Evento(LocalDate.of(2025, 11, 29), "Lanzamiento Zapatillas 'Retro'", "Centro Comercial, Sevilla", 220));
        listaEventos.add(new Evento(LocalDate.of(2025, 12, 3), "Desfile Jóvenes Diseñadores", "Galería de Arte, Málaga", 85));
        listaEventos.add(new Evento(LocalDate.of(2025, 12, 7), "Lanzamiento Ropa Deportiva", "Plaza Central, Zaragoza", 300));
        listaEventos.add(new Evento(LocalDate.of(2025, 12, 11), "Venta Privada de Navidad", "Tienda Principal, Murcia", 110));
        listaEventos.add(new Evento(LocalDate.of(2025, 12, 15), "Desfile de Tendencias", "Showroom Exclusivo, Palma", 65));
        listaEventos.add(new Evento(LocalDate.of(2025, 12, 19), "Lanzamiento de Bisutería", "Tienda Principal, Alicante", 90));
        listaEventos.add(new Evento(LocalDate.of(2025, 12, 23), "Masterclass Maquillaje de Pasarela", "Taller Auxiliar, Bilbao", 30));
        listaEventos.add(new Evento(LocalDate.of(2025, 12, 27), "Taller de Customización Denim", "Taller Creativo, Madrid", 25));
        listaEventos.add(new Evento(LocalDate.of(2025, 12, 30), "Cierre de año por todo lo alto", "Passeig de Gràcia, Barcelona", 3500));
        listaEventos.add(new Evento(LocalDate.of(2026, 1, 3), "Masterclass Personal Shopper", "Showroom Exclusivo, Valencia", 40));
        listaEventos.add(new Evento(LocalDate.of(2026, 1, 7), "Taller 'Crea tu Propio Estilo'", "Sala de Eventos, Sevilla", 50));
        listaEventos.add(new Evento(LocalDate.of(2026, 1, 11), "Masterclass Fotografía de Moda", "Galería de Arte, Málaga", 35));
        listaEventos.add(new Evento(LocalDate.of(2026, 1, 15), "Taller de Reciclaje Textil", "Taller Creativo, Zaragoza", 28));
        listaEventos.add(new Evento(LocalDate.of(2026, 1, 19), "Masterclass Tendencias '26", "Showroom Exclusivo, Murcia", 45));
	    listaEventos.add(new Evento(LocalDate.of(2026, 1, 23), "Taller de Complementos", "Taller Auxiliar, Palma", 20));
	    listaEventos.add(new Evento(LocalDate.of(2026, 1, 27), "Masterclass de Street Style", "Sala de Eventos, Alicante", 55));
	    listaEventos.add(new Evento(LocalDate.of(2026, 1, 31), "Liquidación Total Invierno", "Plaza Central, Bilbao", 500));
	    listaEventos.add(new Evento(LocalDate.of(2026, 2, 4), "Rebajas de Final de Temporada", "Centro Comercial, Madrid", 750));
	    listaEventos.add(new Evento(LocalDate.of(2026, 2, 8), "Venta Flash Zapatillas", "Tienda Principal, Barcelona", 400));
	    listaEventos.add(new Evento(LocalDate.of(2026, 2, 12), "Liquidación Accesorios", "Sala de Eventos, Valencia", 320));
	    listaEventos.add(new Evento(LocalDate.of(2026, 2, 16), "Venta Final de Stock", "Centro Comercial, Sevilla", 800));
	    listaEventos.add(new Evento(LocalDate.of(2026, 2, 20), "Rebajas de Invierno Finales", "Tienda Principal, Málaga", 550));
	    listaEventos.add(new Evento(LocalDate.of(2026, 2, 24), "Liquidación Total Zapatos", "Plaza Central, Zaragoza", 600));
	    listaEventos.add(new Evento(LocalDate.of(2026, 2, 28), "Venta Flash 'Only Dresses'", "Tienda Principal, Murcia", 380));
	    listaEventos.add(new Evento(LocalDate.of(2026, 3, 4), "Rebajas Extraordinarias", "Centro Comercial, Palma", 700));
	    listaEventos.add(new Evento(LocalDate.of(2026, 3, 8), "Liquidación Ropa de Abrigo", "Tienda Principal, Alicante", 450));
	    listaEventos.add(new Evento(LocalDate.of(2026, 3, 12), "Desfile Colección Verano", "Sala de Eventos, Córdoba", 130));
	    listaEventos.add(new Evento(LocalDate.of(2026, 3, 16), "Lanzamiento Colección 'Beach'", "Showroom Exclusivo, Valladolid", 100));
	    listaEventos.add(new Evento(LocalDate.of(2026, 3, 20), "Venta Privada Verano", "Tienda Principal, Vigo", 80));
	    listaEventos.add(new Evento(LocalDate.of(2026, 3, 24), "Desfile de Bañadores", "Centro Comercial, Gijón", 180));
	    listaEventos.add(new Evento(LocalDate.of(2026, 3, 28), "Lanzamiento Zapatillas 'Future'", "Plaza Mayor, Vitoria", 250));
	    listaEventos.add(new Evento(LocalDate.of(2026, 4, 1), "Desfile de Tendencias Primavera", "Galería de Arte, A Coruña", 90));
	    listaEventos.add(new Evento(LocalDate.of(2026, 4, 5), "Lanzamiento Ropa Casual", "Tienda Principal, Granada", 350));
	    listaEventos.add(new Evento(LocalDate.of(2026, 4, 9), "Venta Privada de Pascua", "Showroom Exclusivo, Oviedo", 120));
	    listaEventos.add(new Evento(LocalDate.of(2026, 4, 13), "Desfile de Primavera", "Sala de Eventos, Santa Cruz", 75));
	    listaEventos.add(new Evento(LocalDate.of(2026, 4, 17), "Lanzamiento de Gafas de Sol", "Tienda Principal, Las Palmas", 105));
	    listaEventos.add(new Evento(LocalDate.of(2026, 5, 31), "Liquidación Total Primavera", "Plaza Central, Córdoba", 520));
	    listaEventos.add(new Evento(LocalDate.of(2026, 6, 4), "Rebajas de Final de Temporada", "Centro Comercial, Valladolid", 770));
	    listaEventos.add(new Evento(LocalDate.of(2026, 6, 8), "Venta Flash Vestidos", "Tienda Principal, Vigo", 420));
	    listaEventos.add(new Evento(LocalDate.of(2026, 6, 12), "Liquidación de Joyería", "Sala de Eventos, Gijón", 340));
	    listaEventos.add(new Evento(LocalDate.of(2026, 6, 16), "Venta Final de Stock Verano", "Centro Comercial, Vitoria", 820));
	    listaEventos.add(new Evento(LocalDate.of(2026, 6, 20), "Rebajas de Verano Inicio", "Tienda Principal, A Coruña", 570));
	    listaEventos.add(new Evento(LocalDate.of(2026, 6, 24), "Liquidación Total Sandalias", "Plaza Central, Granada", 620));
	    listaEventos.add(new Evento(LocalDate.of(2026, 6, 28), "Venta Flash 'Only Shorts'", "Tienda Principal, Oviedo", 400));
	    listaEventos.add(new Evento(LocalDate.of(2026, 7, 2), "Rebajas de Mitad de Temporada", "Centro Comercial, Santa Cruz", 720));
	    listaEventos.add(new Evento(LocalDate.of(2026, 7, 6), "Liquidación Ropa de Fiesta", "Tienda Principal, Las Palmas", 470));
        
        this.setLayout(new BorderLayout()); 
        this.setBackground(COLOR_FONDO_CLARO);

        // MENSAJE DESCRIPTIVO
        txtMensaje = new JTextArea("Vive la moda en primera persona. Nuestros eventos exclusivos te esperan. Descubre las últimas tendencias antes que nadie. Tu estilo merece ser protagonista.");
        txtMensaje.setWrapStyleWord(true);
        txtMensaje.setLineWrap(true);
        txtMensaje.setEditable(false);
        txtMensaje.setBackground(COLOR_FONDO_CLARO);
        txtMensaje.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20)); 
        txtMensaje.setFont(FUENTE_NORMAL);
        txtMensaje.setForeground(COLOR_TEXTO_PRINCIPAL);
        
        // TÍTULO
        JLabel lblTitulo = new JLabel("🗓️ Agenda de Eventos", SwingConstants.CENTER);
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_PRIMARIO);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JPanel pNorte = new JPanel(new BorderLayout());
        pNorte.setBackground(COLOR_FONDO_CLARO);
        pNorte.add(lblTitulo, BorderLayout.NORTH);
        pNorte.add(txtMensaje, BorderLayout.CENTER);
        
        this.add(pNorte, BorderLayout.NORTH);

        // TABLA
        modelo = new ModeloEventos(listaEventos);
        tabla = new JTable(modelo);
        
        tabla.setRowHeight(30);
        tabla.setFont(FUENTE_NORMAL);
        tabla.getTableHeader().setFont(FUENTE_TABLA_HEADER);
        tabla.getTableHeader().setBackground(COLOR_PRIMARIO);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setSelectionBackground(new Color(174, 214, 241)); 
        tabla.setGridColor(Color.LIGHT_GRAY);
        tabla.setShowGrid(true);
        tabla.setAutoCreateRowSorter(true); 
        
        scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(0, 20, 20, 20),
            BorderFactory.createLineBorder(Color.LIGHT_GRAY)
        )); 
        
        this.add(scrollTabla, BorderLayout.CENTER);
    }
}