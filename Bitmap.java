package objetosAuxiliares;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import tdas.lista.IteradorLista;
import tdas.lista.ListaEnlazada;

/**
 * Clase que permite crear y manipular una imagen BMP píxel por píxel,
 * dibujando elementos o superponiendo imágenes.
 */
public class Bitmap {
    final int TAM_CELDA = 50;
    final int MARGEN_CELDA = 1;
    final int ESPACIADO_CAPAS = 10;

    private final BufferedImage imagen;
    private final int ancho;
    private final int alto;

    /**
     * Constructor de la clase Bitmap.
     * Crea una imagen vacía del tamaño especificado.
     *
     * @param ancho ancho de la imagen en píxeles
     * @param alto  alto de la imagen en píxeles
     */
    public Bitmap(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        this.imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
    }

    /**
     * Colorea un píxel específico con un color RGB.
     *
     * @param x, y coordenadas horizontal y vertical respectivamente del píxel
     * @param r componente roja del color (0-255)
     * @param g componente verde del color (0-255)
     * @param b componente azul del color (0-255)
     */
    public void setPixel(int x, int y, int r, int g, int b) {
        if (x < 0 || x >= ancho || y < 0 || y >= alto) return;
        int rgb = (r << 16) | (g << 8) | b;
        imagen.setRGB(x, y, rgb);
    }

    /**
     * Dibuja una imagen externa dentro del lienzo de este Bitmap.
     *
     * @param ruta         ruta del archivo de imagen a cargar
     * @param x, y         coordenada X e Y donde comienza el dibujo
     * @param anchoEscalado  ancho al que se escalará la imagen
     * @param altoEscalado alto al que se escalará la imagen
     * @throws IOException por si no puede leerse el archivo
     */
    public void dibujarImagen(String ruta, int x, int y, int anchoEscalado, int altoEscalado) throws IOException {
        BufferedImage pin = ImageIO.read(new File(ruta));
        Graphics graficos = imagen.getGraphics();
        graficos.drawImage(pin, x, y, anchoEscalado, altoEscalado, null);
        graficos.dispose();
    }

    /**
     * Guarda la imagen como un archivo BMP.
     *
     * @param nombreArchivo nombre del archivo a generar (debe terminar en .bmp)
     * @throws IOException por si ocurre un error al escribir el archivo
     */
    public void guardarArchivo(String nombreArchivo) throws IOException {
        ImageIO.write(imagen, "bmp", new File(nombreArchivo));
    }

    /**
     * @return ancho de la imagen en píxeles
     */
    public int getAncho() {
        return ancho;
    }

    /**
     * @return alto de la imagen en píxeles
     */
    public int getAlto() {
        return alto;
    }

    /**
     * Genera un archivo BMP que representa visualmente las capas de un tablero 3D.
     * Cada capa Z se dibuja de arriba hacia abajo.
     *
     * @param tablero tablero a representar gráficamente
     * @throws IOException si hay errores al cargar imágenes o guardar el resultado
     */
    public void generarBMPCompleto(Tablero tablero) throws IOException {
        int dimension = tablero.obtenerDimension();
    
        // Acomodar capas horizontalmente: ancho se multiplica por Z
        int anchoImagen = dimension * (dimension * TAM_CELDA + ESPACIADO_CAPAS) - ESPACIADO_CAPAS;
        int altoImagen = dimension * TAM_CELDA;
    
        Bitmap bmp = new Bitmap(anchoImagen, altoImagen);
    
        for (int x = 0; x < dimension; x++) {
            ListaEnlazada<Sector> sectoresEnX = tablero.obtenerSectores(x);
            IteradorLista<Sector> iter = sectoresEnX.iterador();
            while (iter.haySiguiente()) {
                Sector sector = iter.verActual();
                int[] coords = sector.obtenerCoordenadas();
                int y = coords[1], z = coords[2];
                int yInvertido = dimension - 1 - y;
    
                // Desplazar horizontalmente por Z
                int desplazamientoZ = z * (dimension * TAM_CELDA + ESPACIADO_CAPAS);
                int xFinal = x * TAM_CELDA + desplazamientoZ;
                int yFinal = yInvertido * TAM_CELDA;
    
                Pieza pieza = sector.obtenerValor();
                String simbolo = pieza != null ? pieza.obtenerNombre() : "_";
                int r = 200, g = 200, b = 200; // gris claro para celdas vacías
    
                // Dibuja la celda con bordes
                for (int i = 0; i < TAM_CELDA; i++) {
                    for (int j = 0; j < TAM_CELDA; j++) {
                        boolean hayBorde = (i < MARGEN_CELDA || j < MARGEN_CELDA ||
                                i == TAM_CELDA - MARGEN_CELDA || j == TAM_CELDA - MARGEN_CELDA);
                        if (hayBorde) {
                            bmp.setPixel(xFinal + i, yFinal + j, 100, 100, 100); // borde gris oscuro
                        } else {
                            bmp.setPixel(xFinal + i, yFinal + j, r, g, b); // interior gris claro
                        }
                    }
                }
    
                // Si hay una nave o base, se dibuja su imagen
                if (simbolo.equals("N")) {
                    bmp.dibujarImagen("imagenes/nave.png", xFinal + 5, yFinal + 5, TAM_CELDA - 10, TAM_CELDA - 10);
                } else if (simbolo.equals("B")) {
                    bmp.dibujarImagen("imagenes/base.png", xFinal + 5, yFinal + 5, TAM_CELDA - 10, TAM_CELDA - 10);
                }
    
                iter.siguiente();
            }
        }
    
        bmp.guardarArchivo("tablero.bmp");
    }
    

    /**
     * Dibuja manualmente la letra "N" en una celda del bitmap.
     * (de momento si se puede con iconos, esta funcion queda inutilizada)
     *
     * @param bmp    bitmap donde se dibuja
     * @param startX coordenada X inicial
     * @param startY coordenada Y inicial
     * @param tamCelda  tamaño de la celda
     */
    private static void dibujarLetraN(Bitmap bmp, int startX, int startY, int tamCelda) {
        for (int i = 0; i < tamCelda; i++) {
            for (int j = 0; j < tamCelda; j++) {
                boolean esTrazoN = (j == 2 || j == 6 || i == j); // columnas laterales + diagonal
                if (esTrazoN && i >= 2 && i <= 6) {
                    bmp.setPixel(startX + j, startY + i, 255, 0, 140); // color rosa fuerte
                }
            }
        }
    }
}
