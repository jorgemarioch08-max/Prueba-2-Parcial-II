package gui;

import logica.Restaurante;
import modelo.ItemPedido;
import modelo.Modalidad;
import modelo.Pedido;
import modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelPedido extends JPanel implements ActionListener {
    private Restaurante restaurante;
    private Pedido pedidoActual;
    private JLabel lblPedido, lblTotal;
    private JComboBox<Producto> cmbProductos;
    private JComboBox<Modalidad> cmbModalidad;
    private JTextField txtCantidad;
    private JButton btnAgregar, btnEliminar, btnRegistrar;
    private DefaultTableModel modelo;
    private JTable tabla;

    public PanelPedido(Restaurante restaurante) {
        this.restaurante = restaurante;
        pedidoActual = new Pedido(restaurante.getSiguientePedido());
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel arriba = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblPedido = new JLabel();
        cmbProductos = new JComboBox<Producto>();
        txtCantidad = new JTextField("1", 4);
        btnAgregar = new JButton("Agregar al pedido");
        arriba.add(lblPedido);
        arriba.add(new JLabel("   Producto:"));
        arriba.add(cmbProductos);
        arriba.add(new JLabel("Cantidad:"));
        arriba.add(txtCantidad);
        arriba.add(btnAgregar);
        add(arriba, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{"Producto", "Categoría", "Precio", "Cantidad", "Subtotal"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel abajo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnEliminar = new JButton("Eliminar seleccionado");
        cmbModalidad = new JComboBox<Modalidad>(Modalidad.values());
        btnRegistrar = new JButton("Registrar pedido");
        lblTotal = new JLabel();
        lblTotal.setFont(lblTotal.getFont().deriveFont(Font.BOLD, 14f));
        abajo.add(btnEliminar);
        abajo.add(new JLabel("   Modalidad:"));
        abajo.add(cmbModalidad);
        abajo.add(btnRegistrar);
        abajo.add(lblTotal);
        add(abajo, BorderLayout.SOUTH);

        btnAgregar.addActionListener(this);
        btnEliminar.addActionListener(this);
        btnRegistrar.addActionListener(this);
        refrescar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAgregar)
            agregar();
        else if (e.getSource() == btnEliminar)
            eliminar();
        else if (e.getSource() == btnRegistrar)
            registrar();
    }

    private void agregar() {
        Producto producto = (Producto) cmbProductos.getSelectedItem();
        if (producto == null) {
            error("No hay productos registrados.");
            return;
        }
        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
        } catch (NumberFormatException ex) {
            error("La cantidad debe ser un número entero.");
            return;
        }
        if (cantidad <= 0) {
            error("La cantidad debe ser mayor que 0.");
            return;
        }
        pedidoActual.agregarItem(producto, cantidad);
        txtCantidad.setText("1");
        mostrarDetalle();
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            error("Seleccione un producto de la tabla.");
            return;
        }
        pedidoActual.eliminarItem(fila);
        mostrarDetalle();
    }

    private void registrar() {
        pedidoActual.setModalidad((Modalidad) cmbModalidad.getSelectedItem());
        if (!restaurante.registrarPedido(pedidoActual)) {
            error("No se puede registrar un pedido sin productos.");
            return;
        }
        JOptionPane.showMessageDialog(this, "Pedido #" + pedidoActual.getId() + " registrado. Total: L. " + pedidoActual.calcularTotal());
        pedidoActual = new Pedido(restaurante.getSiguientePedido());
        mostrarDetalle();
    }

    private void error(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarDetalle() {
        modelo.setRowCount(0);
        for (int i = 0; i < pedidoActual.getItems().size(); i++) {
            ItemPedido item = pedidoActual.getItems().get(i);
            Producto p = item.getProducto();
            modelo.addRow(new Object[]{p.getNombre(), p.getCategoria(), p.getPrecio(), item.getCantidad(), item.getSubtotal()});
        }
        lblPedido.setText("Pedido #" + pedidoActual.getId());
        lblTotal.setText("   Total: L. " + pedidoActual.calcularTotal());
    }

    public void refrescar() {
        cmbProductos.removeAllItems();
        for (int i = 0; i < restaurante.getProductos().size(); i++)
            cmbProductos.addItem(restaurante.getProductos().get(i));
        if (pedidoActual.getItems().isEmpty())
            pedidoActual = new Pedido(restaurante.getSiguientePedido());
        mostrarDetalle();
    }
}