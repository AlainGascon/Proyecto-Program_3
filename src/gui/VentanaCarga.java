package gui;

import javax.swing.*;
import java.awt.*;

public class VentanaCarga extends JDialog {
    
    private static final long serialVersionUID = 1L;
    
    // PALETA DE COLORES UNIFICADA
    private static final Color COLOR_PRIMARIO = new Color(41, 128, 185); 
    private static final Color COLOR_TEXTO_PRINCIPAL = new Color(51, 51, 51);
    private static final Color COLOR_FONDO_CLARO = new Color(236, 240, 241);
    
    // FUENTES ESTANDARIZADAS
    private static final Font FUENTE_DESTACADO = new Font("Arial", Font.BOLD, 16);
    
    private JProgressBar progressBar;
    
    public VentanaCarga(JFrame parent) {
        super(parent, "Cargando...", false);
        setSize(400, 80);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        setUndecorated(true);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(COLOR_FONDO_CLARO);
        panelPrincipal.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_PRIMARIO, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lbl = new JLabel("Cargando, por favor espere...", SwingConstants.CENTER);
        lbl.setFont(FUENTE_DESTACADO);
        lbl.setForeground(COLOR_TEXTO_PRINCIPAL);
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setForeground(COLOR_PRIMARIO);
        progressBar.setBackground(Color.WHITE);
        progressBar.setFont(new Font("Arial", Font.PLAIN, 12));
        
        panelPrincipal.add(lbl, BorderLayout.NORTH);
        panelPrincipal.add(progressBar, BorderLayout.CENTER);
        
        add(panelPrincipal);
    }
    
    public void iniciarCarga(Runnable alFinalizar) {
        new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                try {
                    Thread.sleep(30); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final int progreso = i;
                SwingUtilities.invokeLater(() -> progressBar.setValue(progreso));
            }
            dispose();
            SwingUtilities.invokeLater(alFinalizar);
        }).start();
        setVisible(true);
    }
}