package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame; 
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import gui.JFramePrincipal; 

public class JFrameAutenticacion extends JFrame { 

	private static final long serialVersionUID = 1L;

    private JFramePrincipal framePrincipal;
	
	private static final Color COLOR_PRIMARIO = new Color(41, 128, 185);	
	private static final Color COLOR_ACENTO = new Color(46, 204, 113);
	private static final Color COLOR_FONDO = new Color(240, 248, 255);

	
	private Vector<String> usuarios;

	
	private JTextField txtUsuarioLogin;
	private JPasswordField txtContrasenaLogin;
	private JTextField txtUsuarioNuevo;
	private JPasswordField txtContrasenaNueva1;
	private JPasswordField txtContrasenaNueva2;
	private JButton btnLogin;
	private JButton btnCrearCuenta;
	
	
	private Image backgroundImage;
	
	public JFrameAutenticacion(JFramePrincipal principal) {
		
        this.framePrincipal = principal;

		this.setTitle("Tienda DEUSTO - Autenticación de Usuario");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		usuarios = new Vector<>();
		usuarios.add("admin:1234");	

		
		try {
			backgroundImage = new ImageIcon(getClass().getResource("/resources/images/escaparate.png")).getImage();
		} catch (Exception e) {
			System.err.println("Error al cargar la imagen de fondo: /resources/images/escaparate.png");	
			e.printStackTrace();
			backgroundImage = null;
		}
	
		
		JPanel contentPane = new JPanel(new GridBagLayout()) {
			
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (backgroundImage != null) {
					g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
				}
			}
		};
		
		this.setContentPane(contentPane);	
		
		
		JPanel panelRegistro = crearPanelRegistro();
		JPanel panelLogin = crearPanelLogin();

		
		panelRegistro.setBackground(COLOR_FONDO);	
		panelLogin.setBackground(COLOR_FONDO);	 	
		panelRegistro.setOpaque(true);	
		panelLogin.setOpaque(true);	
		
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;	
		gbc.gridy = 0;	
		gbc.insets = new Insets(30, 30, 30, 15);
		
		contentPane.add(panelLogin, gbc);
		
		gbc.gridx = 1;	
		gbc.insets = new Insets(30, 15, 30, 30);
		
		contentPane.add(panelRegistro, gbc);
		
