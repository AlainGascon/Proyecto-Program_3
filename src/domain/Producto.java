package domain;

import java.util.List;

public class Producto {

	private int id;
	private String nombre;
    private String descripcion;
    private double precio;
    private String talla;
    private String color;
    private int stock;
    private String categoria;
    private String marca;
    private boolean activo;
    private List<Opinion> opiniones;
    
	public Producto() {
		super();
	}


	public Producto(int id, String nombre, String descripcion, double precio, String talla, String color, int stock,
			String categoria, String marca, boolean activo, List<Opinion> opiniones) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.talla = talla;
		this.color = color;
		this.stock = stock;
		this.categoria = categoria;
		this.marca = marca;
		this.activo = activo;
		this.opiniones = opiniones;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}


	public List<Opinion> getOpiniones() {
		return opiniones;
	}

	public void setOpiniones(List<Opinion> opiniones) {
		this.opiniones = opiniones;
	}
    	
	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio
				+ ", talla=" + talla + ", color=" + color + ", stock=" + stock + ", categoria=" + categoria + ", marca="
				+ marca + ", activo=" + activo + ", opiniones=" + opiniones + "]";
	}


	public boolean hayStock(int cantidad) {
		if (this.stock >= cantidad) {
			return true;
		} else {
			return false;
		}
	}
    
	public void agregarOpinion(Opinion opinion) {
		this.opiniones.add(opinion);
	}
    
	
	public double getValoracionMedia() {
		
		if (opiniones.isEmpty()) { 
			return 0.0;
		} else {
			int suma = 0;
			for (Opinion op: opiniones) { 
				suma += ((Opinion) opiniones).getPuntuacion();
			}
		return suma / opiniones.size();
		}
		
	}
}
