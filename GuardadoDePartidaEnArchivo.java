import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import tdas.lista.IteradorLista;
import tdas.lista.ListaEnlazada;

public class GuardadoDePartidaEnArchivo {
  public static void main(String[] args) {
    try {
      Tablero tablero = new Tablero(10);
      ListaEnlazada<Jugador> jugadores = new ListaEnlazada<>();

      for (int i = 1; i <= 3; i++) {
        Jugador jugador = new Jugador("Jugador" + i);

        for (int b = 0; b < 2; b++) {
          Base base = new Base(jugador, i * 2, b * 3, i+b,"B",100);
          jugador.agregarBase(base);
        }

        for (int n = 0; n < 3; n++) {
          Nave nave = new Nave(jugador, i * 2, n * 3, i + n, "N", 100, 20);
          jugador.agregarNave(nave);
        }

        for (int s = 0; s < 2; s++) {
          Satelite satelite = new Satelite(jugador, i * 2, s * 3, i + s, "S", 100, 20);
          jugador.agregarSatelite(satelite);
        }

        jugadores.insertarUltimo(jugador);
      }

      guardarPartida(tablero, jugadores);

    } catch (IOException e) {
      System.out.println("Error guardando juego: " + e.getMessage());
    }
  }

  public static void guardarPartida(Tablero tablero, ListaEnlazada<Jugador> jugadores) throws IOException {
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