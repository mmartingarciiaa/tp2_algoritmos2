import java.io.IOException;
import java.util.Scanner;
import tdas.lista.IteradorLista;
import tdas.lista.ListaEnlazada;

public class Menu {
    // Enumeración para las acciones posibles en el menú
    public enum Accion {
        AGREGAR_NAVE(1),
        MOVER_NAVE(2),
        AGREGAR_SATELITE(3),
        ATACAR_SECTOR(4),
        ROBAR_CARTA(5),
        USAR_CARTA(6),
        ALIANZA(7),
        DEJAR_DE_JUGAR(8);

        private final int codigo;

        Accion(int codigo) {
            this.codigo = codigo;
        }

        public int getCodigo() {
            return codigo;
        }

        public static Accion desdeCodigo(int codigo) {
            for (Accion accion : values()) {
                if (accion.codigo == codigo) {
                    return accion;
                }
            }
            throw new IllegalArgumentException("Código de acción inválido: " + codigo);
        }
    }

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

    private Mazo mazo; // Instancia de la clase Mazo
    Scanner sc = new Scanner(System.in);    // Creo un objeto Scanner

    // Método para mostrar el mensaje inicial del juego y configurar el tablero
    public void mensajeInicial() {
        System.out.println("Bienvenidos a la Invasión Galáctica!");
        System.out.println("Este es un juego para máximo 6 jugadores.");
        numJugadores = leerEnteroMayorA2("Ingrese el número de jugadores (2-6): ");

        // Crear el arreglo de jugadores ahora que sabemos cuántos son
        jugadores = new Jugador[numJugadores];

        // Pedir los nombres de todos los jugadores
        for (int i = 0; i < numJugadores; i++) {
            String nombre = obtenerNombreValido("Jugador " + (i + 1) + ", ingrese su nombre: ");
            jugadores[i] = new Jugador(nombre);
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
            boolean baseCreada = false;
            while (!baseCreada) {
                System.out.println(jugadores[i].obtenerNombre() + ", ingrese la posición de su base:");
                int x = leerPosicion("X: ", dimension);
                int y = leerPosicion("Y: ", dimension);
                int z = leerPosicion("Z: ", dimension);
                Pieza pieza = tablero.obtenerSector(x - 1, y - 1, z - 1).obtenerValor();
                if (pieza.obtenerTipo().equals(TipoPieza.VACIO)) {
                    Base base = new Base(TipoPieza.BASE, jugadores[i], x - 1, y - 1, z - 1, BASE, VIDA_BASE);
                    tablero.asignarValor(x - 1, y - 1, z - 1, base);
                    jugadores[i].agregarBase(base);
                    baseCreada = true;
                } else {
                    System.out.println("¡Error! Ya existe una pieza en esa posición.");
                }
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
            System.err.println("Turno de jugador " + jugadores[jugadorActual - 1].obtenerNombre());
            System.out.println("Opciones:");
            System.out.println("1. Agregar nave"); // 
            System.out.println("2. Mover nave (no implementado)");
            System.out.println("3. Agregar satélite (no implementado)");
            System.out.println("4. Atacar sector");
            System.out.println("5. Robar carta (no implementado)");
            System.out.println("6. Usar carta (no implementado)");
            System.out.println("7. Alianza (no implementado)");
            System.out.println("8. Dejar de jugar");

            int opcion = leerEntero("Ingrese una opción: ");

            accion = Accion.desdeCodigo(opcion);

            int posicionX, posicionY, posicionZ;

            switch (accion) {
                case AGREGAR_NAVE:
                    posicionX = leerPosicion("Ingrese la posición en X: ", dimension);
                    posicionY = leerPosicion("Ingrese la posición en Y: ", dimension);
                    posicionZ = leerPosicion("Ingrese la posición en Z: ", dimension);
                    if (agregarNave(posicionX, posicionY, posicionZ)) {
                        jugadorActual = (jugadorActual % numJugadores) + 1; // Cambiar jugador
                    }
                    break;
                case MOVER_NAVE:
                    moverNave();
                    jugadorActual = (jugadorActual % numJugadores) + 1; // Cambiar jugador
                    break;
                case AGREGAR_SATELITE:
                    System.out.println("Agregar satélite no implementado.");
                    break;
                case ATACAR_SECTOR:
                    Jugador jugador = jugadores[jugadorActual - 1];
                    ListaEnlazada<Nave> navesDisponibles = jugador.obtenerNaves();
                
                    if (!jugador.puedeAtacar()) {
                        System.out.println(jugador.obtenerNombre() + " no tiene naves disponibles para atacar.");
                        break;
                    }
                
                    System.out.println("Tus naves:");
                    // Enumero y muestro las naves disponibles
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
                        jugadorActual = (jugadorActual % numJugadores) + 1;
                    }
                    break;
                
                case ROBAR_CARTA:
                    jugadores[jugadorActual - 1].agregarCarta((Carta) mazo.sacarCarta());
                    break;
                case USAR_CARTA:
                    Carta cartaAUsar = jugadores[jugadorActual - 1].sacarCarta();
                    usarCarta(cartaAUsar);
                    break;
                case ALIANZA:
                    System.out.println("Alianza no implementado.");
                    break;
                case DEJAR_DE_JUGAR:
                    System.out.println("Gracias por jugar!");
                    return;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
            int anchoImagen = dimension * (dimension * Bitmap.TAM_CELDA + Bitmap.ESPACIADO_CAPAS) - Bitmap.ESPACIADO_CAPAS;
            int altoImagen = dimension * Bitmap.TAM_CELDA;

            Bitmap bmp = new Bitmap(anchoImagen, altoImagen);

            try {
                bmp.generarBMPCompleto(tablero, jugadores[jugadorActual - 1]);
                bmp.guardarArchivo("tablero.bmp");
            } catch (IOException e) {
                System.out.println("Error al dibujar el tablero: " + e.getMessage());
            }
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

        if (!verificarDistancia(x, y, z)) {
            System.out.println("La nave debe estar a 5 casilleros o menos de su base.");
            return false;
        }

        if (hayPiezaEnPosicion(XAjustado, YAjustado, ZAjustado)) {
            System.out.println("Error: No podes colocar una nave en esta posición.");
            return false;
        }

        Nave nave = new Nave(TipoPieza.NAVE, jugadores[jugadorActual - 1], XAjustado, YAjustado, ZAjustado, NAVE, VIDA_NAVE, 1);
        tablero.asignarValor(XAjustado, YAjustado, ZAjustado, nave);
        
        // Incrementar la cantidad de naves del jugador actual
        jugadores[jugadorActual - 1].agregarNave(nave);

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
    
        //if (!verificarDistancia(x, y, z)) {
        //    System.out.println("El ataque debe ser a menos de 5 casilleros de la nave.");
        //    return false;
        //}
    
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
    
        switch (ficha.obtenerTipo()) {
            case BASE:
                if (ficha instanceof Base base) {
                    base.reducirEscudo(danio);
                    //if (base.obtenerEscudo() <= 0) {
                    //    System.out.println("¡La base ha sido destruida!");
                    //    tablero.asignarValor(XAjustado, YAjustado, ZAjustado, new Pieza(TipoPieza.RADIACION, 5));
                    //    duenio.eliminarBase(XAjustado, YAjustado, ZAjustado);
                    //    if (duenio.obtenerBases().estaVacia()) {
                    //        System.out.println("¡El jugador " + duenio.obtenerNombre() + " ha sido eliminado!");
                    //        jugadores = jugadoresRestantes(jugadores, duenio);
                    //        perdedor(duenio);
                    //    }
                    //} else {
                    //    System.out.println("¡La base ha sido atacada! Escudo restante: " + ficha.obtenerEscudo());
                    //    tablero.asignarValor(XAjustado, YAjustado, ZAjustado, ficha); // Actualiza con escudo reducido
                    //}
                }
                break;
    
            case NAVE:
            case SATELITE:
                System.out.println("¡" + ficha.obtenerTipo() + " enemigo ha sido destruido!");
                tablero.asignarValor(XAjustado, YAjustado, ZAjustado, null);
                duenio.eliminarNave(x, y, z);
                break;
        }
    
        return true;
    }
    
    
    private void usarCarta(Carta carta) {
        int posicionX, posicionY, posicionZ;
        Sector sector;
        Pieza pieza;
        switch (carta.getTipo()) {
            case CAMPO_DE_FUERZA:
                System.out.println("Elija la base:");
                    
                posicionX = leerPosicion("Ingrese la posición en X: ", dimension);
                posicionY = leerPosicion("Ingrese la posición en Y: ", dimension);
                posicionZ = leerPosicion("Ingrese la posición en Z: ", dimension);
                sector = tablero.obtenerSector(posicionX - 1, posicionY - 1, posicionZ - 1);
                pieza = sector.obtenerValor();

                if (pieza instanceof Base base) {
                    if (base.obtenerDuenio().equals(jugadores[jugadorActual - 1])) {
                        base.aumentarEscudo(3);
                    }
                } 
                break;
            case RASTREADOR_CUANTICO:
                // Lógica para usar el rastreador cuántico
                break;
            case DOBLE_SALTO_HIPERESPACIAL:
                // Lógica para usar el doble salto hiperespacial
                break;
            case BASE_ADICIONAL:
                // Lógica para usar la base adicional
                break;
            case SUMAR_VIDA_A_BASE:
                System.out.println("Elija la base:");
                
                posicionX = leerPosicion("Ingrese la posición en X: ", dimension);
                posicionY = leerPosicion("Ingrese la posición en Y: ", dimension);
                posicionZ = leerPosicion("Ingrese la posición en Z: ", dimension);
                sector = tablero.obtenerSector(posicionX - 1, posicionY - 1, posicionZ - 1);
                pieza = sector.obtenerValor();

                if (pieza instanceof Base base) {
                    if (base.obtenerDuenio().equals(jugadores[jugadorActual - 1])) {
                        base.aumentarVida(3);
                    }
                } 
                break;
            case NAVE_HACE_DAÑO_EXTRA:
                // Lógica para hacer daño extra con la nave
                break;
            case NAVE_HACE_DAÑO_EN_AREA:
                // Lógica para hacer daño en área con la nave
                break;
            case NAVE_OBTIENE_ESCUDO:
                /*ListaEnlazada<Pieza> naves = jugadores[jugadorActual - 1].obtenerNaves();
                naves.iterar(nave -> {
                    nave.aumentarEscudo(1);
                    return true;
                });*/
                break;
            default:
                System.out.println("Tipo de carta no reconocido.");
                break;
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
    
        if (!verificarDistancia(nuevaX, nuevaY, nuevaZ)) {
            System.out.println("La nave debe mantenerse a 5 casilleros o menos de su base.");
            return;
        }
    
        if (hayPiezaEnPosicion(nuevaX - 1, nuevaY - 1, nuevaZ - 1)) {
            System.out.println("Ya hay una pieza en esa posición.");
            return;
        }
    
        tablero.asignarValor(coordsViejas[0], coordsViejas[1], coordsViejas[2], new Pieza(TipoPieza.VACIO, null, coordsViejas[0], coordsViejas[1], coordsViejas[2], "_", 0));
        naveAMover.cambiarCoordenadas(nuevaX - 1, nuevaY - 1, nuevaZ - 1);
        tablero.asignarValor(nuevaX - 1, nuevaY - 1, nuevaZ - 1, naveAMover);
    
        System.out.println("Nave movida exitosamente.");
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
            int dx = coords[0] - (x - 1);
            int dy = coords[1] - (y - 1);
            int dz = coords[2] - (z - 1);
            double distancia = Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (distancia <= LIMITE_DISTANCIA) {
                return true; // La nave está dentro del rango de la base
            }
            iter.siguiente();
        }
        return false; // No se encontró ninguna base dentro del rango
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
        String nombre;
        do {
            System.out.print(mensaje);
            nombre = sc.nextLine().trim(); // Elimina espacios en blanco al inicio y al final
            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacío. Inténtalo de nuevo.");
            }
        } while (nombre.isEmpty());
        return nombre;
    }
}