package domain;


import java.util.Map;

public class Producto {

	private int id;
	private String nombre;
    private String descripcion;
    private double precio;
    private String talla;
    private int stock;
    private Map<String, Integer> inventarioPorTalla;
    
	public Producto() {
		super();
	}


	public Producto(int id, String nombre, String descripcion, double precio, String talla, int totalStock, Map<String, Integer> inventarioPorTalla) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.talla = talla;
		this.stock = totalStock;
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

	
	public int getStock(String talla) {
        return inventarioPorTalla.getOrDefault(talla, 0); 
    }
    
    public void setStock(String talla, int cantidad) {
        inventarioPorTalla.put(talla, cantidad);
    }
    	
	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio
				+ ", talla=" + talla + ", stock=" + stock + "]";
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


	public Map<String, Integer> getInventarioPorTalla() {
        return inventarioPorTalla;
    }
	
	
	public void decrementarStock(String talla, int cantidad) {
        if (inventarioPorTalla.containsKey(talla)) {
            int currentStock = inventarioPorTalla.get(talla);
            int newStock = currentStock - cantidad;
            
            if (newStock >= 0) {
                inventarioPorTalla.put(talla, newStock);
                this.stock -= cantidad; // Actualiza también el campo de stock total
            } else {
                // Manejar error si se intenta decrementar más de lo que hay
                System.err.println("Error: Intento de restar stock negativo para la talla " + talla + " del producto " + nombre);
            }
        }
    }

}







