package logica;

import modelo.Pedido;
import modelo.Producto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Restaurante {
    private ArrayList<Producto> productos;
    private ArrayList<Pedido> pedidos;
    private int siguientePedido;

    private final String CARPETA = "restaurante";
    private final String ARCHIVO = "restaurante/datos.dat";

    public Restaurante() {
        productos = new ArrayList<Producto>();
        pedidos = new ArrayList<Pedido>();
        siguientePedido = 1;
    }

    public boolean agregarProducto(Producto producto) {
        if (buscarProducto(producto.getCodigo()) != null)
            return false;
        productos.add(producto);
        return true;
    }

    public Producto buscarProducto(int codigo) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == codigo)
                return productos.get(i);
        }
        return null;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public int getSiguientePedido() {
        return siguientePedido;
    }

    public boolean registrarPedido(Pedido pedido) {
        if (pedido.getItems().isEmpty())
            return false;
        pedidos.add(pedido);
        siguientePedido++;
        return true;
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public boolean existeArchivo() {
        return new File(ARCHIVO).exists();
    }

    public void guardar() throws IOException {
        File carpeta = new File(CARPETA);
        carpeta.mkdir();
        ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ARCHIVO));
        try {
            salida.writeObject(productos);
            salida.writeObject(pedidos);
            salida.writeInt(siguientePedido);
        } finally {
            salida.close();
        }
    }

    @SuppressWarnings("unchecked")
    public void cargar() throws IOException, ClassNotFoundException {
        ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ARCHIVO));
        try {
            productos = (ArrayList<Producto>) entrada.readObject();
            pedidos = (ArrayList<Pedido>) entrada.readObject();
            siguientePedido = entrada.readInt();
        } finally {
            entrada.close();
        }
    }
}