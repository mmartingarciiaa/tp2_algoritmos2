package menu;

// Importaciones necesarias
import estructuras.lista.IteradorLista;
import estructuras.lista.ListaSimplementeEnlazada;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import piezas.Base;
import piezas.Nave;
import piezas.Satelite;
import tablero.Tablero;

public class GuardadoDePartidaEnArchivo {
  
  public static void guardarPartida(Tablero tablero, ListaSimplementeEnlazada<Jugador> jugadores) throws IOException {
    try (PrintWriter writer = new PrintWriter(new FileWriter("juego.txt"))) {
      writer.println("dimension:");
      writer.println(tablero.obtenerDimension());
      IteradorLista<Jugador> iter = jugadores.iterador();

      while (iter.haySiguiente()) {
        Jugador jugador = iter.verActual();
        writer.println("jugador " + jugador.obtenerNombre() + ":");

        IteradorLista<Base> bases = jugador.obtenerBases().iterador();

        while (bases.haySiguiente()) {
          Base base = bases.verActual();
          writer.println("b" + " - " + base.obtenerCoordenadas()[0] + "," + base.obtenerCoordenadas()[1] + "," + base.obtenerCoordenadas()[2] + " - " + base.obtenerVida() + " - " + base.obtenerEscudo() );
          bases.siguiente();
        }

        IteradorLista<Nave> naves = jugador.obtenerNaves().iterador();

        while (naves.haySiguiente()) {
          Nave nave = naves.verActual();
          writer.println("n" + " - "  + nave.obtenerCoordenadas()[0] + "," + nave.obtenerCoordenadas()[1] + "," + nave.obtenerCoordenadas()[2] + " - " + nave.obtenerVida() + " - " + nave.obtenerEscudo());
          naves.siguiente();
        }

        IteradorLista<Satelite> satelites = jugador.obtenerSatelites().iterador();

        while (satelites.haySiguiente()) {
          Satelite satelite = satelites.verActual();
          writer.println("s"+ " - "  + satelite.obtenerCoordenadas()[0] + "," + satelite.obtenerCoordenadas()[1] + "," + satelite.obtenerCoordenadas()[2] + " - " + satelite.obtenerVida() + " - " + satelite.obtenerEscudo() + " - " + satelite.obtenerRadioDeteccion());
          satelites.siguiente();
        }

        iter.siguiente();
      }
    }
  }

}