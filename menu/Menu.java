package menu;

// Importaciones necesarias
import enums.*;
import estructuras.lista.IteradorLista;
import estructuras.lista.ListaSimplementeEnlazada;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import jugador.*;
import mazo.*;
import piezas.*;
import tablero.*;

/**
 * Clase Menu que representa el menú principal del juego,
 * contiene las opciones del juego y maneja la lógica del turno de los jugadores.
 */

public class Menu {

    private final int LIMITE_INFERIOR_JUGADORES = 2; // Límite mínimo de jugadores
    private final int LIMITE_SUPERIOR_JUGADORES = 6; // Límite máximo de jugadores
    private final int LIMITE_INFERIOR_DIMENSION = 4; // Límite mínimo de dimensión del tablero
    private final int LIMITE_SUPERIOR_DIMENSION = 10; // Límite máximo de dimensión del tablero
    private final int LIMITE_DISTANCIA = 5;  // Distancia máxima entre la base y la nave

    private final int VIDA_BASE = 8;  // Vida inicial de la base
    private final int ESCUDO_INICIAL_BASE = 0; // Escudo inicial de la base
    
    private final int VIDA_NAVE = 1;  // Vida inicial de la nave
    private final int DANIO_INICIAL_NAVE = 1; // Daño inicial de la nave
    
    private final int VIDA_SATELITE = 1;  // Vida inicial del satélite
    private final int FACTOR_DETECCION_SATELITE = 4; // Factor de detección del satélite
    private final int FACTOR_RASTREADOR_CUANTICO = 2; // Factor de rastreo del rastreador cuántico

    private final int VIDA_EXTRA = 3; // Vida extra que se puede agregar a la base
    private final int ESCUDO_EXTRA = 3; // Escudo extra que se puede agregar a la base
    private final int DANIO_EXTRA = 2; // Daño extra que se puede agregar a la nave
    private final int REDUCCION_ESCUDO = 1; // Daño que se le saca al escudo al ser golpeado
    private final int DURACION_ALIANZA = 5; // Duración de la alianza en turnos
    private final int DURACION_RADIACION = 4; // Duración de la radiación en turnos
    
    private final String NAVE = "N";  // Caracter para representar una nave
    private final String BASE = "B";  // Caracter para representar una base
    private final String SATELITE = "S";  // Caracter para representar un satélite
    private final String RADIACION = "R";  //Caracter para representar un satelite

    private final int VALOR_AJUSTE = 1; // Valor de ajuste para las coordenadas ingresadas por el usuario

    private Tablero tablero; // Instancia de la clase Tablero
    private int dimension; // Dimensiones del tablero

    private int numJugadores;  // Número de jugadores
    private int jugadorActual = 1; // Jugador actual (1 a número de jugadores) [Usar una cola para el turno]
    private Jugador[] jugadores;    // Arreglo de jugadores

    private final ListaSimplementeEnlazada<Jugador> listaJugadores = new ListaSimplementeEnlazada<>();
    private final ListaSimplementeEnlazada<Alianza> alianzas = new ListaSimplementeEnlazada<>();
    private ListaSimplementeEnlazada<Pieza> piezasDetectadas = new ListaSimplementeEnlazada<>(); // Lista para almacenar piezas detectadas por satélites

    private Mazo mazo; // Instancia de la clase Mazo
    Scanner sc = new Scanner(System.in);    // Creo un objeto Scanner
    
