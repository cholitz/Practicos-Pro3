package obj;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class ElementoGrafico {
    protected int posX, posY;
    protected int color;
    protected boolean seleccionado;
    private PropertyChangeSupport observado;

    public ElementoGrafico(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.color = 0x000000;
        this.seleccionado = false;
        this.observado = new PropertyChangeSupport(this);
    }

    public abstract int getAncho();

    public abstract int getAlto();

    public abstract void dibujar(ImagenP4 img);

    public abstract boolean posicionDentroDeObjeto(int x, int y);

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
        observado.firePropertyChange("SELECCION", !seleccionado, seleccionado);
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void addObservador(PropertyChangeListener observador) {
        observado.addPropertyChangeListener(observador);
    }

    public void setColor(int color) {
        this.color = color;
        observado.firePropertyChange("COLOR", null, color);
    }

    public int getColor() {
        return color;
    }
}