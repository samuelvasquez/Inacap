package samuelvasquez.inacap.cl.unidad3.datamodel;

public class Producto {
    // Propiedades
    public int id;
    public String nombre;
    public Boolean es_activo;

    // Constructor de las instancias de Producto
    public Producto(){	}

    // Sobreescribe metodo toString() de la clase Producto
    public String toString()
    {
        return String.valueOf(this.id)
                + ". "
                + this.nombre;
    }
}