package menu;

// Importaciones necesarias
import enums.*;
import estructuras.cola.ColaEnlazada;
import estructuras.lista.IteradorLista;
import estructuras.lista.ListaEnlazada;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import mazo.*;
import piezas.*;
import tablero.*;



public class Menu {

    private final int LIMITE_DISTANCIA = 5;  // Distancia máxima entre la base y la nave
    private final int VIDA_BASE = 10;  // Vida inicial de la base
    private final int VIDA_NAVE = 1;  // Vida inicial de la nave
    private final int VIDA_SATELITE = 1;  // Vida inicial del satélite

    final String NAVE = "N";  // Caracter para representar una nave
    final String BASE = "B";  // Caracter para representar una base
    private final String SATELITE = "S";  // Caracter para representar un satélite
    
    private final ListaEnlazada<Radiacion> listaRadiacion = new ListaEnlazada<>(); // Lista para almacenar sectores con radiación
    private Tablero tablero; // Instancia de la clase Tablero
    private int dimension; // Dimensiones del tablero

    private int numJugadores;  // Número de jugadores
    private int jugadorActual = 1; // Jugador actual (1 a número de jugadores) [Usar una cola para el turno]
    private Jugador[] jugadores;    // Arreglo de jugadores
    private ColaEnlazada<Jugador> colaJugadores; // Cola para manejar el turno de los jugadores

    private final ListaEnlazada<Alianza> alianzas = new ListaEnlazada<>();

    private Mazo mazo; // Instancia de la clase Mazo
    Scanner sc = new Scanner(System.in);    // Creo un objeto Scanner

    // Método para mostrar el mensaje inicial del juego y configurar el tablero
    public void mensajeInicial() {
        System.out.println("Bienvenidos a la Invasión Galáctica!");
        System.out.println("Este es un juego para máximo 6 jugadores.");
        numJugadores = leerEnteroMayorA2("Ingrese el número de jugadores (2-6): ");

        // Crear el arreglo de jugadores ahora que sabemos cuántos son
        jugadores = new Jugador[numJugadores];
        colaJugadores = new ColaEnlazada<>(); // Inicializar la cola de jugadores

        // Pedir los nombres de todos los jugadores
        sc.nextLine();
        for (int i = 0; i < numJugadores; i++) {
            String nombre = obtenerNombreValido("Jugador " + (i + 1) + ", ingrese su nombre: ");
            jugadores[i] = new Jugador(nombre);
            colaJugadores.encolar(jugadores[i]); // Agregar jugador a la cola
        }
        
        System.out.println("El tablero es un cubo del tamaño que establezca");
        dimension = leerEnteroMayorA2("TAMAÑO (minimo 2): "); // Leer el tamaño del tablero
        
        tablero = new Tablero(dimension); // Crear el tablero con el tamaño especificado

        // Inicializar el tablero solo una vez
        tablero.inicializarTablero();
    }
    
    // Método para crear la base de un jugador
    public void crearBases() {
        for (int i = 0; i < numJugadores; i++) {
            crearBase(i);
        }
    }

    public void crearBase(int i){
        boolean baseCreada = false;
            while (!baseCreada) {
                System.out.println(jugadores[i].obtenerNombre() + ", ingrese la posición de su base:");
                int x = leerPosicion("X: ", dimension);
                int y = leerPosicion("Y: ", dimension);
                int z = leerPosicion("Z: ", dimension);
                Pieza pieza = tablero.obtenerSector(x - 1, y - 1, z - 1).obtenerValor();
                if (pieza.obtenerTipo().equals(TipoPieza.VACIO)) {
                    Base base = new Base(jugadores[i], x - 1, y - 1, z - 1, BASE, VIDA_BASE);
                    tablero.asignarValor(x - 1, y - 1, z - 1, base);
                    jugadores[i].agregarBase(base);
                    baseCreada = true;
                } else {
                    System.out.println("¡Error! Ya existe una pieza en esa posición.");
                }
            }
    }
    
