package piezas;

// Importaciones necesarias
import Coordenada.Coordenada;
import enums.TipoPieza;

/**
 * Clase que representa una pieza de radiación en el juego Invasión Galáctica.
 * Hereda de la clase Pieza y define las características específicas de una pieza con radiación activa.
 */
public class Radiacion extends Pieza {
    private static final int VIDA_POR_DEFECTO = 0;
    private int turnosActiva;
    private boolean recienCreada;

    /**
     * Constructor de la clase Radiacion
     * @param duenio: dueño de la pieza con radiacion activa
     * @param coordenadas: coordenadas de la pieza con radiacion activa
     * @param duracion: cuantos turnos la pieza va a tener radiacion activa
     */
    public Radiacion(Coordenada coordenadas, int duracion, String nombre) {
        super(TipoPieza.RADIACION, null, coordenadas, nombre, VIDA_POR_DEFECTO, duracion);
        this.turnosActiva = duracion;
        if (!creacionValida()) {
            throw new RuntimeException("Creación de pieza Radiación inválida: " + 
                    "Dueño: null, " +
                    "Nombre: " + nombre + ", " +
                    "Vida: " + VIDA_POR_DEFECTO + ", " +
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
     * Reduce los turnos en los que la pieza tiene radiación activa
     * 
     */
    public void reducirDuracion() {
        if (recienCreada) {
            recienCreada = false;
            return; // No se reduce la duración en el primer turno
        }
        this.turnosActiva--;
    }
	
    /**
     * Obtiene la duración restante de la radiación activa en la pieza.
     */
    public int obtenerDuracion() {
        return turnosActiva;
    }
    
    /**
     * 
     * @return: devuelve true si la pieza tiene radiación activa
     */
    public boolean estaActiva() {
        return turnosActiva >= 0;
    }
}
