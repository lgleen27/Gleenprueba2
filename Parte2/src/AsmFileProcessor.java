import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AsmFileProcessor {
    private BufferedReader archivo;
    private String etiqueta;
    private String codop;
    private String operando;

    // Constructor que recibe el nombre del archivo como recurso
    public AsmFileProcessor(String nombreArchivo) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(nombreArchivo);
        if (inputStream == null) {
            System.err.println("No se pudo encontrar el archivo " + nombreArchivo + " en el classpath.");
            return;
        }
        archivo = new BufferedReader(new InputStreamReader(inputStream));
    }

    // Método para verificar si el archivo está abierto correctamente
    public boolean archivoAbierto() {
        return archivo != null;
    }

    // Método para leer y procesar cada línea del archivo
    public void procesarArchivo() {
        String linea;
        try {
            while ((linea = archivo.readLine()) != null) {
                if (esComentario(linea)) {
                    System.out.println("Comentario");
                    continue;
                }

                procesarLinea(linea);  // Procesar cada línea

                // Imprimir el valor de las variables
                imprimirValores();

                // Inicializar las variables a "null" para la siguiente línea
                inicializarValores();
            }
        } catch (IOException e) {
            System.err.println("Error leyendo el archivo.");
        }
    }

    // Función que verifica si una línea es un comentario
    private boolean esComentario(String linea) {
        return !linea.isEmpty() && linea.charAt(0) == ';';  // Suponiendo que los comentarios empiezan con ';'
    }

    // Función que separa la etiqueta, el codop y el operando de una línea
    private void procesarLinea(String linea) {
        inicializarValores();  // Reiniciar valores de las variables

        int i = 0;

        // Ignorar todos los espacios y tabuladores al principio de la línea
        while (i < linea.length() && Character.isWhitespace(linea.charAt(i))) i++;

        // Si la línea está vacía después de ignorar los espacios, no hacer nada
        if (i == linea.length()) {
            return;
        }

        // Leer la primera palabra (podría ser una etiqueta o codop)
        int start = i;
        while (i < linea.length() && !Character.isWhitespace(linea.charAt(i)) && linea.charAt(i) != ':') i++;

        if (i > start) {
            if (i < linea.length() && linea.charAt(i) == ':') {  // Si encontramos un ':', es una etiqueta
                etiqueta = linea.substring(start, i);
                i++;  // Saltar el carácter ':'
            } else {  // De lo contrario, puede ser un codop
                codop = linea.substring(start, i);
            }
        }

        // Ignorar espacios entre palabras
        while (i < linea.length() && Character.isWhitespace(linea.charAt(i))) i++;

        // Leer la segunda palabra (podría ser el CODOP si ya hay etiqueta, o el operando)
        start = i;
        while (i < linea.length() && !Character.isWhitespace(linea.charAt(i))) i++;
        if (i > start) {
            if (!etiqueta.equals("null")) {
                codop = linea.substring(start, i);
            } else {
                operando = linea.substring(start, i);
            }
        }

        // Ignorar espacios entre palabras
        while (i < linea.length() && Character.isWhitespace(linea.charAt(i))) i++;

        // Leer la tercera palabra (podría ser el operando)
        start = i;
        while (i < linea.length() && !Character.isWhitespace(linea.charAt(i))) i++;
        if (i > start) {
            operando = linea.substring(start, i);
        }
    }

    // Método para imprimir los valores actuales de etiqueta, codop y operando
    private void imprimirValores() {
        System.out.println("Etiqueta: " + etiqueta);
        System.out.println("Codop: " + codop);
        System.out.println("Operando: " + operando);
    }

    // Método para inicializar los valores a "null"
    private void inicializarValores() {
        etiqueta = "null";
        codop = "null";
        operando = "null";
    }
}
