package tablero;

// Importaciones necesarias
import Coordenada.Coordenada;
import estructuras.lista.IteradorLista;
import estructuras.lista.ListaSimplementeEnlazada;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import jugador.Jugador;
import piezas.Pieza;
import utils.ValidacionesUtils;

/**
 * Clase que permite crear y manipular una imagen BMP píxel por píxel,
 * dibujando elementos o superponiendo imágenes.
 */

public class Bitmap {
    public static final int TAM_CELDA = 50;
    public static final int MARGEN_CELDA = 1;
    public static final int ESPACIADO_CAPAS = 10;

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
        ValidacionesUtils.validarMayorACero(alto, "del alto del Bitmap");
        ValidacionesUtils.validarMayorACero(ancho, "del ancho del Bitmap");
        this.ancho = ancho;
        this.alto = alto;
        this.imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
    }

    /**
     * Colorea un píxel específico con un color RGB.
     *
     * @param x,y coordenadas horizontal y vertical respectivamente del píxel
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
     * @param x,y         coordenada X e Y donde comienza el dibujo
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
    public void generarBMPCompleto(Tablero tablero, Jugador jugador, ListaSimplementeEnlazada<Pieza> piezasDetectadas) throws IOException {
        int dimension = tablero.obtenerDimension();
    
        for (int x = 0; x < dimension; x++) {
            ListaSimplementeEnlazada<Sector> sectoresEnX = tablero.obtenerSectores(x);
            IteradorLista<Sector> iter = sectoresEnX.iterador();
            while (iter.haySiguiente()) {
                Sector sector = iter.verActual();
                Coordenada coords = sector.obtenerCoordenadas();
                int y = coords.getY(), z = coords.getZ();
                int yInvertido = dimension - 1 - y;
    
                int desplazamientoZ = z * (dimension * TAM_CELDA + ESPACIADO_CAPAS);
                int xFinal = x * TAM_CELDA + desplazamientoZ;
                int yFinal = yInvertido * TAM_CELDA;
    
                Pieza pieza = sector.obtenerValor();
                String simbolo = pieza != null ? pieza.obtenerNombre() : "_";
                int r = 200, g = 200, b = 200;
    
                dibujarBorde(xFinal, yFinal, r, g, b);
                
                if (pieza != null) {
                    Jugador duenio = pieza.obtenerDuenio();
                    String spritePath = obtenerSprite(simbolo, duenio, jugador, piezasDetectadas.contains(pieza));
                    if (spritePath != null) {
                        dibujarImagen(spritePath, xFinal + 5, yFinal + 5, TAM_CELDA - 10, TAM_CELDA - 10);
                    }
                }
                iter.siguiente();
            }
        }
    }
    
    /**
     * Obtiene la ruta del sprite correspondiente a un símbolo y su dueño.
     * 
     * @param simbolo el símbolo de la pieza (N, B, S, R)
     * @param duenio el jugador dueño de la pieza
     * @param jugadorActual el jugador que está visualizando el tablero
     * @param esDetectada indica si la pieza ha sido detectada por el jugador actual
     * 
     * @return la ruta del sprite correspondiente o null si no se encuentra
     */
    private String obtenerSprite(String simbolo, Jugador duenio, Jugador jugadorActual, boolean esDetectada) {
        String base = "tablero/imagenes/";

        if (simbolo.equals("R")) {
            return base + "radiacion.png"; // Radiación no tiene dueño
        }
        
        if (duenio == null) {
            return null; // Si no hay símbolo o dueño, no hay sprite
        }


        if (duenio.equals(jugadorActual)) {
            return switch (simbolo) {
                case "N" -> base + "nave.png";
                case "B" -> base + "base.png";
                case "S" -> base + "satelite.png";
                default -> null;
            };
        }

        if (duenio.esAliado(jugadorActual)) {
            return switch (simbolo) {
                case "N" -> base + "nave_aliada.png";
                case "S" -> base + "satelite_aliado.png";
                // Agregá más tipos si tenés otros sprites aliados
                default -> null;
            };
        }

        if (esDetectada) {
            return switch (simbolo) {
                case "N" -> base + "nave_enemiga.png";
                case "B" -> base + "base_enemiga.png";
                case "S" -> base + "satelite_enemigo.png";
                default -> null;
            };
        }

        return null;
    }

    /**
     * Dibuja un borde alrededor de una celda en el tablero.
     * El borde es de un color gris claro, y el interior de la celda
     * se colorea con los valores RGB especificados.
     */
    private void dibujarBorde(int xFinal, int yFinal, int r, int g, int b) {
        for (int i = 0; i < TAM_CELDA; i++) {
            for (int j = 0; j < TAM_CELDA; j++) {
                boolean hayBorde = (i < MARGEN_CELDA || j < MARGEN_CELDA ||
                        i == TAM_CELDA - MARGEN_CELDA || j == TAM_CELDA - MARGEN_CELDA);
                if (hayBorde) {
                    setPixel(xFinal + i, yFinal + j, 100, 100, 100);
                } else {
                    setPixel(xFinal + i, yFinal + j, r, g, b);
                }
            }
        }
    }
}
