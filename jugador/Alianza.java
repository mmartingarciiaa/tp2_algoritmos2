package jugador;

// Importaciones necesarias

/*
 * Clase que representa una alianza entre dos jugadores.
 * Esta clase permite crear una alianza, obtener los jugadores que la componen,
 * verificar si un jugador pertenece a la alianza, obtener el otro jugador de la alianza,
 * reducir la duración de la alianza y verificar si está activa.
 */

public class Alianza {
    private final Jugador[] jugadores;
    private int duracion;

    /**
     * Constructor de la clase Alianza.
     * Crea una alianza entre dos jugadores.
     *
     * @param jugador1 El primer jugador de la alianza.
     * @param jugador2 El segundo jugador de la alianza.
     * @throws IllegalArgumentException Si alguno de los jugadores es nulo o si son el mismo jugador.
     */
    public Alianza(Jugador jugador1, Jugador jugador2, int duracion) {
        if (jugador1 == null || jugador2 == null) {
            throw new IllegalArgumentException("Los jugadores no pueden ser nulos");
        }
        if (jugador1.equals(jugador2)) {
            throw new IllegalArgumentException("Los jugadores no pueden ser el mismo");
        }
        if (duracion <= 0) {
            throw new IllegalArgumentException("La duración de la alianza debe ser mayor a cero");
        }
        this.jugadores = new Jugador[]{jugador1, jugador2};
        this.duracion = duracion;
    }

    /**
     * Obtiene los jugadores que componen la alianza.
     * 
     * @return Un arreglo con los dos jugadores de la alianza si está activa.
     *         Si la alianza no está activa, retorna null.
     */
    public Jugador[] obtenerJugadores() {
        if (this.estaActiva()) {
            return jugadores;
        }
        return null;
    }

    /**
     * Obtiene el otro jugador de la alianza dado un jugador.
     * 
     * @param jugador El jugador del cual se desea obtener el otro miembro de la alianza.
     * @return El otro jugador de la alianza si el jugador dado es parte de ella.
     * @throws IllegalArgumentException Si el jugador dado es nulo o si el jugador dado no pertenece a esta alianza.
     */
    public Jugador obtenerOtroJugador(Jugador jugador) {
        if (jugador == null) {
            throw new IllegalArgumentException("El jugador no puede ser nulo");
        }

        if (!jugador.equals(jugadores[0]) && !jugador.equals(jugadores[1])) {
            throw new IllegalArgumentException("El jugador no pertenece a esta alianza");
        }

        return jugador.equals(jugadores[0]) ? jugadores[1] : jugadores[0];
    }

    /**
     * Verifica si la alianza contiene a un jugador específico.
     * 
     * @param jugador El jugador a verificar.
     * @return true si el jugador está en la alianza, false en caso contrario.
     */
    public boolean contieneJugador(Jugador jugador) {
        if (jugador == null) {
            return false;
        }
        return jugador.equals(jugadores[0]) || jugador.equals(jugadores[1]);
    }

    /**
     * Reduce la duración de la alianza.
     */
    public void reducirDuracion() {
        if (duracion > 0) {
            duracion--;
        }
    }

    /**
     * Obtiene la duración restante de la alianza.
     * 
     * @return La duración restante de la alianza.
     */
    public int obtenerDuracion() {
        return duracion;
    }

    /**
     * Verifica si la alianza está activa.
     * Una alianza se considera activa si su duración es mayor a 0.
     */
    public boolean estaActiva() {
        return duracion > 0;
    }

    /**
     * Desarma la alianza si no está activa.
     */
    public void desarmarAlianza() {
        if (!this.estaActiva()) {
            this.jugadores[0] = null;
            this.jugadores[1] = null;
        }
    }
}
