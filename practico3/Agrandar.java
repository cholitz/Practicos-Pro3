public class Agrandar implements IOperacionImagen {
    private Imagen target; //Imagen Original
    private int porcentaje;

    public Agrandar(Imagen target, int porcentaje) {
        this.porcentaje = porcentaje;
        this.target = target;
    }

    @Override
    public void ejecutar() {
        int wf = (target.getAncho() * this.porcentaje) / 100; //variable
        int hf = (target.getAlto() * this.porcentaje) / 100;  //variable
        Imagen resultado = new Imagen(wf, hf);

        for (int i = 0; i < wf; i++) {
            for (int j = 0; j < hf; j++) {
                int xImagenOriginal = Math.min(i * target.getAncho() / wf, target.getAncho() - 1);
                int yImagenOriginal = Math.min(j * target.getAlto() / hf, target.getAlto() - 1);
                int color = target.get(xImagenOriginal, yImagenOriginal);
                resultado.setPixel(i, j, (color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF); 
            }
        }
        target.setPixeles(wf, hf, resultado.getPixeles());
    }
}