package samuelvasquez.inacap.cl.unidad3.datamodel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Pedido {
    // Propiedades
    public int id;
    public int id_vendedor;
    public int id_cliente;
    public Date fecha_entrega;
    public Boolean entregado;
    public Boolean es_activo;
    public ArrayList<DetallePedido> detalles;
    public String nombre_cliente;

    // Constructor de las instancias de Pedido
    public Pedido(){
        detalles = new ArrayList<DetallePedido>();
    }

    // Sobreescribe metodo toString() de la clase Pedido
    public String toString()
    {
        SimpleDateFormat dateFormat = ((SimpleDateFormat) DateFormat.getDateInstance());
        return dateFormat.format(this.fecha_entrega) + "\n" + this.nombre_cliente;
    }
}

