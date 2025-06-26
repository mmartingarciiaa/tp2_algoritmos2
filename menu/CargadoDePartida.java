package menu;

import Coordenada.Coordenada;
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
                } else if (linea.startsWith("alianzas")) {
                    while ((linea = lector.readLine()) != null && linea.startsWith("a - ")) {
                        Alianza alianza = manejarAlianzas(linea, jugadores);
                        alianzas.insertarUltimo(alianza);
                    }
                } else if (linea.startsWith("r - ")) {
                    if (tablero == null) {
                        throw new IllegalStateException("El tablero debe ser inicializado antes de colocar radiación.");
                    }
                    procesarRadiacion(linea, tablero);
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

            String tipo = partes[0].trim();
            String[] coordenadas = partes[1].split(",");
            if (coordenadas.length != 3) {
                throw new IllegalArgumentException("Coordenadas inválidas: " + partes[1]);
            }

            Coordenada coords = new Coordenada(Integer.parseInt(coordenadas[0].trim()),
                                                Integer.parseInt(coordenadas[1].trim()),
                                                Integer.parseInt(coordenadas[2].trim()));
            int vida = Integer.parseInt(partes[2].trim());
            
            Pieza pieza;
            
            switch (tipo) {
                case "b" -> {
                    if (partes.length < 4) {
                        throw new IllegalArgumentException("Falta escudo para la base: " + linea);
                    }
                    int escudo = Integer.parseInt(partes[3].trim());
                    pieza = new Base(jugador, coords, "B", vida, escudo);
                    jugador.agregarBase((Base) pieza);
                }
                case "n" -> {
                    if (partes.length < 5) {
                        throw new IllegalArgumentException("Falta daño para la nave: " + linea);
                    }
                    int danio = Integer.parseInt(partes[4].trim());
                    pieza = new Nave(jugador, coords, "N", vida, danio);
                    jugador.agregarNave((Nave) pieza);
                }
                case "s" -> {
                    if (partes.length < 5) {
                        throw new IllegalArgumentException("Falta radio para el satélite: " + linea);
                    }
                    int radio = Integer.parseInt(partes[4].trim());
                    pieza = new Satelite(jugador, coords, "S", radio);
                    jugador.agregarSatelite((Satelite) pieza);
                }
                default -> {
                    return;
                }
            }
            tablero.asignarValor(coords, pieza);

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Error al parsear la línea: " + linea, e);
        }
    }

    /**
     * Procesa una línea de radiación y la coloca en el tablero.
     * 
     * @param linea La línea del archivo que contiene la información de la radiación.
     * @param tablero El tablero donde se colocará la radiación.
     * @throws IllegalArgumentException si la línea no tiene el formato esperado.
     */
    private static void procesarRadiacion(String linea, Tablero tablero) {
        String[] partes = linea.split(" - ");
        if (partes.length != 3) {
            throw new IllegalArgumentException("Formato inválido de línea de radiación: " + linea);
        }
        String[] coordenadas = partes[1].split(",");
        Coordenada coords = new Coordenada(
            Integer.parseInt(coordenadas[0].trim()),
            Integer.parseInt(coordenadas[1].trim()),
            Integer.parseInt(coordenadas[2].trim())
        );
        int duracion = Integer.parseInt(partes[2].trim());
        
        Pieza radiacion = new Radiacion(coords, duracion, "R");
        tablero.asignarValor(coords, radiacion);
    }

    /**
     * Procesa una línea de alianza y la crea asignando los jugadores correspondientes.
     *
     * @param linea La línea del archivo que contiene la información de la alianza.
     * @param listaJugadores La lista de jugadores donde se buscarán los jugadores de la alianza.
     * @return Un objeto Alianza si se creó correctamente, o null si la alianza ya existía.
     * @throws IllegalArgumentException si la línea no tiene el formato esperado.
     * @throws RuntimeException si no se encuentran ambos jugadores para la alianza.
     */
    private static Alianza manejarAlianzas(String linea, ListaSimplementeEnlazada<Jugador> listaJugadores) {
        String[] partes = linea.split(" - ");
        if (partes.length != 3) {
            throw new IllegalArgumentException("Formato inválido de línea de alianza: " + linea);
        }

        String[] nombresJugadores = partes[1].split(",");
        if (nombresJugadores.length != 2) {
            throw new IllegalArgumentException("Se esperaban exactamente 2 jugadores para una alianza: " + linea);
        }

        String nombre1 = nombresJugadores[0].trim();
        String nombre2 = nombresJugadores[1].trim();
        int turnos = Integer.parseInt(partes[2].trim());

        if (turnos <= 0) {
            throw new IllegalArgumentException("La duración de la alianza debe ser un número positivo: " + linea);
        }

        Jugador jugador1 = null;
        Jugador jugador2 = null;

        IteradorLista<Jugador> iter = listaJugadores.iterador();
        while (iter.haySiguiente()) {
            Jugador jugadorActual = iter.verActual();
            if (jugadorActual.obtenerNombre().equals(nombre1)) {
                jugador1 = jugadorActual;
            } else if (jugadorActual.obtenerNombre().equals(nombre2)) {
                jugador2 = jugadorActual;
            }
            iter.siguiente();
        }

        if (jugador1 == null || jugador2 == null) {
            throw new RuntimeException("No se encontraron ambos jugadores para la alianza: " + linea);
        }

        if (jugador1.esAliado(jugador2)) {
            System.out.println("Alianza ya existente entre " + nombre1 + " y " + nombre2 + ". Se omite.");
            return null;
        }

        Alianza alianza = new Alianza(jugador1, jugador2, turnos);
        jugador1.agregarAlianza(alianza);
        jugador2.agregarAlianza(alianza);
        return alianza;
    }

}