    /**
     * Muestra el menú del juego y maneja las acciones de los jugadores.
     * Itera a través de los turnos de los jugadores, permitiendo realizar acciones como agregar naves,
     * mover naves, agregar satélites, atacar sectores, robar cartas, usar cartas, formar alianzas y dejar de jugar.
     * El juego continúa hasta que solo queda un jugador o se decide dejar de jugar.
     */
    public void menu() {
        this.mensajeInicial();
        Accion accion;
        ListaSimplementeEnlazada<Carta> listaCartas = CartaUtils.crearListaDeCartasBase(jugadores.length);
        mazo = new Mazo<>(listaCartas); // Crear el mazo de cartas base
        int cartasRobadas = 0; // Contador de cartas robadas

        int anchoImagen = dimension * (dimension * Bitmap.TAM_CELDA + Bitmap.ESPACIADO_CAPAS) - Bitmap.ESPACIADO_CAPAS;
        int altoImagen = dimension * Bitmap.TAM_CELDA;
        Bitmap bmp = new Bitmap(anchoImagen, altoImagen);
        
        while(jugadores.length > 1) {
            piezasDetectadas = obtenerPiezasDetectadasPorSatelite(jugadores[jugadorActual - 1]);
            dibujarBMP(bmp);

            System.err.println("\nTurno de jugador " + jugadores[jugadorActual - 1].obtenerNombre());
            System.out.println("Opciones:");
            System.out.println("1. Agregar nave");
            System.out.println("2. Mover nave");
            System.out.println("3. Agregar satélite");
            System.out.println("4. Atacar sector");
            System.out.println("5. Robar carta");
            System.out.println("6. Usar carta");
            System.out.println("7. Alianza");
            System.out.println("8. Dejar de jugar");

            int opcion = leerEnteroEntreLimites("Ingrese una opción: ", 1, 8);
            accion = Accion.desdeCodigo(opcion - 1);
            int[] coordenadas;
            boolean cambioDeTurno = false;

            switch (accion) {
                case AGREGAR_NAVE -> {
                    coordenadas = solicitarCoordenadas("Ingrese la posición en ");
                    if (agregarNave(coordenadas[0], coordenadas[1], coordenadas[2])) {
                        cambioDeTurno = true;
                        System.out.println("Nave agregada exitosamente.");
                    }
                }
                case MOVER_NAVE -> {
                    if (moverNave()) {
                        cambioDeTurno = true;
                    }
                }
                case AGREGAR_SATELITE -> {
                    coordenadas = solicitarCoordenadas("Ingrese la posición en ");
                    if (agregarSatelite(coordenadas[0], coordenadas[1], coordenadas[2])) {
                        cambioDeTurno = true;
                        System.out.println("Satelite agregado exitosamente.");
                    }
                }
                case ATACAR_SECTOR -> {
                    Jugador jugador = jugadores[jugadorActual - 1];
                    ListaSimplementeEnlazada<Nave> navesDisponibles = jugador.obtenerNaves();
                
                    if (!jugador.puedeAtacar()) {
                        System.out.println(jugador.obtenerNombre() + " no tiene naves disponibles para atacar.");
                        break;
                    }
                
                    Nave naveSeleccionada = seleccionarPieza(navesDisponibles, "Nave");
                    if (naveSeleccionada == null) {
                        System.out.println("Selección inválida.");
                        break;
                    }
                    
                    coordenadas = solicitarCoordenadas("Ingrese la posición en ");
                    if (atacarSector(coordenadas[0], coordenadas[1], coordenadas[2], naveSeleccionada)) {
                        cambioDeTurno = true;
                        System.out.println("Ataque realizado.");
                    }
                }
                case ROBAR_CARTA -> {
                    cartasRobadas++;
                    if (cartasRobadas <= 1) {
                        if (mazo.cantidadDeCartas() <= 0) {
                            mazo = new Mazo<>(listaCartas);
                        }
                        Carta cartaRobada = (Carta) mazo.sacarCarta();
                        System.out.println("Carta robada: " + cartaRobada.getNombre());
                        System.out.println("Descripción: " + cartaRobada.getDescripcion());
                        jugadores[jugadorActual - 1].agregarCarta(cartaRobada); 
                    } else {
                        System.out.println("¡Has alcanzado el límite de cartas robadas en este turno!");
                    }
                }
                case USAR_CARTA -> {
                    if (jugadores[jugadorActual - 1].obtenerCartas().estaVacia()) {
                        System.out.println("No tenés cartas para usar.");
                        break;
                    }
                    Carta cartaAUsar = jugadores[jugadorActual - 1].sacarCarta();
                    usarCarta(cartaAUsar, jugadores[jugadorActual - 1]);
                    System.out.println("Carta usada exitosamente");
                    cambioDeTurno = true;
                }
                case ALIANZA -> {
                    if (jugadores.length < 3) {
                        System.out.println("¡No se pueden formar alianzas cuando solo quedan dos jugadores!");
                        break;
                    }
                    sc.nextLine();
                    String nombreAliado = "";
                    while (nombreAliado == null || nombreAliado.isEmpty() || nombreAliado.equals(jugadores[jugadorActual - 1].obtenerNombre())) { 
                        System.out.println("Ingrese el nombre del jugador con el que desea formar una alianza:");
                        nombreAliado = sc.nextLine();
                    }
                    boolean encontrado = false;
                    for (Jugador jugador : jugadores) {
                        String nombreJugador = jugador.obtenerNombre();
                        if (nombreJugador.equals(nombreAliado)) {
                            encontrado = true;
                            String eleccion = "";
                            while (!(eleccion.equals("SI") || eleccion.equals("NO"))) {
                                System.out.println(nombreJugador + ", desea formar una alianza con " + jugadores[jugadorActual - 1].obtenerNombre() + "? (Si/No)");
                                eleccion = sc.nextLine().trim().toUpperCase();
                            }
                            if (eleccion.equals("SI")) {
                                Alianza nuevaAlianza = new Alianza(jugadores[jugadorActual - 1], jugador,  DURACION_ALIANZA);
                                alianzas.insertarUltimo(nuevaAlianza);
                                jugadores[jugadorActual - 1].agregarAlianza(nuevaAlianza);
                                jugador.agregarAlianza(nuevaAlianza);
                                System.out.println("Alianza formada entre " + jugadores[jugadorActual - 1].obtenerNombre() + " y " + jugador.obtenerNombre() + ".");
                            } else {
                                System.out.println("Alianza no formada.");
                            }
                            break;
                        }
                    }
                    if (!encontrado) {
                        System.out.println("Jugador no encontrado. Asegúrese de que el nombre sea correcto.");
                    }
                }
                case DEJAR_DE_JUGAR -> {
                    finalizarPartida();
                }
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }

            if(cambioDeTurno) {
                cartasRobadas = 0; // Reiniciar el contador de cartas robadas
                jugadorActual = (jugadorActual % numJugadores) + 1; // Cambiar al siguiente jugador
                reducirDuracionEscudosYRadiaciones();
                reducirDuracionAlianzas();
            }
        }
        System.out.println("¡El juego ha terminado! Todos los jugadores han sido eliminados excepto uno...");
        System.out.println("¡El jugador " + jugadores[0].obtenerNombre() + " ha ganado el juego!");
    }

    /*--------------- METODOS DEL MENU ---------------*/

    /**
     * Muestra un mensaje inicial de bienvenida al juego y permite al usuario elegir entre cargar una partida o iniciar una nueva.
     * Si elige cargar una partida, se llama al método cargarPartida().
     * Si elige iniciar una nueva partida, se llama al método iniciarPartidaNueva().
     */
    public void mensajeInicial() {
        System.out.println("Bienvenidos a la Invasión Galáctica!");
        System.out.println("Este es un juego para máximo 6 jugadores.");

        int eleccion = leerEnteroEntreLimites("¿Desea (1) cargar una partida o (2) empezar una nueva?: ", 1, 2);
        switch (eleccion) {
            case 1 -> {
                sc.nextLine();
                cargarPartida();
            }
            case 2 -> {
                iniciarPartidaNueva();
            }
        }
    }
    
    /**
     * Crea las bases de todos los jugadores.
     */
    private void crearBases() {
        for (int i = 0; i < numJugadores; i++) {
            crearBase(i);
        }
    }

