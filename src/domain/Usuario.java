package domain;

import java.io.Serializable;

public class Usuario implements Serializable{
	


	private static final long serialVersionUID = 1L;
	private String nombre;
	private String apellidos;
	private String dni;
	private String id;
	private String email;
	private String numTelefono;
	private String password;
	
	
	public Usuario() {
		super();
	}
	
	



	public Usuario(String nombre, String apellidos, String dni, String id, String email, String numTelefono,
			String password) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.id = id;
		this.email = email;
		this.numTelefono = numTelefono;
		this.password = password;
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumTelefono() {
		return numTelefono;
	}

	public void setNumTelefono(String numTelefono) {
		this.numTelefono = numTelefono;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", apellidos=" + apellidos + ", dni=" + dni + ", email="
				+ email + ", numTelefono=" + numTelefono + "]";
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}


}
