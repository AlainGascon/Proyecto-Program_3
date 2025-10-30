package domain;

import java.util.Date;

public class Admin {

	private String rol;
	private Date fechaContratacion;
	private boolean permisoTotal;
	
	
	public Admin() {
		super();
		this.permisoTotal = true;
	}


	public Admin(String rol, Date fechaContratacion, boolean permisoTotal) {
		super();
		this.rol = rol;
		this.fechaContratacion = new Date();
		this.permisoTotal = true;
	}
	
	
	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public Date getFechaContratacion() {
		return fechaContratacion;
	}

	public void setFechaContratacion(Date fechaContratacion) {
		this.fechaContratacion = fechaContratacion;
	}

	public boolean isPermisoTotal() {
		return permisoTotal;
	}

	public void setPermisoTotal(boolean permisoTotal) {
		this.permisoTotal = permisoTotal;
	}


	@Override
	public String toString() {
		return "Admin [rol=" + rol + ", fechaContratacion=" + fechaContratacion + ", permisoTotal=" + permisoTotal
				+ "]";
	}

	public void agregarProducto(Producto producto) {
        System.out.println("Producto agregado: " + producto.getNombre());
    }
    
    public void eliminarProducto(Producto producto) {
        producto.setActivo(false);
        System.out.println("Producto eliminado: " + producto.getNombre());
    }
    
    public void modificarProducto(Producto producto, String nuevoNombre, double nuevoPrecio) {
        producto.setNombre(nuevoNombre);
        producto.setPrecio(nuevoPrecio);
        System.out.println("Producto modificado");
    }
    
    public void gestionarPedido(Pedido pedido, String nuevoEstado) {
        pedido.setEstado(nuevoEstado);
        System.out.println("Estado del pedido " + pedido.getId() + " actualizado a: " + nuevoEstado);
    }
    
    public void eliminarOpinion(Opinion opinion) {
        opinion.setVisible(false);
        System.out.println("Opinión eliminada por el administrador");
    }
	
}