    /**
     * Crea una base para el jugador especificado por el índice i.
     */
    private void crearBase(int i){
        boolean baseCreada = false;
        while (!baseCreada) {
            System.out.println(jugadores[i].obtenerNombre() + ", ingrese la posición de su base:");
            int[] coordenadas = solicitarCoordenadas("Ingrese la coordenada en ");
            Pieza pieza = obtenerPiezaEnPosicion(coordenadas[0], coordenadas[1], coordenadas[2]);
            if (pieza.obtenerTipo() == TipoPieza.VACIO) {
                Base base = new Base(jugadores[i], coordenadas[0], coordenadas[1], coordenadas[2], BASE, VIDA_BASE, ESCUDO_INICIAL_BASE);
                tablero.asignarValor(coordenadas[0], coordenadas[1], coordenadas[2], base);
                this.jugadores[i].agregarBase(base);
                baseCreada = true;
            } else {
                System.out.println("¡Error! Ya existe una pieza en esa posición.");
            }
        }
    }

    /**
     * Agrega una nave al tablero en las coordenadas especificadas.
     * Verifica que las coordenadas sean válidas, que la nave esté a una distancia máxima de 5 casilleros de su base,
     * y que no haya otra pieza en la misma posición.
     * 
     * @param x Coordenada X de la nave.
     * @param y Coordenada Y de la nave.
     * @param z Coordenada Z de la nave.
     * 
     * @return true si la nave fue agregada exitosamente, false en caso contrario.
     */
    private boolean agregarNave(int x, int y, int z) {

        if (!coordenadasValidas(x, y, z)) {
            System.out.println("Posición fuera de los límites.");
            return false;
        }

        if (!verificarDistancia(x, y, z)) {
            System.out.println("La nave debe estar a 5 casilleros o menos de su base.");
            return false;
        }
        Pieza pieza = obtenerPiezaEnPosicion(x, y, z);
        if (pieza.obtenerTipo() != TipoPieza.VACIO) {
            System.out.println("Error: No podes colocar una nave en esta posición.");
            return false;
        }

        Nave nave = new Nave(jugadores[jugadorActual - 1], x, y, z, NAVE, VIDA_NAVE, DANIO_INICIAL_NAVE);
        tablero.asignarValor(x, y, z, nave);

        // Incrementar la cantidad de naves del jugador actual
        jugadores[jugadorActual - 1].agregarNave(nave);

        return true;
    }
    
    /**
     * Agrega un satélite al tablero en las coordenadas especificadas.
     * Verifica que las coordenadas sean válidas, que no haya otra pieza en la misma posición,
     * y que no haya una pieza en la misma posición.
     * 
     * @param x Coordenada X del satélite.
     * @param y Coordenada Y del satélite.
     * @param z Coordenada Z del satélite.
     * 
     * @return true si el satélite fue agregado exitosamente, false en caso contrario.
     */
    private boolean agregarSatelite(int x, int y, int z) {

        if (!coordenadasValidas(x, y, z)) {
            System.out.println("Posición fuera de los límites.");
            return false;
        }
        
        Pieza pieza = obtenerPiezaEnPosicion(x, y, z);
        if (pieza.obtenerTipo() != TipoPieza.VACIO) {
            if(pieza.obtenerTipo() == TipoPieza.SATELITE) {
                Jugador duenio = pieza.obtenerDuenio();
                duenio.eliminarSatelite(x, y, z);
                tablero.asignarValor(x, y, z, new Radiacion(x, y, z, DURACION_RADIACION, RADIACION));
                System.out.println("Habia un satelite en esa posición, ambos fueron destruidos.");
                return true;
            } else {
                System.out.println("Error: No podes colocar un satélite en esta posición.");
                return false;
            }
        }

        Satelite satelite = new Satelite(jugadores[jugadorActual - 1], x, y, z, SATELITE, VIDA_SATELITE, dimension / FACTOR_DETECCION_SATELITE);
        tablero.asignarValor(x, y, z, satelite);

        // Incrementar la cantidad de satélites del jugador actual
        jugadores[jugadorActual - 1].agregarSatelite(satelite);

        return true;
    }
    
    /**
     * Ataca un sector del tablero en las coordenadas especificadas por el jugador actual.
     * Verifica que las coordenadas sean válidas, que la nave atacante esté a una distancia máxima de 5 casilleros de su posición,
     * y que no se ataque a una pieza propia o a un sector con radiación.
     * 
     * @param x Coordenada X del sector a atacar.
     * @param y Coordenada Y del sector a atacar.
     * @param z Coordenada Z del sector a atacar.
     * @param naveAtacante Nave que está realizando el ataque.
     */
    private boolean atacarSector(int x, int y, int z, Nave naveAtacante) {

        if (!coordenadasValidas(x, y, z)) {
            System.out.println("Posición fuera de los límites.");
            return false;
        }

        if (!verificarDistancia(x, y, z, naveAtacante)) {
            System.out.println("El ataque debe ser a menos de 5 casilleros de la nave.");
            return false;
        }

        Sector sector = tablero.obtenerSector(x, y, z);
        Pieza ficha = sector.obtenerValor();
    
        if (ficha == null || ficha.obtenerTipo() == TipoPieza.VACIO) {
            System.out.println("¡Disparo a la nada!");
            return true;
        }
    
        if (esCasillaPropia(ficha)) {
            System.out.println("No podés atacar tus propias unidades.");
            return false;
        }
    
        if (ficha.obtenerTipo() == TipoPieza.RADIACION) {
            System.out.println("No se puede atacar un sector con radiación.");
            return false;
        }
    
        Jugador duenio = ficha.obtenerDuenio(); // Jugador dueño de la pieza atacada
        if (duenio.esAliado(jugadores[jugadorActual - 1])) {
            System.out.println("No podés atacar a un aliado.");
            return false;
        }
        int danio = naveAtacante.obtenerDanio(); // Daño infligido por la nave atacante
        Radiacion radiacion = new Radiacion(x, y, z, DURACION_RADIACION, RADIACION); // Variable para almacenar la radiación si es necesario
        boolean piezaDestruida = false; // Variable para verificar si la base fue destruida
        switch (ficha.obtenerTipo()) {
            case BASE -> {
                if (ficha instanceof Base base) {
                    int vida = base.obtenerVida();
                    if (base.obtenerEscudo() > 0) {
                        base.reducirEscudo(danio);
                        System.out.println("¡Una base ha sido atacada! Escudo restante: " + base.obtenerEscudo());
                        if (vida != base.obtenerVida()) {
                            System.out.println("¡Una base ha sido atacada! Vida restante: " + base.obtenerVida());
                            tablero.asignarValor(x, y, z, ficha); // Actualiza con vida reducida
                        }
                    } else {
                        base.reducirVida(danio);
                        if (base.obtenerVida() <= 0) {
                            System.out.println("¡Una base ha sido destruida!");
                            piezaDestruida = true;
                            duenio.eliminarBase(x, y, z);
                            if (duenio.obtenerBases().estaVacia()) {
                                System.out.println("¡El jugador " + duenio.obtenerNombre() + " ha sido eliminado!");
                                jugadores = jugadoresRestantes(jugadores, duenio);
                                perdedor(duenio);
                            }
                        } else {
                            System.out.println("¡Una base ha sido atacada! Vida restante: " + base.obtenerVida());
                            tablero.asignarValor(x, y, z, base); // Actualiza con vida reducida
                        }
                    }
                }
            }
            case NAVE -> {
                if (ficha.obtenerEscudo() > 0) {
                    ficha.reducirEscudo(danio);
                    System.out.println("¡Escudo reducido a la nave!");
                } else {
                    ficha.reducirVida(danio);
                }
                if (ficha.obtenerVida() < 1) {
                    System.out.println("¡Una nave enemiga ha sido destruida!");
                    piezaDestruida = true;
                    duenio.eliminarNave(x, y, z);
                }
            }
            case SATELITE -> {
                System.out.println("¡Un satelite enemigo ha sido destruido!");
                piezaDestruida = true;
                duenio.eliminarSatelite(x, y, z);
            }
        }
        if (piezaDestruida) {
            tablero.asignarValor(x, y, z, radiacion);
        }

        return true;
    }
    
