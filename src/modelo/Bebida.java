package modelo;

public class Bebida extends Producto {
    private int mililitros;
    private boolean conHielo;

    public Bebida(int codigo, String nombre, double precio, int mililitros, boolean conHielo) {
        super(codigo, nombre, precio);
        this.mililitros = mililitros;
        this.conHielo = conHielo;
    }

    public int getMililitros() {
        return mililitros;
    }

    public boolean isConHielo() {
        return conHielo;
    }

    @Override
    public Categoria getCategoria() {
        return Categoria.BEBIDA;
    }

    @Override
    public String getDetalle() {
        return mililitros + " ml" + (conHielo ? ", con hielo" : ", sin hielo");
    }
}