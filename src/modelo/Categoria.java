package modelo;

public enum Categoria {
    PLATO("Plato"), BEBIDA("Bebida"), POSTRE("Postre");

    private final String texto;

    Categoria(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return texto;
    }
}
