package domain;

import java.util.Date;

public class Pago {

	

	private int id;
	private double cantidadAPagar;
	private String metodoPago;
	private String estado;
	private Date fechaPago;
	private String numTransaccion;
	private String numeroTarjeta;
	private String titularTarjeta;
	
	public Pago() {
		super();
	}

	public Pago(int id, double cantidadAPagar, String metodoPago, String estado, Date fechaPago, String numTransaccion,
			String numeroTarjeta, String titularTarjeta) {
		super();
		this.id = id;
		this.cantidadAPagar = cantidadAPagar;
		this.metodoPago = metodoPago;
		this.estado = estado;
		this.fechaPago = fechaPago;
		this.numTransaccion = numTransaccion;
		this.numeroTarjeta = numeroTarjeta;
		this.titularTarjeta = titularTarjeta;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getCantidadAPagar() {
		return cantidadAPagar;
	}

	public void setCantidadAPagar(double cantidadAPagar) {
		this.cantidadAPagar = cantidadAPagar;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getNumTransaccion() {
		return numTransaccion;
	}

	public void setNumTransaccion(String numTransaccion) {
		this.numTransaccion = numTransaccion;
	}

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	public String getTitularTarjeta() {
		return titularTarjeta;
	}

	public void setTitularTarjeta(String titularTarjeta) {
		this.titularTarjeta = titularTarjeta;
	}

	@Override
	public String toString() {
		return "Pago [id=" + id + ", cantidadAPagar=" + cantidadAPagar + ", metodoPago=" + metodoPago + ", estado="
				+ estado + ", fechaPago=" + fechaPago + ", numTransaccion=" + numTransaccion + "]";
	}
	
	
	public boolean validarPago() {
		
		if (cantidadAPagar <= 0) {
			return false;
		}
		if (metodoPago == null || metodoPago.isEmpty()) {
			return false;
		}
		return true; 
	}
	
	public boolean estaPagado() { 
		return this.estado.equals("Pago completado");
	}
	
}
