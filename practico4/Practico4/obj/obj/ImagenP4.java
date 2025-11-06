package obj;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ImagenP4 {
    private int[][] pixeles;
    private int width;
    private int height;
    private PropertyChangeSupport observado;

    public ImagenP4(int width, int height) {
        pixeles = new int[width][height];
        this.width = width;
        this.height = height;
        observado = new PropertyChangeSupport(this);
        lienzoBlanco();
    }

    public void lienzoBlanco() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixeles[i][j] = 0xFFFFFF;
            }
        }
        observado.firePropertyChange("IMAGEN", true, false);
    }

    public void setPixel(int x, int y, int color) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            pixeles[x][y] = color;
            observado.firePropertyChange("IMAGEN", true, false);
        }
    }

    public int getPixel(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return pixeles[x][y];
        }
        return 0;
    }

    public void lineaHorizontal(int x, int y, int largo, int color) {
        for (int i = x; i < x + largo && i < width; i++) {
            setPixel(i, y, color);
        }
    }

    public void lineaVertical(int x, int y, int alto, int color) {
        for (int j = y; j < y + alto && j < height; j++) {
            setPixel(x, j, color);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addObservador(PropertyChangeListener observador) {
        observado.addPropertyChangeListener(observador);
    }

    public BufferedImage getBufferedImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                image.setRGB(i, j, pixeles[i][j]);
            }
        }
        return image;
    }
}