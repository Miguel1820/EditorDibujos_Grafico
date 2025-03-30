import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class FrmTrazos extends JPanel {
    private ListaTrazo lista;
    private Nodo trazoSeleccionado;
    private int xInicio, yInicio;
    private String tipoSeleccionado = "Linea"; // Línea por defecto

    // Método para redimensionar imágenes
    private ImageIcon escalarImagen(String ruta, int ancho, int alto) {
        ImageIcon icono = new ImageIcon(getClass().getResource(ruta));
        Image imagen = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagen);
    }

    public FrmTrazos() {
        lista = new ListaTrazo();
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(800, 600));

        // Selector de figura
        String[] opciones = {"Linea", "Circulo", "Rectangulo"};
        JComboBox<String> selectorFiguras = new JComboBox<>(opciones);
        selectorFiguras.addActionListener(e -> tipoSeleccionado = (String) selectorFiguras.getSelectedItem());

        // Tamaño de los íconos
        int anchoIcono = 50;
        int altoIcono = 50;

        // Botones con imágenes redimensionadas
        JButton btnGuardar = new JButton(escalarImagen("/Imagenes/Guardar.png", anchoIcono, altoIcono));
        JButton btnCargar = new JButton(escalarImagen("/Imagenes/Subir.png", anchoIcono, altoIcono));
        JButton btnSeleccionar = new JButton(escalarImagen("/Imagenes/Seleccionar.png", anchoIcono, altoIcono));
        JButton btnEliminar = new JButton(escalarImagen("/Imagenes/Eliminar.png", anchoIcono, altoIcono));

        Color fondoBoton = new Color(255, 255, 255);
        btnGuardar.setBackground(fondoBoton);
        btnCargar.setBackground(fondoBoton);
        btnSeleccionar.setBackground(fondoBoton);
        btnEliminar.setBackground(fondoBoton);

        btnGuardar.setOpaque(true);
        btnCargar.setOpaque(true);
        btnSeleccionar.setOpaque(true);
        btnEliminar.setOpaque(true);

        btnGuardar.setContentAreaFilled(true);
        btnCargar.setContentAreaFilled(true);
        btnSeleccionar.setContentAreaFilled(true);
        btnEliminar.setContentAreaFilled(true);

        btnGuardar.addActionListener(e -> guardarDibujo());
        btnCargar.addActionListener(e -> cargarDibujo());
        btnSeleccionar.addActionListener(e -> JOptionPane.showMessageDialog(null, "Haz clic en un trazo para seleccionarlo."));
        btnEliminar.addActionListener(e -> eliminarTrazo());

        // Panel de botones en la parte superior
        JPanel panelBotones = new JPanel();
        panelBotones.add(new JLabel("Figura:"));
        panelBotones.add(selectorFiguras);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCargar);
        panelBotones.add(btnSeleccionar);
        panelBotones.add(btnEliminar);

        // Crear la ventana
        JFrame ventana = new JFrame("Editor de Dibujos");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new BorderLayout());
        ventana.add(panelBotones, BorderLayout.NORTH);
        ventana.add(this, BorderLayout.CENTER);
        ventana.pack();
        ventana.setVisible(true);

        // Eventos del mouse
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xInicio = e.getX();
                yInicio = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int xFin = e.getX();
                int yFin = e.getY();
                Trazo nuevoTrazo = new Trazo(xInicio, yInicio, xFin, yFin, tipoSeleccionado);
                lista.agregarTrazo(nuevoTrazo); //Agregar el trazo
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                trazoSeleccionado = lista.seleccionarTrazo(e.getX(), e.getY());
                if (trazoSeleccionado != null) {
                    JOptionPane.showMessageDialog(null, "Trazo seleccionado.");
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.YELLOW);
        lista.mostrarTrazo(g);
    }

    private void eliminarTrazo() {
        if (trazoSeleccionado != null) {
            lista.eliminarTrazo(trazoSeleccionado);
            trazoSeleccionado = null;
            repaint();
        } else {
            JOptionPane.showMessageDialog(null, "No hay trazo seleccionado para eliminar.");
        }
    }

    private void guardarDibujo() {
        String nombreArchivo = JOptionPane.showInputDialog("Ingrese el nombre del archivo:");
        if (nombreArchivo != null && !nombreArchivo.trim().isEmpty()) {
            if (lista.guardarDibujo(nombreArchivo)) {
                JOptionPane.showMessageDialog(null, "Dibujo guardado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar el dibujo.");
            }
        }
    }

    private void cargarDibujo() {
        String nombreArchivo = JOptionPane.showInputDialog("Ingrese el nombre del archivo:");
        if (nombreArchivo != null && !nombreArchivo.trim().isEmpty()) {
            if (lista.cargarDibujo(nombreArchivo)) {
                JOptionPane.showMessageDialog(null, "Dibujo cargado exitosamente.");
                repaint();
            } else {
                JOptionPane.showMessageDialog(null, "Error al cargar el dibujo.");
            }
        }
    }
}
