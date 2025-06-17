package piezas;

// Importaciones necesarias
import Coordenada.Coordenada;
import enums.TipoPieza;

/**
 * Clase que representa una pieza de radiación en el juego Invasión Galáctica.
 * Hereda de la clase Pieza y define las características específicas de una pieza con radiación activa.
 */
public class Radiacion extends Pieza {
    private static final int VIDA = 1;
    private int turnosActiva;
    private boolean recienCreada;

    /**
     * Constructor de la clase Radiacion
     * @param duenio: dueño de la pieza con radiacion activa
     * @param coordenadas: coordenadas de la pieza con radiacion activa
     * @param duracion: cuantos turnos la pieza va a tener radiacion activa
     */
    public Radiacion(Coordenada coordenadas, int duracion, String nombre) {
        super(TipoPieza.RADIACION, null, coordenadas, nombre, VIDA, duracion);
        this.turnosActiva = duracion;
        if (!creacionValida()) {
            throw new RuntimeException("Creación de pieza Radiación inválida: " + 
                    "Dueño: null, " +
                    "Nombre: " + nombre + ", " +
                    "Vida: " + VIDA + ", " +
                    "Coordenadas: [" + coordenadas.getX() + ", " + coordenadas.getY() + ", " + coordenadas.getZ() + "]");
        }
        this.recienCreada = true;
    }

    @Override
    protected boolean creacionValida() {
        return this.obtenerNombre() != null &&
                !this.obtenerNombre().isEmpty() &&
                turnosActiva > 0 &&
                this.obtenerCoordenadas() != null && this.obtenerCoordenadas() != null;
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
        return this.turnosActiva >= 0;
    }
}
