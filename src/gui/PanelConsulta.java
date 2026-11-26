package gui;

import logica.Restaurante;
import modelo.EstadoPedido;
import modelo.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelConsulta extends JPanel implements ActionListener {
    private Restaurante restaurante;
    private DefaultTableModel modelo;
    private JTable tabla;
    private JComboBox<EstadoPedido> cmbEstado;
    private JButton btnActualizar, btnDetalle;

    public PanelConsulta(Restaurante restaurante) {
        this.restaurante = restaurante;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        modelo = new DefaultTableModel(new String[]{"ID", "Fecha", "Modalidad", "Estado", "Productos", "Total"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel abajo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnDetalle = new JButton("Ver detalle");
        cmbEstado = new JComboBox<EstadoPedido>(EstadoPedido.values());
        btnActualizar = new JButton("Actualizar estado");
        abajo.add(btnDetalle);
        abajo.add(new JLabel("   Nuevo estado:"));
        abajo.add(cmbEstado);
        abajo.add(btnActualizar);
        add(abajo, BorderLayout.SOUTH);

        btnDetalle.addActionListener(this);
        btnActualizar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Pedido pedido = restaurante.getPedidos().get(fila);
        if (e.getSource() == btnDetalle) {
            JOptionPane.showMessageDialog(this, pedido.getDetalle(), "Detalle del pedido", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == btnActualizar) {
            pedido.setEstado((EstadoPedido) cmbEstado.getSelectedItem());
            refrescar();
            tabla.setRowSelectionInterval(fila, fila);
        }
    }

    public void refrescar() {
        modelo.setRowCount(0);
        for (int i = 0; i < restaurante.getPedidos().size(); i++) {
            Pedido p = restaurante.getPedidos().get(i);
            modelo.addRow(new Object[]{p.getId(), p.getFechaTexto(), p.getModalidad(), p.getEstado(), p.getItems().size(), p.calcularTotal()});
        }
    }
}