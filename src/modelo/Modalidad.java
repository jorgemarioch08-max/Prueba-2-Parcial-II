package modelo;

public enum Modalidad {
    LOCAL("Consumo en el restaurante"), DOMICILIO("Pedido a domicilio");

    private final String texto;

    Modalidad(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return texto;
    }

}
