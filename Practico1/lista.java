import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class lista {
    private static List<String> numeros_almacenados = new ArrayList<>();
    private String ultimoMensaje = "";

    // Lista de observadores
    private List<Consumer<String>> observers = new ArrayList<>();

    public static List<String> getNumeros_almacenados() {
        return numeros_almacenados;
    }

    public void addNumero(String n) {
        numeros_almacenados.add(n);
        notifyObservers("Nuevo número agregado: " + n);
    }

    public void setUltimoMensaje(String msg) {
        this.ultimoMensaje = msg;
        notifyObservers(msg); // Notifica cada vez que se actualiza
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }

    // Observer
    public void addObserver(Consumer<String> observer) {
        observers.add(observer);
    }

    public void notifyObservers(String msg) {
        for (Consumer<String> obs : observers) {
            obs.accept(msg);
        }
    }

    // Método sin parámetros para notificar con el último mensaje
    public void notifyObservers() {
        notifyObservers(this.ultimoMensaje);
    }
}
