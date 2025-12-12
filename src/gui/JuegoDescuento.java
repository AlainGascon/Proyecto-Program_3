package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class JuegoDescuento extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final Color COLOR_PRIMARIO = new Color(41, 128, 185);
    private static final Color COLOR_SECUNDARIO = new Color(52, 152, 219);
    private static final Color COLOR_ACENTO = new Color(46, 204, 113); 
    private static final Color COLOR_PRECIO = new Color(231, 76, 60); 

    private static final Color COLOR_FONDO_INICIO = COLOR_PRIMARIO;
    private static final Color COLOR_FONDO_FIN = COLOR_SECUNDARIO;
    private static final Color COLOR_SLOT_BG = new Color(236, 240, 241);
    private static final Color COLOR_TEXTO_NORMAL = new Color(44, 62, 80);
    
    private static final Color COLOR_BTN_PARAR = COLOR_PRECIO;
    private static final Color COLOR_BTN_JUGAR = COLOR_ACENTO;
    private static final Color COLOR_WIN = new Color(255, 215, 0); 
    private static final Color COLOR_LOSE = COLOR_PRECIO;

    private static final Font FUENTE_SLOT = new Font("Impact", Font.PLAIN, 60);
    private static final Font FUENTE_TITULO = new Font("Arial", Font.BOLD, 22);

    private JPanel pSur, pCentro, pNorte;
    private JButton btnAccion;
    private JLabel lbl1, lbl2, lbl3;
    private JLabel lblTitulo;
    
    private volatile boolean pararHilo; 
    private Thread t;
    private final Runnable hiloTirada;

    public JuegoDescuento() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        this.setLayout(new BorderLayout(0, 0));

        pNorte = createTransparentPanel();
        lblTitulo = new JLabel("🎰 LUCKY DISCOUNT 🎰");
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBorder(new EmptyBorder(20, 0, 10, 0));
        pNorte.add(lblTitulo);

        pCentro = createTransparentPanel();
        pCentro.setLayout(new GridLayout(1, 3, 20, 0));
        pCentro.setBorder(new EmptyBorder(10, 40, 30, 40));

        lbl1 = crearSlotVisual();
        lbl2 = crearSlotVisual();
        lbl3 = crearSlotVisual();

        randomizeSlots();

        pCentro.add(lbl1);
        pCentro.add(lbl2);
        pCentro.add(lbl3);

        pSur = createTransparentPanel();
        pSur.setBorder(new EmptyBorder(0, 0, 30, 0));

        btnAccion = new JButton("🛑 DETENER");
        estilizarBoton(btnAccion, COLOR_BTN_PARAR);
        
        pSur.add(btnAccion);

        this.add(pNorte, BorderLayout.NORTH);
        this.add(pCentro, BorderLayout.CENTER);
        this.add(pSur, BorderLayout.SOUTH);

        hiloTirada = () -> {
            Random r1 = new Random();
            while(!pararHilo) {
                int n1 = r1.nextInt(9) + 1;
                int n2 = r1.nextInt(9) + 1;
                int n3 = r1.nextInt(9) + 1;
                
                SwingUtilities.invokeLater(() -> {
                    lbl1.setText(String.valueOf(n1));
                    lbl2.setText(String.valueOf(n2));
                    lbl3.setText(String.valueOf(n3));
                });

                try {
                    Thread.sleep(70);
                } catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        };

        pararHilo = false;
        t = new Thread(hiloTirada);
        t.start();

        btnAccion.addActionListener(e -> {
            if(!pararHilo) {
                pararHilo = true;
                btnAccion.setEnabled(false);
                
                new Thread(() -> {
                     try { t.join(100); } catch (Exception ex) {}
                     
                     SwingUtilities.invokeLater(() -> {
                         verificarResultado();
                         btnAccion.setText("▶ JUGAR DE NUEVO");
                         estilizarBoton(btnAccion, COLOR_BTN_JUGAR);
                         btnAccion.setEnabled(true);
                     });
                }).start();

            } else {
                reiniciarEstiloSlots();
                pararHilo = false;
                t = new Thread(hiloTirada); 
                t.start();
                
                btnAccion.setText("🛑 DETENER");
                estilizarBoton(btnAccion, COLOR_BTN_PARAR);
            }
        });
    }

    // IAG
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, COLOR_FONDO_INICIO, 0, h, COLOR_FONDO_FIN);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }

    private JPanel createTransparentPanel() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        return p;
    }

    private JLabel crearSlotVisual() {
        JLabel lbl = new JLabel("7", SwingConstants.CENTER);
        lbl.setFont(FUENTE_SLOT);
        lbl.setForeground(COLOR_TEXTO_NORMAL);
        lbl.setOpaque(true);
        lbl.setBackground(COLOR_SLOT_BG);
        lbl.setPreferredSize(new Dimension(100, 120));
        
        Border lineaExterior = new LineBorder(new Color(20, 20, 20), 4, true);
        Border efectoHundido = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
        Border padding = new EmptyBorder(10, 10, 10, 10);
        
        lbl.setBorder(new CompoundBorder(lineaExterior, 
                      new CompoundBorder(efectoHundido, padding)));
        
        return lbl;
    }

    private void estilizarBoton(JButton btn, Color colorFondo) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(colorFondo);
        btn.setForeground(Color.WHITE); 
        
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setFocusPainted(false);
        
        btn.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.WHITE, 2),
                new EmptyBorder(10, 25, 10, 25)
        ));
        
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void randomizeSlots() {
        Random r = new Random();
        lbl1.setText(String.valueOf(r.nextInt(9) + 1));
        lbl2.setText(String.valueOf(r.nextInt(9) + 1));
        lbl3.setText(String.valueOf(r.nextInt(9) + 1));	
    }

    private void reiniciarEstiloSlots() {
        Color normal = COLOR_SLOT_BG;
        lbl1.setBackground(normal);
        lbl2.setBackground(normal);
        lbl3.setBackground(normal);
        lbl1.setForeground(COLOR_TEXTO_NORMAL);
        lbl2.setForeground(COLOR_TEXTO_NORMAL);
        lbl3.setForeground(COLOR_TEXTO_NORMAL);
    }

    private void verificarResultado() {
        int v1 = Integer.parseInt(lbl1.getText());
        int v2 = Integer.parseInt(lbl2.getText());
        int v3 = Integer.parseInt(lbl3.getText());

        if (v1 == v2 && v2 == v3) {
            Color dorado = COLOR_WIN;
            lbl1.setBackground(dorado);
            lbl2.setBackground(dorado);
            lbl3.setBackground(dorado);
            
            JOptionPane.showMessageDialog(this, 
                "✨ ¡JACKPOT! ✨\nHas conseguido un descuento del 20%", 
                "¡Ganador!", JOptionPane.INFORMATION_MESSAGE);
            VentanaCarrito.descuentoAplicado=true;
        } else {
            lbl1.setForeground(COLOR_LOSE);
            lbl2.setForeground(COLOR_LOSE);
            lbl3.setForeground(COLOR_LOSE);
        }
    }
}