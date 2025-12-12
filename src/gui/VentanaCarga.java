package gui;

import javax.swing.*;
import java.awt.*;

public class VentanaCarga extends JDialog {

    private static final long serialVersionUID = 1L;
    private JProgressBar progressBar;

    public VentanaCarga(JFrame parent) {
        super(parent, "Cargando...", false);
        setSize(400, 120);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        
        JLabel lbl = new JLabel("Cargando, por favor espere...", SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(lbl, BorderLayout.NORTH);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(30, 144, 255));
        add(progressBar, BorderLayout.CENTER);
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