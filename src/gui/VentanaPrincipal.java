package gui;

import logica.Restaurante;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class VentanaPrincipal extends JFrame implements ChangeListener {
    private Restaurante restaurante;
    private JTabbedPane pestanas;
    private PanelProductos panelProductos;
    private PanelPedido panelPedido;
    private PanelConsulta panelConsulta;

    public VentanaPrincipal() {
        restaurante = new Restaurante();
        cargarAlIniciar();

        setTitle("Sistema de Gestión de Pedidos - Restaurante");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Sistema de Gestión de Pedidos - Restaurante", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        panelProductos = new PanelProductos(restaurante);
        panelPedido = new PanelPedido(restaurante);
        panelConsulta = new PanelConsulta(restaurante);
        pestanas = new JTabbedPane();
        pestanas.addTab("Productos", panelProductos);
        pestanas.addTab("Nuevo pedido", panelPedido);
        pestanas.addTab("Consulta de pedidos", panelConsulta);
        pestanas.addTab("Almacenamiento", new PanelAlmacenamiento(restaurante));
        pestanas.addChangeListener(this);
        add(pestanas, BorderLayout.CENTER);

        panelProductos.refrescar();
    }

    private void cargarAlIniciar() {
        if (!restaurante.existeArchivo())
            return;
        try {
            restaurante.cargar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudieron cargar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        Component actual = pestanas.getSelectedComponent();
        if (actual == panelProductos)
            panelProductos.refrescar();
        else if (actual == panelPedido)
            panelPedido.refrescar();
        else if (actual == panelConsulta)
            panelConsulta.refrescar();
    }
}