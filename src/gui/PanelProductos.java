package gui;

import logica.Restaurante;
import modelo.Bebida;
import modelo.Categoria;
import modelo.Plato;
import modelo.Postre;
import modelo.Producto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelProductos extends JPanel implements ActionListener {

    private Restaurante restaurante;
    private JTextField txtCodigo, txtNombre, txtPrecio, txtTiempo, txtMililitros;
    private JCheckBox chkHielo, chkSinAzucar;
    private JComboBox<Categoria> cmbCategoria;
    private JButton btnAgregar;
    private DefaultTableModel modelo;

    public PanelProductos(Restaurante restaurante) {
        this.restaurante = restaurante;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridLayout(9, 2, 5, 5));
        cmbCategoria = new JComboBox<Categoria>(Categoria.values());
        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtPrecio = new JTextField();
        txtTiempo = new JTextField();
        txtMililitros = new JTextField();
        chkHielo = new JCheckBox("Con hielo");
        chkSinAzucar = new JCheckBox("Sin azúcar");
        btnAgregar = new JButton("Registrar producto");

        form.add(new JLabel("Categoría:"));
        form.add(cmbCategoria);
        form.add(new JLabel("Código:"));
        form.add(txtCodigo);
        form.add(new JLabel("Nombre:"));
        form.add(txtNombre);
        form.add(new JLabel("Precio (L.):"));
        form.add(txtPrecio);
        form.add(new JLabel("Tiempo preparación (min) [Plato]:"));
        form.add(txtTiempo);
        form.add(new JLabel("Mililitros [Bebida]:"));
        form.add(txtMililitros);
        form.add(new JLabel("[Bebida]"));
        form.add(chkHielo);
        form.add(new JLabel("[Postre]"));
        form.add(chkSinAzucar);
        form.add(new JLabel());
        form.add(btnAgregar);

        JPanel izquierda = new JPanel(new BorderLayout());
        izquierda.add(form, BorderLayout.NORTH);
        add(izquierda, BorderLayout.WEST);

        modelo = new DefaultTableModel(new String[]{"Categoría", "Código", "Nombre", "Precio", "Detalle"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        JTable tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        cmbCategoria.addActionListener(this);
        btnAgregar.addActionListener(this);
        actualizarCampos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cmbCategoria)
            actualizarCampos();
        else if (e.getSource() == btnAgregar)
            registrar();
    }

    private void actualizarCampos() {
        Categoria c = (Categoria) cmbCategoria.getSelectedItem();
        txtTiempo.setEnabled(c == Categoria.PLATO);
        txtMililitros.setEnabled(c == Categoria.BEBIDA);
        chkHielo.setEnabled(c == Categoria.BEBIDA);
        chkSinAzucar.setEnabled(c == Categoria.POSTRE);
    }

    private void registrar() {
        int codigo;
        double precio;
        String nombre = txtNombre.getText().trim();
        try {
            codigo = Integer.parseInt(txtCodigo.getText().trim());
            precio = Double.parseDouble(txtPrecio.getText().trim());
        } catch (NumberFormatException ex) {
            error("El código debe ser entero y el precio un número.");
            return;
        }
        if (codigo <= 0) {
            error("El código debe ser mayor que 0.");
            return;
        }
        if (nombre.isEmpty()) {
            error("Ingrese el nombre.");
            return;
        }
        if (precio <= 0) {
            error("El precio debe ser mayor que 0.");
            return;
        }

        Producto producto;
        Categoria c = (Categoria) cmbCategoria.getSelectedItem();
        try {
            if (c == Categoria.PLATO) {
                int tiempo = Integer.parseInt(txtTiempo.getText().trim());
                if (tiempo <= 0) {
                    error("El tiempo de preparación debe ser mayor que 0.");
                    return;
                }
                producto = new Plato(codigo, nombre, precio, tiempo);
            } else if (c == Categoria.BEBIDA) {
                int ml = Integer.parseInt(txtMililitros.getText().trim());
                if (ml <= 0) {
                    error("Los mililitros deben ser mayores que 0.");
                    return;
                }
                producto = new Bebida(codigo, nombre, precio, ml, chkHielo.isSelected());
            } else {
                producto = new Postre(codigo, nombre, precio, chkSinAzucar.isSelected());
            }
        } catch (NumberFormatException ex) {
            error("El campo de la categoría debe ser un número entero.");
            return;
        }

        if (!restaurante.agregarProducto(producto)) {
            error("Ya existe un producto con el código " + codigo + ".");
            return;
        }
        JOptionPane.showMessageDialog(this, "Producto registrado.");
        limpiar();
        refrescar();
    }

    private void limpiar() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtTiempo.setText("");
        txtMililitros.setText("");
        chkHielo.setSelected(false);
        chkSinAzucar.setSelected(false);
    }

    private void error(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void refrescar() {
        modelo.setRowCount(0);
        Categoria[] categorias = Categoria.values();
        for (int c = 0; c < categorias.length; c++) {
            for (int i = 0; i < restaurante.getProductos().size(); i++) {
                Producto p = restaurante.getProductos().get(i);
                if (p.getCategoria() == categorias[c])
                    modelo.addRow(new Object[]{p.getCategoria(), p.getCodigo(), p.getNombre(), p.getPrecio(), p.getDetalle()});
            }
        }
    }
}
