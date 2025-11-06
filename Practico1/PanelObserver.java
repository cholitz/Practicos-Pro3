import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class PanelObserver implements Observer {
    private JTextArea resultadoArea;
    private lista lista;

    public PanelObserver(JTextArea resultadoArea, lista lista) {
        this.resultadoArea = resultadoArea;
        this.lista = lista;
    }

    @Override
    public void update() {

        // Actualizar el JTextArea en el hilo de Swing (EDT)
        SwingUtilities.invokeLater(() -> {
            resultadoArea.setText(lista.getUltimoMensaje());
            System.out.println("Panel actualizado: " + lista.getNumeros_almacenados());
        });
    }
}

