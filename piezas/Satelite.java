package piezas;

// Importaciones necesarias
import enums.TipoPieza;
import jugador.Jugador;
import utils.ValidacionesUtils;

/**
 * Clase que representa un satélite espía en el juego Invasión Galáctica
 * Hereda de la clase Pieza y define las características específicas de un satélite.
 * Un satélite puede detectar naves, bases y satélites enemigos dentro de un radio de detección específico.
 */

public class Satelite extends Pieza {
    private final int radioDeteccion;

    /**
     * Constructor  del Satélite
     * @param duenio   Jugador propietario del satélite
     * @param x        Coordenada X
     * @param y        Coordenada Y
     * @param z        Coordenada Z
     * @param nombre   Representación del satélite en el tablero
     * @param vida     Vida inicial del satélite
     * @param radioDeteccion Radio de detección del satélite
     *    
     */
    public Satelite(Jugador duenio, int x, int y, int z, String nombre, int vida, int radioDeteccion) {
        super(TipoPieza.SATELITE, duenio, x, y, z, nombre, vida, 0);
        if (!creacionValida()) {
            throw new RuntimeException("Creación de pieza Satélite inválida: " + 
                    "Dueño: " + duenio + ", " +
                    "Nombre: " + nombre + ", " +
                    "Vida: " + vida + ", " +
                    "Coordenadas: [" + x + ", " + y + ", " + z + "]");
        }
        ValidacionesUtils.validarMayorACero(radioDeteccion, "Radio de detección");
        this.radioDeteccion = radioDeteccion;
    }

    /**
     * Obtiene el radio de detección del satélite
     * @return El radio de detección como un entero
     */
    public int obtenerRadioDeteccion() {
        return radioDeteccion;
    }
}