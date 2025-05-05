/**
 * Clase que representa una nave en el juego Invasión Galáctica.
 * Hereda de la clase Pieza.
 * 
 * Las naves pueden moverse, atacar y verse afectadas por cartas como 
 * "Doble Salto Hiperespacial". Esta clase gestiona su estado de ataque 
 * y habilidades especiales temporales.
 */
public class Nave extends Pieza {
    private static int ESCUDO_INICIAL = 0;
    private int escudo;
    private int danio;

    /**
     * Constructor de la nave 
     * @param tipo          Tipo de pieza
     * @param duenio        Jugador propietario de la nave
     * @param x             Coordenada X
     * @param y             Coordenada Y
     * @param z             Coordenada Z
     * @param nombre        Representación de la nave en el tablero
     * @param vida          Vida inicial de la nave
     * @param danioInicial  Poder de ataque inicial de la nave 
     */
    public Nave(TipoPieza tipo, Jugador duenio, int x, int y, int z, String nombre, int vida, int danioInicial) {
        super(tipo, duenio, x, y, z, nombre, vida);
        this.escudo = ESCUDO_INICIAL;
        this.danio = danioInicial;
    }

    /**
     * Aumenta el escudo de la nave
     * @param cantidad puntos de escudo a añadir
     */
    public void aumentarEscudo(int cantidad) {
        this.escudo += cantidad;
    }

    /**
     * Reduce el escudo de la nave, si el escudo pasa a negativo,
     * el exceso se aplica a la vida
     * @param cantidad daño recibido
     */
    public void reducirEscudo(int cantidad) {
        escudo -= cantidad;
        if (escudo < 0) {
            super.reducirVida(Math.abs(escudo));
            escudo = 0;
        }
    }

    /** Obtiene el daño 
     * @return el daño que hace la nave al atacar
     */
    public int obtenerDanio() {
        return danio;
    }

    /**
     * Establece un nuevo valor del daño
     * @param nuevoDanio nuevo poder de ataque
     */
    public void setDanio(int nuevoDanio) {
        this.danio = nuevoDanio;
    }

    /**
     * Incrementa el daño de la nave 
     * @param puntos extras de ataque 
     */
    public void aumentarDanio(int extra) {
        this.danio += extra;
    }

    /**
     * Hace el ataque y devuelve el daño hecho
     * @return puntos de daño inflijido
     */
    public int atacar() {
        return danio;
    }


}
   