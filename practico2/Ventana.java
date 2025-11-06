import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Ventana extends JFrame {
    private JTextArea textArea;
    private JTextField textField;

    public Ventana() {
        // Configuración de la ventana
        setTitle("Búsqueda de Persona por CI");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        getContentPane().setBackground(new Color(240, 248, 255)); 
        // Panel 
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 

        // Etiqueta
        JLabel label = new JLabel("Ingrese CI:");
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(0, 102, 204)); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label, gbc);

        // Campo de texto
        textField = new JTextField(15);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(textField, gbc);

        // Botón
        JButton buscar = new JButton("Buscar");
        buscar.setFont(new Font("Arial", Font.BOLD, 14));
        buscar.setBackground(new Color(0, 102, 204)); 
        buscar.setForeground(Color.WHITE);
        buscar.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        buscar.setFocusPainted(false);
        buscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buscar.setBackground(new Color(0, 153, 255)); 
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buscar.setBackground(new Color(0, 102, 204)); 
            }
        });
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(buscar, gbc);

        // Área de resultados (solo muestra resultados, no logs)
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setBackground(new Color(255, 255, 240)); // Fondo beige claro
        textArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 102, 204), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10))); // Borde y padding
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(new JScrollPane(textArea), gbc);

        add(panel);

        // Log de inicio 
        log("Aplicación iniciada: " + getCurrentTime());

        // Acción del botón
        buscar.addActionListener(e -> {
            textArea.setText(""); 
            log("Iniciando búsqueda: CI " + textField.getText());
            try {
                int ci = Integer.parseInt(textField.getText());
                CargadorDatos cargador = new CargadorDatos();
                List<Persona> personas = cargador.buscarPorCi("personas.csv", ci);
                if (personas != null && !personas.isEmpty()) {
                    new QuickSort().ordenar(personas);
                    StringBuilder resultado = new StringBuilder();
                    for (Persona p : personas) {
                        resultado.append(p.toString()).append("\n");
                    }
                    textArea.append(resultado.toString()); // solo resultados en ventana
                } else {
                    textArea.append("Persona no encontrada.\n");
                }
            } catch (NumberFormatException ex) {
                log("Error: Ingrese un CI válido (número entero)."); //terminal
                textArea.append("Error: Ingrese un CI válido (número entero).\n");//ventana
            }
            textField.setText(""); 
            textField.requestFocus();
        });

        setVisible(true);
    }

    private String getCurrentTime() {
        return java.time.LocalDateTime.now().toString();
    }

    private void log(String mensaje) {
        System.out.println(mensaje);
    }
}
