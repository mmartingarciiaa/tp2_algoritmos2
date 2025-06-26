package piezas;

// Importaciones necesarias
import Coordenada.Coordenada;
import enums.TipoPieza;

/**
 * Clase que representa una pieza de radiación en el juego Invasión Galáctica.
 * Hereda de la clase Pieza y define las características específicas de una pieza con radiación activa.
 */
public class Radiacion extends Pieza {
    private static final int ESCUDO = 0;
    private int turnosActiva;
    private boolean recienCreada;

    /**
     * Constructor de la clase Radiacion
	 * @param duenio: Jugador dueño de la pieza 
	 * @param coordenadas: Coordenadas de la pieza en el tablero no pueden ser nulas
     * @param duracion: cuantos turnos la pieza va a tener radiacion activa (debe ser mayor a cero)
     */
    public Radiacion(Coordenada coordenadas, int duracion, String nombre) {
        super(TipoPieza.RADIACION, null, coordenadas, nombre, duracion, ESCUDO);
        this.turnosActiva = duracion;
        this.recienCreada = true;
    }


    /**
     * Reduce los turnos en los que la pieza tiene radiación activa menos en el primer 
     * turno en la que se genera
     * 
     */
    public void reducirDuracion() {
        // No se reduce la duración en el primer turno
        if (!recienCreada) {
            this.turnosActiva--;
        } else {
            this.recienCreada = false;
        }
    }

    /**
     * Obtiene la duración restante de la radiación activa en la pieza.
     */
    public int obtenerDuracion() {
        return this.turnosActiva;
    }
    
    /**
     *  Devuelve true si la radiacion sigue activa y false si no
     */
    public boolean estaActiva() {
        return this.turnosActiva > 0;
    }
}
