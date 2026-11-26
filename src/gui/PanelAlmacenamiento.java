package gui;

import logica.Restaurante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PanelAlmacenamiento extends JPanel implements ActionListener {
    private Restaurante restaurante;
    private JButton btnGuardar, btnCargar;
    private JLabel lblEstado;

    public PanelAlmacenamiento(Restaurante restaurante) {
        this.restaurante = restaurante;
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 40));

        btnGuardar = new JButton("Guardar datos");
        btnCargar = new JButton("Cargar datos");
        lblEstado = new JLabel("Archivo: restaurante/datos.dat");
        add(btnGuardar);
        add(btnCargar);
        add(lblEstado);

        btnGuardar.addActionListener(this);
        btnCargar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGuardar) {
            try {
                restaurante.guardar();
                mensaje("Datos guardados: " + restaurante.getProductos().size() + " productos, " + restaurante.getPedidos().size() + " pedidos.");
            } catch (IOException ex) {
                error("Error al guardar: " + ex.getMessage());
            }
        } else if (e.getSource() == btnCargar) {
            if (!restaurante.existeArchivo()) {
                error("No existe un archivo guardado.");
                return;
            }
            try {
                restaurante.cargar();
                mensaje("Datos cargados: " + restaurante.getProductos().size() + " productos, " + restaurante.getPedidos().size() + " pedidos.");
            } catch (IOException ex) {
                error("Error al leer el archivo: " + ex.getMessage());
            } catch (ClassNotFoundException ex) {
                error("Formato de archivo no válido.");
            }
        }
    }

    private void mensaje(String texto) {
        lblEstado.setText(texto);
        JOptionPane.showMessageDialog(this, texto);
    }

    private void error(String texto) {
        lblEstado.setText(texto);
        JOptionPane.showMessageDialog(this, texto, "Error", JOptionPane.ERROR_MESSAGE);
    }
}