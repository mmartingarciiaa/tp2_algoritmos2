package tablero;

// Importaciones necesarias
import piezas.Pieza;

// Class Sector es la clase en la cual se almacena la información de cada sector
// del tablero de juego, sea nave o base, y su escudo

public class Sector {
    private final int[] coordenadas;
    private Pieza valor;

    public Sector(int x, int y, int z, Pieza valor) {
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