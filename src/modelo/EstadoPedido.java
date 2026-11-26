package modelo;

public enum EstadoPedido {
    PENDIENTE("Pendiente"), EN_PREPARAXION("En preparación"), LISTO("Listo"), ENTREGADO("Entregado");

    private final String texto;

    EstadoPedido(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return texto;
    }
}
