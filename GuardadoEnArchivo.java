import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import tdas.lista.ListaEnlazada;

public class GuardadoEnArchivo {
  public static void main(String[] args) {
    try {
      Tablero tablero = new Tablero(10);
      ListaEnlazada<Jugador> jugadores = new ListaEnlazada<>();

      for (int i = 1; i <= 3; i++) {
        Jugador jugador = new Jugador("Jugador" + i);

        for (int b = 0; b < 2; b++) {
          Base base = new Base(TipoPieza.BASE, jugador, i * 2, b * 3, i + b, "B" + i + b, 100);
          jugador.agregarBase(base);
        }

        for (int n = 0; n < 3; n++) {
          Nave nave = new Nave(TipoPieza.NAVE, jugador, i * 2, n * 3, i + n, "N" + i + n, 100, 20);
          jugador.agregarNave(nave);
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
      writer.println("dimensiones:");
      writer.println(tablero.obtenerDimension() + "," + tablero.obtenerDimension() + "," + tablero.obtenerDimension());

      jugadores.iterar(jugador -> {
        writer.println("\njugador " + jugador.obtenerNombre() + ":");

        writer.println("bases:");


        jugador.obtenerBases().iterar(base -> {
          writer.println("b" + base.obtenerDuenio().obtenerNombre() + ": " +
                  base.obtenerCoordenadas()[0] + "," +
                  base.obtenerCoordenadas()[1] + "," +
                  base.obtenerCoordenadas()[2] + " - " +
                  base.obtenerVida());// + " - " +
//                  base.obtenerEscudo());
          return true;
        });

        writer.println("naves:");
        jugador.obtenerNaves().iterar(nave -> {
          writer.println("n" + nave.obtenerCoordenadas()[0] + ": " +
                  nave.obtenerCoordenadas()[0] + "," +
                  nave.obtenerCoordenadas()[1] + "," +
                  nave.obtenerCoordenadas()[2] + " - " +
                  nave.obtenerVida());// + " - " +
//                  nave.obtenerEscudo());
          return true;
        });

        writer.println("satélites:");
        /*jugador.obtenerSatelites().iterar(satelite -> {
          writer.println("s" + satelite.obtenerCoordenadas()[0] + ": " +
                  satelite.obtenerCoordenadas()[0] + "," +
                  satelite.obtenerCoordenadas()[1] + "," +
                  satelite.obtenerCoordenadas()[2] + " - " +
                  satelite.obtenerVida() + " - " +
                  satelite.obtenerEscudo());
          return true;
        });*/
        return true;
      });
    }
  }

}