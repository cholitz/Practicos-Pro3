import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class ventana extends JFrame {

    class Fondo extends JPanel {
        private Image imagen;

        public Fondo(String ruta) {
            this.imagen = new ImageIcon(ruta).getImage();
        }

        @Override
        protected void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }

    JPanel panelP = new JPanel();
    JPanel panelS = new JPanel();

    public ventana() {

        setTitle("Ventana");
        setSize(500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        // Panel principal con fondo
        JPanel panelP = new Fondo("MetaV.jpg");
        panelP.setBounds(0, 0, 500, 800);
        panelP.setLayout(null);
        add(panelP);

        // Panel secundario con fondo
        JPanel panelS = new Fondo("Fondo2.gif");
        panelS.setBounds(0, 0, 500, 750);
        panelS.setLayout(null);
        panelS.setVisible(false);
        add(panelS);

        JLabel sticker999 = new JLabel(new ImageIcon("juicesimb-unscreen.gif"));
        sticker999.setBounds(500 - 120 - 10, 750 - 120 - 10, 120, 120);
        panelS.add(sticker999);

        // Panel donde se mostrarán los resultados
        JPanel panelT = new JPanel();
        panelT.setBackground(Color.WHITE);
        panelT.setBounds(75, 120, 390, 290);
        panelT.setLayout(null);
        panelS.add(panelT);
        panelT.setVisible(true);

        // Botón principal que inicia la ventana secundaria
        JButton boton = new JButton();
        boton.setBounds(230, 180, 80, 30);
        panelP.add(boton);
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);

        // Botón personalizado que maneja operaciones
        botonPersonalizado_3 miBoton3 = new botonPersonalizado_3(panelT);

        // --- Registrar observer para seguimiento en terminal ---
        miBoton3.getLista().addObserver(msg -> System.out.println("Seguimiento: " + msg));

        // Botón "Nuevo"
        JButton boton2 = new JButton("Nuevo");
        boton2.setBounds(120, 500, 100, 50);
        panelS.add(boton2);
        boton2.addActionListener(new botonPersonalizado_2(panelS));
        boton2.addActionListener(e -> {
            miBoton3.limpiar();
            miBoton3.getLista().setUltimoMensaje("Click en botón NUEVO: panelT limpiado");
        });

        // Botón "Ordenar" con menú
        JButton boton3 = new JButton("Ordenar");
        boton3.setBounds(280, 500, 100, 50);
        panelS.add(boton3);

        JPopupMenu menuBar = new JPopupMenu();
        JMenuItem opcion1 = new JMenuItem("MULTIPLOS");
        JMenuItem opcion2 = new JMenuItem("MENOR A MAYOR");
        JMenuItem opcion3 = new JMenuItem("MAYOR A MENOR");
        JMenuItem opcion4 = new JMenuItem("PRIMOS");
        JMenuItem opcion5 = new JMenuItem("PARES");
        JMenuItem opcion6 = new JMenuItem("IMPARES");

        // Usamos la misma instancia de miBoton3 para el menú
        opcion1.addActionListener(miBoton3);
        opcion2.addActionListener(miBoton3);
        opcion3.addActionListener(miBoton3);
        opcion4.addActionListener(miBoton3);
        opcion5.addActionListener(miBoton3);
        opcion6.addActionListener(miBoton3);

        menuBar.add(opcion1);
        menuBar.add(opcion2);
        menuBar.add(opcion3);
        menuBar.add(opcion4);
        menuBar.add(opcion5);
        menuBar.add(opcion6);

        boton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuBar.show(boton3, 0, boton3.getHeight());
                miBoton3.getLista().setUltimoMensaje("Click en botón ORDENAR: menú desplegado");
            }
        });

        // Acción del botón principal para mostrar panelS y panelT
        boton.addActionListener(e -> {
            panelP.setVisible(false);
            panelS.setVisible(true);
            panelT.setVisible(true);
            miBoton3.getLista().setUltimoMensaje("Click en botón principal: Se muestra panelS y panelT");
        });

        panelT.setVisible(true);
        boton.setVisible(true);
        setVisible(true);
    }
}