    // Método para mostrar el menú de opciones y realizar las acciones
    public void menu() {
        this.crearBases();
        Accion accion;
        ListaEnlazada<Carta> listaCartas = CartaUtils.crearListaDeCartasBase(jugadores.length);
        mazo = new Mazo<>(listaCartas); // Crear el mazo de cartas base
        
        while(jugadores.length > 1) {
            int anchoImagen = dimension * (dimension * Bitmap.TAM_CELDA + Bitmap.ESPACIADO_CAPAS) - Bitmap.ESPACIADO_CAPAS;
            int altoImagen = dimension * Bitmap.TAM_CELDA;

            Bitmap bmp = new Bitmap(anchoImagen, altoImagen);

            try {
                bmp.generarBMPCompleto(tablero, jugadores[jugadorActual - 1]);
                bmp.guardarArchivo("tablero.bmp");
            } catch (IOException e) {
                System.out.println("Error al dibujar el tablero: " + e.getMessage());
            }
            //Jugador jugadorActual = colaJugadores.desencolar(); // Obtener el jugador actual de la cola
            System.err.println("Turno de jugador " + jugadores[jugadorActual - 1].obtenerNombre());
            System.out.println("Opciones:");
            System.out.println("1. Agregar nave");
            System.out.println("2. Mover nave");
            System.out.println("3. Agregar satélite");
            System.out.println("4. Atacar sector");
            System.out.println("5. Robar carta");
            System.out.println("6. Usar carta");
            System.out.println("7. Alianza (no implementado)");
            System.out.println("8. Dejar de jugar");

            int opcion = leerEntero("Ingrese una opción: ");

            accion = Accion.desdeCodigo(opcion);

            int posicionX, posicionY, posicionZ;
            int cartasRobadas = 0; // Contador de cartas robadas
            boolean cambioDeTurno = false;

            switch (accion) {
                case AGREGAR_NAVE -> {
                    posicionX = leerPosicion("Ingrese la posición en X: ", dimension);
                    posicionY = leerPosicion("Ingrese la posición en Y: ", dimension);
                    posicionZ = leerPosicion("Ingrese la posición en Z: ", dimension);
                    if (agregarNave(posicionX, posicionY, posicionZ)) {
                        cambioDeTurno = true;
                    }
                }
                case MOVER_NAVE -> {
                    moverNave();
                    cambioDeTurno = true;
                }
                case AGREGAR_SATELITE -> {
                    posicionX = leerPosicion("Ingrese la posición en X: ", dimension);
                    posicionY = leerPosicion("Ingrese la posición en Y: ", dimension);
                    posicionZ = leerPosicion("Ingrese la posición en Z: ", dimension);
                    if (agregarSatelite(posicionX, posicionY, posicionZ)) {
                        cambioDeTurno = true;
                    }
                }
                case ATACAR_SECTOR -> {
                    Jugador jugador = jugadores[jugadorActual - 1];
                    ListaEnlazada<Nave> navesDisponibles = jugador.obtenerNaves();
                
                    if (!jugador.puedeAtacar()) {
                        System.out.println(jugador.obtenerNombre() + " no tiene naves disponibles para atacar.");
                        break;
                    }
                
                    System.out.println("Tus naves:");
                    // Enumero y muestro las naves disponibles
                    //! hacer una funcion auxiliar para mostrar las naves
                    final int[] contador = {1};
                    navesDisponibles.iterar(nave -> {
                        int[] coords = nave.obtenerCoordenadas();
                        System.out.println(contador[0] + ". Nave en X: " + (coords[0] + 1) + " Y: " + (coords[1] + 1) + " Z: " + (coords[2] + 1));
                        contador[0]++;
                        return true;
                    });
                
                    int seleccion = leerEntero("Elegí el número de la nave para atacar: ");
                    if (seleccion < 1 || seleccion >= contador[0]) {
                        System.out.println("Selección inválida.");
                        break;
                    }
                
                    Nave naveSeleccionada = navesDisponibles.obtenerEnPosicion(seleccion - 1);
                
                    int x = leerPosicion("Ingrese la posición en X del objetivo: ", dimension);
                    int y = leerPosicion("Ingrese la posición en Y del objetivo: ", dimension);
                    int z = leerPosicion("Ingrese la posición en Z del objetivo: ", dimension);
                
                    if (atacarSector(x, y, z, naveSeleccionada)) {
                        cambioDeTurno = true;
                    }
                }
                case ROBAR_CARTA -> {
                    cartasRobadas++;
                    if (cartasRobadas <= 1) {
                        Carta cartaRobada = (Carta) mazo.sacarCarta();
                        System.out.println("Carta robada: " + cartaRobada.getNombre());
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
                    cambioDeTurno = true;
                }
                case ALIANZA -> {
                    String nombreAliado = "";
                    while (nombreAliado == null || nombreAliado.isEmpty() || nombreAliado.equals(jugadores[jugadorActual - 1].obtenerNombre())) { 
                        System.out.println("Ingrese el nombre del jugador con el que desea formar una alianza:");
                        nombreAliado = sc.nextLine();
                    }
                    
                    for (Jugador jugador : jugadores) {
                        String nombreJugador = jugador.obtenerNombre();
                        if (nombreJugador.equals(nombreAliado)) {
                            String eleccion = "";
                            while(!(eleccion.equals("SI") || eleccion.equals("NO"))) {
                                System.out.println(nombreJugador + ", desea formar una alianza con " + jugadores[jugadorActual - 1].obtenerNombre() + "? (Si/No)");
                                eleccion = sc.nextLine().trim().toUpperCase();
                            }
                            if (eleccion.equals("SI")) {
                                alianzas.insertarUltimo(new Alianza(jugadores[jugadorActual - 1], jugador));
                            } else if (eleccion.equals("NO")) {
                                System.out.println("Alianza no formada.");
                            }
                            break;
                        }
                    }
                }
                case DEJAR_DE_JUGAR -> {
                    System.out.println("Gracias por jugar!");
                    return;
                }
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
            if(cambioDeTurno){
                bajarTurnoARadiacion();
                jugadorActual = (jugadorActual % numJugadores) + 1; // Cambiar al siguiente jugador
            }
            //ListaEnlazada<Pieza> informacionSatelite = obtenerPiezasDetectadasPorSatelite(jugadores[jugadorActual - 1]);
        }
    }

    // Método para agregar una nave en el tablero
    private boolean agregarNave(int x, int y, int z) {
        // Ajustar las coordenadas para que sean cero-basadas
        int XAjustado = x - 1;
        int YAjustado = y - 1;
        int ZAjustado = z - 1;

        if (!coordenadasValidas(XAjustado, YAjustado, ZAjustado)) {
            System.out.println("Posición fuera de los límites.");
            return false;
        }

        if (!verificarDistancia(XAjustado, YAjustado, ZAjustado)) {
            System.out.println("La nave debe estar a 5 casilleros o menos de su base.");
            return false;
        }

        if (hayPiezaEnPosicion(XAjustado, YAjustado, ZAjustado)) {
            System.out.println("Error: No podes colocar una nave en esta posición.");
            return false;
        }

        Nave nave = new Nave(jugadores[jugadorActual - 1], XAjustado, YAjustado, ZAjustado, NAVE, VIDA_NAVE, 1);
        tablero.asignarValor(XAjustado, YAjustado, ZAjustado, nave);
        
        // Incrementar la cantidad de naves del jugador actual
        jugadores[jugadorActual - 1].agregarNave(nave);

        return true;
    }
    
    // Método para agregar un satélite en el tablero
    private boolean agregarSatelite(int x, int y, int z) {
        // Ajustar las coordenadas para que sean cero-basadas
        int XAjustado = x - 1;
        int YAjustado = y - 1;
        int ZAjustado = z - 1;

        if (!coordenadasValidas(XAjustado, YAjustado, ZAjustado)) {
            System.out.println("Posición fuera de los límites.");
            return false;
        }

        if (hayPiezaEnPosicion(XAjustado, YAjustado, ZAjustado)) {
            System.out.println("Error: No podes colocar un satélite en esta posición.");
            return false;
        }

        Satelite satelite = new Satelite(jugadores[jugadorActual - 1], XAjustado, YAjustado, ZAjustado, SATELITE, VIDA_SATELITE, 3);
        tablero.asignarValor(XAjustado, YAjustado, ZAjustado, satelite);
        
        // Incrementar la cantidad de satélites del jugador actual
        jugadores[jugadorActual - 1].agregarSatelite(satelite);

        return true;
    }
    
    // Método para atacar un sector del tablero
    private boolean atacarSector(int x, int y, int z, Nave naveAtacante) {
        int XAjustado = x - 1;
        int YAjustado = y - 1;
        int ZAjustado = z - 1;
    
        if (!coordenadasValidas(XAjustado, YAjustado, ZAjustado)) {
            System.out.println("Posición fuera de los límites.");
            return false;
        }
    
        if (!verificarDistancia(XAjustado, YAjustado, ZAjustado, naveAtacante)) {
            System.out.println("El ataque debe ser a menos de 5 casilleros de la nave.");
            return false;
        }
    
        Sector sector = tablero.obtenerSector(XAjustado, YAjustado, ZAjustado);
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
        int danio = naveAtacante.obtenerDanio(); // Daño infligido por la nave atacante
        Radiacion radiacion = new Radiacion(XAjustado, YAjustado, ZAjustado, 4); // Variable para almacenar la radiación si es necesario
        switch (ficha.obtenerTipo()) {
            case BASE -> {
                if (ficha instanceof Base base) {
                    base.reducirVida(danio);
                    if (base.obtenerVida() <= 0) {
                        System.out.println("¡La base ha sido destruida!");
                        duenio.eliminarBase(XAjustado, YAjustado, ZAjustado);
                        base.destruirPieza();
                        if (duenio.obtenerBases().estaVacia()) {
                            System.out.println("¡El jugador " + duenio.obtenerNombre() + " ha sido eliminado!");
                            jugadores = jugadoresRestantes(jugadores, duenio);
                            perdedor(duenio);
                        }
                    } else {
                        System.out.println("¡La base ha sido atacada! Vida restante: " + ficha.obtenerVida());
                        tablero.asignarValor(XAjustado, YAjustado, ZAjustado, ficha); // Actualiza con vida reducida
                    }
                }
            }
            case NAVE -> {
                System.out.println("¡Una nave enemiga ha sido destruida!");
                duenio.eliminarNave(XAjustado, YAjustado, ZAjustado);
            }
            case SATELITE -> {
                System.out.println("¡Un satelite enemigo ha sido destruido!");
                duenio.eliminarSatelite(XAjustado, YAjustado, ZAjustado);
            }
        }
        tablero.asignarValor(XAjustado, YAjustado, ZAjustado, radiacion);
        listaRadiacion.insertarUltimo(radiacion);
    
        return true;
    }
    
    // Método para usar una carta del jugador actual
    private void usarCarta(Carta carta, Jugador jugador) {
        int posicionX, posicionY, posicionZ;
        Sector sector;
        Pieza pieza;
        switch (carta.getTipo()) {
            case CAMPO_DE_FUERZA -> {
                ListaEnlazada<Base> bases = jugador.obtenerBases();
                System.out.println("Elija la base:");
                final int[] contador = {1};
                bases.iterar(base -> {
                    int[] coords = base.obtenerCoordenadas();
                    System.out.println(contador[0] + ". Base en X: " + (coords[0] + 1) + " Y: " + (coords[1] + 1) + " Z: " + (coords[2] + 1));
                    contador[0]++;
                    return true;
                });
                int seleccion = leerEntero("Elija la base a aumentar escudo: ");
                if (seleccion < 1 || seleccion >= contador[0]) {
                    System.out.println("Selección inválida.");
                    return;
                }
                Base baseSeleccionada = bases.obtenerEnPosicion(seleccion - 1);
                baseSeleccionada.aumentarEscudo(3);
            }
            case RASTREADOR_CUANTICO -> {
                posicionX = leerPosicion("Ingrese la posicion en x", dimension);
                posicionY = leerPosicion("Ingrese la posicion en y", dimension);
                posicionZ = leerPosicion("Ingrese la posicion en z", dimension);
                int radioRandom = new Random().nextInt(dimension / 2) + 1; // Radio aleatorio entre 1 y la mitad de la dimension del tablero
                for (int dx = -radioRandom; dx <= radioRandom; dx++) {
                    for (int dy = -radioRandom; dy <= radioRandom; dy++) {
                        for (int dz = -radioRandom; dz <= radioRandom; dz++) {
                            int nuevaX = posicionX + dx;
                            int nuevaY = posicionY + dy;
                            int nuevaZ = posicionZ + dz;
                            
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
                moverNave(); // Permite mover dos veces en el mismo turno
            }
            case BASE_ADICIONAL -> {
                crearBase(jugadorActual - 1); // Crea una base adicional para el jugador actual 
            }
            case SUMAR_VIDA_A_BASE -> {
                ListaEnlazada<Base> bases = jugador.obtenerBases();
                System.out.println("Elija la base:");
                final int[] contador = {1};
                bases.iterar(base -> {
                    int[] coords = base.obtenerCoordenadas();
                    System.out.println(contador[0] + ". Base en X: " + (coords[0] + 1) + " Y: " + (coords[1] + 1) + " Z: " + (coords[2] + 1));
                    contador[0]++;
                    return true;
                });
                int seleccion = leerEntero("Elija la base a aumentar escudo: ");
                if (seleccion < 1 || seleccion >= contador[0]) {
                    System.out.println("Selección inválida.");
                    return;
                }
                Base baseSeleccionada = bases.obtenerEnPosicion(seleccion - 1);
                baseSeleccionada.aumentarVida(3);
            }
            case NAVE_HACE_DAÑO_EXTRA -> {
                System.out.println("Elija la nave:");
                
                posicionX = leerPosicion("Ingrese la posición en X: ", dimension);
                posicionY = leerPosicion("Ingrese la posición en Y: ", dimension);
                posicionZ = leerPosicion("Ingrese la posición en Z: ", dimension);
                sector = tablero.obtenerSector(posicionX - 1, posicionY - 1, posicionZ - 1);
                pieza = sector.obtenerValor();

                if (pieza instanceof Nave nave) {
                    if (nave.obtenerDuenio().equals(jugadores[jugadorActual - 1])) {
                        nave.aumentarDanio(2); // Aumenta el daño de la nave en 2
                    } else {
                        System.out.println("No podés usar esta carta en una nave que no es tuya.");
                    }
                } else {
                    System.out.println("No hay una nave en esa posición.");
                }
            }
            case NAVE_HACE_DAÑO_EN_AREA -> {
                ListaEnlazada<Nave> naves = jugador.obtenerNaves();
                System.out.println("Elija la nave:");
                final int[] contador = {1};
                naves.iterar(nave -> {
                    int[] coords = nave.obtenerCoordenadas();
                    System.out.println(contador[0] + ". Nave en X: " + (coords[0] + 1) + " Y: " + (coords[1] + 1) + " Z: " + (coords[2] + 1));
                    contador[0]++;
                    return true;
                });
                int seleccion = leerEntero("Elija la nave a aumentar escudo: ");
                if (seleccion < 1 || seleccion >= contador[0]) {
                    System.out.println("Selección inválida.");
                    return;
                }
                Nave naveSeleccionada = naves.obtenerEnPosicion(seleccion - 1);
                int radio = 1;
                for (int dx = -radio; dx <= radio; dx++) {
                    for (int dy = -radio; dy <= radio; dy++) {
                        for (int dz = -radio; dz <= radio; dz++) {
                            int nuevaX = naveSeleccionada.obtenerCoordenadas()[0] + dx;
                            int nuevaY = naveSeleccionada.obtenerCoordenadas()[1] + dy;
                            int nuevaZ = naveSeleccionada.obtenerCoordenadas()[2] + dz;
                            if (coordenadasValidas(nuevaX, nuevaY, nuevaZ)) {
                                sector = tablero.obtenerSector(nuevaX, nuevaY, nuevaZ);
                                pieza = sector.obtenerValor();
                                if (pieza.obtenerTipo() != TipoPieza.VACIO) {
                                    atacarSector(nuevaX + 1, nuevaY + 1, nuevaZ + 1, naveSeleccionada);
                                }
                            }
                        }
                    }
                }
            }
            case NAVE_OBTIENE_ESCUDO -> {
                ListaEnlazada<Nave> naves = jugador.obtenerNaves();
                System.out.println("Elija la nave:");
                final int[] contador = {1};
                naves.iterar(nave -> {
                    int[] coords = nave.obtenerCoordenadas();
                    System.out.println(contador[0] + ". Nave en X: " + (coords[0] + 1) + " Y: " + (coords[1] + 1) + " Z: " + (coords[2] + 1));
                    contador[0]++;
                    return true;
                });
                int seleccion = leerEntero("Elija la nave a aumentar escudo: ");
                if (seleccion < 1 || seleccion >= contador[0]) {
                    System.out.println("Selección inválida.");
                    return;
                }
                Nave naveSeleccionada = naves.obtenerEnPosicion(seleccion - 1);
                naveSeleccionada.aumentarEscudo(3);
            }
            default -> {
                System.out.println("Tipo de carta no reconocido.");
            }
        }
        System.out.println("Usando carta: " + carta.getNombre());
    }
    
    // Método para mover naves
    private void moverNave() {
        ListaEnlazada<Nave> naves = jugadores[jugadorActual - 1].obtenerNaves();
    
        if (naves.estaVacia()) {
            System.out.println("No tenés naves para mover.");
            return;
        }
    
        System.out.println("Tus naves:");
        final int[] contador = {1};
        naves.iterar(nave -> {
            int[] coords = nave.obtenerCoordenadas();
            System.out.println(contador[0] + ". Nave en X: " + (coords[0] + 1) + " Y: " + (coords[1] + 1) + " Z: " + (coords[2] + 1));
            contador[0]++;
            return true;
        });
    
        int seleccion = leerEntero("Elegí el número de la nave a mover: ");
        if (seleccion < 1 || seleccion >= contador[0]) {
            System.out.println("Selección inválida.");
            return;
        }
    
        Nave naveAMover = naves.obtenerEnPosicion(seleccion - 1);
        int[] coordsViejas = naveAMover.obtenerCoordenadas();
    
        int nuevaX = leerPosicion("Nueva posición en X: ", dimension);
        int nuevaY = leerPosicion("Nueva posición en Y: ", dimension);
        int nuevaZ = leerPosicion("Nueva posición en Z: ", dimension);
    
        if (!coordenadasValidas(nuevaX - 1, nuevaY - 1, nuevaZ - 1)) {
            System.out.println("Posición fuera del tablero.");
            return;
        }
    
        if (!verificarDistancia(nuevaX - 1, nuevaY - 1, nuevaZ - 1)) {
            System.out.println("La nave debe mantenerse a 5 casilleros o menos de su base.");
            return;
        }
    
        if (hayPiezaEnPosicion(nuevaX - 1, nuevaY - 1, nuevaZ - 1)) {
            System.out.println("Ya hay una pieza en esa posición.");
            return;
        }
    
        tablero.asignarValor(coordsViejas[0], coordsViejas[1], coordsViejas[2], new Vacio(coordsViejas[0], coordsViejas[1], coordsViejas[2]));
        naveAMover.cambiarCoordenadas(nuevaX - 1, nuevaY - 1, nuevaZ - 1);
        tablero.asignarValor(nuevaX - 1, nuevaY - 1, nuevaZ - 1, naveAMover);
        System.out.println("Nave movida exitosamente.");
    }
    
    // Método para manejar la eliminación de un jugador
    private void perdedor(Jugador jugador) {
        System.out.println("El jugador " + jugador.obtenerNombre() + " ha sido eliminado.");
        ListaEnlazada<Nave> naves = jugador.obtenerNaves();
        naves.iterar((nave) -> {
            int[] coords = nave.obtenerCoordenadas();
            tablero.asignarValor(coords[0], coords[1], coords[2], new Radiacion(coords[0], coords[1], coords[2], 3));
            return true;
        });
        ListaEnlazada<Satelite> satelites = jugador.obtenerSatelites();
        satelites.iterar((satelite) -> {
            int[] coords = satelite.obtenerCoordenadas();
            tablero.asignarValor(coords[0], coords[1], coords[2], new Radiacion(coords[0], coords[1], coords[2], 3));
            return true;
        });
        while(naves.largo() > 0) {
            naves.borrarPrimero();
        }
        while(satelites.largo() > 0) {
            satelites.borrarPrimero();
        }
    }

    // Método para obtener los jugadores restantes después de eliminar al perdedor
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

    public void bajarTurnoARadiacion() {
        IteradorLista<Radiacion> iteradorRadiacion = listaRadiacion.iterador();
                while (iteradorRadiacion.haySiguiente()) {
                    Radiacion radiacion = iteradorRadiacion.verActual();
                    radiacion.reducirDuracion();
                    if (radiacion.obtenerDuracion() <= 0) {
                        int[] coordenadasRadiacion = radiacion.obtenerCoordenadas();
                        tablero.asignarValor(coordenadasRadiacion[0],coordenadasRadiacion[1], coordenadasRadiacion[2], new Vacio(coordenadasRadiacion[0], coordenadasRadiacion[1], coordenadasRadiacion[2]));
                        iteradorRadiacion.borrar();
                    }
                }
    }

    /* -------------- Metodos auxiliares -------------- */

    // Verificar si las coordenadas están dentro de los límites del tablero
    private boolean coordenadasValidas(int x, int y, int z) {
        return x >= 0 && x < dimension &&
               y >= 0 && y < dimension &&
               z >= 0 && z < dimension;
    }
    
    // Verificar si la casilla es propia
    private boolean esCasillaPropia(Pieza pieza) {
    if (pieza == null || pieza.obtenerTipo() == TipoPieza.VACIO) {
        return false;
    }
    Jugador duenio = pieza.obtenerDuenio();
    return duenio != null && duenio.equals(jugadores[jugadorActual - 1]);
}

    // Verificar si hay una pieza en la posición especificada
    private boolean hayPiezaEnPosicion(int x, int y, int z) {
        Pieza pieza = tablero.obtenerSector(x, y, z).obtenerValor();
        return !pieza.obtenerTipo().equals(TipoPieza.VACIO);
    }

    // Verificar si la distancia entre la base y la nave es menor o igual a 5
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
    
    // Verificar si la distancia entre un punto y la nave atacante esta en el rango permitido
    private boolean verificarDistancia(int x, int y, int z, Nave naveAtacante) {
        int[] coordsNave = naveAtacante.obtenerCoordenadas();
        int dx = coordsNave[0] - x;
        int dy = coordsNave[1] - y;
        int dz = coordsNave[2] - z;
        double distancia = Math.sqrt(dx * dx + dy * dy + dz * dz);
        return distancia <= LIMITE_DISTANCIA; // Verifica si la distancia es menor o igual a 5
    }

    // Método para leer un número entero positivo
    private int leerEntero(String mensaje) {
        int numero;
        while (true) {
            try {
                System.out.print(mensaje);
                numero = sc.nextInt();
                if (numero > 0) {
                    return numero;
                } else {
                    System.out.println("Debe ingresar un número entero positivo.");
                }
            } catch (Exception e) {
                System.out.println("Debe ingresar un número entero válido.");
                sc.nextLine();
            }
        }
    }

    // Método para leer un número entero mayor o igual a 2 (especificamente usada para las dimensiones del tablero)
    private int leerEnteroMayorA2(String mensaje) {
        int numero;
        while (true) {
            try {
                System.out.print(mensaje);
                numero = sc.nextInt();
                if (numero >= 2) {  // Asegurar que sea al menos 2
                    return numero;
                } else {
                    System.out.println("Debe ingresar un número mayor o igual a 2.");
                }
            } catch (Exception e) {
                System.out.println("Debe ingresar un número entero válido.");
                sc.nextLine(); // Limpiar el buffer del scanner
            }
        }
    }

    // Método para leer una posición en el tablero
    private int leerPosicion(String mensaje, int limite) {
        int posicion;
        while (true) {
            posicion = leerEntero(mensaje); // Lee la posición
            if (posicion >= 1 && posicion <= limite) { // Verifica si está dentro del rango
                return posicion;
            } else {
                System.out.println("Posición fuera de los límites del tablero. Debe estar entre 1 y " + limite);
            }
        }
    }

    // Método para obtener un nombre válido (no vacío)
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

}