package gui;


import javax.swing.*;

import domain.Evento;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VentanaEventos extends JFrame {
    private ModeloEventos modelo;
    private JTable tabla;
    private JScrollPane scrollTabla;
    private JTextArea txtMensaje;
    private List<Evento> listaEventos;

    public VentanaEventos() {
        setTitle("Eventos de la Tienda de Ropa");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        listaEventos = new ArrayList<>();
        listaEventos.add(new Evento(LocalDate.of(2025, 11, 15), "Desfile de invierno", "Gran Vía, Bilbao",120));
        listaEventos.add(new Evento(LocalDate.of(2025, 12, 5), "Rebajas de Navidad", "Gran Vía, Madrid", 200));
        listaEventos.add(new Evento(LocalDate.of(2025, 12, 30), "Cierre de año por todo lo alto", "Passeig de Gràcia, Barcelona", 3500));
        
        // Mensaje explicativo con wrap de texto
        txtMensaje = new JTextArea("Vive la moda en primera persona. Nuestros eventos exclusivos te esperan. Descubre las últimas tendencias antes que nadie. Tu estilo merece ser protagonista.");
        txtMensaje.setWrapStyleWord(true);
        txtMensaje.setLineWrap(true);
        txtMensaje.setEditable(false);
        txtMensaje.setBackground(getBackground());
        txtMensaje.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20)); // Márgenes interno
        txtMensaje.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        modelo = new ModeloEventos(listaEventos);
        tabla = new JTable(modelo);
        
        // Mejorar apariencia de la tabla
        tabla.setRowHeight(25);
        tabla.setIntercellSpacing(new Dimension(10, 3));
        tabla.setShowGrid(true);
        tabla.setGridColor(Color.LIGHT_GRAY);
        
        // ScrollPane con márgenes
        scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20)); // Márgenes: top, left, bottom, right
        
        // Configurar layout principal
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(txtMensaje, BorderLayout.NORTH);
        getContentPane().add(scrollTabla, BorderLayout.CENTER);
        
        setVisible(true);
    }

    public static void main(String[] args) {
        new VentanaEventos();
        
    }
}
