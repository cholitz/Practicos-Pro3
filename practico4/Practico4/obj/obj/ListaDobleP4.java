package obj;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;

public class ListaDobleP4<E> implements Iterable<E>, PropertyChangeListener {
    private Nodo<E> inicio;
    private Nodo<E> fin;
    private PropertyChangeSupport observado;
    protected int cantidadObjetos;

    public ListaDobleP4() {
        inicio = null;
        fin = null;
        observado = new PropertyChangeSupport(this);
        cantidadObjetos = 0;
    }

    public void addObservador(PropertyChangeListener observador) {
        observado.addPropertyChangeListener(observador);
    }

    public void insertarInicio(E c) {
        Nodo<E> nuevo = new Nodo<>(c);
        if (inicio == null) {
            inicio = nuevo;
            fin = nuevo;
        } else {
            nuevo.setSiguiente(inicio);
            inicio.setAnterior(nuevo);
            inicio = nuevo;
        }
        cantidadObjetos++;
        enlazarConObserverSiElementoGrafico(c);
    }

    public void insertarFin(E c) {
        Nodo<E> nuevo = new Nodo<>(c);
        if (fin == null) {
            inicio = nuevo;
            fin = nuevo;
        } else {
            nuevo.setAnterior(fin);
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
        cantidadObjetos++;
        enlazarConObserverSiElementoGrafico(c);
    }

    public void eliminarInicio() {
        if (inicio == null) {
            return;
        }
        inicio = inicio.getSiguiente();
        if (inicio != null) {
            inicio.setAnterior(null);
        } else {
            fin = null;
        }
        cantidadObjetos--;
        observado.firePropertyChange("LISTA", true, false);
    }

    public void eliminarFin() {
        if (fin == null) {
            return;
        }
        fin = fin.getAnterior();
        if (fin != null) {
            fin.setSiguiente(null);
        } else {
            inicio = null;
        }
        cantidadObjetos--;
        observado.firePropertyChange("LISTA", true, false);
    }

    public void eliminar(int posicion) {
        if (posicion < 0 || posicion >= cantidadObjetos) {
            throw new IndexOutOfBoundsException("Posición fuera de rango");
        }
        if (posicion == 0) {
            eliminarInicio();
            return;
        }
        if (posicion == cantidadObjetos - 1) {
            eliminarFin();
            return;
        }
        Nodo<E> actual = inicio;
        for (int i = 0; i < posicion; i++) {
            actual = actual.getSiguiente();
        }
        Nodo<E> anterior = actual.getAnterior();
        Nodo<E> siguiente = actual.getSiguiente();
        anterior.setSiguiente(siguiente);
        siguiente.setAnterior(anterior);
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
    public void propertyChange(PropertyChangeEvent evt) {
        observado.firePropertyChange("LISTA", true, false);
    }

    public int tamano() {
        return cantidadObjetos;
    }

    public E get(int posicion) {
        if (posicion < 0 || posicion >= cantidadObjetos) {
            throw new IndexOutOfBoundsException("Posición fuera de rango");
        }
        Nodo<E> actual = inicio;
        for (int i = 0; i < posicion; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getContenido();
    }

    @Override
    public Iterator<E> iterator() {
        return new IteradorListaDoble(inicio);
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

    class Nodo<T> {
        private T contenido;
        private Nodo<T> siguiente;
        private Nodo<T> anterior;

        public Nodo(T contenido) {
            this.contenido = contenido;
            this.siguiente = null;
            this.anterior = null;
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

        public Nodo<T> getAnterior() {
            return anterior;
        }

        public void setAnterior(Nodo<T> anterior) {
            this.anterior = anterior;
        }

        @Override
        public String toString() {
            return contenido.toString() + " <-> ";
        }
    }

    class IteradorListaDoble implements Iterator<E> {
        private Nodo<E> actual;

        public IteradorListaDoble(Nodo<E> inicio) {
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