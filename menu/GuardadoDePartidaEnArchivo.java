package menu;

// Importaciones necesarias
import enums.TipoPieza;
import estructuras.lista.IteradorLista;
import estructuras.lista.ListaSimplementeEnlazada;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import jugador.Alianza;
import jugador.Jugador;
import piezas.*;
import tablero.Tablero;
import utils.ValidacionesUtils;

/**
 * La clase GuardadoDePartidaEnArchivo proporciona funcionalidades para guardar la información del estado del juego,
 * incluyendo la dimensión del tablero y los detalles de los jugadores como sus bases, naves y satélites,
 * en un archivo de texto especificado.
 * @author Patricio Alaniz
 */
public class GuardadoDePartidaEnArchivo {
  /**
   * Guarda el estado de un tablero y lista de jugadores en un archivo de texto especificado.
   *
   * @param tablero           El tablero que contiene la información del juego.
   * @param jugadores         La lista simplemente enlazada que contiene los jugadores del juego.
   * @param nombreDelArchivo  El nombre del archivo donde se guardará la partida. Debe terminar con ".txt".
   * @throws IOException      Si ocurre un error de entrada/salida al intentar escribir en el archivo.
   * @author Patricio Alaniz
   */
  public static void guardarPartida(Tablero tablero, ListaSimplementeEnlazada<Jugador> jugadores, String nombreDelArchivo, ListaSimplementeEnlazada<Alianza> alianzas, ListaSimplementeEnlazada<Pieza> piezas) throws IOException {
    ValidacionesUtils.validarNoNulo(tablero, "El tablero no puede ser nulo.");
    ValidacionesUtils.validarNoNulo(jugadores, "La lista de jugadores no puede ser nula.");
    ValidacionesUtils.validarNoNulo(nombreDelArchivo, "El nombre del archivo no puede ser nulo.");
    ValidacionesUtils.validarFinDeCadena(nombreDelArchivo, ".txt", "El nombre del archivo debe terminar con \".txt\".");

    try (PrintWriter writer = new PrintWriter(new FileWriter(nombreDelArchivo))) {

      GuardadoDePartidaEnArchivo.guardarDimensionDelTablero(tablero, writer);
      GuardadoDePartidaEnArchivo.guardarJugadores(jugadores, writer);
      GuardadoDePartidaEnArchivo.guardarAlianza(alianzas, writer);
      GuardadoDePartidaEnArchivo.guardarPiezasConRadiacion(piezas, writer);

      writer.flush();
    }
  }

  /**
   * Guarda la información de cada jugador de la lista en un archivo utilizando un {@link PrintWriter}.
   * Para cada jugador, se guardan los detalles del jugador, sus bases, naves y satélites.
   *
   * @param jugadores La lista simplemente enlazada que contiene los jugadores cuya información será escrita en el archivo.
   * @param writer El objeto PrintWriter utilizado para escribir en el archivo.
   * @author Patricio Alaniz
   */
  private static void guardarJugadores(ListaSimplementeEnlazada<Jugador> jugadores, PrintWriter writer) {
    IteradorLista<Jugador> iter = jugadores.iterador();
    while (iter.haySiguiente()) {
      Jugador jugador = iter.verActual();
      GuardadoDePartidaEnArchivo.guardarJugador(jugador, writer);
      GuardadoDePartidaEnArchivo.guardarBase(jugador, writer);
      GuardadoDePartidaEnArchivo.guardarNave(jugador, writer);
      GuardadoDePartidaEnArchivo.guardarSatelite(jugador, writer);
      iter.siguiente();
    }
  }

  /**
   * Escribe la dimensión del tablero en el archivo utilizando el objeto PrintWriter.
   *
   * @param tablero El objeto de tipo Tablero del cual se obtendrá la dimensión.
   * @param writer  El PrintWriter utilizado para escribir en el archivo.
   * @author Patricio Alaniz
   */
  private static void guardarDimensionDelTablero(Tablero tablero, PrintWriter writer) {
    writer.println("dimension:");
    writer.println(tablero.obtenerDimension());
  }

  /**
   * Escribe la información de un jugador en un archivo utilizando el objeto PrintWriter.
   *
   * @param jugador El objeto de tipo Jugador cuya información será escrita en el archivo.
   * @param writer  El PrintWriter utilizado para escribir en el archivo.
   * @author Patricio Alaniz
   */
  private static void guardarJugador(Jugador jugador, PrintWriter writer) {
    writer.println("jugador " + jugador.obtenerNombre() + ":");
  }

