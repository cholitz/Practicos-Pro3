package obj.redes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import obj.*;
import java.io.*;
import java.net.Socket;
import java.util.regex.*;
import java.beans.*;

public class ServicioDibujo {
    private static final String CLAVE = "bolivia2025";
    private static final Logger log = LogManager.getLogger(ServicioDibujo.class);
    private final Socket cliente;
    private final ImagenP4 lienzo;
    private final ListaP4<ElementoGrafico> lista;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private PrintWriter out;

    public ServicioDibujo(Socket cliente, ImagenP4 lienzo, ListaP4<ElementoGrafico> lista) {
        this.cliente = cliente;
        this.lienzo = lienzo;
        this.lista = lista;
    }

    public void addObserver(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    public void iniciar() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        out = new PrintWriter(cliente.getOutputStream(), true);

        log.info("Cliente conectado: {}", cliente.getInetAddress());
        pcs.firePropertyChange("CONEXION", null, cliente.getInetAddress());

        String linea = in.readLine();
        if (!autenticar(linea)) {
            cerrar();
            return;
        }

        out.println("OK");
        log.info("Autenticación exitosa");
        pcs.firePropertyChange("LOGIN_OK", null, true);

        while ((linea = in.readLine()) != null) {
            log.debug("Recibido: {}", linea);
            if (procesarComando(linea)) break;
        }
        cerrar();
    }

    private boolean autenticar(String linea) {
        if (!linea.startsWith("LOGIN ")) {
            out.println("ERROR");
            log.warn("Falta LOGIN");
            pcs.firePropertyChange("ERROR", null, "Falta LOGIN");
            return false;
        }
        String clave = linea.substring(6).trim();
        if (!CLAVE.equals(clave)) {
            out.println("ERROR");
            log.warn("Clave incorrecta: {}", clave);
            pcs.firePropertyChange("ERROR", null, "Clave incorrecta");
            return false;
        }
        return true;
    }

    private boolean procesarComando(String cmd) {
        cmd = cmd.trim();
        if (cmd.equals("FIN")) {
            out.println("OK");
            log.info("Cliente solicitó FIN");
            pcs.firePropertyChange("FIN", null, true);
            return true;
        }

        try {
            if (cmd.startsWith("LINEA ")) return procesarLinea(cmd.substring(6));
            if (cmd.startsWith("CIRCULO ")) return procesarCirculo(cmd.substring(8));
            if (cmd.startsWith("RECTANGULO ")) return procesarRectangulo(cmd.substring(11));
        } catch (Exception e) {
            out.println("ERROR");
            log.error("Error procesando comando: {}", cmd, e);
            pcs.firePropertyChange("ERROR", null, e.getMessage());
            return false;
        }

        out.println("ERROR");
        log.warn("Comando desconocido: {}", cmd);
        pcs.firePropertyChange("ERROR", null, "Comando desconocido");
        return false;
    }

    private boolean procesarLinea(String p) {
        Matcher m = Pattern.compile("(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(verde|azul|rojo|negro)").matcher(p);
        if (!m.matches()) {
            out.println("ERROR");
            log.error("LINEA: parámetros inválidos");
            pcs.firePropertyChange("ERROR", null, "LINEA inválida");
            return false;
        }
        int x1 = Integer.parseInt(m.group(1)), y1 = Integer.parseInt(m.group(2));
        int x2 = Integer.parseInt(m.group(3)), y2 = Integer.parseInt(m.group(4));
        int color = color(m.group(5));

        Linea linea = new Linea(x1, y1, x2, y2);
        linea.setColor(color);
        lista.insertar(linea);
        redibujar();

        out.println("OK");
        log.info("Línea: ({},{}) -> ({},{})", x1, y1, x2, y2);
        pcs.firePropertyChange("DIBUJO", null, "LINEA");
        return false;
    }

    private boolean procesarCirculo(String p) {
        Matcher m = Pattern.compile("(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(verde|azul|rojo|negro)").matcher(p);
        if (!m.matches()) {
            out.println("ERROR");
            log.error("CIRCULO: parámetros inválidos");
            pcs.firePropertyChange("ERROR", null, "CIRCULO inválido");
            return false;
        }
        int x = Integer.parseInt(m.group(1)), y = Integer.parseInt(m.group(2));
        int diam = Integer.parseInt(m.group(3));
        int radio = diam / 2;
        int color = color(m.group(4));

        Circulo circulo = new Circulo(x, y, radio);
        circulo.setColor(color);
        lista.insertar(circulo);
        redibujar();

        out.println("OK");
        log.info("Círculo: centro ({},{}), diámetro {}", x, y, diam);
        pcs.firePropertyChange("DIBUJO", null, "CIRCULO");
        return false;
    }

    private boolean procesarRectangulo(String p) {
        Matcher m = Pattern.compile("(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(verde|azul|rojo|negro)").matcher(p);
        if (!m.matches()) {
            out.println("ERROR");
            log.error("RECTANGULO: parámetros inválidos");
            pcs.firePropertyChange("ERROR", null, "RECTANGULO inválido");
            return false;
        }
        int x = Integer.parseInt(m.group(1)), y = Integer.parseInt(m.group(2));
        int w = Integer.parseInt(m.group(3)), h = Integer.parseInt(m.group(4));
        int c = color(m.group(5));

        Linea l1 = new Linea(x, y, x + w, y);
        l1.setColor(c);
        lista.insertar(l1);

        Linea l2 = new Linea(x + w, y, x + w, y + h);
        l2.setColor(c);
        lista.insertar(l2);

        Linea l3 = new Linea(x + w, y + h, x, y + h);
        l3.setColor(c);
        lista.insertar(l3);

        Linea l4 = new Linea(x, y + h, x, y);
        l4.setColor(c);
        lista.insertar(l4);

        redibujar();

        out.println("OK");
        log.info("Rectángulo: ({},{}), {}x{}", x, y, w, h);
        pcs.firePropertyChange("DIBUJO", null, "RECTANGULO");
        return false;
    }

    private int color(String c) {
        if (c == null) return 0x000000;
        switch (c) {
            case "rojo":
                return 0xFF0000;
            case "verde":
                return 0x00FF00;
            case "azul":
                return 0x0000FF;
            default:
                return 0x000000;
        }
    }

    private void redibujar() {
        lienzo.lienzoBlanco();
        for (ElementoGrafico el : lista) el.dibujar(lienzo);
    }

    private void cerrar() throws IOException {
        if (cliente != null && !cliente.isClosed()) {
            cliente.close();
            log.info("Conexión cerrada");
        }
    }
}