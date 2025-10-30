package domain;

import java.util.List;

public class CarritoCompra {
	
	private int id;
    private List<ItemCarrito> items;
    private double descuento;
    
    
	public CarritoCompra() {
		super();
	}

	public CarritoCompra(int id, List<ItemCarrito> items, double descuento) {
		super();
		this.id = id;
		this.items = items;
		this.descuento = descuento;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ItemCarrito> getItems() {
		return items;
	}

	public void setItems(List<ItemCarrito> items) {
		this.items = items;
	}

	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	@Override
	public String toString() {
		return "CarritoCompra [id=" + id + ", items=" + items + ", descuento=" + descuento + "]";
	}
    
	public void agregarProducto(Producto producto, int cantidad) {
      
        for (ItemCarrito item : items) {
            if (item.getProducto().getId() == producto.getId()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        
        items.add(new ItemCarrito(producto, cantidad));
    }

	

}
