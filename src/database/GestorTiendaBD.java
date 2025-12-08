package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import domain.Opinion;
import domain.Pago;



public class GestorTiendaBD {
	
    private static final String SQLITE_FILE = "resources/db/tienda.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:" + SQLITE_FILE;
    
    public GestorTiendaBD() {
        File directorio = new File("resources/db");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.format("* Error al cargar el driver de la BBDD: %s\n", e.getMessage());
        }
    }
    
    /**
     * Crea todas las tablas necesarias para la aplicación de la tienda.
     */
    public void createTables() {
        System.out.println("-> Creando tablas si no existen...");
        
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = con.createStatement()) {

            // Habilitar la integridad referencial para FKs en SQLite
            stmt.execute("PRAGMA foreign_keys = ON;");

            // 1. TABLA USUARIOS (EXISTENTE)
            String sqlUsuario = "CREATE TABLE IF NOT EXISTS USUARIOS ("
                            + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "NOMBRE TEXT NOT NULL,"
                            + "APELLIDOS TEXT,"
                            + "DNI TEXT UNIQUE NOT NULL,"
                            + "EMAIL TEXT UNIQUE NOT NULL,"
                            + "NUM_TELEFONO TEXT,"
                            + "PASSWORD TEXT NOT NULL,"
                            + "FECHA_REGISTRO TEXT," 
                            + "ACTIVO BOOLEAN NOT NULL" 
                            + ");";
            stmt.execute(sqlUsuario);
            
            // 2. TABLA PRODUCTOS (EXISTENTE)
            String sqlProducto = "CREATE TABLE IF NOT EXISTS PRODUCTOS ("
                            + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "NOMBRE TEXT NOT NULL,"
                            + "PRECIO REAL NOT NULL,"
                            + "DESCRIPCION TEXT"
                            + ");";
            stmt.execute(sqlProducto);
            
            // 3. TABLA STOCK_TALLA (EXISTENTE - Relación de Producto)
            String sqlStock = "CREATE TABLE IF NOT EXISTS STOCK_TALLA ("
                            + "ID_PRODUCTO INTEGER NOT NULL,"
                            + "TALLA TEXT NOT NULL,"
                            + "CANTIDAD INTEGER NOT NULL,"
                            + "PRIMARY KEY (ID_PRODUCTO, TALLA),"
                            + "FOREIGN KEY (ID_PRODUCTO) REFERENCES PRODUCTOS(ID) ON DELETE CASCADE"
                            + ");";
            stmt.execute(sqlStock);

            // 4. TABLA EVENTOS (EXISTENTE)
            String sqlEvento = "CREATE TABLE IF NOT EXISTS EVENTOS ("
                            + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "NOMBRE TEXT NOT NULL,"
                            + "FECHA TEXT NOT NULL," 
                            + "DESCRIPCION TEXT,"
                            + "LUGAR TEXT NOT NULL,"
                            + "CAPACIDAD INTEGER"
                            + ");";
            stmt.execute(sqlEvento);

            // 5. TABLA PAGO (NUEVA)
            String sqlPago = "CREATE TABLE IF NOT EXISTS PAGO ("
                            + " ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + " CANTIDAD_A_PAGAR REAL NOT NULL,"
                            + " METODO_PAGO TEXT NOT NULL,"
                            + " ESTADO TEXT NOT NULL,"
                            + " FECHA_PAGO TEXT NOT NULL,"
                            + " NUM_TRANSACCION TEXT UNIQUE NOT NULL,"
                            + " NUMERO_TARJETA TEXT,"
                            + " TITULAR_TARJETA TEXT"
                            + ");";
            stmt.execute(sqlPago);
            
            // 6. TABLA PEDIDO (NUEVA)
            String sqlPedido = "CREATE TABLE IF NOT EXISTS PEDIDO ("
                            + " ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + " USUARIO_ID INTEGER NOT NULL,"
                            + " FECHA_PEDIDO TEXT NOT NULL,"
                            + " FECHA_ENTREGA_ESTIMADA TEXT,"
                            + " ESTADO TEXT NOT NULL,"
                            + " SUBTOTAL REAL,"
                            + " GASTOS_ENVIO REAL,"
                            + " TOTAL REAL NOT NULL,"
                            + " PAGO_ID INTEGER UNIQUE," 
                            + " NUMERO_SEGUIMIENTO TEXT UNIQUE NOT NULL,"
                            + " FOREIGN KEY(USUARIO_ID) REFERENCES USUARIOS(ID) ON DELETE RESTRICT,"
                            + " FOREIGN KEY(PAGO_ID) REFERENCES PAGO(ID) ON DELETE RESTRICT"
                            + ");";
            stmt.execute(sqlPedido);
            
            // 7. TABLA PEDIDO_PRODUCTO (NUEVA - Relación Pedido N:M Producto)
            String sqlPedidoProducto = "CREATE TABLE IF NOT EXISTS PEDIDO_PRODUCTO ("
                            + " PEDIDO_ID INTEGER NOT NULL,"
                            + " PRODUCTO_ID INTEGER NOT NULL,"
                            + " CANTIDAD INTEGER NOT NULL,"
                            + " TALLA TEXT NOT NULL,"
                            + " PRIMARY KEY (PEDIDO_ID, PRODUCTO_ID, TALLA),"
                            + " FOREIGN KEY(PEDIDO_ID) REFERENCES PEDIDO(ID) ON DELETE CASCADE,"
                            + " FOREIGN KEY(PRODUCTO_ID) REFERENCES PRODUCTOS(ID) ON DELETE RESTRICT"
                            + ");";
            stmt.execute(sqlPedidoProducto);
            
            // 8. TABLA OPINION (NUEVA)
            String sqlOpinion = "CREATE TABLE IF NOT EXISTS OPINION ("
                            + " ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + " USUARIO_ID INTEGER NOT NULL,"
                            + " PRODUCTO_ID INTEGER NOT NULL,"
                            + " PUNTUACION INTEGER NOT NULL,"
                            + " TITULO TEXT,"
                            + " COMENTARIO TEXT,"
                            + " FECHA_OPINION TEXT NOT NULL,"
                            + " ME_GUSTA INTEGER,"
                            + " VISIBLE BOOLEAN NOT NULL,"
                            + " FOREIGN KEY(USUARIO_ID) REFERENCES USUARIOS(ID) ON DELETE CASCADE,"
                            + " FOREIGN KEY(PRODUCTO_ID) REFERENCES PRODUCTOS(ID) ON DELETE CASCADE"
                            + ");";
            stmt.execute(sqlOpinion);
            
            // 9. TABLA CARRITO_COMPRA (NUEVA - Asumiendo 1:1 con USUARIO)
            String sqlCarritoCompra = "CREATE TABLE IF NOT EXISTS CARRITO_COMPRA ("
                            + " ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + " USUARIO_ID INTEGER UNIQUE NOT NULL,"
                            + " DESCUENTO REAL,"
                            + " FOREIGN KEY(USUARIO_ID) REFERENCES USUARIOS(ID) ON DELETE CASCADE"
                            + ");";
            stmt.execute(sqlCarritoCompra);

            // 10. TABLA ITEM_CARRITO (NUEVA - Relación Carrito N:M Producto)
            String sqlItemCarrito = "CREATE TABLE IF NOT EXISTS ITEM_CARRITO ("
                            + " CARRITO_ID INTEGER NOT NULL,"
                            + " PRODUCTO_ID INTEGER NOT NULL,"
                            + " TALLA TEXT NOT NULL,"
                            + " CANTIDAD INTEGER NOT NULL,"
                            + " PRIMARY KEY (CARRITO_ID, PRODUCTO_ID, TALLA),"
                            + " FOREIGN KEY(CARRITO_ID) REFERENCES CARRITO_COMPRA(ID) ON DELETE CASCADE,"
                            + " FOREIGN KEY(PRODUCTO_ID) REFERENCES PRODUCTOS(ID) ON DELETE RESTRICT"
                            + ");";
            stmt.execute(sqlItemCarrito);

            System.out.println("-> Todas las tablas de la Tienda creadas/verificadas.");

        } catch (SQLException e) {
            System.err.format("* Error SQL al crear las tablas: %s\n", e.getMessage());
            e.printStackTrace();
        }
    }
    
    // NOTA: Los métodos de inserción (insertUsuario, insertProducto, etc.) 
    // y los métodos de carga (loadProductos, etc.) deben implementarse ahora para cada una de las 
    // nuevas tablas, siguiendo la misma lógica usada en los métodos previos.
    
    // Métodos para USUARIOS, PRODUCTOS, EVENTOS se mantienen.
    // ...
    
    /**
     * Inserta un nuevo Pago en la BD.
     */
    public void insertPago(Pago pago) {
        String sql = "INSERT INTO PAGO (CANTIDAD_A_PAGAR, METODO_PAGO, ESTADO, FECHA_PAGO, NUM_TRANSACCION, NUMERO_TARJETA, TITULAR_TARJETA) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setDouble(1, pago.getCantidadAPagar());
            pstmt.setString(2, pago.getMetodoPago());
            pstmt.setString(3, pago.getEstado());
            pstmt.setString(4, pago.getFechaPago() != null ? pago.getFechaPago().toString() : null); 
            pstmt.setString(5, pago.getNumTransaccion());
            pstmt.setString(6, pago.getNumeroTarjeta());
            pstmt.setString(7, pago.getTitularTarjeta());
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pago.setId(rs.getInt(1)); // Asignar el ID generado al objeto Pago
                }
            }
            
        } catch (SQLException e) {
            System.err.format("* Error al insertar Pago '%s': %s\n", pago.getNumTransaccion(), e.getMessage());
        }
    }

    /**
     * Inserta una nueva Opinión en la BD.
     */
    public void insertOpinion(Opinion opinion) {
        String sql = "INSERT INTO OPINION (USUARIO_ID, PRODUCTO_ID, PUNTUACION, TITULO, COMENTARIO, FECHA_OPINION, ME_GUSTA, VISIBLE) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        // **NOTA IMPORTANTE**: Se necesitan los IDs de Usuario y Producto.
        // Asumimos que los objetos Usuario y Producto dentro de Opinion tienen sus IDs cargados.
        int usuarioId = opinion.getUsuario() != null ? opinion.getUsuario().getId() : -1;
        int productoId = opinion.getProducto() != null ? opinion.getProducto().getId() : -1;

        if (usuarioId == -1 || productoId == -1) {
            System.err.println("* Error al insertar Opinión: Falta el ID de Usuario o Producto.");
            return;
        }

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setInt(1, usuarioId);
            pstmt.setInt(2, productoId);
            pstmt.setInt(3, opinion.getPuntuacion());
            pstmt.setString(4, opinion.getTitulo());
            pstmt.setString(5, opinion.getComentario());
            pstmt.setString(6, opinion.getFechaOpinion() != null ? opinion.getFechaOpinion().toString() : null);
            pstmt.setInt(7, opinion.getMeGusta());
            pstmt.setBoolean(8, opinion.isVisible());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.format("* Error al insertar Opinión: %s\n", e.getMessage());
        }
    }
    
    // (Los demás métodos insert/load para CarritoCompra, ItemCarrito y Pedido deben seguir aquí)
    
    // ... Se omite el resto de la clase para no repetir el código que ya conoces
}