package piezas;

// Importaciones necesarias
import enums.TipoPieza;

public class Radiacion extends Pieza {
    private static final int VIDA_POR_DEFECTO = 0;
    private static final String NOMBRE_POR_DEFECTO = "R";
    private int turnosActiva;
    private boolean recienCreada;

    /**
     * Constructor de la clase Radiacion
     * @param duenio: dueño de la pieza con radiacion activa
     * @param x: coordenada x de la pieza con radiacion activa
     * @param y: coordenada y de la pieza con radiacion activa
     * @param z: coordenada z de la pieza con radiacion activa
     * @param duracion: cuantos turnos la pieza va a tener radiacion activa
     */
    public Radiacion(int x, int y, int z, int duracion) {
		super(TipoPieza.RADIACION, null, x, y, z, NOMBRE_POR_DEFECTO, VIDA_POR_DEFECTO, duracion);
        this.turnosActiva = duracion;
        this.recienCreada = true;
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
        turnosActiva--;
    }
	
    public int obtenerDuracion() {
        return turnosActiva;
    }
    
    /**
     * 
     * @return: devuelve true si la pieza tiene radiación activa
     */
    public boolean estaActiva() {
        return turnosActiva > 0;
    }
    
}
