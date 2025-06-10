package menu;

import estructuras.lista.IteradorLista;
import estructuras.lista.ListaSimplementeEnlazada;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import jugador.*;
import piezas.*;
import tablero.Tablero;


/**
 * Clase que se encarga de cargar una partida desde un archivo.
 * Esta clase lee un archivo de texto que contiene la configuración del tablero,
 * los jugadores y sus piezas, y las carga en un objeto Tablero.
 */
public class CargadoDePartida {

    /**
     * Carga una partida desde un archivo especificado por la ruta.
     * El archivo debe contener la dimensión del tablero, los jugadores y sus piezas.
     * 
     * @param ruta Ruta del archivo desde donde se cargará la partida.
     * @param jugadores ColaEnlazada que contendrá los jugadores cargados desde el archivo.
     * 
     * @return Un objeto Tablero con la configuración cargada desde el archivo.
     * @throws Exception Si ocurre un error al leer el archivo o si el formato es inválido.
     */
    public static Tablero cargarPartida(String ruta, ListaSimplementeEnlazada<Jugador> jugadores, ListaSimplementeEnlazada<Alianza> alianzas) throws Exception {
        Tablero tablero = null;
        Jugador jugadorActual = null;

        try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                if (linea.equals("dimension:")) {
                    linea = lector.readLine();
                    if (linea == null) throw new IllegalArgumentException("Falta valor de dimensión del tablero.");
                    int dimension = Integer.parseInt(linea.trim());
                    tablero = new Tablero(dimension);
                    tablero.inicializarTablero();

                } else if (linea.startsWith("jugador")) {
                    String[] partes = linea.split(" ");
                    if (partes.length < 2) throw new IllegalArgumentException("Formato inválido para jugador: " + linea);
                    String nombre = partes[1].replace(":", "");
                    jugadorActual = new Jugador(nombre);
                    jugadores.insertarUltimo(jugadorActual);
                } else if (linea.equals("alianzas:")) {
                    linea = lector.readLine();
                    alianzas.insertarUltimo(manejarAlianzas(linea, jugadores));
                } else if (linea.equals("radiacion")) {
                    linea = lector.readLine();
                    
                } else {
                    if (tablero == null) throw new IllegalStateException("El tablero debe ser inicializado antes de colocar piezas.");
                    if (jugadorActual == null) throw new IllegalStateException("Debe haber un jugador activo antes de asignar piezas.");
                    parsearPieza(linea, jugadorActual, tablero);
                }
            }

            if (tablero == null) throw new IllegalStateException("No se encontró la definición de la dimensión del tablero.");
            if (jugadores.estaVacia()) throw new IllegalStateException("No se cargó ningún jugador.");

            return tablero;

        } catch (IOException e) {
            throw new IOException("Error al leer el archivo: " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato de número inválido en el archivo: " + e.getMessage(), e);
        }
    }

    /**
     * Método privado que parsea una línea del archivo y crea la pieza correspondiente.
     *
     * @param linea   La línea del archivo que contiene la información de la pieza.
     * @param jugador El jugador al que pertenece la pieza.
     * @param tablero El tablero donde se colocará la pieza.
     * @throws IllegalArgumentException si la línea no tiene el formato esperado.
     */
    private static void parsearPieza(String linea, Jugador jugador, Tablero tablero) {
        try {
            String[] partes = linea.split(" - ");
            if (partes.length < 4) {
                throw new IllegalArgumentException("Formato de linea inválido: " + linea);
            }

            String tipo = partes[0].trim();
            String[] coordenadas = partes[1].split(",");
            if (coordenadas.length != 3) {
                throw new IllegalArgumentException("Coordenadas inválidas: " + partes[1]);
            }

            int x = Integer.parseInt(coordenadas[0].trim());
            int y = Integer.parseInt(coordenadas[1].trim());
            int z = Integer.parseInt(coordenadas[2].trim());
            int vida = Integer.parseInt(partes[2].trim());
            int escudo = Integer.parseInt(partes[3].trim());

            Pieza pieza;

            switch (tipo) {
                case "b" -> {
                    pieza = new Base(jugador, x, y, z, "B", vida, escudo);
                    jugador.agregarBase((Base) pieza);
                }
                case "n" -> {
                    if (partes.length < 5) {
                        throw new IllegalArgumentException("Falta daño para la nave: " + linea);
                    }
                    int danio = Integer.parseInt(partes[4].trim());
                    pieza = new Nave(jugador, x, y, z, "N", vida, danio);
                    jugador.agregarNave((Nave) pieza);
                }
                case "s" -> {
                    if (partes.length < 5) {
                        throw new IllegalArgumentException("Falta radio para el satélite: " + linea);
                    }
                    int radio = Integer.parseInt(partes[4].trim());
                    pieza = new Satelite(jugador, x, y, z, "S", vida, radio);
                    jugador.agregarSatelite((Satelite) pieza);
                }
                default -> throw new IllegalArgumentException("Tipo de pieza desconocido: " + tipo);
            }

            tablero.asignarValor(x, y, z, pieza);

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Error al parsear la línea: " + linea, e);
        }
    }

    private static Alianza manejarAlianzas(String linea, ListaSimplementeEnlazada<Jugador> listaJugadores) {
        try {
            String[] partes = linea.split(" - ");
            if (partes.length < 4) {
                throw new IllegalArgumentException("Formato de linea inválido: " + linea);
            }

            String[] coordenadas = partes[1].split(",");
            if (coordenadas.length != 3) {
                throw new IllegalArgumentException("Coordenadas inválidas: " + partes[1]);
            }
            
            String[] jugadoresAliados = partes[1].split(",");
            int turnos = Integer.parseInt(partes[2]);
            String j1 = jugadoresAliados[0].trim();
            String j2 = jugadoresAliados[1].trim();

            IteradorLista<Jugador> iter = listaJugadores.iterador();
            Jugador jugador1 = null;
            Jugador jugador2 = null;

            while (iter.haySiguiente()) {
                Jugador actual = iter.verActual();
                if (actual.obtenerNombre().equals(j1)) {
                    jugador1 = actual;
                } else if (actual.obtenerNombre().equals(j2)) {
                    jugador2 = actual;
                }
                
                if (jugador1 != null && jugador2 != null) {
                    break;
                }
                
                iter.siguiente();
            }

            if (jugador1 == null || jugador2 == null) {
                throw new RuntimeException("No se encontraron ambos jugadores para la alianza");
            }

            Alianza alianza = new Alianza(jugador1, jugador2, turnos);
            jugador1.agregarAlianza(alianza);
            jugador2.agregarAlianza(alianza);
            return alianza;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException("Error al parsear la línea: " + linea, e);
        }
    }
}

