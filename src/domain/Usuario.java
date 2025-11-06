package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Usuario implements Serializable{ 
	
	
	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private String apellidos;
	private String dni;
	private int id;
	private String email;
	private String numTelefono;
	private String password;
	private CarritoCompra carrito;
	private Date fechaRegistro;
	private List<Pedido> historiaPedidos;
	private boolean activo;
	
	
	public Usuario() {
		super();
	}
	
	

	public Usuario(String nombre, String apellidos, String dni, int id, String email, String numTelefono,
			String password, CarritoCompra carrito, Date fechaRegistro, List<Pedido> historiaPedidos, boolean activo) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.id = id;
		this.email = email;
		this.numTelefono = numTelefono;
		this.password = password;
		this.carrito = carrito;
		this.fechaRegistro = fechaRegistro;
		this.historiaPedidos = historiaPedidos;
		this.activo = activo;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public CarritoCompra getCarrito() {
		return carrito;
	}

	public void setCarrito(CarritoCompra carrito) {
		this.carrito = carrito;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public List<Pedido> getHistoriaPedidos() {
		return historiaPedidos;
	}

	public void setHistoriaPedidos(List<Pedido> historiaPedidos) {
		this.historiaPedidos = historiaPedidos;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}


	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", apellidos=" + apellidos + ", dni=" + dni + ", id=" + id + ", email="
				+ email + ", numTelefono=" + numTelefono + ", carrito=" + carrito + ", fechaRegistro=" + fechaRegistro
				+ ", historiaPedidos=" + historiaPedidos + ", activo=" + activo + "]";
	}


}
