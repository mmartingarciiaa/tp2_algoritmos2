package piezas;

// Importaciones necesarias
import Coordenada.Coordenada;
import enums.TipoPieza;
import jugador.Jugador;
import utils.ValidacionesUtils;

/**
 * Clase que representa un satélite espía en el juego Invasión Galáctica
 * Hereda de la clase Pieza y define las características específicas de un satélite.
 * Un satélite puede detectar naves, bases y satélites enemigos dentro de un radio de detección específico.
 */

public class Satelite extends Pieza {
    private static final int ESCUDO = 0;
    private final int radioDeteccion;

    /**
     * Constructor  del Satélite
	 * @param duenio: Jugador dueño de la pieza 
	 * @param coordenadas: Coordenadas de la pieza en el tablero no pueden ser nulas
	 * @param nombre: Nombre de la pieza no puede ser nulo y debe tener al menos 1 caracter
	 * @param vida: Vida inicial de la pieza (debe ser mayor a cero)
     * @param radioDeteccion Radio de detección del satélite debe ser mayor a 0
     *    
     */
    public Satelite(Jugador duenio, Coordenada coordenadas, String nombre, int vida, int radioDeteccion) {
        super(TipoPieza.SATELITE, duenio, coordenadas, nombre, vida, ESCUDO);
        ValidacionesUtils.validarMayorACero(radioDeteccion, "Radio de detección");
        this.radioDeteccion = radioDeteccion;
    }

    /**
     * Obtiene el radio de detección del satélite
     */
    public int obtenerRadioDeteccion() {
        return this.radioDeteccion;
    }
}