    /**
     * Usa una carta del mazo del jugador actual.
     * Dependiendo del tipo de carta, realiza diferentes acciones como aumentar el escudo de una base,
     * rastrear piezas en el tablero, mover naves, crear bases adicionales, aumentar la vida de una base,
     * aumentar el daño de una nave, atacar en área o aumentar el escudo de una nave.
     * 
     * @param carta Carta a usar.
     * @param jugador Jugador que está usando la carta.
     */
    private void usarCarta(Carta carta, Jugador jugador) {
        System.out.println("Usando carta: " + carta.getNombre());
        int[] coordenadas;
        Sector sector;
        Pieza pieza;
        switch (carta.getTipo()) {
            case CAMPO_DE_FUERZA -> {
                Base baseSeleccionada = seleccionarPieza(jugador.obtenerBases(), "Base");
                if (baseSeleccionada == null) {
                    System.out.println("Selección inválida.");
                    return;
                }
                System.out.println("Aumentando escudo de la base");
                baseSeleccionada.aumentarEscudo(3);
            }
            case RASTREADOR_CUANTICO -> {
                coordenadas = solicitarCoordenadas("Ingrese la coordenada en ");
                int radioRandom = new Random().nextInt(dimension / FACTOR_RASTREADOR_CUANTICO) + 1; // Radio aleatorio entre 1 y la mitad de la dimension del tablero
                for (int dx = -radioRandom; dx <= radioRandom; dx++) {
                    for (int dy = -radioRandom; dy <= radioRandom; dy++) {
                        for (int dz = -radioRandom; dz <= radioRandom; dz++) {
                            int nuevaX = coordenadas[0] + dx;
                            int nuevaY = coordenadas[1] + dy;
                            int nuevaZ = coordenadas[2] + dz;

                            if (coordenadasValidas(nuevaX, nuevaY, nuevaZ)) {
                                sector = tablero.obtenerSector(nuevaX, nuevaY, nuevaZ);
                                pieza = sector.obtenerValor();
                                if (pieza.obtenerTipo() != TipoPieza.VACIO) {
                                    System.out.println("Pieza encontrada en X: " + (nuevaX + 1)
                                            + " Y: " + (nuevaY + 1)
                                            + " Z: " + (nuevaZ + 1)
                                            + " - Tipo: " + pieza.obtenerTipo());
                                }
                            }
                        }
                    }
                }
            }
            case DOBLE_SALTO_HIPERESPACIAL -> {
                moverNave();
                moverNave();
                System.out.println("¡Nave movida dos veces!");
            }
            case BASE_ADICIONAL -> {
                crearBase(jugadorActual - 1);
                System.out.println("¡Base adicional creada exitosamente!");
            }
            case SUMAR_VIDA_A_BASE -> {
                Base baseSeleccionada = seleccionarPieza(jugador.obtenerBases(), "Base");
                if (baseSeleccionada == null) {
                    System.out.println("Selección inválida.");
                    return;
                }
                baseSeleccionada.aumentarVida(VIDA_EXTRA);
                System.out.println("¡Vida de la base aumentada exitosamente!");
            }
            case NAVE_HACE_DAÑO_EXTRA -> {
                Nave naveSeleccionada = seleccionarPieza(jugador.obtenerNaves(), "Nave");
                if (naveSeleccionada == null) {
                    System.out.println("Selección inválida.");
                    return;
                }
                naveSeleccionada.aumentarDanio(DANIO_EXTRA);
                System.out.println("¡Daño de la nave aumentado exitosamente!");
            }
            case NAVE_HACE_DAÑO_EN_AREA -> {
                Nave naveSeleccionada = seleccionarPieza(jugador.obtenerNaves(), "Nave");
                if (naveSeleccionada == null) {
                    System.out.println("Selección inválida.");
                    return;
                }
                int radio = 1;
                coordenadas = solicitarCoordenadas("Ingrese la coordenada en ");
                for (int dx = -radio; dx <= radio; dx++) {
                    for (int dy = -radio; dy <= radio; dy++) {
                        for (int dz = -radio; dz <= radio; dz++) {
                            int nuevaX = coordenadas[0] + dx;
                            int nuevaY = coordenadas[1] + dy;
                            int nuevaZ = coordenadas[2] + dz;

                            if (coordenadasValidas(nuevaX, nuevaY, nuevaZ)) {
                                sector = tablero.obtenerSector(nuevaX, nuevaY, nuevaZ);
                                pieza = sector.obtenerValor();
                                if (pieza != null && pieza.obtenerTipo() != TipoPieza.VACIO && !esCasillaPropia(pieza)) {
                                    atacarSector(nuevaX, nuevaY, nuevaZ, naveSeleccionada);
                                }
                            }
                        }
                    }
                }
                System.out.println("¡Ataque en área realizado exitosamente!");
            }
            case NAVE_OBTIENE_ESCUDO -> {
                Nave naveSeleccionada = seleccionarPieza(jugador.obtenerNaves(), "Nave");
                if (naveSeleccionada == null) {
                    System.out.println("Selección inválida.");
                    return;
                }
                naveSeleccionada.aumentarEscudo(ESCUDO_EXTRA);
                System.out.println("¡Escudo de la nave aumentado exitosamente!");
            }
            default -> {
                System.out.println("Tipo de carta no reconocido.");
            }
        }
    }
    
