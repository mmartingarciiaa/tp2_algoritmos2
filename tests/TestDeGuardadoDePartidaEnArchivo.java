package tests;

import estructuras.lista.ListaSimplementeEnlazada;
import jugador.Alianza;
import jugador.Jugador;
import menu.GuardadoDePartidaEnArchivo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import piezas.Base;
import piezas.Nave;
import piezas.Satelite;
import tablero.Tablero;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestDeGuardadoDePartidaEnArchivo {

  @Test
  public void testGuardarPartidaArchivoInvalido() {
    Tablero tablero = new Tablero(10);
    ListaSimplementeEnlazada<Jugador> jugadores = new ListaSimplementeEnlazada<>();

    assertThrows(RuntimeException.class, () ->
            GuardadoDePartidaEnArchivo.guardarPartida(tablero, jugadores, "archivo", null, null)
    );
  }

  @Test
  public void testGuardarPartidaVacia(@TempDir Path tempDir) throws IOException {
    Tablero tablero = new Tablero(10);
    ListaSimplementeEnlazada<Jugador> jugadores = new ListaSimplementeEnlazada<>();
    String archivo = tempDir.resolve("partida.txt").toString();

    GuardadoDePartidaEnArchivo.guardarPartida(tablero, jugadores, archivo, null, null);

    List<String> lineas = Files.readAllLines(Path.of(archivo));
    assertEquals("dimension:", lineas.get(0));
    assertEquals("10", lineas.get(1));
  }

  @Test
  public void testGuardarPartidaCompleta(@TempDir Path tempDir) throws IOException {
    Tablero tablero = new Tablero(10);
    ListaSimplementeEnlazada<Jugador> jugadores = new ListaSimplementeEnlazada<>();

    Jugador jugador = new Jugador("test");
    jugador.agregarBase(new Base(jugador, 1,1,1,"b", 100, 50));
    jugador.agregarNave(new Nave(jugador, 2, 2, 2, "n", 80, 30));
    jugador.agregarSatelite(new Satelite(jugador, 3,3,3, "s", 60, 40));

    jugadores.insertarUltimo(jugador);

    String archivo = tempDir.resolve("partida.txt").toString();

    GuardadoDePartidaEnArchivo.guardarPartida(tablero, jugadores, archivo, null, null);

    List<String> lineas = Files.readAllLines(Path.of(archivo));
    assertTrue(lineas.size() > 2);
    assertEquals("dimension:", lineas.get(0));
    assertEquals("10", lineas.get(1));
    assertEquals("jugador test:", lineas.get(2));
    assertEquals("b - 1,1,1 - 100 - 50", lineas.get(3));
    assertEquals("n - 2,2,2 - 80 - 0 - 30", lineas.get(4));
    assertEquals("s - 3,3,3 - 60 - 0 - 40", lineas.get(5));
  }

  @Test
  public void testGuardarPartidaConAlianza(@TempDir Path tempDir) throws IOException {
    Tablero tablero = new Tablero(10);
    ListaSimplementeEnlazada<Jugador> jugadores = new ListaSimplementeEnlazada<>();
    String archivo = tempDir.resolve("partida.txt").toString();

    Jugador jugador = new Jugador("jugador1");
    jugador.agregarBase(new Base(jugador, 1,1,1,"b", 100, 50));
    jugador.agregarNave(new Nave(jugador, 2, 2, 2, "n", 80, 30));
    jugador.agregarSatelite(new Satelite(jugador, 3,3,3, "s", 60, 40));

    jugadores.insertarUltimo(jugador);

    Jugador jugador2 = new Jugador("jugador2");
    jugador2.agregarBase(new Base(jugador2, 4,4,4,"b", 100, 50));
    jugador2.agregarNave(new Nave(jugador2, 5, 5, 5, "n", 80, 30));
    jugador2.agregarSatelite(new Satelite(jugador2, 6,6,6, "s", 60, 40));

    jugadores.insertarUltimo(jugador2);

    Jugador jugador3 = new Jugador("jugador3");
    jugador3.agregarBase(new Base(jugador2, 7,7,7,"b", 100, 50));
    jugador3.agregarNave(new Nave(jugador2, 8, 8, 8, "n", 80, 30));
    jugador3.agregarSatelite(new Satelite(jugador2, 9,9,9, "s", 60, 40));

    jugadores.insertarUltimo(jugador3);

    Alianza alianza = new Alianza(jugador, jugador2, 5);
    ListaSimplementeEnlazada<Alianza> alianzas = new ListaSimplementeEnlazada<>();

    alianzas.insertarUltimo(alianza);

    GuardadoDePartidaEnArchivo.guardarPartida(tablero, jugadores, archivo, alianzas, null);

    List<String> lineas = Files.readAllLines(Path.of(archivo));

    assertTrue(lineas.size() > 2);
    assertEquals("dimension:", lineas.get(0));
    assertEquals("10", lineas.get(1));
    assertEquals("jugador jugador1:", lineas.get(2));
    assertEquals("b - 1,1,1 - 100 - 50", lineas.get(3));
    assertEquals("n - 2,2,2 - 80 - 0 - 30", lineas.get(4));
    assertEquals("s - 3,3,3 - 60 - 0 - 40", lineas.get(5));
    assertEquals("jugador jugador2:", lineas.get(6));
    assertEquals("b - 4,4,4 - 100 - 50", lineas.get(7));
    assertEquals("n - 5,5,5 - 80 - 0 - 30", lineas.get(8));
    assertEquals("s - 6,6,6 - 60 - 0 - 40", lineas.get(9));
    assertEquals("jugador jugador3:", lineas.get(10));
    assertEquals("b - 7,7,7 - 100 - 50", lineas.get(11));
    assertEquals("n - 8,8,8 - 80 - 0 - 30", lineas.get(12));
    assertEquals("s - 9,9,9 - 60 - 0 - 40", lineas.get(13));
    assertEquals("alianzas:", lineas.get(14));
    assertEquals("a - jugador1,jugador2 - 5", lineas.get(15));
  }
}
