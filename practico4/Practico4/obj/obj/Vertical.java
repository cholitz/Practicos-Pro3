package obj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Vertical extends JFrame implements PropertyChangeListener {
    private ListaP4<ElementoGrafico> listaElementos;
    private ImagenFinal imagenFinal;
    private String currentTool;
    private int startX, startY;
    private JPanel canvas;
    private JButton circuloButton, cuadradoButton, lineaButton, imagenButton, seleccionarButton, eliminarButton;

    public Vertical() {
        super("Editor Gráfico");
        listaElementos = new ListaP4<>();
        imagenFinal = new ImagenFinal(800, 600);
        imagenFinal.setListaElementos(listaElementos);
        currentTool = "Circulo";
        initComponents();
        listaElementos.addObservador(this);
        imagenFinal.setObservador(canvas);
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());

        JPanel toolbar = new JPanel();
        toolbar.setLayout(new FlowLayout());

        circuloButton = new JButton("Círculo");
        cuadradoButton = new JButton("Cuadrado");
        lineaButton = new JButton("Línea");
        imagenButton = new JButton("Imagen");
        seleccionarButton = new JButton("Seleccionar");
        eliminarButton = new JButton("Eliminar");

        circuloButton.addActionListener(e -> currentTool = "Circulo");
        cuadradoButton.addActionListener(e -> currentTool = "Cuadrado");
        lineaButton.addActionListener(e -> currentTool = "Linea");
        imagenButton.addActionListener(e -> currentTool = "Imagen");
        seleccionarButton.addActionListener(e -> currentTool = "Seleccionar");
        eliminarButton.addActionListener(e -> {
            for (int i = listaElementos.tamano() - 1; i >= 0; i--) {
                ElementoGrafico el = listaElementos.get(i);
                if (el.isSeleccionado()) {
                    listaElementos.eliminar(i);
                }
            }
            actualizarImagenFinal();
        });

        toolbar.add(circuloButton);
        toolbar.add(cuadradoButton);
        toolbar.add(lineaButton);
        toolbar.add(imagenButton);
        toolbar.add(seleccionarButton);
        toolbar.add(eliminarButton);

        canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imagenFinal.getImagenP4().getBufferedImage(), 0, 0, this);
            }
        };
        canvas.setPreferredSize(new Dimension(800, 600));
        canvas.setBackground(Color.WHITE);

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int endX = e.getX();
                int endY = e.getY();
                switch (currentTool) {
                    case "Circulo":
                        int radius = (int) Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
                        listaElementos.insertar(new Circulo(startX, startY, radius));
                        break;
                    case "Cuadrado":
                        int side = Math.max(Math.abs(endX - startX), Math.abs(endY - startY));
                        listaElementos.insertar(new Cuadrado(startX, startY, side));
                        break;
                    case "Linea":
                        listaElementos.insertar(new Linea(startX, startY, endX, endY));
                        break;
                    case "Imagen":
                        JFileChooser fileChooser = new JFileChooser();
                        if (fileChooser.showOpenDialog(Vertical.this) == JFileChooser.APPROVE_OPTION) {
                            String filename = fileChooser.getSelectedFile().getAbsolutePath();
                            FabricaImagen fabrica = new FabricaImagen();
                            ImagenP4 imagenP4 = fabrica.fabricarDesdeArchivo(filename);
                            if (imagenP4 != null) {
                                listaElementos.insertar(new Imagen(startX, startY, imagenP4));
                            }
                        }
                        break;
                    case "Seleccionar":
                        for (int i = 0; i < listaElementos.tamano(); i++) {
                            ElementoGrafico el = listaElementos.get(i);
                            if (el.posicionDentroDeObjeto(endX, endY)) {
                                el.setSeleccionado(!el.isSeleccionado());
                            }
                        }
                        break;
                }
                actualizarImagenFinal();
            }
        });

        add(toolbar, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
    }

    private void actualizarImagenFinal() {
        imagenFinal.actualizar();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        actualizarImagenFinal();
    }
}