		agregarListeners();
		
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	
	private JPanel crearPanelRegistro() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBackground(COLOR_FONDO);
		panel.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(15, 15, 15, 7),	
			BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(COLOR_PRIMARIO, 2),	
				"➕ Crear Cuenta Nueva",	
				TitledBorder.LEFT,	
				TitledBorder.TOP,	
				new Font("SansSerif", Font.BOLD, 16),	
				COLOR_PRIMARIO
			)
		));

		JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 15));
		formPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		formPanel.setBackground(COLOR_FONDO);

		txtUsuarioNuevo = new JTextField(15);
		txtContrasenaNueva1 = new JPasswordField(15);
		txtContrasenaNueva2 = new JPasswordField(15);
		btnCrearCuenta = new JButton("Crear Cuenta");
		btnCrearCuenta.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnCrearCuenta.setBackground(COLOR_ACENTO);	
		btnCrearCuenta.setForeground(Color.WHITE);

		formPanel.add(crearEtiqueta("👤 Nuevo Usuario:"));
		formPanel.add(txtUsuarioNuevo);
		formPanel.add(crearEtiqueta("🔑 Contraseña (1/2):"));
		formPanel.add(txtContrasenaNueva1);
		formPanel.add(crearEtiqueta("🔑 Repetir Contraseña (2/2):"));
		formPanel.add(txtContrasenaNueva2);
		formPanel.add(new JLabel());	
		formPanel.add(btnCrearCuenta);

		panel.add(formPanel, BorderLayout.CENTER);
		return panel;
	}

	private JPanel crearPanelLogin() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBackground(COLOR_FONDO);
		panel.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(15, 7, 15, 15),	
			BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(COLOR_PRIMARIO, 2),	
				"➡️ Iniciar Sesión",	
				TitledBorder.RIGHT,	
				TitledBorder.TOP,	
				new Font("SansSerif", Font.BOLD, 16),	
				COLOR_PRIMARIO
			)
		));
		
		JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 15));
		formPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		formPanel.setBackground(COLOR_FONDO);

		txtUsuarioLogin = new JTextField(15);
		txtContrasenaLogin = new JPasswordField(15);
		btnLogin = new JButton("Iniciar Sesión");
		btnLogin.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnLogin.setBackground(COLOR_PRIMARIO);
		btnLogin.setForeground(Color.WHITE);

		formPanel.add(crearEtiqueta("👤 Usuario:"));
		formPanel.add(txtUsuarioLogin);
		formPanel.add(crearEtiqueta("🔑 Contraseña:"));
		formPanel.add(txtContrasenaLogin);
		formPanel.add(new JLabel());	
		formPanel.add(btnLogin);
		
		panel.add(formPanel, BorderLayout.CENTER);
		return panel;
	}
	
	private JLabel crearEtiqueta(String texto) {
		JLabel lbl = new JLabel(texto, SwingConstants.RIGHT);
		lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
		return lbl;
	}

	
	private void agregarListeners() {
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				iniciarSesion();
			}
		});

		btnCrearCuenta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				crearCuenta();
			}
		});
	}
	
	private void iniciarSesion() {
		String usuario = txtUsuarioLogin.getText().trim();
		String contrasena = new String(txtContrasenaLogin.getPassword()).trim();
		String credenciales = usuario + ":" + contrasena;

		if (usuario.isEmpty() || contrasena.isEmpty()) {
			mostrarError("Debe rellenar ambos campos para iniciar sesión.", "Campos Vacíos");
			return;
		}
		
		if (usuarios.contains(credenciales)) {
			mostrarExito("¡Inicio de sesión exitoso! Bienvenido, " + usuario + ".", "Éxito");
			
            if (framePrincipal != null) {
                JFramePrincipal.setLoggedIn(true);
            }
			
			dispose();
		} else {
			boolean usuarioExiste = false;
			for (String userPass : usuarios) {
				if (userPass.startsWith(usuario + ":")) {
					usuarioExiste = true;
					break;
				}
			}
			
			if (!usuarioExiste) {
				mostrarError("❌ Usuario no encontrado. Por favor, regístrese.", "Error de Usuario");
			} else {
				mostrarError("❌ Contraseña incorrecta para el usuario " + usuario + ".", "Error de Contraseña");
			}
		}
	}
	
	private void crearCuenta() {
		String usuario = txtUsuarioNuevo.getText().trim();
		String contrasena1 = new String(txtContrasenaNueva1.getPassword()).trim();
		String contrasena2 = new String(txtContrasenaNueva2.getPassword()).trim();

		if (usuario.isEmpty() || contrasena1.isEmpty() || contrasena2.isEmpty()) {
			mostrarError("Debe rellenar todos los campos para crear una cuenta.", "Campos Vacíos");
			return;
		}

		
		for (String userPass : usuarios) {
			if (userPass.startsWith(usuario + ":")) {
				mostrarError("❌ El nombre de usuario '" + usuario + "' ya está registrado. Por favor, elija otro.", "Error de Registro");
				return;	
			}
		}

		
		if (!contrasena1.equals(contrasena2)) {
			mostrarError("❌ Las contraseñas no coinciden. Inténtelo de nuevo.", "Error de Contraseña");
			txtContrasenaNueva1.setText("");
			txtContrasenaNueva2.setText("");
			return;
		}
		
		if (contrasena1.length() < 6) {
			mostrarError("❌ La contraseña debe tener al menos 6 caracteres.", "Contraseña Débil");
			return;
		}
		
		
		boolean tieneNumero = false;
		
		for (char c: contrasena1.toCharArray()) {
			if (Character.isDigit(c)) {
				tieneNumero = true;
				break;
			}
		}
		
		if (!tieneNumero) {
			mostrarError("❌ La contraseña debe contener al menos un número.", "Contraseña Débil");
			return;
		}
		
		usuarios.add(usuario + ":" + contrasena1);
		JOptionPane.showMessageDialog(this, "🎉 ¡Cuenta creada con éxito!\nAhora puede iniciar sesión.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
		
		
		txtUsuarioNuevo.setText("");
		txtContrasenaNueva1.setText("");
		txtContrasenaNueva2.setText("");
		txtUsuarioLogin.setText(usuario);	
	}
	
	private void mostrarError(String mensaje, String titulo) {
		JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
	}

	private void mostrarExito(String mensaje, String titulo) {
		JOptionPane.showMessageDialog(this, "✅ " + mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
	}


}