package obj;

public class ListaElementos {
    private ElementoGrafico[] elementos;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public ListaElementos() {
        elementos = new ElementoGrafico[DEFAULT_CAPACITY];
        size = 0;
    }

    public void add(ElementoGrafico elemento) {
        if (size == elementos.length) {
            ElementoGrafico[] newArray = new ElementoGrafico[elementos.length * 2];
            System.arraycopy(elementos, 0, newArray, 0, size);
            elementos = newArray;
        }
        elementos[size++] = elemento;
    }

    public ElementoGrafico getElement(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        return elementos[index];
    }

    public void removeSeleccionados() {
        int newSize = 0;
        for (int i = 0; i < size; i++) {
            if (!elementos[i].isSeleccionado()) {
                elementos[newSize] = elementos[i];
                newSize++;
            }
        }
        for (int i = newSize; i < size; i++) {
            elementos[i] = null;
        }
        size = newSize;
    }

    public int getSize() {
        return size;
    }
}