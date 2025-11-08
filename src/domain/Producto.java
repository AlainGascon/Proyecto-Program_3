package domain;

import java.util.List;
import java.util.Map;

public class Producto {

	private int id;
	private String nombre;
    private String descripcion;
    private double precio;
    private String talla;
    private int stock;
    private List<Opinion> opiniones;
    private Map<String, Integer> inventarioPorTalla;
    
	public Producto() {
		super();
	}


	public Producto(int id, String nombre, String descripcion, double precio, String talla, int stock,
			List<Opinion> opiniones, Map<String, Integer> inventarioPorTalla) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.talla = talla;
		this.stock = stock;
		this.opiniones = opiniones;
		this.inventarioPorTalla = inventarioPorTalla;
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


	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}


	public List<Opinion> getOpiniones() {
		return opiniones;
	}

	public void setOpiniones(List<Opinion> opiniones) {
		this.opiniones = opiniones;
	}
	
	public int getStock(String talla) {
        return inventarioPorTalla.getOrDefault(talla, 0); 
    }
    
    public void setStock(String talla, int cantidad) {
        inventarioPorTalla.put(talla, cantidad);
    }
    	
	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio
				+ ", talla=" + talla + ", stock=" + stock + ", opiniones=" + opiniones + "]";
	}


	public int getStockTotal() {
        if (inventarioPorTalla == null || inventarioPorTalla.isEmpty()) {
            return 0;
        }
        
        return inventarioPorTalla.values().stream().mapToInt(Integer::intValue).sum();
    }
	
	public boolean hayStock(int cantidad) {
        
		if (getStockTotal() >= cantidad) { 
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

	public Map<String, Integer> getInventarioPorTalla() {
        return inventarioPorTalla;
    }
	

}







