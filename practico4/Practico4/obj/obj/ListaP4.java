package obj;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;

public class ListaP4<E> implements Iterable<E>, PropertyChangeListener {
    private Nodo<E> inicio;
    private PropertyChangeSupport observado;
    protected int cantidadObjetos;

    public ListaP4() {
        inicio = null;
        observado = new PropertyChangeSupport(this);
        cantidadObjetos = 0;
    }

    public void addObservador(PropertyChangeListener observador) {
        observado.addPropertyChangeListener(observador);
    }

    public void insertar(E c) {
        Nodo<E> nuevo = new Nodo<>(c);
        nuevo.setSiguiente(inicio);
        inicio = nuevo;
        cantidadObjetos++;
        enlazarConObserverSiElementoGrafico(c);
    }

    public void eliminar(int posicion) {
        if (posicion < 0 || posicion >= cantidadObjetos) {
            throw new IndexOutOfBoundsException("Posicion esta fuera de rango");
        }
        if (posicion == 0) {
            inicio = inicio.getSiguiente();
            cantidadObjetos--;
            observado.firePropertyChange("LISTA", true, false);
            return;
        }
        int index = 0;
        Nodo<E> actual = inicio;
        while (index < posicion - 1) {
            actual = actual.getSiguiente();
            index++;
        }
        Nodo<E> anteriorAEliminar = actual;
        Nodo<E> siguienteDelEliminado = actual.getSiguiente().getSiguiente();
        anteriorAEliminar.setSiguiente(siguienteDelEliminado);
        cantidadObjetos--;
        observado.firePropertyChange("LISTA", true, false);
    }

    private void enlazarConObserverSiElementoGrafico(E c) {
        if (c instanceof ElementoGrafico) {
            ElementoGrafico objElementoGrafico = (ElementoGrafico) c;
            objElementoGrafico.addObservador(this);
        }
        observado.firePropertyChange("LISTA", true, false);
    }

    @Override
    public String toString() {
        StringBuilder resultado = new StringBuilder();
        Nodo<E> actual = inicio;
        while (actual != null) {
            resultado.append(actual);
            actual = actual.getSiguiente();
        }
        return resultado.toString();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        observado.firePropertyChange("LISTA", true, false);
    }

    public int tamano() {
        return cantidadObjetos;
    }

    public E get(int posicion) {
        if (posicion < 0) {
            throw new IndexOutOfBoundsException("No existen posiciones negativas");
        }
        int posicionDentroDeArreglo = posicion % cantidadObjetos;
        Nodo<E> actual = inicio;
        int posicionActual = 0;
        while (posicionActual < posicionDentroDeArreglo && actual != null) {
            actual = actual.getSiguiente();
            posicionActual++;
        }
        if (actual == null) {
            throw new IndexOutOfBoundsException("La posicion esta fuera del tamano de la lista");
        }
        return actual.getContenido();
    }

    @Override
    public Iterator<E> iterator() {
        return new IteradorLista(inicio);
    }

    class Nodo<T> {
        private T contenido;
        private Nodo<T> siguiente;

        public Nodo(T contenido) {
            this.contenido = contenido;
            this.siguiente = null;
        }

        public T getContenido() {
            return contenido;
        }

        public void setContenido(T contenido) {
            this.contenido = contenido;
        }

        public Nodo<T> getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(Nodo<T> siguiente) {
            this.siguiente = siguiente;
        }

        @Override
        public String toString() {
            return contenido.toString() + " --> ";
        }
    }

    class IteradorLista implements Iterator<E> {
        private Nodo<E> actual;

        public IteradorLista(Nodo<E> inicio) {
            actual = inicio;
        }

        @Override
        public boolean hasNext() {
            return actual != null;
        }

        @Override
        public E next() {
            E result = actual.getContenido();
            actual = actual.getSiguiente();
            return result;
        }
    }
}