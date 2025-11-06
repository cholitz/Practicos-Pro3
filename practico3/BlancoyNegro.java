public class BlancoyNegro implements IOperacionImagen {
    private Imagen target; 

    public BlancoyNegro(Imagen img) {
        target = img;
    }

    @Override
    public void ejecutar() {
        Imagen resultado = new Imagen(target.getAncho(), target.getAlto());
        
        for (int i = 0; i < target.getAncho(); i++) {
            for (int j = 0; j < target.getAlto(); j++) {
                int r = target.getR(i, j);
                int g = target.getG(i, j);
                int b = target.getB(i, j);
                int grises = (r + g + b) / 3;
                int bn = grises > 127 ? 255 : 0;
                resultado.setPixel(i, j, bn, bn, bn);
            }
        }
        target.setPixeles(resultado.getAncho(), resultado.getAlto(), resultado.getPixeles());
    }
}