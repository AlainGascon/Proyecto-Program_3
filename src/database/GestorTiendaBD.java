package database;

import java.beans.Statement;
import java.io.FileReader;
import java.util.Properties;

public class GestorTiendaBD {
	
	public GestorTiendaBD() {		
		
		try {
			//Se crea el Properties y se actualizan los 3 parámetros
			Properties connectionProperties = new Properties();
			connectionProperties.load(new FileReader("resources/parametros.properties"));
			
			DRIVER_NAME = connectionProperties.getProperty("DRIVER_NAME");
			DATABASE_FILE = connectionProperties.getProperty("DATABASE_FILE");
			CONNECTION_STRING = connectionProperties.getProperty("CONNECTION_STRING") + DATABASE_FILE;
			
			//Cargar el diver SQLite
			Class.forName(DRIVER_NAME);
		} catch (Exception ex) {
			System.err.format("\n* Error al cargar el driver de BBDD: %s", ex.getMessage());
			ex.printStackTrace();
		}
	}


	public void crearBBDD() {
		
		String sqlUsuario = "CREATE TABLE IF NOT EXISTS USUARIO (\n"
				+ " ID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
				+ " NOMBRE_USUARIO TEXT NOT NULL UNIQUE,\n" 
				+ " PASSWORD TEXT NOT NULL\n"
				+ ");";
		
		String sqlProducto = "CREATE TABLE IF NOT EXISTS PRODUCTOS (\n"
				+ " ID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
				+ " NOMBRE TEXT NOT NULL,\n"
				+ " PRECIO REAL NOT NULL,\n"
				+ " DESCRIPCION TEXT\n"
				+ ");";
		
		String sqlStock = "CREATE TABLE IF NOT EXISTS STOCK_TALLA (\n"
				+ " ID_PRODUCTO INTEGER,\n"
				+ " TALLA TEXT NOT NULL,\n"
				+ " CANTIDAD INTEGER NOT NULL,\n"
				+ " PRIMARY KEY (ID_PRODUCTO, TALLA),\n"
				+ " FOREIGN KEY (ID_PRODUCTO) REFERENCES PRODUCTOS(ID) ON DELETE CASCADE\n"
				+ ");";
		
		String sqlEvento = "CREATE TABLE IF NOT EXISTS EVENTOS (\n"
				+ " ID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
				+ " FECHA TEXT NOT NULL,\n" // Almacenado como texto (ISO 8601: YYYY-MM-DD)
				+ " NOMBRE TEXT NOT NULL,\n"
				+ " LUGAR TEXT NOT NULL,\n"
				+ " ASISTENTES_ESTIMADOS INTEGER\n"
				+ ");";
		
		try (Statement stmt = con.createStatement()) {
			stmt.execute(sqlUsuario);
			System.out.println("\n- Se ha creado la tabla USUARIOS");
			stmt.execute(sqlProducto);
			System.out.println("- Se ha creado la tabla PRODUCTOS");
			stmt.execute(sqlStock);
			System.out.println("- Se ha creado la tabla STOCK_TALLA");
			stmt.execute(sqlEvento);
			System.out.println("- Se ha creado la tabla EVENTOS");
		}

	} catch (Exception ex) {
		System.err.format("\n* Error al crear la BBDD: %s", ex.getMessage());
		ex.printStackTrace();
	}
		
	}
	
	
	public void borrarBBDD() {
		
	}
	
}
