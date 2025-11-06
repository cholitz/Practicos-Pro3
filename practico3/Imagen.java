import java.awt.*;
import java.util.Observable;

public class Imagen extends Observable {
    private int pixeles[][]; 
    private int ancho;
    private int alto;

    public Imagen(int w, int h) {
        pixeles = new int[w][h];
        ancho = w;
        alto = h;
    }

    public void setPixel(int x, int y, int r, int g, int b) { //establecer el color de un pixel
        pixeles[x][y] = (r << 16) | (g << 8) | b; //desplazamiento de bits
    }

    public void setPixel(int x, int y, int c) {
        pixeles[x][y] = c;
    }

    //Reemplazar la matriz anterior por una nueva 
    public void setPixeles(int w, int h, int[][] nuevosPixeles) {
        pixeles = nuevosPixeles; 
        ancho = w;
        alto = h;
        setChanged(); //notifica 
        notifyObservers(); //permite la actualizacion 
    }

    public int get(int x, int y) {
        return pixeles[x][y];
    }

    public int getR(int x, int y) {
        return pixeles[x][y] >> 16;
    }

    public int getG(int x, int y) {
        return (0x000000FF & (pixeles[x][y] >> 8));
    }

    public int getB(int x, int y) {
        return (0x000000FF & pixeles[x][y]);
    }

    public int[][] getPixeles() { //Matriz completa
        return pixeles;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void dibujar(Graphics g) {
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                g.setColor(new Color(pixeles[i][j]));
                g.drawLine(i, j, i, j);
            }
        }
    }
}