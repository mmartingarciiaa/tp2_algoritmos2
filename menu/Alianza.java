package menu;

// Importaciones necesarias

public class Alianza {
    private final Jugador[] jugadores;
    private int duracion = 5;

    public Alianza(Jugador jugador1, Jugador jugador2) throws IllegalArgumentException {
        if (jugador1 == null || jugador2 == null) {
            throw new IllegalArgumentException("Los jugadores no pueden ser nulos");
        }
        if (jugador1.equals(jugador2)) {
            throw new IllegalArgumentException("Los jugadores no pueden ser el mismo");
        }
        this.jugadores = new Jugador[]{jugador1, jugador2};
    }
    
    public Jugador[] obtenerJugadores() {
        return jugadores;
    }

    public Jugador obtenerOtroJugador(Jugador jugador) throws IllegalArgumentException {
        if (jugador == null) {
            throw new IllegalArgumentException("El jugador no puede ser nulo");
        }
        if (jugador.equals(jugadores[0])) {
            return jugadores[1];
        } else if (jugador.equals(jugadores[1])) {
            return jugadores[0];
        } else {
            throw new IllegalArgumentException("El jugador no pertenece a esta alianza");
        }
    }

    public boolean contieneJugador(Jugador jugador) {
        if (jugador == null) {
            return false;
        }
        return jugador.equals(jugadores[0]) || jugador.equals(jugadores[1]);
    }

    public void reducirDuracion() {
        if (duracion > 0) {
            duracion--;
        }
    }

    public int obtenerDuracion() {
        return duracion;
    }

    public boolean estaActiva() {
        return duracion > 0;
    }
}
