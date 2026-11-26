package modelo;

public class Postre extends Producto {
    private boolean sinAzucar;

    public Postre(int codigo, String nombre, double precio, boolean sinAzucar) {
        super(codigo, nombre, precio);
        this.sinAzucar = sinAzucar;
    }

    public boolean isSinAzucar() {
        return sinAzucar;
    }

    @Override
    public Categoria getCategoria() {
        return Categoria.POSTRE;
    }

    @Override
    public String getDetalle() {
        return sinAzucar ? "Sin azúcar" : "Con azúcar";
    }
}