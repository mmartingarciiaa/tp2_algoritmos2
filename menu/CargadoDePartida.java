package menu;

import estructuras.cola.ColaEnlazada;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import piezas.*;
import tablero.Tablero;

public class CargadoDePartida {
    public static Tablero cargarPartida(String ruta, ColaEnlazada<Jugador> jugadores) throws Exception {
        Tablero tablero = null;
        String nombre = "";
        Jugador jugadorActual = null;
        try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) {
                    continue; // Ignorar líneas vacías
                }
                if (linea.equals("dimension:")) {
                    int dimension = Integer.parseInt(lector.readLine().trim());
                    tablero = new Tablero(dimension);
                    tablero.inicializarTablero();
                } else if (linea.startsWith("jugador")) {
                    String[] partes = linea.split(" ");
                    nombre = partes[1].replace(":", "");
                    jugadorActual = new Jugador(nombre);
                    jugadores.encolar(jugadorActual);
                } else {
                    parsearPieza(linea, jugadorActual, tablero);
                }
            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error al leer el archivo: " + e.getMessage());
        }
        return tablero;
    }

    private static void parsearPieza(String linea, Jugador jugador, Tablero tablero) {
        String[] partes = linea.split(" - ");
        String tipo = partes[0];
        String[] coords = partes[1].split(",");
        int x = Integer.parseInt(coords[0]);
        int y = Integer.parseInt(coords[1]);
        int z = Integer.parseInt(coords[2]);
        int vida = Integer.parseInt(partes[2]);
        int escudo = Integer.parseInt(partes[3]);
        
        switch (tipo) {
            case "b" -> {
                Base base = new Base(jugador, x, y, z, "B", vida, escudo);
                jugador.agregarBase(base);
                tablero.asignarValor(x, y, z, base);
            }
            case "n" -> {
                int danio = Integer.parseInt(partes[4]);
                Nave nave = new Nave(jugador, x, y, z, "N", vida, danio);
                jugador.agregarNave(nave);
                tablero.asignarValor(x, y, z, nave);
            }
            case "s" -> {
                int radio = Integer.parseInt(partes[4]);
                Satelite satelite = new Satelite(jugador, x, y, z, "S", vida, radio);
                jugador.agregarSatelite(satelite);
                tablero.asignarValor(x, y, z, satelite);
            }
        }
    }
}
