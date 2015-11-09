package samuelvasquez.inacap.cl.unidad3.dataaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static int version = 1;
    private static String name = "ProductosDb" ;
    private static CursorFactory factory = null;

    private static final String DB_CREATE_USUARIO = "CREATE TABLE USUARIO "
            + "(ID INTEGER PRIMARY KEY, "
            + "NOMBRE TEXT NOT NULL, "
            + "LOGIN TEXT NOT NULL, "
            + "CONTRASENA TEXT NOT NULL,"
            + "ES_ACTIVO INTEGER DEFAULT 1);";

    private static final String DB_INSERT_USUARIO_1 = "INSERT INTO USUARIO(ID, NOMBRE, LOGIN, CONTRASENA, ES_ACTIVO) VALUES(1, 'Vendedor 1', 'demo', 'demo', 1);";
    private static final String DB_INSERT_USUARIO_2 = "INSERT INTO USUARIO(ID, NOMBRE, LOGIN, CONTRASENA, ES_ACTIVO) VALUES(2, 'Vendedor 2', 'test', 'test', 1);";

    private static final String DB_CREATE_PRODUCTO = "CREATE TABLE PRODUCTO "
            + "(ID INTEGER PRIMARY KEY, "
            + "NOMBRE TEXT NOT NULL, "
            + "ES_ACTIVO INTEGER DEFAULT 1);";

    private static final String DB_INSERT_PRODUCTO_1 = "INSERT INTO PRODUCTO(ID, NOMBRE, ES_ACTIVO) VALUES(1, 'Producto 1', 1);";
    private static final String DB_INSERT_PRODUCTO_2 = "INSERT INTO PRODUCTO(ID, NOMBRE, ES_ACTIVO) VALUES(2, 'Producto 2', 1);";
    private static final String DB_INSERT_PRODUCTO_3 = "INSERT INTO PRODUCTO(ID, NOMBRE, ES_ACTIVO) VALUES(3, 'Producto 3', 1);";
    private static final String DB_INSERT_PRODUCTO_4 = "INSERT INTO PRODUCTO(ID, NOMBRE, ES_ACTIVO) VALUES(4, 'Producto 4', 1);";
    private static final String DB_INSERT_PRODUCTO_5 = "INSERT INTO PRODUCTO(ID, NOMBRE, ES_ACTIVO) VALUES(5, 'Producto 5', 1);";

    private static final String DB_CREATE_CLIENTE = "CREATE TABLE CLIENTE "
            + "(ID INTEGER PRIMARY KEY, "
            + "ID_VENDEDOR INTEGER NOT NULL, "
            + "NOMBRE TEXT NOT NULL, "
            + "DIRECCION TEXT NOT NULL, "
            + "TELEFONO TEXT NOT NULL, "
            + "LATITUD REAL NOT NULL, "
            + "LONGUITUD REAL NOT NULL, "
            + "ES_ACTIVO INTEGER DEFAULT 1);";

    private static final String DB_INSERT_CLIENTE_1 = "INSERT INTO CLIENTE(ID, ID_VENDEDOR, NOMBRE, DIRECCION, TELEFONO, LATITUD, LONGUITUD,  ES_ACTIVO) VALUES(1, 1, 'Lider Express 10 de Julio', '10 de Julio Huamachuco 1625, Santiago', '111111111', -33.454044, -70.6462585, 1);";
    private static final String DB_INSERT_CLIENTE_2 = "INSERT INTO CLIENTE(ID, ID_VENDEDOR, NOMBRE, DIRECCION, TELEFONO, LATITUD, LONGUITUD,  ES_ACTIVO) VALUES(2, 1, 'Lider Grecia', 'Grecia 245, Macul', '222222222', -33.454044, -70.6462585, 1);";
    private static final String DB_INSERT_CLIENTE_3 = "INSERT INTO CLIENTE(ID, ID_VENDEDOR, NOMBRE, DIRECCION, TELEFONO, LATITUD, LONGUITUD,  ES_ACTIVO) VALUES(3, 1, 'Unimarc', 'Gran Avda 5485, San Miguel', '333333333', -33.4986852, -70.6711805, 1);";
    private static final String DB_INSERT_CLIENTE_4 = "INSERT INTO CLIENTE(ID, ID_VENDEDOR, NOMBRE, DIRECCION, TELEFONO, LATITUD, LONGUITUD,  ES_ACTIVO) VALUES(4, 2, 'Ekono', 'San Diego 2067, Santiago', '444444444', -33.4905945, -70.6600226, 1);";
    private static final String DB_INSERT_CLIENTE_5 = "INSERT INTO CLIENTE(ID, ID_VENDEDOR, NOMBRE, DIRECCION, TELEFONO, LATITUD, LONGUITUD,  ES_ACTIVO) VALUES(5, 2, 'Tottus', 'Catedral 1850, Santiago', '555555555', -33.4905735, -70.7125533, 1);";
    private static final String DB_INSERT_CLIENTE_6 = "INSERT INTO CLIENTE(ID, ID_VENDEDOR, NOMBRE, DIRECCION, TELEFONO, LATITUD, LONGUITUD,  ES_ACTIVO) VALUES(6, 2, 'Jumbo', 'Llano Subercaseaux 3519, San Miguel', '666666666', -33.494811, -70.7277944, 1);";

    private static final String DB_CREATE_PEDIDO = "CREATE TABLE PEDIDO "
            + "(ID INTEGER PRIMARY KEY, "
            + "ID_VENDEDOR INTEGER NOT NULL, "
            + "ID_CLIENTE INTEGER NOT NULL, "
            + "FECHA_ENTREGA DATE NULL, "
            + "ENTREGADO INTEGER DEFAULT 1, "
            + "ES_ACTIVO INTEGER DEFAULT 1);";


    private static final String DB_CREATE_PEDIDO_DETALLE = "CREATE TABLE PEDIDO_DETALLE "
            + "(ID INTEGER PRIMARY KEY, "
            + "ID_PEDIDO INTEGER NOT NULL, "
            + "ID_PRODUCTO INTEGER NOT NULL, "
            + "CANTIDAD INTEGER NOT NULL, "
            + "PRECIO_UNITARIO INT NOT NULL, "
            + "ES_ACTIVO INTEGER DEFAULT 1);";

    private static DBHelper instance;

    public static synchronized DBHelper getHelper(Context context) {
        if (instance == null)
            instance = new DBHelper(context);
        return instance;
    }

    public DBHelper(Context context)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.i("dbhelper", "oncreated");

        if(db.isReadOnly())
        {
            db = getWritableDatabase();
        }
        db.execSQL(DB_CREATE_USUARIO);
        db.execSQL(DB_INSERT_USUARIO_1);
        db.execSQL(DB_INSERT_USUARIO_2);
        db.execSQL(DB_CREATE_PRODUCTO);
        db.execSQL(DB_INSERT_PRODUCTO_1);
        db.execSQL(DB_INSERT_PRODUCTO_2);
        db.execSQL(DB_INSERT_PRODUCTO_3);
        db.execSQL(DB_INSERT_PRODUCTO_4);
        db.execSQL(DB_INSERT_PRODUCTO_5);
        db.execSQL(DB_CREATE_CLIENTE);
        db.execSQL(DB_INSERT_CLIENTE_1);
        db.execSQL(DB_INSERT_CLIENTE_2);
        db.execSQL(DB_INSERT_CLIENTE_3);
        db.execSQL(DB_INSERT_CLIENTE_4);
        db.execSQL(DB_INSERT_CLIENTE_5);
        db.execSQL(DB_INSERT_CLIENTE_6);
        db.execSQL(DB_CREATE_PEDIDO);
        db.execSQL(DB_CREATE_PEDIDO_DETALLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i("dbhelper", "onupgrade");

        if(newVersion > oldVersion)
        {

        }
    }
}