    /**
     * Mueve una nave seleccionada a una nueva posición en el tablero.
     * Verifica que la nave tenga coordenadas válidas, que la nueva posición esté dentro de los límites del tablero y a menos de 5 casilleros de su base.
     * Si la nueva posición ya está ocupada por una base con vida menor o igual a 3, la nave captura la base y se asigna al jugador actual.
     * Si la nueva posición ya está ocupada por otra pieza, se muestra un mensaje de error.
     * 
     * @return true si la nave fue movida exitosamente, false en caso contrario.
     */
    private boolean moverNave() {
        ListaSimplementeEnlazada<Nave> naves = jugadores[jugadorActual - 1].obtenerNaves();
    
        if (naves.estaVacia()) {
            System.out.println("No tenés naves para mover.");
            return false;
        }
    
        Nave naveAMover = seleccionarPieza(naves, "Nave");
        
        if (naveAMover == null) {
            System.out.println("Selección inválida.");
            return false;
        }

        int[] coordsViejas = naveAMover.obtenerCoordenadas();
        int[] nuevaCoordenadas = solicitarCoordenadas("Ingrese la nueva posición en ");
    
        if (!coordenadasValidas(nuevaCoordenadas[0], nuevaCoordenadas[1], nuevaCoordenadas[2])) {
            System.out.println("Posición fuera del tablero.");
            return false;
        }
    
        if (!verificarDistancia(nuevaCoordenadas[0], nuevaCoordenadas[1], nuevaCoordenadas[2])) {
            System.out.println("La nave debe mantenerse a 5 casilleros o menos de su base.");
            return false;
        }

        Pieza pieza = obtenerPiezaEnPosicion(nuevaCoordenadas[0], nuevaCoordenadas[1], nuevaCoordenadas[2]);
        
        if (pieza.obtenerTipo() != TipoPieza.VACIO) {
            if (pieza.obtenerTipo() == TipoPieza.BASE && pieza.obtenerVida() <= 3) {
                pieza.obtenerDuenio().eliminarBase(nuevaCoordenadas[0], nuevaCoordenadas[1], nuevaCoordenadas[2]);
                jugadores[jugadorActual - 1].agregarBase((Base) pieza);
                if (pieza.obtenerDuenio().obtenerBases().largo() < 1) {
                    perdedor(pieza.obtenerDuenio());
                    jugadores = jugadoresRestantes(jugadores, pieza.obtenerDuenio());
                }
                System.out.println("¡La nave ha capturado una base!");
                System.out.println("La nave no se movió");
                return true;
            } else {
                System.out.println("Ya hay una pieza en esa posición.");
                return false;
            }
        }
    
        tablero.asignarValor(coordsViejas[0], coordsViejas[1], coordsViejas[2], new Vacio(coordsViejas[0], coordsViejas[1], coordsViejas[2]));
        naveAMover.cambiarCoordenadas(nuevaCoordenadas[0], nuevaCoordenadas[1], nuevaCoordenadas[2]);
        tablero.asignarValor(nuevaCoordenadas[0], nuevaCoordenadas[1], nuevaCoordenadas[2], naveAMover);
        System.out.println("Nave desplazada exitosamente.");
        return true;
    }
    
    /* -------------- METODOS AUXILIARES DEL MENÚ -------------- */
    
    /**
     * Vacia las listas de naves y satélites del jugador eliminado, asignando radiación a sus coordenadas en el tablero.
     * Elimina todas las naves y satélites del jugador de las listas correspondientes.
     * 
     * @param jugador Jugador que ha sido eliminado.
     */
    private void perdedor(Jugador jugador) {
        System.out.println("El jugador " + jugador.obtenerNombre() + " ha sido eliminado.");
        ListaSimplementeEnlazada<Nave> naves = jugador.obtenerNaves();
        naves.iterar((nave) -> {
            int[] coords = nave.obtenerCoordenadas();
            tablero.asignarValor(coords[0], coords[1], coords[2], new Radiacion(coords[0], coords[1], coords[2], DURACION_RADIACION, RADIACION));
            return true;
        });
        ListaSimplementeEnlazada<Satelite> satelites = jugador.obtenerSatelites();
        satelites.iterar((satelite) -> {
            int[] coords = satelite.obtenerCoordenadas();
            tablero.asignarValor(coords[0], coords[1], coords[2], new Radiacion(coords[0], coords[1], coords[2], DURACION_RADIACION, RADIACION));
            return true;
        });
        IteradorLista<Nave> iterNaves = naves.iterador();
        while (iterNaves.haySiguiente()) {
            iterNaves.borrar();
        }
        IteradorLista<Satelite> iterSatelites = satelites.iterador();
        while (iterSatelites.haySiguiente()) {
            iterSatelites.borrar();
        }
    }

