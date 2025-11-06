import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Date;

public class Ventana extends JFrame {

    private List<Imagen> modelo; //almacena objetos tipo Imagen
    private PanelPrincipal panel;//instancia clase externa

    public Ventana() {
        setTitle("OPERACIONES IMAGENES");
        setSize(500, 670);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        modelo = new ArrayList<>();
        panel = new PanelPrincipal(null);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(panel, BorderLayout.CENTER);

        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("TRANSFORMACIONES");

        JMenuItem itemAbrir = new JMenuItem("Imagenes");
        itemAbrir.addActionListener(e -> abrirImagen()); //metodo especifico
        menu.add(itemAbrir);

        JMenuItem item = new JMenuItem("Tonos grises");
        item.addActionListener(e -> menuTransformaciones_tonosGrises());//metodo especifico
        menu.add(item);

        JMenuItem itemAchicar = new JMenuItem("Achicar");
        itemAchicar.addActionListener(e -> menuTransformaciones_achicar());//metodo especifico
        menu.add(itemAchicar);

        JMenuItem itemAgrandar = new JMenuItem("Agrandar");
        itemAgrandar.addActionListener(e -> menuTransformaciones_agrandar());//metodo especifico
        menu.add(itemAgrandar);

        JMenuItem itemBlancoNegro = new JMenuItem("Blanco y negro");
        itemBlancoNegro.addActionListener(e -> menuTransformaciones_blancoynegro());//metodo especifico
        menu.add(itemBlancoNegro);

        JMenuItem itemEspejoH = new JMenuItem("Espejo Horizontal");
        itemEspejoH.addActionListener(e -> menuTransformaciones_espejoH());//metodo especifico
        menu.add(itemEspejoH);

        JMenuItem itemEspejoV = new JMenuItem("Espejo Vertical");
        itemEspejoV.addActionListener(e-> menuTransformaciones_espejoV());//metodo especifico
        menu.add(itemEspejoV);

        bar.add(menu);
        setJMenuBar(bar);
        setVisible(true);
    }

    // Nuevo método de logging
    private void logMessage(String message) {
        Date date = new Date();
        System.out.println("[" + date + "] " + message);
    }

    private void abrirImagen() {
        logMessage("Cargando imagen..."); //obtiene la ruta del archivo
        JFileChooser chooser = new JFileChooser(); //cuadro de seleccion a los archivos
         //si confirma abrir
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
            try {
                String filename = chooser.getSelectedFile().getPath(); //obtiene la ruta del archivo
                FabricaImagen fabrica = new FabricaImagen(); 
                Imagen nuevaImagen = fabrica.fabricarDesdeArchivo(filename); //convierte el archivo en un objeto
                modelo.clear();
                modelo.add(nuevaImagen);
                panel.setImagen(nuevaImagen);
                logMessage("Imagen cargada: " + filename);
            } catch (Exception ex) {
                logMessage("Error al cargar la imagen: " + ex.getMessage());
            }
        }
    }

    private void menuTransformaciones_tonosGrises() {
        Imagen imagenActual = modelo.get(0); 
        IOperacionImagen operacion = new TonosGrises(imagenActual); //instancia de la clase
        operacion.ejecutar();
        panel.setImagen(imagenActual);
    }

    private void menuTransformaciones_blancoynegro() {
        Imagen imagenActual = modelo.get(0);
        IOperacionImagen operacion = new BlancoyNegro(imagenActual);
        operacion.ejecutar();
        panel.setImagen(imagenActual);
    }

    private void menuTransformaciones_achicar() {
        
        try {
            int porcentaje = panel.getPorcentaje();
            if (porcentaje > 0 && porcentaje <= 100) {
                Imagen imagenActual = modelo.get(0);
                IOperacionImagen operacion = new Achicar(imagenActual, porcentaje);
                operacion.ejecutar();
                panel.setImagen(imagenActual);
            } 
        } catch (NumberFormatException ex) {}
    }

    private void menuTransformaciones_agrandar() {
        try {
            int porcentaje = panel.getPorcentaje();
            if (porcentaje > 100) {
                Imagen imagenActual = modelo.get(0);
                IOperacionImagen operacion = new Agrandar(imagenActual, porcentaje);
                operacion.ejecutar();
                panel.setImagen(imagenActual);
            } 
        } catch (NumberFormatException ex) {}
    }

    private void menuTransformaciones_espejoH() {
        Imagen imagenActual = modelo.get(0);
        IOperacionImagen operacion = new EspejoHorizontal(imagenActual);
        operacion.ejecutar();
        panel.setImagen(imagenActual);
    }

    private void menuTransformaciones_espejoV() {
        Imagen imagenActual = modelo.get(0);
        IOperacionImagen operacion = new EspejoVertical(imagenActual);
        operacion.ejecutar();
        panel.setImagen(imagenActual);
    }

    class PanelPrincipal extends JPanel implements Observer {
        private Imagen modelo; // alamacenan las imagenes actuales
        private JTextField porcentajeField; 

        public PanelPrincipal(Imagen modelo) {
            this.modelo = modelo;
            if (this.modelo != null) {
                this.modelo.addObserver(this);
            }
            setLayout(new BorderLayout());
           
            porcentajeField = new JTextField(5);
            porcentajeField.addActionListener(e -> {
                try {
                    int porcentaje = Integer.parseInt(porcentajeField.getText()); //lo convierte en enetero
                    if (modelo != null) {
                        if (porcentaje > 0 && porcentaje <= 100) {
                            IOperacionImagen operacion = new Achicar(modelo, porcentaje);
                            operacion.ejecutar();
                        } else if (porcentaje > 100) {
                            IOperacionImagen operacion = new Agrandar(modelo, porcentaje);
                            operacion.ejecutar();
                        } 
                    } 
                } catch (NumberFormatException ex) {}
            });
            add(porcentajeField, BorderLayout.SOUTH);
        }

        public void setImagen(Imagen nuevaImagen) {  //metodo para actualizar la imagen del panel
            if (this.modelo != null) {
                this.modelo.deleteObserver(this); //elimina viejo observer
            }
            this.modelo = nuevaImagen;
            if (this.modelo != null) {
                this.modelo.addObserver(this); //añade nuevo observer
            }
            revalidate();  //recalcular diseño del panel
            repaint(); //redibujar el panel ,con la nueva imagen
        }

        public int getPorcentaje() throws NumberFormatException { // metodo para leer y convertir el valor en entero 
            return Integer.parseInt(porcentajeField.getText());
        }

        @Override
        public void update(Observable o, Object arg) {
            repaint();
        }

        @Override
        public Dimension getPreferredSize() { //metodo para ajustarse a la imagen 
            if (modelo != null) {
                return new Dimension(modelo.getAncho(), modelo.getAlto() + 30); //+30 para acomordar la barra de texto
            }
            return new Dimension(500, 600);
        }

        @Override
        public void paintComponent(Graphics g) { 
            super.paintComponent(g);
            if (modelo != null) {
                modelo.dibujar(g); //dibuja pixel por poxel la imagen
            } 
        }
    }
}
