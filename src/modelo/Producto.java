package modelo;

import java.io.Serializable;

public abstract class Producto implements Serializable {
    private int codigo;
    private String nombre;
    private double precio;

    public Producto(int codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public abstract Categoria getCategoria();

    public abstract String getDetalle();

    @Override
    public String toString() {
        return codigo + " - " + nombre + " (" + getCategoria() + ", L. " + precio + ")";
    }
}