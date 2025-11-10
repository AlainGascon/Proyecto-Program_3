package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class JuegoDescuento extends JPanel {
	private static final long serialVersionUID = 1L;

    private static final Color COLOR_PRIMARIO = new Color(30, 144, 255);
    private static final Color COLOR_FONDO_GENERAL = new Color(245, 245, 245);
    private static final Color COLOR_SLOT_TEXTO_MOVIMIENTO = Color.BLACK;
    private static final Color COLOR_SLOT_FONDO = new Color(230, 230, 230);
    private static final Color COLOR_FALLO = new Color(255, 69, 0);
    private static final Color COLOR_ACIERTO = Color.GREEN;
    private static final Font FUENTE_SLOT = new Font("Monospaced", Font.BOLD, 48);

	private JPanel pSur, pCentro;
	private JButton btnParar;
	private JLabel lbl1, lbl2, lbl3;
	private volatile boolean pararHilo; 
	private Thread t;
    private final Runnable hiloTirada;
    private final Border padding = new EmptyBorder(5, 15, 5, 15);
    private final Border bordeBase = new LineBorder(COLOR_PRIMARIO.darker(), 2);

	public JuegoDescuento() {
        
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {}

        this.setLayout(new BorderLayout());
        this.setBackground(COLOR_FONDO_GENERAL);

		pSur = new JPanel();
        pSur.setBackground(COLOR_FONDO_GENERAL);
		pCentro = new JPanel(new GridLayout(1, 3, 15, 15));
        pCentro.setBackground(COLOR_FONDO_GENERAL);
        pCentro.setBorder(new EmptyBorder(20, 20, 20, 20));

		this.add(pCentro, BorderLayout.CENTER);
		this.add(pSur, BorderLayout.SOUTH);

		btnParar = new JButton("PARAR");
        btnParar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        btnParar.setBackground(COLOR_PRIMARIO);
        btnParar.setForeground(Color.WHITE);
        btnParar.setFocusPainted(false);

        Border bordeInicial = BorderFactory.createCompoundBorder(bordeBase, padding);

        java.util.function.Supplier<JLabel> inicializarLabel = () -> {
            JLabel lbl = new JLabel();
            lbl.setHorizontalAlignment(JLabel.CENTER);
            lbl.setFont(FUENTE_SLOT);
            lbl.setForeground(COLOR_SLOT_TEXTO_MOVIMIENTO);
            lbl.setOpaque(true);
            lbl.setBackground(COLOR_SLOT_FONDO);
            lbl.setBorder(bordeInicial);
            return lbl;
        };

		lbl1 = inicializarLabel.get();
		lbl2 = inicializarLabel.get();
		lbl3 = inicializarLabel.get();

        Random r = new Random();
        lbl1.setText(String.valueOf(r.nextInt(20) + 1));
        lbl2.setText(String.valueOf(r.nextInt(20) + 1));
        lbl3.setText(String.valueOf(r.nextInt(20) + 1));

		pSur.add(btnParar);
		pCentro.add(lbl1);
		pCentro.add(lbl2);
		pCentro.add(lbl3);

		
		hiloTirada = () -> {
			Random r1 = new Random();
			
			while(!pararHilo) {
				int n1 = r1.nextInt(20) + 1;
				int n2 = r1.nextInt(20) + 1;
				int n3 = r1.nextInt(20) + 1;
                
                SwingUtilities.invokeLater(() -> {
                    lbl1.setText(String.valueOf(n1));
                    lbl2.setText(String.valueOf(n2));
                    lbl3.setText(String.valueOf(n3));
                    lbl1.setForeground(COLOR_SLOT_TEXTO_MOVIMIENTO);
                    lbl2.setForeground(COLOR_SLOT_TEXTO_MOVIMIENTO);
                    lbl3.setForeground(COLOR_SLOT_TEXTO_MOVIMIENTO);
                });
				try {
					Thread.sleep(100);
				}catch(InterruptedException e){
					
					Thread.currentThread().interrupt();
                    return;
				}
			}
		};

		
		pararHilo = false;
		t = new Thread(hiloTirada);
		t.start();

		
		btnParar.addActionListener(e -> {
			if(!pararHilo) {
				
				pararHilo = true;
				t.interrupt(); 

                
                try {
                    t.join(150);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

				
				int v1 = Integer.parseInt(lbl1.getText());
				int v2 = Integer.parseInt(lbl2.getText());
				int v3 = Integer.parseInt(lbl3.getText());
                String mensaje;
                LineBorder bordeResultado;

				if(v1 == v2 && v2 == v3) {
					mensaje = "🎉 ¡FELICIDADES! Has ganado un descuento.";
                    bordeResultado = new LineBorder(COLOR_ACIERTO.brighter(), 4);
                    lbl1.setForeground(COLOR_ACIERTO);
                    lbl2.setForeground(COLOR_ACIERTO);
                    lbl3.setForeground(COLOR_ACIERTO);
				} else {
					mensaje = "❌ No hubo coincidencia. Inténtalo de nuevo.";
                    bordeResultado = new LineBorder(COLOR_FALLO, 4);
                    lbl1.setForeground(COLOR_FALLO);
                    lbl2.setForeground(COLOR_FALLO);
                    lbl3.setForeground(COLOR_FALLO);
				}
                
                
                lbl1.setBorder(BorderFactory.createCompoundBorder(bordeResultado, padding));
                lbl2.setBorder(BorderFactory.createCompoundBorder(bordeResultado, padding));
                lbl3.setBorder(BorderFactory.createCompoundBorder(bordeResultado, padding));
                
                
                JOptionPane.showMessageDialog(null, mensaje, "Resultado de la Tirada", JOptionPane.INFORMATION_MESSAGE);
                
				btnParar.setText("REANUDAR");
                btnParar.setBackground(COLOR_PRIMARIO.darker());
			} else {
				
				pararHilo = false;
				t = new Thread(hiloTirada); 
				t.start();
				btnParar.setText("PARAR");
                btnParar.setBackground(COLOR_PRIMARIO);
                
                
                Border bordeReset = BorderFactory.createCompoundBorder(bordeBase, padding);
                lbl1.setBorder(bordeReset);
                lbl2.setBorder(bordeReset);
                lbl3.setBorder(bordeReset);
			}
		});
	}
    
}