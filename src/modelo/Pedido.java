package modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Pedido implements Serializable {
    private int id;
    private long fecha;
    private ArrayList<ItemPedido> items;
    private Modalidad modalidad;
    private EstadoPedido estado;

    public Pedido(int id) {
        this.id = id;
        fecha = Calendar.getInstance().getTimeInMillis();
        items = new ArrayList<ItemPedido>();
        modalidad = Modalidad.LOCAL;
        estado = EstadoPedido.PENDIENTE;
    }

    public void agregarItem(Producto producto, int cantidad) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getProducto().getCodigo() == producto.getCodigo()) {
                items.get(i).sumarCantidad(cantidad);
                return;
            }
        }
        items.add(new ItemPedido(producto, cantidad));
    }

    public void eliminarItem(int indice) {
        if (indice >= 0 && indice < items.size())
            items.remove(indice);
    }

    public double calcularTotal() {
        return calcularTotal(0);
    }

    private double calcularTotal(int i) {
        if (i == items.size())
            return 0;
        return items.get(i).getSubtotal() + calcularTotal(i+1);
    }

    public int getId() {
        return id;
    }

    public long getFecha() {
        return fecha;
    }

    public String getFechaTexto() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return formato.format(new Date(fecha));
    }

    public ArrayList<ItemPedido> getItems() {
        return items;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public String getDetalle() {
        String texto = "Pedido #" + id + "\nFecha: " + getFechaTexto() + "\nModalidad: " + modalidad + "\nEstado: " + estado + "\n\n";
        for (int i = 0; i < items.size(); i++) {
            ItemPedido item = items.get(i);
            texto+=item.getCantidad() + " x " + item.getProducto().getNombre() + "  (L. " + item.getProducto().getPrecio() + ")  = L. " + item.getSubtotal() + "\n";
        }
        texto+="\nTotal: L. " + calcularTotal();
        return texto;
    }
}