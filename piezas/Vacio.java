package piezas;

// Importaciones necesarias
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
     * @param x Coordenada X
     * @param y Coordenada Y
     * @param z Coordenada Z
     */
    public Vacio(int x, int y, int z) {
        super(TipoPieza.VACIO, null, x,y,z, NOMBRE_POR_DEFECTO, VIDA_POR_DEFECTO, 0);
        if (!creacionValida()) {
            throw new RuntimeException("Creación de pieza Vacio inválida: " + 
                    "Nombre: " + NOMBRE_POR_DEFECTO + ", " +
                    "Vida: " + VIDA_POR_DEFECTO + ", " +
                    "Coordenadas: [" + x + ", " + y + ", " + z + "]");
        }
    }

    @Override
    protected boolean creacionValida() {
        return this.obtenerCoordenadas() != null && 
                this.obtenerCoordenadas().length == 3;
    }
}