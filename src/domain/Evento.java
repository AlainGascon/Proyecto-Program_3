package domain;



import java.time.LocalDate;

public class Evento {
    private LocalDate fecha;
    private String descripcion;
    private String lugar;
    private int capacidad;

    public Evento(LocalDate fecha, String descripcion, String lugar, int capacidad) {
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.capacidad = capacidad;
    }

    // Getters y setters
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

