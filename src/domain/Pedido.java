package domain;

import java.util.Date;
import java.util.List;

public class Pedido {
	

	private int id;
    private Usuario usuario;
    private List<Producto> productos;
    private Date fechaPedido;
    private Date fechaEntregaEstimada;
    private String estado; 
    private double subtotal;
    private double gastosEnvio;
    private double total;
    private Pago pago;
    private String numeroSeguimiento;
    
    
    
    
	public Pedido() {
		super();
	}
	

	public Pedido(int id, Usuario usuario, List<Producto> productos, Date fechaPedido, Date fechaEntregaEstimada,
			String estado, double subtotal, double gastosEnvio, double total, Pago pago, String numeroSeguimiento) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.productos = productos;
		this.fechaPedido = fechaPedido;
		this.fechaEntregaEstimada = fechaEntregaEstimada;
		this.estado = estado;
		this.subtotal = subtotal;
		this.gastosEnvio = gastosEnvio;
		this.total = total;
		this.pago = pago;
		this.numeroSeguimiento = numeroSeguimiento;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public Date getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public Date getFechaEntregaEstimada() {
		return fechaEntregaEstimada;
	}

	public void setFechaEntregaEstimada(Date fechaEntregaEstimada) {
		this.fechaEntregaEstimada = fechaEntregaEstimada;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public double getGastosEnvio() {
		return gastosEnvio;
	}

	public void setGastosEnvio(double gastosEnvio) {
		this.gastosEnvio = gastosEnvio;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Pago getPago() {
		return pago;
	}

	public void setPago(Pago pago) {
		this.pago = pago;
	}

	public String getNumeroSeguimiento() {
		return numeroSeguimiento;
	}

	public void setNumeroSeguimiento(String numeroSeguimiento) {
		this.numeroSeguimiento = numeroSeguimiento;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}


	@Override
	public String toString() {
		return "Pedido [id=" + id + ", usuario=" + usuario + ", productos=" + productos + ", fechaPedido=" + fechaPedido
				+ ", fechaEntregaEstimada=" + fechaEntregaEstimada + ", estado=" + estado +  ", gastosEnvio=" + gastosEnvio + ", total=" + total + ", pago=" + pago
				+ ", numeroSeguimiento=" + numeroSeguimiento + "]";
	}
    
    public void calcularGastosEnvio() {
    	
    	
    	if (this.getSubtotal() > 60) {
    		this.gastosEnvio = 0;
    	} else {
    		this.gastosEnvio = 8;
    	}
    	
    	this.total = this.subtotal + this.gastosEnvio;
    }
    

}
