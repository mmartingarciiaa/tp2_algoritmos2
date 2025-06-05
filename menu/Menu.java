package menu;

// Importaciones necesarias
import enums.*;
import estructuras.cola.ColaEnlazada;
import estructuras.lista.IteradorLista;
import estructuras.lista.ListaSimplementeEnlazada;
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
    
    private Tablero tablero; // Instancia de la clase Tablero
    private int dimension; // Dimensiones del tablero

    private int numJugadores;  // Número de jugadores
    private int jugadorActual = 1; // Jugador actual (1 a número de jugadores) [Usar una cola para el turno]
    private Jugador[] jugadores;    // Arreglo de jugadores
    private final ColaEnlazada<Jugador> colaJugadores = new ColaEnlazada<>(); // Cola para manejar el turno de los jugadores

    private final ListaSimplementeEnlazada<Jugador> jugadoresLista = new ListaSimplementeEnlazada<>();
    private final ListaSimplementeEnlazada<Alianza> alianzas = new ListaSimplementeEnlazada<>();
    private ListaSimplementeEnlazada<Pieza> piezasDetectadas = new ListaSimplementeEnlazada<>(); // Lista para almacenar piezas detectadas por satélites

    private Mazo mazo; // Instancia de la clase Mazo
    Scanner sc = new Scanner(System.in);    // Creo un objeto Scanner

    // Método para mostrar el mensaje inicial del juego y configurar el tablero
    public void mensajeInicial() {
        System.out.println("Bienvenidos a la Invasión Galáctica!");
        System.out.println("Este es un juego para máximo 6 jugadores.");

        int eleccion = leerEnteroEntreLimites("¿Desea (1) cargar una partida o (2) empezar una nueva?: ", 1, 2);
        switch (eleccion) {
            case 1 -> {
                String ruta = obtenerRutaValida("Ingrese la ruta al archivo txt: ");
                try {
                    this.tablero = CargadoDePartida.cargarPartida(ruta, colaJugadores);
                    int i = 0;
                    jugadores = new Jugador[colaJugadores.tamanio()];
                    while(!colaJugadores.estaVacia()){
                        jugadores[i] =  colaJugadores.desencolar();
                        i++;
                    }
                } catch (Exception e) {
                    System.out.println("Error al cargar la partida: " + e.getMessage());
                }
            }
            case 2 -> {
                numJugadores = leerEnteroEntreLimites("Ingrese el número de jugadores (2-6): ", 2, 6); // Leer el número de jugadores
                
                // Crear el arreglo de jugadores ahora que sabemos cuántos son
                jugadores = new Jugador[numJugadores];

                // Pedir los nombres de todos los jugadores
                sc.nextLine();
                for (int i = 0; i < numJugadores; i++) {
                    String nombre = obtenerNombreValido("Jugador " + (i + 1) + ", ingrese su nombre: ");
                    jugadores[i] = new Jugador(nombre);
                    colaJugadores.encolar(jugadores[i]); // Agregar jugador a la cola
                    jugadoresLista.insertarUltimo(jugadores[i]);
                }
                
                System.out.println("El tablero es un cubo del tamaño que establezca");
                dimension = leerEnteroEntreLimites("Tamaño (4-10): ", 4, 10); // Leer el tamaño del tablero
                
                tablero = new Tablero(dimension); // Crear el tablero con el tamaño especificado

                // Inicializar el tablero solo una vez
                tablero.inicializarTablero();
                this.crearBases();
            }
        }
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
                    Base base = new Base(jugadores[i], x - 1, y - 1, z - 1, BASE, VIDA_BASE, 0);
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
        Accion accion;
        ListaSimplementeEnlazada<Carta> listaCartas = CartaUtils.crearListaDeCartasBase(jugadores.length);
        mazo = new Mazo<>(listaCartas); // Crear el mazo de cartas base
        int cartasRobadas = 0; // Contador de cartas robadas

        while(jugadores.length > 1) {
            piezasDetectadas = obtenerPiezasDetectadasPorSatelite(jugadores[jugadorActual - 1]);
            int anchoImagen = dimension * (dimension * Bitmap.TAM_CELDA + Bitmap.ESPACIADO_CAPAS) - Bitmap.ESPACIADO_CAPAS;
            int altoImagen = dimension * Bitmap.TAM_CELDA;

            Bitmap bmp = new Bitmap(anchoImagen, altoImagen);

            try {
                bmp.generarBMPCompleto(tablero, jugadores[jugadorActual - 1], piezasDetectadas);
                bmp.guardarArchivo("tablero.bmp");
            } catch (IOException e) {
                System.out.println("Error al dibujar el tablero: " + e.getMessage());
            }
            //Jugador jugadorActual = colaJugadores.desencolar(); // Obtener el jugador actual de la cola
            System.err.println("\nTurno de jugador " + jugadores[jugadorActual - 1].obtenerNombre());
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

            int[] coordenadas;
            
            boolean cambioDeTurno = false;

            switch (accion) {
                case AGREGAR_NAVE -> {
                    coordenadas = solicitarCoordenadas("Ingrese la posición en ");
                    if (agregarNave(coordenadas[0], coordenadas[1], coordenadas[2])) {
                        cambioDeTurno = true;
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
                    }
                }
                case ATACAR_SECTOR -> {
                    Jugador jugador = jugadores[jugadorActual - 1];
                    ListaSimplementeEnlazada<Nave> navesDisponibles = jugador.obtenerNaves();
                
                    if (!jugador.puedeAtacar()) {
                        System.out.println(jugador.obtenerNombre() + " no tiene naves disponibles para atacar.");
                        break;
                    }
                
                    Nave naveSeleccionada = seleccionarPieza(navesDisponibles);
                    if (naveSeleccionada == null) {
                        System.out.println("Selección inválida.");
                        break;
                    }
                    
                    coordenadas = solicitarCoordenadas("Ingrese la posición en ");
                    if (atacarSector(coordenadas[0], coordenadas[1], coordenadas[2], naveSeleccionada)) {
                        cambioDeTurno = true;
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
                    cambioDeTurno = true;
                }
                case ALIANZA -> {
                    sc.nextLine(); // justo antes de este bloque
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
                                System.out.println("DEBUG: Eleccion ingresada -> [" + eleccion + "]");
                            }
                            if (eleccion.equals("SI")) {
                                Alianza nuevaAlianza = new Alianza(jugadores[jugadorActual - 1], jugador);
                                alianzas.insertarUltimo(nuevaAlianza);
                                jugadores[jugadorActual - 1].agregarAlianza(nuevaAlianza);
                                jugador.agregarAlianza(nuevaAlianza);
                                System.out.println("Alianza formada entre " + jugadores[jugadorActual - 1].obtenerNombre() + " y " + jugador.obtenerNombre() + ".");
                            } else {
                                System.out.println("Alianza no formada.");
                            }
                            break; // sale del for
                        }
                    }
                    if (!encontrado) {
                        System.out.println("Jugador no encontrado. Asegúrese de que el nombre sea correcto.");
                    }
                }
                case DEJAR_DE_JUGAR -> {
                    sc.nextLine();                    
                    String eleccion = "";
                    while (!(eleccion.equals("SI") || eleccion.equals("NO"))) {
                        System.out.println("¿Desea guardar la partida? (SI/NO)");
                        eleccion = sc.nextLine().trim().toUpperCase();
                        System.out.println("DEBUG: Eleccion ingresada -> [" + eleccion + "]");
                    }
                    if (eleccion.equals("SI")) {
                        try {
                            GuardadoDePartidaEnArchivo.guardarPartida(tablero, jugadoresLista);
                        } catch (IOException e) {
                            System.out.println("Error al guardar la partida: " + e.getMessage());
                        }
                    }
                    System.out.println("Gracias por jugar!");
                    return;
                }
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }

            //! verificacion de cambio de turno
            if(cambioDeTurno) {
                cartasRobadas = 0; // Reiniciar el contador de cartas robadas
                jugadorActual = (jugadorActual % numJugadores) + 1; // Cambiar al siguiente jugador
                //! reducir duracion de escudos y radiaciones
                ListaSimplementeEnlazada<Pieza> piezasNoVacias = tablero.obtenerPiezasNoVacias();
                IteradorLista<Pieza> iter = piezasNoVacias.iterador();
                while (iter.haySiguiente()) {
                    Pieza pieza = iter.verActual();
                    if (pieza.obtenerEscudo() > 0 && (pieza.obtenerTipo() == TipoPieza.BASE || 
                        pieza.obtenerTipo() == TipoPieza.NAVE)) {
                        pieza.reducirEscudo(1);
                    }
                    if (pieza.obtenerTipo() == TipoPieza.RADIACION) {
                        Radiacion radiacion = (Radiacion) pieza;
                        radiacion.reducirDuracion();
                        if (!radiacion.estaActiva()) {
                            tablero.asignarValor(pieza.obtenerCoordenadas()[0], pieza.obtenerCoordenadas()[1], pieza.obtenerCoordenadas()[2], new Vacio(pieza.obtenerCoordenadas()[0], pieza.obtenerCoordenadas()[1], pieza.obtenerCoordenadas()[2]));
                            iter.borrar();
                            
                        }
                    }
                    iter.siguiente();
                }

                //! Reducir la duración de las alianzas activas
                IteradorLista<Alianza> iterAlianzas = alianzas.iterador();
                while (iterAlianzas.haySiguiente()) {
                    Alianza alianza = iterAlianzas.verActual();
                    alianza.reducirDuracion();
                    if (!alianza.estaActiva()) {
                        Jugador[] aliados = alianza.obtenerJugadores();
                        for (Jugador aliado : aliados) {
                            aliado.eliminarAlianza(alianza);
                        }
                        iterAlianzas.borrar(); // Eliminar la alianza si ya no está activa
                    }
                    iterAlianzas.siguiente();
                }
            }
            //ListaSimplementeEnlazada<Pieza> informacionSatelite = obtenerPiezasDetectadasPorSatelite(jugadores[jugadorActual - 1]);
        }
    }

    // Método para agregar una nave en el tablero
    private boolean agregarNave(int x, int y, int z) {

        if (!coordenadasValidas(x, y, z)) {
            System.out.println("Posición fuera de los límites.");
            return false;
        }

        if (!verificarDistancia(x, y, z)) {
            System.out.println("La nave debe estar a 5 casilleros o menos de su base.");
            return false;
        }

        if (hayPiezaEnPosicion(x, y, z)) {
            System.out.println("Error: No podes colocar una nave en esta posición.");
            return false;
        }

        Nave nave = new Nave(jugadores[jugadorActual - 1], x, y, z, NAVE, VIDA_NAVE, 1);
        tablero.asignarValor(x, y, z, nave);

        // Incrementar la cantidad de naves del jugador actual
        jugadores[jugadorActual - 1].agregarNave(nave);

        return true;
    }
    
    // Método para agregar un satélite en el tablero
    private boolean agregarSatelite(int x, int y, int z) {

        if (!coordenadasValidas(x, y, z)) {
            System.out.println("Posición fuera de los límites.");
            return false;
        }

        if (hayPiezaEnPosicion(x, y, z)) {
            System.out.println("Error: No podes colocar un satélite en esta posición.");
            return false;
        }

        Satelite satelite = new Satelite(jugadores[jugadorActual - 1], x, y, z, SATELITE, VIDA_SATELITE, dimension / 4);
        tablero.asignarValor(x, y, z, satelite);

        // Incrementar la cantidad de satélites del jugador actual
        jugadores[jugadorActual - 1].agregarSatelite(satelite);

        return true;
    }
    
    // Método para atacar un sector del tablero
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
        int danio = naveAtacante.obtenerDanio(); // Daño infligido por la nave atacante
        Radiacion radiacion = new Radiacion(x, y, z, 5); // Variable para almacenar la radiación si es necesario
        boolean piezaDestruida = false; // Variable para verificar si la base fue destruida
        switch (ficha.obtenerTipo()) {
            case BASE -> {
                if (ficha instanceof Base base) {
                    int vida = base.obtenerVida();
                    if (base.obtenerEscudo() > 0) {
                        base.reducirEscudo(danio);
                        System.out.println("¡La base ha sido atacada! Escudo restante: " + base.obtenerEscudo());
                        if (vida != base.obtenerVida()) {
                            System.out.println("¡La base ha sido atacada! Vida restante: " + base.obtenerVida());
                            tablero.asignarValor(x, y, z, ficha); // Actualiza con vida reducida
                        }
                    } else {
                        base.reducirVida(danio);
                        if (base.obtenerVida() <= 0) {
                            System.out.println("¡La base ha sido destruida!");
                            piezaDestruida = true;
                            duenio.eliminarBase(x, y, z);
                            base.destruirPieza();
                            if (duenio.obtenerBases().estaVacia()) {
                                System.out.println("¡El jugador " + duenio.obtenerNombre() + " ha sido eliminado!");
                                jugadores = jugadoresRestantes(jugadores, duenio);
                                perdedor(duenio);
                            }
                        } else {
                            System.out.println("¡La base ha sido atacada! Vida restante: " + base.obtenerVida());
                            tablero.asignarValor(x, y, z, base); // Actualiza con vida reducida
                        }
                    }
                }
            }
            case NAVE -> {
                if (ficha.obtenerEscudo() > 0) {
                    ficha.reducirEscudo(danio);
                    System.out.println("¡Escudo reducido a la nave!");
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
            tablero.obtenerPiezasNoVacias().insertarUltimo(radiacion);
        }

        return true;
    }
    
    // Método para usar una carta del jugador actual
    private void usarCarta(Carta carta, Jugador jugador) {
        System.out.println("Usando carta: " + carta.getNombre());
        int[] coordenadas;
        Sector sector;
        Pieza pieza;
        switch (carta.getTipo()) {
            case CAMPO_DE_FUERZA -> {
                Base baseSeleccionada = seleccionarPieza(jugador.obtenerBases());
                baseSeleccionada.aumentarEscudo(3);
            }
            case RASTREADOR_CUANTICO -> {
                coordenadas = solicitarCoordenadas("Ingrese la coordenada en ");
                int radioRandom = new Random().nextInt(dimension / 2) + 1; // Radio aleatorio entre 1 y la mitad de la dimension del tablero
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
                moverNave(); // Permite mover dos veces en el mismo turno
            }
            case BASE_ADICIONAL -> {
                crearBase(jugadorActual - 1); // Crea una base adicional para el jugador actual 
            }
            case SUMAR_VIDA_A_BASE -> {
                Base baseSeleccionada = seleccionarPieza(jugador.obtenerBases());
                if (baseSeleccionada == null) {
                    System.out.println("Selección inválida.");
                    return;
                }
                baseSeleccionada.aumentarVida(3);
            }
            case NAVE_HACE_DAÑO_EXTRA -> {
                System.out.println("Elija la nave:");
                coordenadas = solicitarCoordenadas("Ingrese la posición en ");
                sector = tablero.obtenerSector(coordenadas[0] - 1, coordenadas[1] - 1, coordenadas[2] - 1);
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
                Nave naveSeleccionada = seleccionarPieza(jugador.obtenerNaves());
                if (naveSeleccionada == null) {
                    System.out.println("Selección inválida.");
                    return;
                }
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
                Nave naveSeleccionada = seleccionarPieza(jugador.obtenerNaves());
                if (naveSeleccionada == null) {
                    System.out.println("Selección inválida.");
                    return;
                }
                naveSeleccionada.aumentarEscudo(3);
            }
            default -> {
                System.out.println("Tipo de carta no reconocido.");
            }
        }
        System.out.println("Carta usada exitosamente.");
    }
    
    // Método para mover naves
    private boolean moverNave() {
        ListaSimplementeEnlazada<Nave> naves = jugadores[jugadorActual - 1].obtenerNaves();
    
        if (naves.estaVacia()) {
            System.out.println("No tenés naves para mover.");
            return false;
        }
    
        Nave naveAMover = seleccionarPieza(naves);
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
    
        if (hayPiezaEnPosicion(nuevaCoordenadas[0], nuevaCoordenadas[1], nuevaCoordenadas[2])) {
            System.out.println("Ya hay una pieza en esa posición.");
            return false;
        }
    
        tablero.asignarValor(coordsViejas[0], coordsViejas[1], coordsViejas[2], new Vacio(coordsViejas[0], coordsViejas[1], coordsViejas[2]));
        naveAMover.cambiarCoordenadas(nuevaCoordenadas[0], nuevaCoordenadas[1], nuevaCoordenadas[2]);
        tablero.asignarValor(nuevaCoordenadas[0], nuevaCoordenadas[1], nuevaCoordenadas[2], naveAMover);
        System.out.println("Nave movida exitosamente.");
        return true;
    }
    
    // Método para manejar la eliminación de un jugador
    private void perdedor(Jugador jugador) {
        System.out.println("El jugador " + jugador.obtenerNombre() + " ha sido eliminado.");
        ListaSimplementeEnlazada<Nave> naves = jugador.obtenerNaves();
        naves.iterar((nave) -> {
            int[] coords = nave.obtenerCoordenadas();
            tablero.asignarValor(coords[0], coords[1], coords[2], new Radiacion(coords[0], coords[1], coords[2], 3));
            return true;
        });
        ListaSimplementeEnlazada<Satelite> satelites = jugador.obtenerSatelites();
        satelites.iterar((satelite) -> {
            int[] coords = satelite.obtenerCoordenadas();
            tablero.asignarValor(coords[0], coords[1], coords[2], new Radiacion(coords[0], coords[1], coords[2], 3));
            return true;
        });
        IteradorLista<Nave> iterNaves = naves.iterador();
        while (iterNaves.haySiguiente()) {
            iterNaves.borrar();
            iterNaves.siguiente();
        }
        IteradorLista<Satelite> iterSatelites = satelites.iterador();
        while (iterSatelites.haySiguiente()) {
            iterSatelites.borrar();
            iterSatelites.siguiente();
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

    /* -------------- Metodos auxiliares -------------- */

    public <T extends Pieza> T seleccionarPieza(ListaSimplementeEnlazada<T> piezas) {
        System.out.println("Tus naves:");
        final int[] contador = {1};
        piezas.iterar(pieza -> {
            int[] coords = pieza.obtenerCoordenadas();
            System.out.println(contador[0] + ". Nave en X: " + (coords[0] + 1) + " Y: " + (coords[1] + 1) + " Z: " + (coords[2] + 1));
            contador[0]++;
            return true;
        });

        int seleccion = leerEntero("Elegí el número de la nave a mover: ");
        if (seleccion < 1 || seleccion >= contador[0]) {
            System.out.println("Selección inválida.");
            return null;
        }

        return piezas.obtenerEnPosicion(seleccion); // devuelve T (Pieza o subclase)
    }

    // Método para solicitar las coordenadas al usuario
    private int[] solicitarCoordenadas(String mensaje) {
        int x = leerPosicion(mensaje + "X: ", dimension) - 1;
        int y = leerPosicion(mensaje + "Y: ", dimension) - 1;
        int z = leerPosicion(mensaje + "Z: ", dimension) - 1;
        return new int[]{x, y, z};
    }

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
    // Método para obtener una ruta valida
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