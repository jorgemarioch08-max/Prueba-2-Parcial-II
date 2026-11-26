package modelo;

public class Plato extends Producto {
    private int tiempoPreparacion;

    public Plato(int codigo, String nombre, double precio, int tiempoPreparacion) {
        super(codigo, nombre, precio);
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    @Override
    public Categoria getCategoria() {
        return Categoria.PLATO;
    }

    @Override
    public String getDetalle() {
        return "Preparación: " + tiempoPreparacion + " min";
    }
}