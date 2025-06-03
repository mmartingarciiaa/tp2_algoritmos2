package menu;

// Importaciones necesarias
import estructuras.lista.ListaSimplementeEnlazada;

public class Alianza {
    private final ListaSimplementeEnlazada<Jugador> jugadores;
    private int duracion = 5;

    public Alianza(Jugador jugador1, Jugador jugador2) {
        this.jugadores = new ListaSimplementeEnlazada<>();
        this.jugadores.insertarUltimo(jugador1);
        this.jugadores.insertarUltimo(jugador2);
    }
    
    public ListaSimplementeEnlazada<Jugador> getJugadores() {
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
