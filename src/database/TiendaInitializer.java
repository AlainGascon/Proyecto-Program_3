package database;

import domain.Producto;

import java.time.LocalDate;

import domain.Evento;
import domain.Usuario;
	
public class TiendaInitializer {
	public static void inicializarBD() {
		GestorTiendaBD gestor= new GestorTiendaBD();
		gestor.deleteDatabase();
		gestor.createTables();
		insertarDatosIniciales(gestor);
		System.out.println("Base de datos inicializada correctamente.");
	}
	
	private static void insertarDatosIniciales(GestorTiendaBD gestor) {
		if(!gestor.loadProductos().isEmpty()) {
			System.out.println("Base de datos ya inicializada. Datos no añadidos.");
			return;
		}
		
		Usuario u1= new Usuario("Admin", "Tienda", "12345678A", "admin.tienda", "admin@tienda.com", "606000001", "admin123");
		gestor.insertUsuario(u1);
		
		Producto p1= new Producto(5,"CAMISETA KTM", "Estilo Fde competición. Tejido transpirable y ligero, perfecto para fans del motor.", 19.95, "M", 2, null);
		Producto p2= new Producto(1, "CAMISETA BASICA BLANCA", "Algodón 100% orgánico, corte clásico y duradero. Ideal para el día a día.", 15.95, "L", 1, null);
		gestor.insertProducto(p1);
		gestor.insertProducto(p2);
		
		Evento e1= new Evento(LocalDate.now(), "Desfile de invierno", "Gran Via, Bilbao", 120);
		gestor.insertEvento(e1);
	}
}
