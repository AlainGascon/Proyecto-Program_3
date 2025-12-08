package domain;



import java.time.LocalDate;

public class Evento {
	private String nombre;
    private LocalDate fecha;
    private String descripcion;
    private String lugar;
    private int capacidad;

    public Evento(String nombre, LocalDate fecha, String descripcion, String lugar, int capacidad) {
        this.setNombre(nombre);
    	this.fecha = fecha;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.capacidad = capacidad;
    }

    // Getters y setters
    
    public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

	@Override
	public String toString() {
		return "Evento [fecha=" + fecha + ", descripcion=" + descripcion + ", lugar=" + lugar + ", capacidad="
				+ capacidad + "]";
	}

	
	
    
}

