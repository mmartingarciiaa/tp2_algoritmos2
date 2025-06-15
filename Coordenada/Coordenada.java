package Coordenada;

/**
 * Clase Coordenada representa una coordenada tridimensional en un tablero de juego.
 * Cada coordenada tiene tres componentes: x, y, z.
 */

public class Coordenada {
    private final int x;
    private final int y;
    private final int z;

    /**
     * Constructor de la clase Coordenada.
     * 
     * @param x Componente x de la coordenada
     * @param y Componente y de la coordenada
     * @param z Componente z de la coordenada
     */
    public Coordenada(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Obtiene la representación en cadena de la coordenada.
     * 
     * @return Cadena con las coordenadas en formato "x, y, z"
     */
    public int getX() {
        return x;
    }

    /**
     * Obtiene la componente y de la coordenada.
     * 
     * @return Componente y de la coordenada
     */
    public int getY() {
        return y;
    }

    /**
     * Obtiene la componente z de la coordenada.
     * 
     * @return Componente z de la coordenada
     */
    public int getZ() {
        return z;
    }

    /**
     * Compara esta coordenada con otra para verificar si son iguales.
     * Dos coordenadas son iguales si sus componentes x, y, z son iguales.
     * 
     * @param obj Objeto a comparar con esta coordenada
     * @return true si las coordenadas son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Coordenada)) return false;
        Coordenada other = (Coordenada) obj;
        return x == other.x && y == other.y && z == other.z;
    }

    /**
     * Obtiene el código hash de la coordenada.
     * El código hash se calcula utilizando las componentes x, y, z.
     * 
     * @return Código hash de la coordenada
     */
    @Override
    public int hashCode() {
        int result = Integer.hashCode(x);
        result = 31 * result + Integer.hashCode(y);
        result = 31 * result + Integer.hashCode(z);
        return result;
    }
}