package piezas;

// Importaciones necesarias
import Coordenada.Coordenada;
import enums.TipoPieza;

/**
 * Clase que representa un sector vacío en el tablero
 * Hereda de la Pieza y se utiliza para representar espacios
 * donde no hay naves, bases, satélites ni radiación
*/

public class Vacio extends Pieza {
    private static final String NOMBRE_POR_DEFECTO = "_";
    private static final int VIDA_POR_DEFECTO = 0;

    /**
     * Construye un sector Vacio en las coordenadas dadas
     * @param coordenadas Coordenadas del sector vacío
     */
    public Vacio(Coordenada coordenadas) {
        super(TipoPieza.VACIO, null, coordenadas, NOMBRE_POR_DEFECTO, VIDA_POR_DEFECTO, 0);
        if (!(this.obtenerCoordenadas() != null && 
                this.obtenerCoordenadas() != null)) {
            throw new RuntimeException("Creación de pieza Vacio inválida: " + 
                    "Nombre: " + NOMBRE_POR_DEFECTO + ", " +
                    "Vida: " + VIDA_POR_DEFECTO + ", " +
                    "Coordenadas: [" + coordenadas.getX() + ", " + coordenadas.getY() + ", " + coordenadas.getZ() + "]");
        }
    }
}