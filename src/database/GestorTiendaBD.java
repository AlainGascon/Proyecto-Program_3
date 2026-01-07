package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import domain.Producto;
import domain.Evento;
import domain.Usuario;
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

    // --- MÉTODOS DE CREACION ---

    public void createTables() {
        System.out.println("-> Creando tablas si no existen...");
        
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = con.createStatement()) {

            stmt.execute("PRAGMA foreign_keys = ON;");

            // 1. USUARIOS 
            String sqlUsuario = "CREATE TABLE IF NOT EXISTS USUARIOS ("
                            + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "NOMBRE TEXT NOT NULL,"
                            + "APELLIDOS TEXT,"
                            + "DNI TEXT UNIQUE NOT NULL,"
                            + "EMAIL TEXT UNIQUE NOT NULL,"
                            + "NUM_TELEFONO TEXT,"
                            + "PASSWORD TEXT NOT NULL"
                            + ");";
            stmt.execute(sqlUsuario);
            
            // 2. PRODUCTOS 
            String sqlProducto = "CREATE TABLE IF NOT EXISTS PRODUCTOS ("
                            + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "NOMBRE TEXT NOT NULL,"                  
                            + "DESCRIPCION TEXT,"
                            + "PRECIO REAL NOT NULL"
                            + ");";
            stmt.execute(sqlProducto);
            
            // 3. STOCK_TALLA
            String sqlStock = "CREATE TABLE IF NOT EXISTS STOCK_TALLA ("
                            + "ID_PRODUCTO INTEGER NOT NULL,"
                            + "TALLA TEXT NOT NULL,"
                            + "CANTIDAD INTEGER NOT NULL,"
                            + "PRIMARY KEY (ID_PRODUCTO, TALLA),"
                            + "FOREIGN KEY (ID_PRODUCTO) REFERENCES PRODUCTOS(ID) ON DELETE CASCADE"
                            + ");";
            stmt.execute(sqlStock);

            // 4. EVENTOS
            String sqlEvento = "CREATE TABLE IF NOT EXISTS EVENTOS ("
                            + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "NOMBRE TEXT NOT NULL,"
                            + "FECHA TEXT NOT NULL," 
                            + "DESCRIPCION TEXT,"
                            + "LUGAR TEXT NOT NULL,"
                            + "CAPACIDAD INTEGER"
                            + ");";
            stmt.execute(sqlEvento);

            // 5. PAGO
            String sqlPago = "CREATE TABLE IF NOT EXISTS PAGO ("
                            + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"                            
                            + "METODO_PAGO TEXT NOT NULL,"
                            + "CANTIDAD_A_PAGAR REAL NOT NULL,"
                            + "ESTADO TEXT NOT NULL,"
                            + "FECHA_PAGO TEXT NOT NULL,"
                            + "NUM_TRANSACCION TEXT UNIQUE NOT NULL,"
                            + "NUMERO_TARJETA TEXT,"
                            + "TITULAR_TARJETA TEXT"
                            + ");";
            stmt.execute(sqlPago);
            
            // 6. PEDIDO
            String sqlPedido = "CREATE TABLE IF NOT EXISTS PEDIDO ("
                            + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "USUARIO_ID INTEGER NOT NULL,"
                            + "FECHA_PEDIDO TEXT NOT NULL,"
                            + "FECHA_ENTREGA_ESTIMADA TEXT,"
                            + "ESTADO TEXT NOT NULL,"
                            + "SUBTOTAL REAL,"
                            + "GASTOS_ENVIO REAL,"
                            + "TOTAL REAL NOT NULL,"
                            + "PAGO_ID INTEGER UNIQUE," 
                            + "NUMERO_SEGUIMIENTO TEXT UNIQUE NOT NULL,"
                            + "FOREIGN KEY(USUARIO_ID) REFERENCES USUARIOS(ID) ON DELETE RESTRICT,"
                            + "FOREIGN KEY(PAGO_ID) REFERENCES PAGO(ID) ON DELETE RESTRICT"
                            + ");";
            stmt.execute(sqlPedido);
            
            // 7. PEDIDO_PRODUCTO (Tabla Auxiliar N:M)
            String sqlPedidoProducto = "CREATE TABLE IF NOT EXISTS PEDIDO_PRODUCTO ("
                            + "PEDIDO_ID INTEGER NOT NULL,"
                            + "PRODUCTO_ID INTEGER NOT NULL,"
                            + "CANTIDAD INTEGER NOT NULL,"
                            + "TALLA TEXT NOT NULL,"
                            + "PRIMARY KEY (PEDIDO_ID, PRODUCTO_ID, TALLA),"
                            + "FOREIGN KEY(PEDIDO_ID) REFERENCES PEDIDO(ID) ON DELETE CASCADE,"
                            + "FOREIGN KEY(PRODUCTO_ID) REFERENCES PRODUCTOS(ID) ON DELETE RESTRICT"
                            + ");";
            stmt.execute(sqlPedidoProducto);
            
            // 8. CARRITO_COMPRA (Asumiendo 1:1 con USUARIO)
            String sqlCarritoCompra = "CREATE TABLE IF NOT EXISTS CARRITO_COMPRA ("
                            + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + "USUARIO_ID INTEGER UNIQUE NOT NULL,"
                            + "DESCUENTO REAL,"
                            + "FOREIGN KEY(USUARIO_ID) REFERENCES USUARIOS(ID) ON DELETE CASCADE"
                            + ");";
            stmt.execute(sqlCarritoCompra);

            // 9. ITEM_CARRITO (Tabla Auxiliar N:M)
            String sqlItemCarrito = "CREATE TABLE IF NOT EXISTS ITEM_CARRITO ("
                            + "CARRITO_ID INTEGER NOT NULL,"
                            + "PRODUCTO_ID INTEGER NOT NULL,"
                            + "TALLA TEXT NOT NULL,"
                            + "CANTIDAD INTEGER NOT NULL,"
                            + "PRIMARY KEY (CARRITO_ID, PRODUCTO_ID, TALLA),"
                            + "FOREIGN KEY(CARRITO_ID) REFERENCES CARRITO_COMPRA(ID) ON DELETE CASCADE,"
                            + "FOREIGN KEY(PRODUCTO_ID) REFERENCES PRODUCTOS(ID) ON DELETE RESTRICT"
                            + ");";
            stmt.execute(sqlItemCarrito);

            System.out.println("-> Todas las tablas de la Tienda creadas/verificadas.");

        } catch (SQLException e) {
            System.err.format("* Error SQL al crear las tablas: %s\n", e.getMessage());
            e.printStackTrace();
        }
    }

    // --- MÉTODOS DE INSERCIÓN ---

    public void insertUsuario(Usuario usuario) {
    	String sql = "INSERT OR IGNORE INTO USUARIOS "
                   + "(NOMBRE, APELLIDOS, DNI, EMAIL, NUM_TELEFONO, PASSWORD) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellidos());
            pstmt.setString(3, usuario.getDni());
            pstmt.setString(4, usuario.getEmail());
            pstmt.setString(5, usuario.getNumTelefono());
            pstmt.setString(6, usuario.getPassword());

            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.format("* Error al insertar Usuario '%s': %s\n", usuario.getEmail(), e.getMessage());
        }
    }


	public void insertProducto(Producto producto) {
        String sqlProducto = "INSERT INTO PRODUCTOS (NOMBRE, PRECIO, DESCRIPCION) VALUES (?, ?, ?)";
        String sqlStock = "INSERT INTO STOCK_TALLA (ID_PRODUCTO, TALLA, CANTIDAD) VALUES (?, ?, ?)";

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
            con.setAutoCommit(false); 
            
            try (PreparedStatement pstmtProducto = con.prepareStatement(sqlProducto, Statement.RETURN_GENERATED_KEYS)) {
                
                pstmtProducto.setString(1, producto.getNombre());
                pstmtProducto.setDouble(2, producto.getPrecio());
                pstmtProducto.setString(3, producto.getDescripcion());
                pstmtProducto.executeUpdate();

                int idProducto = -1; 
                try (ResultSet rs = pstmtProducto.getGeneratedKeys()) {
                    if (rs.next()) { 
                        idProducto = rs.getInt(1); 
                        producto.setId(idProducto) ;
                    }
                }
                
                con.commit(); 
            }

        } catch (SQLException e) {
            System.err.format("* Error al insertar Producto '%s': %s\n", producto.getNombre(), e.getMessage());
            try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) { 
                con.rollback(); 
            } catch (SQLException ex) {}
        }
    }
    
    public void insertEvento(Evento evento) {
        String sql = "INSERT OR IGNORE INTO EVENTOS (NOMBRE, FECHA, DESCRIPCION, LUGAR, CAPACIDAD) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, evento.getNombre());
            pstmt.setString(2, evento.getFecha().toString());
            pstmt.setString(3, evento.getDescripcion());
            pstmt.setString(4, evento.getLugar());
            pstmt.setInt(5, evento.getCapacidad());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.format("* Error al insertar Evento '%s': %s\n", evento.getNombre(), e.getMessage());
        }
    }
    
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
                    pago.setId(rs.getInt(1));
                }
            }
            
        } catch (SQLException e) {
            System.err.format("* Error al insertar Pago '%s': %s\n", pago.getNumTransaccion(), e.getMessage());
        }
    }
    
    public boolean existeUsuario(String email) {
        String sql = "SELECT COUNT(*) FROM USUARIOS WHERE EMAIL = ?";
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<Producto> loadProductos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT ID, NOMBRE, PRECIO, DESCRIPCION FROM PRODUCTOS";
        
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int id = rs.getInt("ID");
                Producto p = new Producto(id, rs.getString("NOMBRE"), rs.getString("DESCRIPCION"), rs.getDouble("PRECIO"), null, 0, null);                
                p.setStock(obtenerStockPorProducto(id, con));
                productos.add(p);
            }
            
        } catch (Exception e) {
            System.err.format("\n* Error recuperando productos: %s.", e.getMessage());
        }
        return productos;
    }
    
    private Integer obtenerStockPorProducto(int idProducto, Connection con) throws SQLException {
        int stock = 0;
        String sql = "SELECT TALLA, CANTIDAD FROM STOCK_TALLA WHERE ID_PRODUCTO = ?";
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idProducto);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    stock = (rs.getInt("CANTIDAD"));
                }
            }
        }
        return stock;
    }

    public List<Evento> loadEventos() {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT ID, NOMBRE, FECHA, DESCRIPCION, LUGAR, CAPACIDAD FROM EVENTOS ORDER BY FECHA ASC";
        
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String fechaStr = rs.getString("FECHA");
                
                Evento evento = new Evento(
                    LocalDate.parse(fechaStr), 
                    rs.getString("DESCRIPCION"),
                    rs.getString("LUGAR"),
                    rs.getInt("CAPACIDAD")
                );
                eventos.add(evento);
            }
            
        } catch (Exception e) {
            System.err.format("\n* Error recuperando eventos: %s.", e.getMessage());
        }
        return eventos;
    }

    public int obtenerStockEspecifico(int idProducto, String talla) {
        String sql = "SELECT CANTIDAD FROM STOCK_TALLA WHERE ID_PRODUCTO = ? AND TALLA = ?";
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setInt(1, idProducto);
            pstmt.setString(2, talla);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("CANTIDAD");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar stock específico: " + e.getMessage());
        }
        return 0; // Si no hay registro, asumimos stock 0
    }
    
    public void deleteDatabase() {
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = con.createStatement()) {
            
            stmt.execute("DROP TABLE IF EXISTS ITEM_CARRITO");
            stmt.execute("DROP TABLE IF EXISTS CARRITO_COMPRA");
            stmt.execute("DROP TABLE IF EXISTS PEDIDO_PRODUCTO");
            stmt.execute("DROP TABLE IF EXISTS PEDIDO");
            stmt.execute("DROP TABLE IF EXISTS PAGO");
            stmt.execute("DROP TABLE IF EXISTS EVENTOS");
            stmt.execute("DROP TABLE IF EXISTS STOCK_TALLA");
            stmt.execute("DROP TABLE IF EXISTS PRODUCTOS");
            stmt.execute("DROP TABLE IF EXISTS USUARIOS");
            System.out.println("\n- Tablas borradas.");
            
        } catch (Exception ex) {
            System.err.format("\n* Error al borrar las tablas: %s", ex.getMessage());
        }

        try {
            File dbFile = new File(SQLITE_FILE);
            if (dbFile.exists()) {
                dbFile.delete();
                System.out.println("- Fichero de BBDD borrado.");
            }
        } catch (Exception ex) {
            System.err.format("\n* Error al borrar el archivo de la BBDD: %s", ex.getMessage());
        }
    }
}