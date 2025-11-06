package obj;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Vertical editor = new Vertical();
            editor.setVisible(true);
        });
    }
}