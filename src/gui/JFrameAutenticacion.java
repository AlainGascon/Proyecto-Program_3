package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
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

public class JFrameAutenticacion extends JFrame {

    private static final long serialVersionUID = 1L;

   
    private static final Color COLOR_PRIMARIO = new Color(30, 144, 255); 
    private static final Color COLOR_ERROR = new Color(220, 20, 60); 
    private static final Color COLOR_FONDO = new Color(240, 248, 255); 

    
    private Vector<String> usuarios;

    
    private JTextField txtUsuarioLogin;
    private JPasswordField txtContrasenaLogin;
    private JButton btnLogin;

    
    private JTextField txtUsuarioNuevo;
    private JPasswordField txtContrasenaNueva1;
    private JPasswordField txtContrasenaNueva2;
    private JButton btnCrearCuenta;

    public JFrameAutenticacion() {
       
        usuarios = new Vector<>();
        usuarios.add("admin:1234");

        setTitle("🔑 Acceso y Registro de Usuario");
        setSize(1200, 550); 
        setResizable(true); 
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        
        setLayout(new BorderLayout()); 
        getContentPane().setBackground(COLOR_FONDO);
        
        
        JPanel panelRegistro = crearPanelRegistro();
        JPanel panelLogin = crearPanelLogin();

        
        Dimension panelDim = new Dimension(485, 500); 
        
        panelRegistro.setPreferredSize(panelDim);
        panelLogin.setPreferredSize(panelDim);
        
      
        JPanel panelCentral = new JPanel(new GridBagLayout()); 
        panelCentral.setBackground(COLOR_FONDO);
        
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; 
        gbc.gridy = 0; 
        gbc.insets = new Insets(0, 0, 0, 15);
        
        
        panelCentral.add(panelRegistro, gbc);
        
        gbc.gridx = 1; 
        gbc.insets = new Insets(0, 15, 0, 0); 
        
        
        panelCentral.add(panelLogin, gbc);
        
        
        add(panelCentral, BorderLayout.CENTER);
        
        agregarListeners();
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
        btnCrearCuenta.setBackground(new Color(60, 179, 113)); 
        btnCrearCuenta.setForeground(Color.WHITE);

        
        formPanel.add(crearEtiqueta("👤 Nuevo Usuario:"));
        formPanel.add(txtUsuarioNuevo);
        formPanel.add(crearEtiqueta("🔑 Contraseña:"));
        formPanel.add(txtContrasenaNueva1);
        formPanel.add(crearEtiqueta("🔑 Repetir Contraseña:"));
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
            JOptionPane.showMessageDialog(this, "✅ ¡Inicio de sesión exitoso! Bienvenido, " + usuario + ".", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
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
        
       
        usuarios.add(usuario + ":" + contrasena1);
        JOptionPane.showMessageDialog(this, "🎉 ¡Cuenta creada con éxito!\nAhora puede iniciar sesión.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
        
       
        txtUsuarioNuevo.setText("");
        txtContrasenaNueva1.setText("");
        txtContrasenaNueva2.setText("");
        txtUsuarioLogin.setText(usuario);
    }
    
    
    private void mostrarError(String mensaje, String titulo) {
        
        String mensajeHTML = "<html><font color='red'><b>¡ERROR!</b></font><br>" + mensaje + "</html>";
        JOptionPane.showMessageDialog(this, mensajeHTML, titulo, JOptionPane.ERROR_MESSAGE);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrameAutenticacion frame = new JFrameAutenticacion();
            frame.setVisible(true);
        });
    }
}