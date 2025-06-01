package menu;

// Importaciones necesarias
import estructuras.lista.ListaEnlazada;

public class Alianza {
    private final ListaEnlazada<Jugador> jugadores;
    private int duracion = 5;

    public Alianza(Jugador jugador1, Jugador jugador2) {
        this.jugadores = new ListaEnlazada<>();
        this.jugadores.insertarUltimo(jugador1);
        this.jugadores.insertarUltimo(jugador2);
    }
    
    public ListaEnlazada<Jugador> getJugadores() {
        return jugadores;
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
