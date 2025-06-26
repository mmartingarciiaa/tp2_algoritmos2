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
    private static final String NOMBRE = "_";
    private static final int VIDA = 1;
    private static final int ESCUDO = 0;

    /**
     * Construye un sector Vacio en las coordenadas dadas
     * @param coordenadas Coordenadas del sector vacío no pueden ser vacias
     */
    public Vacio(Coordenada coordenadas) {
        super(TipoPieza.VACIO, null, coordenadas, NOMBRE, VIDA, ESCUDO);
    }
}