    /**
     * Devuelve un nuevo arreglo de jugadores que contiene a todos los jugadores menos el perdedor.
     * Este método se utiliza para actualizar la lista de jugadores activos después de que uno de ellos ha sido eliminado.
     * 
     * @param jugadores Arreglo de jugadores actuales.
     * @param perdedor Jugador que ha sido eliminado.
     * 
     * @return Un nuevo arreglo de jugadores sin el jugador eliminado.
     */
    public Jugador[] jugadoresRestantes(Jugador[] jugadores, Jugador perdedor) {
        Jugador[] nuevosJugadores = new Jugador[jugadores.length - 1];
        int contador = 0;
        for (Jugador jugador : jugadores) {
            if (!jugador.equals(perdedor)) {
                nuevosJugadores[contador] = jugador;
                contador++;
            }
        }
        return nuevosJugadores;
    }

    /**
     * Obtiene las piezas detectadas por los satélites del jugador.
     * Recorre todos los satélites del jugador, verifica las coordenadas de detección y agrega las piezas detectadas a una lista.
     * Si una pieza es detectada, se verifica que no sea propia y que no sea aliada del jugador.
     * 
     * @param jugador Jugador cuyo satélites se utilizarán para detectar piezas.
     * 
     * @return Una lista de piezas detectadas por los satélites del jugador.
     */
    public ListaSimplementeEnlazada<Pieza> obtenerPiezasDetectadasPorSatelite(Jugador jugador) {
        ListaSimplementeEnlazada<Pieza> detectadas = new ListaSimplementeEnlazada<>();
        ListaSimplementeEnlazada<Satelite> satelites = jugador.obtenerSatelites();
        IteradorLista<Satelite> iter = satelites.iterador();
        
        while (iter.haySiguiente()) {
            Satelite satelite = iter.verActual();
            int[] coords = satelite.obtenerCoordenadas();
            int radio = satelite.obtenerRadioDeteccion();
            for (int dx = -radio; dx <= radio; dx++) {
                for (int dy = -radio; dy <= radio; dy++) {
                    for (int dz = -radio; dz <= radio; dz++) {
                        int nuevaX = coords[0] + dx;
                        int nuevaY = coords[1] + dy;
                        int nuevaZ = coords[2] + dz;
                        if (coordenadasValidas(nuevaX, nuevaY, nuevaZ)) {
                            Sector sector = tablero.obtenerSector(nuevaX, nuevaY, nuevaZ);
                            Pieza pieza = sector.obtenerValor();
                            if (pieza.obtenerTipo() != TipoPieza.VACIO && !esCasillaPropia(pieza) && !jugador.esAliado(pieza.obtenerDuenio())) {
                                detectadas.insertarUltimo(pieza);
                            }
                        }
                    }
                }
            }
            iter.siguiente();
        }
        return detectadas;
    }
    
    /**
     * Solicita al usuario seleccionar una pieza de un tipo específico de una lista enlazada.
     * Muestra las piezas disponibles y permite al usuario seleccionar una por su índice.
     * 
     * @param piezas Lista enlazada de piezas del tipo T.
     * @param tipoPieza Tipo de pieza a seleccionar (por ejemplo, "Nave", "Base").
     * 
     * @return La pieza seleccionada por el usuario, o null si la selección es inválida.
     */
    public <T extends Pieza> T seleccionarPieza(ListaSimplementeEnlazada<T> piezas, String tipoPieza) {
        System.out.println();
        final int[] contador = {1};
        piezas.iterar(pieza -> {
            int[] coords = pieza.obtenerCoordenadas();
            System.out.println(contador[0] + ". " + tipoPieza + " en X: " + (coords[0] + 1) + " Y: " + (coords[1] + 1) + " Z: " + (coords[2] + 1));
            contador[0]++;
            return true;
        });

        int seleccion = leerEnteroEntreLimites("Seleccione la " + tipoPieza + ": ", 1, piezas.largo() + 1);
        if (seleccion < 1 || seleccion >= piezas.largo() + 1) {
            return null;
        }

        return piezas.obtenerEnPosicion(seleccion); // devuelve T (Pieza o subclase)
    }

    /**
     * Carga una partida desde un archivo de texto.
     * Solicita al usuario la ruta del archivo, valida la ruta y carga el tablero y los jugadores desde el archivo.
     * 
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    private void cargarPartida() {
        String ruta = obtenerRutaValida("Ingrese la ruta al archivo txt: ");
        try {
            this.tablero = CargadoDePartida.cargarPartida(ruta, listaJugadores);
            int i = 0;
            this.jugadores = new Jugador[listaJugadores.largo()];
            IteradorLista<Jugador> iter = listaJugadores.iterador();
            while (iter.haySiguiente()) {
                this.jugadores[i] = iter.verActual();
                i++;
                iter.siguiente();
            }
            this.numJugadores = this.jugadores.length; // Actualizar el número de jugadores
            this.dimension = tablero.obtenerDimension(); // Obtener la dimensión del tablero cargado
        } catch (Exception e) {
            System.out.println("Error al cargar la partida: " + e.getMessage());
        }
    }

    /**
     * Inicia una nueva partida solicitando el número de jugadores, sus nombres y el tamaño del tablero.
     * Crea un arreglo de jugadores y un tablero con el tamaño especificado.
     * Inicializa el tablero y crea las bases para cada jugador.
     */
    private void iniciarPartidaNueva() {
        numJugadores = leerEnteroEntreLimites("Ingrese el número de jugadores (2-6): ", LIMITE_INFERIOR_JUGADORES, LIMITE_SUPERIOR_JUGADORES); // Leer el número de jugadores
        // Crear el arreglo de jugadores ahora que sabemos cuántos son
        this.jugadores = new Jugador[numJugadores];

        // Pedir los nombres de todos los jugadores
        sc.nextLine();
        for (int i = 0; i < numJugadores; i++) {
            String nombre = obtenerNombreValido("Jugador " + (i + 1) + ", ingrese su nombre: ");
            this.jugadores[i] = new Jugador(nombre);
            listaJugadores.insertarUltimo(jugadores[i]);
        }
        System.out.println("El tablero es un cubo del tamaño que establezca");
        dimension = leerEnteroEntreLimites("Tamaño (4-10): ", LIMITE_INFERIOR_DIMENSION, LIMITE_SUPERIOR_DIMENSION); // Leer el tamaño del tablero
        
        tablero = new Tablero(dimension); // Crear el tablero con el tamaño especificado

        // Inicializar el tablero solo una vez
        tablero.inicializarTablero();
        this.crearBases();
    }

