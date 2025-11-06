package obj.redes;

import obj.*;
import java.net.*;
import java.io.IOException;  // ¡AÑADIR ESTO!

public class ServidorDibujo {
    private static final int PUERTO = 7744;
    private static final int ANCHO = 800, ALTO = 600;

    public static void main(String[] args) {
        ImagenP4 lienzo = new ImagenP4(ANCHO, ALTO);
        ListaP4<ElementoGrafico> lista = new ListaP4<>();

        try (ServerSocket srv = new ServerSocket(PUERTO)) {
            System.out.println("Servidor en puerto " + PUERTO);

            while (true) {
                Socket cliente = srv.accept();
                ServicioDibujo servicio = new ServicioDibujo(cliente, lienzo, lista);

                servicio.addObserver(evt -> {
                    System.out.println("[EVENTO] " + evt.getPropertyName() + ": " + evt.getNewValue());
                });

                new Thread(() -> {
                    try { servicio.iniciar(); }
                    catch (Exception e) { e.printStackTrace(); }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}