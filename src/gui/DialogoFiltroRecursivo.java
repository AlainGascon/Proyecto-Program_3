package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class DialogoFiltroRecursivo extends JDialog {
    
	private static final long serialVersionUID = 1L;
	private String tallaSeleccionada;
    private double presupuesto;
    private boolean confirmado = false;

    // IAG
    public DialogoFiltroRecursivo(JFrame padre) {
        super(padre, true);
        setUndecorated(true); 
        setSize(450, 350);
        setLocationRelativeTo(padre);
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(41, 128, 185), 2));

        JPanel pPrincipal = new JPanel(new BorderLayout());
        pPrincipal.setBackground(Color.WHITE);

        // IAG
        JPanel pHeader = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(41, 128, 185), getWidth(), 0, new Color(52, 152, 219));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        pHeader.setPreferredSize(new Dimension(0, 60));
        pHeader.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));
        JLabel lblTitulo = new JLabel("CONFIGURAR COMPRA INTELIGENTE");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        pHeader.add(lblTitulo);

        // Cuerpo del formulario
        JPanel pCuerpo = new JPanel(new GridLayout(4, 1, 5, 5));
        pCuerpo.setBackground(Color.WHITE);
        pCuerpo.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel lbl1 = new JLabel("Selecciona tu talla:");
        lbl1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        JComboBox<String> comboTalla = new JComboBox<>(new String[]{"XS", "S", "M", "L", "XL"});
        comboTalla.setSelectedItem("M");
        comboTalla.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel lbl2 = new JLabel("Presupuesto máximo (€):");
        lbl2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        JTextField txtPresupuesto = new JTextField();
        txtPresupuesto.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
        txtPresupuesto.setHorizontalAlignment(JTextField.CENTER);
        txtPresupuesto.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(41, 128, 185)));

        pCuerpo.add(lbl1);
        pCuerpo.add(comboTalla);
        pCuerpo.add(lbl2);
        pCuerpo.add(txtPresupuesto);

        // Botones Inferiores
        JPanel pBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        pBotones.setBackground(Color.WHITE);

        JButton btnBuscar = new JButton("BUSCAR AHORA");
        btnBuscar.setPreferredSize(new Dimension(160, 40));
        btnBuscar.setBackground(new Color(46, 204, 113)); // Verde Acento
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btnCancelar = new JButton("CANCELAR");
        btnCancelar.setPreferredSize(new Dimension(120, 40));
        btnCancelar.setBackground(new Color(231, 76, 60)); // Rojo suave
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorderPainted(false);

        btnBuscar.addActionListener(e -> {
            try {
                presupuesto = Double.parseDouble(txtPresupuesto.getText().replace(",", "."));
                tallaSeleccionada = (String) comboTalla.getSelectedItem();
                confirmado = true;
                dispose();
            } catch (Exception ex) {
                txtPresupuesto.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED));
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        pBotones.add(btnBuscar);
        pBotones.add(btnCancelar);

        pPrincipal.add(pHeader, BorderLayout.NORTH);
        pPrincipal.add(pCuerpo, BorderLayout.CENTER);
        pPrincipal.add(pBotones, BorderLayout.SOUTH);
        add(pPrincipal);
    }

    public String getTalla() { 
    	return tallaSeleccionada; 
    	}
    public double getPresupuesto() { 
    	return presupuesto; 
    	}
    public boolean isConfirmado() { 
    	return confirmado; 
    	}



    private void mostrarMensajePersonalizado(String msj) {
    JOptionPane.showMessageDialog(this, msj, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
