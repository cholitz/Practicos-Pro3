import java.io.BufferedReader; //lee el archivo linea por linea
import java.io.FileReader;
import java.io.IOException; //errores de lectura
import java.util.ArrayList;
import java.util.List;

public class CargadorDatos {
    public List<Persona> buscarPorCi(String archivo, int ciBuscado) {
        log("Intentando abrir " + archivo + " a las " + getCurrentTime() + ".");
        List<Persona> resultados = new ArrayList<>();
        try  {
            log("Archivo " + archivo + " abierto correctamente.");
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            br.readLine(); // Saltar la primera linea del documento
            String linea;
            int lineasProcesadas = 0;
            while ((linea = br.readLine()) != null) {
                lineasProcesadas++;
                log("Procesando línea " + lineasProcesadas + ": " + linea);
                try {
                    String[] datos = linea.split(",");
                    if (datos.length >= 5) {
                        int ci = Integer.parseInt(datos[2].trim());
                        log("Verificando CI " + ci + " contra " + ciBuscado);
                        if (ci == ciBuscado) {
                            log("Persona encontrada en línea " + lineasProcesadas);
                            resultados.add(new Persona(
                                datos[0].trim(),
                                datos[1].trim(),
                                ci,
                                datos[3].trim(),
                                datos[4].trim()
                            ));
                        }
                    } else {
                        log("Línea " + lineasProcesadas + " ignorada: menos de 5 campos (" + linea + ")");
                    }
                } catch (NumberFormatException e) {
                    log("Línea " + lineasProcesadas + " error: CI inválido en '" + linea + "'");
                } catch (ArrayIndexOutOfBoundsException e) {
                    log("Línea " + lineasProcesadas + " error: índice fuera de rango en '" + linea + "'");
                } catch (Exception e) {
                    log("Línea " + lineasProcesadas + " error inesperado: " + e.getMessage());
                }
            }
            log("Búsqueda completada: " + resultados.size() + " personas encontradas con CI " 
                + ciBuscado + " después de " + lineasProcesadas + " líneas.");
        } catch (IOException e) {
            log("Error crítico: No se pudo leer " + archivo + ": " + e.getMessage() + " a las " + getCurrentTime());
        }
        return resultados;
    }
    //Metodo para obtener la hora actual
    private String getCurrentTime() {
        return java.time.LocalDateTime.now().toString();
    }

    //logs
    private void log(String mensaje) {
        System.out.println(mensaje);
    }
}