    /**
     * Reduce la duración de los escudos y radiaciones en el tablero.
     * Recorre todas las piezas del tablero, reduce el escudo de las naves y bases si es mayor a 0,
     * y reduce la duración de las radiaciones. Si una radiación ya no está activa, se reemplaza por un sector vacío.
     */
    private void reducirDuracionEscudosYRadiaciones() {
        ListaSimplementeEnlazada<Pieza> piezasNoVacias = tablero.obtenerPiezasNoVacias();
        IteradorLista<Pieza> iter = piezasNoVacias.iterador();
        int contador = 1;
        while (iter.haySiguiente()) {
            boolean elementoBorrado = false;
            Pieza pieza = iter.verActual();
            if (pieza.obtenerEscudo() > 0 && (pieza.obtenerTipo() == TipoPieza.BASE || 
                pieza.obtenerTipo() == TipoPieza.NAVE)) {
                pieza.reducirEscudo(REDUCCION_ESCUDO);
            }
            if (pieza.obtenerTipo() == TipoPieza.RADIACION) {
                Radiacion radiacion = (Radiacion) pieza;
                radiacion.reducirDuracion();
                if (!radiacion.estaActiva()) {
                    tablero.asignarValor(pieza.obtenerCoordenadas()[0], pieza.obtenerCoordenadas()[1], pieza.obtenerCoordenadas()[2], new Vacio(pieza.obtenerCoordenadas()[0], pieza.obtenerCoordenadas()[1], pieza.obtenerCoordenadas()[2]));
                    iter.borrar();
                    tablero.obtenerPiezasNoVacias().borrarEnPosicion(contador);
                    elementoBorrado = true;
                }
            }
            contador++;
            if (!elementoBorrado) {
                iter.siguiente();
            }
        }
    }

    /**
     * Reduce la duración de todas las alianzas activas.
     * Recorre todas las alianzas, reduce su duración y elimina aquellas que ya no están activas.
     * Si una alianza ya no está activa, elimina a los jugadores de la alianza y desarma la alianza.
     */
    private void reducirDuracionAlianzas() {
        IteradorLista<Alianza> iterAlianzas = alianzas.iterador();
        while (iterAlianzas.haySiguiente()) {
            boolean elementoBorrado = false;
            Alianza alianza = iterAlianzas.verActual();
            alianza.reducirDuracion();
            if (!alianza.estaActiva()) {
                Jugador[] aliados = alianza.obtenerJugadores();
                if (aliados != null && aliados.length > 0) {
                    for (Jugador aliado : aliados) {
                        aliado.eliminarAlianza(alianza);
                    }
                }
                alianza.desarmarAlianza();
                iterAlianzas.borrar(); // Eliminar la alianza si ya no está activa
                elementoBorrado = true;
            }
            if (!elementoBorrado) {
                iterAlianzas.siguiente();
            }
        }
    }

    /**
     * Finaliza la partida, mostrando un mensaje de despedida y limpiando los recursos.
     */
    private void finalizarPartida() {
        sc.nextLine();                    
        String eleccion = "";
        while (!(eleccion.equals("SI") || eleccion.equals("NO"))) {
            System.out.println("¿Desea guardar la partida? (SI/NO)");
            eleccion = sc.nextLine().trim().toUpperCase();
        }
        if (eleccion.equals("SI")) {
            try {
                String ruta = obtenerRutaValida("Ingrese el nombre del archivo (el nombre debe finalizar con .txt): ");
                GuardadoDePartidaEnArchivo.guardarPartida(tablero, listaJugadores, ruta, null, null);
            } catch (IOException e) {
                System.out.println("Error al guardar la partida: " + e.getMessage());
            }
        }
        System.out.println("Gracias por jugar!");
        sc.close(); // Cerrar el Scanner
        System.exit(0); // Terminar el programa
    }

    /**
     * Dibuja el tablero en un archivo BMP.
     * Utiliza la clase Bitmap para generar un archivo BMP del tablero actual, incluyendo las piezas y sus estados.
     * 
     * @param bmp Instancia de la clase Bitmap para generar el archivo BMP.
     * @throws IOException Si ocurre un error al guardar el archivo BMP.
     */
    private void dibujarBMP(Bitmap bmp) {
        try {
            bmp.generarBMPCompleto(tablero, jugadores[jugadorActual - 1], piezasDetectadas);
            bmp.guardarArchivo("tablero.bmp");
        } catch (IOException e) {
            System.out.println("Error al dibujar el tablero: " + e.getMessage());
        }
    }
    
    /* -------------- VALIDACIONES -------------- */

    /**
     * Solicita las coordenadas al usuario y las devuelve como un arreglo de enteros.
     * 
     * @param mensaje El mensaje que se mostrará al solicitar las coordenadas.
     * @return Un arreglo de enteros con las coordenadas [x, y, z].
     */
    private int[] solicitarCoordenadas(String mensaje) {
        int x = leerPosicion(mensaje + "X: ", dimension) - VALOR_AJUSTE;
        int y = leerPosicion(mensaje + "Y: ", dimension) - VALOR_AJUSTE;
        int z = leerPosicion(mensaje + "Z: ", dimension) - VALOR_AJUSTE;
        return new int[]{x, y, z};
    }

