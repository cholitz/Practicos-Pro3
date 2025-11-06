public class Achicar implements IOperacionImagen {
    private Imagen target; //Imagen Original
    private int porcentaje;

    public Achicar(Imagen target, int porcentaje) {
        this.porcentaje = porcentaje;
        this.target = target;
    }

    @Override
    public void ejecutar() {
        int wf = (target.getAncho() * this.porcentaje) / 100;
        int hf = (target.getAlto() * this.porcentaje) / 100;
        Imagen resultado = new Imagen(wf, hf);
        for (int i = 0; i < wf; i++) {
            for (int j = 0; j < hf; j++) {
                int xImagenOriginal = i * 100 / porcentaje;
                int yImagenOriginal = j * 100 / porcentaje;
                int color = target.get(xImagenOriginal, yImagenOriginal);
                resultado.setPixel(i, j, (color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF); //operaciones de bits
            }
        }
        target.setPixeles(wf, hf, resultado.getPixeles());
    }
}