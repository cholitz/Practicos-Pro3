import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class botonPersonalizado_3 implements ActionListener {

    //LOGS
    private static final Logger logger = LogManager.getLogger(botonPersonalizado_3.class);

    //instancias
    private JPanel panelT; 
    private lista lista;
    private JTextArea resultadoArea;

    // Constructor que recibe el panel donde se mostrarán los resultados
    public botonPersonalizado_3(JPanel panelT) {
        this.panelT = panelT;
        this.lista = new lista();

        //  Un JTextArea único para todo el panel
        this.resultadoArea = new JTextArea();
        this.resultadoArea.setEditable(false);
        this.resultadoArea.setBounds(20, 20, 350, 200);
        panelT.add(resultadoArea);
        
        panelT.revalidate();
        panelT.repaint();
    }

    // Getter para exponer la instancia de lista
    public lista getLista() {
        return this.lista;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String opcion = e.getActionCommand();
        logger.info("Opción seleccionada: " + opcion);

        switch (opcion) {
            case "MULTIPLOS":
                logicaMultiplos();
                break;
            case "MENOR A MAYOR":
                logicaMAM();    
                break;
            case "MAYOR A MENOR":
                logicaMAm();
                break;
            case "PRIMOS":
                logicaPrimos();
                break;
            case "PARES":
                logicaPares();
                break;
            case "IMPARES":
                logicaImpares();
                break;
            default:
                logger.warn("Opción desconocida: " + opcion);
                break;
        }
    }

    //MULTIPLOS
    private void logicaMultiplos() {
        JTextField input = new JTextField(10);
        int resultado = JOptionPane.showConfirmDialog(
                null,
                input,
                "Ingrese un número para encontrar sus múltiplos:",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                int numero = Integer.parseInt(input.getText());
                List<Integer> multiplos = new ArrayList<>();
                for (String nStr : lista.getNumeros_almacenados()) {
                    int n = Integer.parseInt(nStr);
                    if (n % numero == 0) 
                    multiplos.add(n);
                }
                resultadoArea.setText("Múltiplos de " + numero + ":\n" + multiplos);
                logger.info("Múltiplos de " + numero + ": " + multiplos);
                lista.setUltimoMensaje("MULTIPLOS de " + numero + ": " + multiplos);
            } catch (NumberFormatException ex) {
                resultadoArea.setText("⚠ Por favor, ingrese un número válido.");
                logger.error("Error al ingresar número para múltiplos", ex);
                lista.setUltimoMensaje("Error: número inválido para MULTIPLOS");
            }
        } 

        panelT.revalidate();
        panelT.repaint();
    }

    //MENOR A MAYOR
    private void logicaMAM() {
        try {
            List<Integer> numeros = new ArrayList<>();
            for (String nStr : lista.getNumeros_almacenados()) {
                numeros.add(Integer.parseInt(nStr));
            }
            numeros.sort(Integer::compareTo);
            resultadoArea.setText("Números de menor a mayor:\n" + numeros);
            logger.info("Números ordenados de menor a mayor: " + numeros);
            lista.setUltimoMensaje("MENOR A MAYOR: " + numeros);
        } catch (NumberFormatException ex) {
            resultadoArea.setText("⚠ Error al convertir los números.");
            logger.error("Error al ordenar de menor a mayor", ex);
            lista.setUltimoMensaje("Error: MENOR A MAYOR");
        }
        panelT.revalidate();
        panelT.repaint();
    }

    // MAYOR A MENOR
    public void logicaMAm() {
        try {
            List<Integer> numeros = new ArrayList<>();
            for (String nStr : lista.getNumeros_almacenados()) {
                numeros.add(Integer.parseInt(nStr));
            }
            numeros.sort((a, b) -> b.compareTo(a));
            resultadoArea.setText("Números de mayor a menor:\n" + numeros);
            logger.info("Números ordenados de mayor a menor: " + numeros);
            lista.setUltimoMensaje("MAYOR A MENOR: " + numeros);
        } catch (NumberFormatException ex) {
            resultadoArea.setText("⚠ Error al convertir los números.");
            logger.error("Error al ordenar de mayor a menor", ex);
            lista.setUltimoMensaje("Error: MAYOR A MENOR");
        }
        panelT.revalidate();
        panelT.repaint();
    }

    // PRIMOS
    private boolean esPrimo(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public void logicaPrimos() {
        try {
            List<Integer> primos = new ArrayList<>();
            for (String nStr : lista.getNumeros_almacenados()) {
                int n = Integer.parseInt(nStr);
                if (esPrimo(n)) primos.add(n);
            }
            resultadoArea.setText("Números primos:\n" + primos);
            logger.info("Números primos: " + primos);
            lista.setUltimoMensaje("PRIMOS: " + primos);
        } catch (NumberFormatException ex) {
            resultadoArea.setText("⚠ Error al convertir los números.");
            logger.error("Error al calcular primos", ex);
            lista.setUltimoMensaje("Error: PRIMOS");
        }
        panelT.revalidate();
        panelT.repaint();
    }

    //PARES
    public void logicaPares() {
        try {
            List<Integer> pares = new ArrayList<>();
            for (String nStr : lista.getNumeros_almacenados()) {
                int n = Integer.parseInt(nStr);
                if (n % 2 == 0) {
                    pares.add(n);
                }
            }
            resultadoArea.setText("Números pares:\n" + pares);
            logger.info("Números pares: " + pares);
            lista.setUltimoMensaje("PARES: " + pares);
        } catch (NumberFormatException ex) {
            resultadoArea.setText("⚠ Error al convertir los números.");
            logger.error("Error al calcular pares", ex);
            lista.setUltimoMensaje("Error: PARES");
        }
        panelT.revalidate();
        panelT.repaint();
    }

    // IMPARES
    public void logicaImpares() {
        try {
            List<Integer> impares = new ArrayList<>();
            for (String nStr : lista.getNumeros_almacenados()) {
                int n = Integer.parseInt(nStr);
                if (n % 2 != 0) {
                    impares.add(n);
                }
            }
            resultadoArea.setText("Números impares:\n" + impares);
            logger.info("Números impares: " + impares);
            lista.setUltimoMensaje("IMPARES: " + impares);
        } catch (NumberFormatException ex) {
            resultadoArea.setText("⚠ Error al convertir los números.");
            logger.error("Error al calcular impares", ex);
            lista.setUltimoMensaje("Error: IMPARES");
        }
        panelT.revalidate();
        panelT.repaint();
    }

    // LIMPIAR
    public void limpiar() {
        panelT.removeAll();
        panelT.add(resultadoArea);
        resultadoArea.setText("");
        panelT.revalidate();
        panelT.repaint();
        logger.info("Panel limpiado.");
        lista.setUltimoMensaje("Panel limpiado");
    }
}