  /**
   * Escribe la información de las bases de un jugador en el archivo utilizando el objeto PrintWriter.
   *
   * @param jugador El objeto de tipo Jugador que contiene las bases cuya información será escrita en el archivo.
   * @param writer  El PrintWriter utilizado para escribir en el archivo.
   * @author Patricio Alaniz
   */
  private static void guardarBase(Jugador jugador, PrintWriter writer) {
    IteradorLista<Base> bases = jugador.obtenerBases().iterador();

    while (bases.haySiguiente()) {
      Base base = bases.verActual();
      writer.println("b" + " - " + base.obtenerCoordenadas().getX() + "," + base.obtenerCoordenadas().getY() + "," + base.obtenerCoordenadas().getZ() + " - " + base.obtenerVida() + " - " + base.obtenerEscudo() );
      bases.siguiente();
    }
  }

  /**
   * Escribe la información de las naves de un jugador en el archivo utilizando el objeto PrintWriter.
   *
   * @param jugador El objeto de tipo Jugador que contiene las naves cuya información será escrita en el archivo.
   * @param writer  El PrintWriter utilizado para escribir en el archivo.
   * @author Patricio Alaniz
   */
  private static void guardarNave(Jugador jugador, PrintWriter writer) {
    IteradorLista<Nave> naves = jugador.obtenerNaves().iterador();

    while (naves.haySiguiente()) {
      Nave nave = naves.verActual();
      writer.println("n" + " - "  + nave.obtenerCoordenadas().getX() + "," + nave.obtenerCoordenadas().getY() + "," + nave.obtenerCoordenadas().getZ() + " - " + nave.obtenerVida() + " - " + nave.obtenerEscudo() + " - " + nave.obtenerDanio());
      naves.siguiente();
    }
  }

  /**
   * Escribe la información de los satélites de un jugador en un archivo utilizando el objeto PrintWriter.
   *
   * @param jugador El objeto de tipo Jugador que contiene los satélites cuya información será escrita en el archivo.
   * @param writer  El PrintWriter utilizado para escribir en el archivo.
   * @author Patricio Alaniz
   */
  private static void guardarSatelite(Jugador jugador, PrintWriter writer) {
    IteradorLista<Satelite> satelites = jugador.obtenerSatelites().iterador();

    while (satelites.haySiguiente()) {
      Satelite satelite = satelites.verActual();
      writer.println("s"+ " - "  + satelite.obtenerCoordenadas().getX() + "," + satelite.obtenerCoordenadas().getY() + "," + satelite.obtenerCoordenadas().getZ() + " - " + satelite.obtenerVida() + " - " + satelite.obtenerEscudo() + " - " + satelite.obtenerRadioDeteccion());
      satelites.siguiente();
    }
  }

  private static void guardarAlianza(ListaSimplementeEnlazada<Alianza> alianzas, PrintWriter writer) {
    if (alianzas != null) {
      writer.println("alianzas:");

      IteradorLista<Alianza> iteradorAlianzas = alianzas.iterador();

      while (iteradorAlianzas.haySiguiente()) {
        Alianza alianza = iteradorAlianzas.verActual();
        Jugador[] jugadores = alianza.obtenerJugadores();

        writer.println("a" + " - " + jugadores[0].obtenerNombre() + "," + jugadores[1].obtenerNombre() + " - " + alianza.obtenerDuracion());
        iteradorAlianzas.siguiente();
      }

    }
  }

  private static void guardarPiezasConRadiacion(ListaSimplementeEnlazada<Pieza> piezas, PrintWriter writer) {
    if (piezas != null) {
      writer.println("radiacion:");

      IteradorLista<Pieza> iteradorPiezas = piezas.iterador();

      while (iteradorPiezas.haySiguiente()) {
        Pieza pieza = iteradorPiezas.verActual();

        if (pieza.obtenerTipo() == TipoPieza.RADIACION) {
          writer.println("r" + " - " + pieza.obtenerCoordenadas().getX() + "," + pieza.obtenerCoordenadas().getY() + "," + pieza.obtenerCoordenadas().getZ() + " - " + ((Radiacion)pieza).obtenerDuracion());
        }

        iteradorPiezas.siguiente();
      }
    }
  }
}