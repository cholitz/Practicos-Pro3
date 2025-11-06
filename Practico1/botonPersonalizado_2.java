import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class botonPersonalizado_2 implements ActionListener {
    
   private static final Logger logger = LogManager.getLogger(botonPersonalizado_2.class);
    private JPanel panelb;

    public botonPersonalizado_2(JPanel panelb) {
        this.panelb = panelb;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField barra_de_interaccion = new JTextField();
        barra_de_interaccion.setBounds(150, 10, 200, 30);
        JButton agregar= new JButton("Agregar");
        agregar.setBounds(150, 50, 80, 30);



        agregar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = barra_de_interaccion.getText();
                if (!texto.isEmpty()) {
                   if(lista.getNumeros_almacenados().size()<20){
                    lista.getNumeros_almacenados().add(texto);
                    logger.info("Número agregado: " + texto);
                    logger.debug("Lista actual: " + lista.getNumeros_almacenados());
                    barra_de_interaccion.setText("");  //DEJA LIMPIA LA BARRA DE TEXTO
                     }else{
                    logger.info("La lista ha al  canzado el máximo de 20 números.");
                    
                        JOptionPane.showMessageDialog(null, "LIMITE ALCANZADO"); 
                   }
                    barra_de_interaccion.setText("");
                } 

            }
        });

        panelb.add(agregar);
        barra_de_interaccion.addKeyListener(new java.awt.event.KeyAdapter() {
           
            //CON ESTE METODO SE PUEDE INGRESAR EXCLUSIVAMENTE SOLO NUMEROS
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c) ) {
                    evt.consume(); 
                }
               
            }
        });
       
        



        panelb.add(barra_de_interaccion);
        panelb.revalidate();
        panelb.repaint();

    }



}
        