package gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;


import javax.swing.JButton;
import javax.swing.JFrame; 
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JFramePrincipal extends JFrame{

	private static final long serialVersionUID = 1L;
	public JTextField display;
	
	public void Principal()  {
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		
		this.setTitle("Tienda de Ropa DEUSTO - Bienvenido");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout(10,10));
		this.setSize(750,500);
		this.setLocationRelativeTo(null);
		
		display = new JTextField();
		display.setEditable(false);
		display.setHorizontalAlignment(JTextField.RIGHT);
		display.setFont(new Font("Courier", Font.BOLD, 24));
		display.setPreferredSize(new Dimension(0, 100));
		
		JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		
		JButton boton1 = new JButton("CATALOGO");
		JButton boton2 = new JButton("PEDIDOS");
		JButton boton3 = new JButton("????");
		
		panelSuperior.add(boton1);
		panelSuperior.add(boton2);
		panelSuperior.add(boton3);
		
		mainPanel.add(panelSuperior);
		
		this.add(mainPanel, BorderLayout.CENTER);
		
		this.setVisible(true);
	}
}

