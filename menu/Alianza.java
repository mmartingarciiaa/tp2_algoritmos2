package menu;

// Importaciones necesarias
import estructuras.lista.ListaEnlazada;

public class Alianza {
    private final ListaEnlazada<Jugador> jugadores;

    public Alianza(Jugador jugador1, Jugador jugador2) {
        this.jugadores = new ListaEnlazada<>();
        this.jugadores.insertarUltimo(jugador1);
        this.jugadores.insertarUltimo(jugador2);
    }
    
    public ListaEnlazada<Jugador> getJugadores() {
        return jugadores;
    }  

}
