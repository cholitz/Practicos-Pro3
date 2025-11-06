package obj;

public class Linea extends ElementoGrafico {
    private int endX, endY;

    public Linea(int startX, int startY, int endX, int endY) {
        super(startX, startY);
        this.endX = endX;
        this.endY = endY;
    }

    @Override
    public int getAncho() {
        return Math.abs(endX - posX);
    }

    @Override
    public int getAlto() {
        return Math.abs(endY - posY);
    }

    @Override
    public void dibujar(ImagenP4 img) {
        int x0 = posX;
        int y0 = posY;
        int x1 = endX;
        int y1 = endY;
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            img.setPixel(x0, y0, color);
            if (x0 == x1 && y0 == y1) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
        if (seleccionado) {
            int minX = Math.min(posX, endX);
            int maxY = Math.max(posY, endY);
            img.lineaHorizontal(minX, maxY + 2, getAncho(), 0xFF0000);
        }
    }

    @Override
    public boolean posicionDentroDeObjeto(int x, int y) {
        int x0 = posX;
        int y0 = posY;
        int x1 = endX;
        int y1 = endY;
        double distance = Math.abs((y1 - y0) * x - (x1 - x0) * y + x1 * y0 - y1 * x0) /
                         Math.sqrt(Math.pow(y1 - y0, 2) + Math.pow(x1 - x0, 2));
        return distance <= 5; // Tolerancia de 5 píxeles
    }
}