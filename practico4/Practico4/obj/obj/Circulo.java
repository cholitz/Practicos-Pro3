package obj;

public class Circulo extends ElementoGrafico {
    private int radio;

    public Circulo(int posX, int posY, int radio) {
        super(posX, posY);
        this.radio = radio;
    }

    @Override
    public int getAncho() {
        return 2 * radio;
    }

    @Override
    public int getAlto() {
        return 2 * radio;
    }

    @Override
    public void dibujar(ImagenP4 img) {
        int x0 = posX + radio;
        int y0 = posY + radio;
        int x = radio;
        int y = 0;
        int err = 0;

        while (x >= y) {
            img.setPixel(x0 + x, y0 + y, color);
            img.setPixel(x0 + y, y0 + x, color);
            img.setPixel(x0 - y, y0 + x, color);
            img.setPixel(x0 - x, y0 + y, color);
            img.setPixel(x0 - x, y0 - y, color);
            img.setPixel(x0 - y, y0 - x, color);
            img.setPixel(x0 + y, y0 - x, color);
            img.setPixel(x0 + x, y0 - y, color);

            if (err <= 0) {
                y += 1;
                err += 2 * y + 1;
            }
            if (err > 0) {
                x -= 1;
                err -= 2 * x + 1;
            }
        }
        if (seleccionado) {
            img.lineaHorizontal(posX, posY + getAlto() + 2, getAncho(), 0xFF0000);
        }
    }

    @Override
    public boolean posicionDentroDeObjeto(int x, int y) {
        int dx = x - (posX + radio);
        int dy = y - (posY + radio);
        return dx * dx + dy * dy <= radio * radio;
    }
}