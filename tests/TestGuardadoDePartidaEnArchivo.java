package tests;

import estructuras.lista.ListaSimplementeEnlazada;
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

public class TestGuardadoDePartidaEnArchivo {

  @Test
  public void testGuardarPartidaArchivoInvalido() {
    Tablero tablero = new Tablero(10);
    ListaSimplementeEnlazada<Jugador> jugadores = new ListaSimplementeEnlazada<>();

    assertThrows(RuntimeException.class, () ->
            GuardadoDePartidaEnArchivo.guardarPartida(tablero, jugadores, "archivo")
    );
  }

  @Test
  public void testGuardarPartidaVacia(@TempDir Path tempDir) throws IOException {
    Tablero tablero = new Tablero(10);
    ListaSimplementeEnlazada<Jugador> jugadores = new ListaSimplementeEnlazada<>();
    String archivo = tempDir.resolve("partida.txt").toString();

    GuardadoDePartidaEnArchivo.guardarPartida(tablero, jugadores, archivo);

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

    GuardadoDePartidaEnArchivo.guardarPartida(tablero, jugadores, archivo);

    List<String> lineas = Files.readAllLines(Path.of(archivo));
    assertTrue(lineas.size() > 2);
    assertEquals("dimension:", lineas.get(0));
    assertEquals("10", lineas.get(1));
    assertEquals("jugador test:", lineas.get(2));
    assertEquals("b - 1,1,1 - 100 - 50", lineas.get(3));
    assertEquals("n - 2,2,2 - 80 - 0 - 30", lineas.get(4));
    assertEquals("s - 3,3,3 - 60 - 0 - 40", lineas.get(5));
  }
}
