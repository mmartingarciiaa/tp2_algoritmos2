package tablero;

// Importaciones necesarias
import piezas.Pieza;

// Class Sector es la clase en la cual se almacena la información de cada sector
// del tablero de juego, sea nave o base, y su escudo

public class Sector {
    private final int[] coordenadas;
    private Pieza valor;

    /**
     * Constructor de la clase Sector.
     * Inicializa un sector con coordenadas y un valor.
     *
     * @param x     coordenada x del sector
     * @param y     coordenada y del sector
     * @param z     coordenada z del sector
     * @param valor valor del sector (puede ser una nave, base, etc.)
     * 
     * @throws RuntimeException si el valor del sector es nulo
     */
    public Sector(int x, int y, int z, Pieza valor) {
        if (valor == null) {
            throw new RuntimeException("El valor del sector no puede ser nulo");
        }
        this.coordenadas = new int[] {x, y, z};
        this.valor = valor;
    }

    // Obtiene el valor del sector
    public Pieza obtenerValor() {
        return valor;
    }

    // Asigna un valor al sector
    public void asignarValor(Pieza valor) {
        this.valor = valor;
    }

    // El orden de los valores del array es: [x, y, z]
    public int[] obtenerCoordenadas() {
        return coordenadas;
    }
}