    /**
     * Verifica si las coordenadas dadas están dentro de los límites del tablero.
     * 
     * @param x La coordenada x.
     * @param y La coordenada y.
     * @param z La coordenada z.
     * @return true si las coordenadas son válidas, false en caso contrario.
     */
    private boolean coordenadasValidas(int x, int y, int z) {
        return x >= 0 && x < dimension &&
               y >= 0 && y < dimension &&
               z >= 0 && z < dimension;
    }
    
    /**
     * Verifica si la pieza pertenece al jugador actual.
     * 
     * @param pieza La pieza a verificar.
     * @return true si la pieza es del jugador actual, false en caso contrario.
     */
    private boolean esCasillaPropia(Pieza pieza) {
        if (pieza == null || pieza.obtenerTipo() == TipoPieza.VACIO) {
            return false;
        }
        Jugador duenio = pieza.obtenerDuenio();
        return duenio != null && duenio.equals(jugadores[jugadorActual - 1]);
    }

    /**
     * Verifica si hay una pieza en la posición especificada del tablero.
     * 
     * @param x La coordenada x de la posición.
     * @param y La coordenada y de la posición.
     * @param z La coordenada z de la posición.
     * @return true si hay una pieza en la posición, false en caso contrario.
     */
    private Pieza obtenerPiezaEnPosicion(int x, int y, int z) {
        return tablero.obtenerSector(x, y, z).obtenerValor();
    }

    /**
     * Verifica si la distancia entre las coordenadas dadas y las bases del jugador actual es válida.
     * 
     * @param x La coordenada x.
     * @param y La coordenada y.
     * @param z La coordenada z.
     * @return true si la distancia es válida (5 casilleros o menos), false en caso contrario.
     */
    private boolean verificarDistancia(int x, int y, int z) {
        Jugador jugador = jugadores[jugadorActual - 1];
        IteradorLista<Base> iter = jugador.obtenerBases().iterador();
        while (iter.haySiguiente()) {
            Pieza base = iter.verActual();
            int[] coords = base.obtenerCoordenadas();
            int dx = coords[0] - x;
            int dy = coords[1] - y;
            int dz = coords[2] - z;
            double distancia = Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (distancia <= LIMITE_DISTANCIA) {
                return true; // La nave está dentro del rango de la base
            }
            iter.siguiente();
        }
        return false; // No se encontró ninguna base dentro del rango
    }
    
    /**
     * Verifica si la distancia entre las coordenadas dadas y las coordenadas de la nave atacante es válida.
     * 
     * @param x La coordenada x del sector a atacar.
     * @param y La coordenada y del sector a atacar.
     * @param z La coordenada z del sector a atacar.
     * @param naveAtacante La nave que está realizando el ataque.
     */
    private boolean verificarDistancia(int x, int y, int z, Nave naveAtacante) {
        int[] coordsNave = naveAtacante.obtenerCoordenadas();
        int dx = coordsNave[0] - x;
        int dy = coordsNave[1] - y;
        int dz = coordsNave[2] - z;
        double distancia = Math.sqrt(dx * dx + dy * dy + dz * dz);
        return distancia <= LIMITE_DISTANCIA; // Verifica si la distancia es menor o igual a 5
    }

    /**
     * Lee un número entero entre dos límites especificados.
     * 
     * @param mensaje El mensaje que se mostrará al solicitar el número.
     * @param limiteInferior El límite inferior del rango.
     * @param limiteSuperior El límite superior del rango.
     * @return El número entero ingresado por el usuario, si está dentro de los límites.    
     */
    private int leerEnteroEntreLimites(String mensaje, int limiteInferior, int limiteSuperior) {
        int numero;
        while (true) {
            try {
                System.out.print(mensaje);
                numero = sc.nextInt();
                if (numero >= limiteInferior && numero <= limiteSuperior) {  // Asegurar que esté dentro de los límites
                    return numero;
                } else {
                    System.out.println("Debe ingresar un número entre " + limiteInferior + " y " + limiteSuperior + ".");
                }
            } catch (Exception e) {
                System.out.println("Debe ingresar un número entero válido.");
                sc.nextLine(); // Limpiar el buffer del scanner
            }
        }
    }

    /**
     * Lee una posición del tablero entre 1 y un límite especificado.
     * 
     * @param mensaje El mensaje que se mostrará al solicitar la posición.
     * @param limite El límite superior del tablero.
     * @return La posición ingresada por el usuario, si está dentro de los límites.
     */
    private int leerPosicion(String mensaje, int limite) {
        int posicion;
        while (true) {
            posicion = leerEnteroEntreLimites(mensaje, 1, limite); // Lee la posición
            if (posicion >= 1 && posicion <= limite) { // Verifica si está dentro del rango
                return posicion;
            } else {
                System.out.println("Posición fuera de los límites del tablero. Debe estar entre 1 y " + limite);
            }
        }
    }

    /**
     * Obtiene un nombre válido (no vacío) del usuario.
     * 
     * @param mensaje El mensaje que se mostrará al solicitar el nombre.
     * @return El nombre ingresado por el usuario, sin espacios al principio ni al final.
     *         Si el nombre está vacío, se solicita nuevamente.
     */
    public String obtenerNombreValido(String mensaje) {
        String nombre = "";
        while (nombre.isEmpty()) {
            System.out.print(mensaje);
            nombre = sc.nextLine().trim();
            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacío. Inténtalo de nuevo.");
            }
        }
        return nombre;
    }
    
    /**
     * Obtiene una ruta válida para un archivo de texto.
     * 
     * @param mensaje El mensaje que se mostrará al solicitar la ruta.
     * @return La ruta ingresada por el usuario, asegurándose de que no esté vacía y termine con ".txt".
     *         Si la ruta no es válida, se solicita nuevamente.
     */
    public String obtenerRutaValida(String mensaje) {
        String path = "";
        while (path.isEmpty() || !path.endsWith(".txt")) {
            System.out.print(mensaje);
            path = sc.nextLine().trim();
            if (path.isEmpty() || !path.endsWith(".txt")) {
                System.out.println("El nombre no puede estar vacío y debe terminar en .txt. Inténtalo de nuevo.");
            }
        }
        return path;
    }
}