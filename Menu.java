import java.util.Scanner;
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
    
    private final String NAVE = "N";  // Caracter para representar una nave
    private final String BASE = "B";  // Caracter para representar una base
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
                Pieza pieza = tablero.obtenerSector(x, y, z).obtenerValor();
                if (pieza.obtenerTipo().equals(VACIO)) {
                    Pieza base = new Pieza(tipo, dueño, x, y, z, BASE + (i + 1)); 
                    tablero.asignarValor(x, y, z, base);
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
        Accion accion;
        ListaEnlazada<Carta> listaCartas = CartaUtils.crearListaDeCartasBase(jugadores.length);
        mazo = new Mazo(listaCartas); // Crear el mazo de cartas base
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
                    
                    break;
                case AGREGAR_SATELITE:
                    System.out.println("Agregar satélite no implementado.");
                    break;
                case ATACAR_SECTOR:
                    if (!jugadores[jugadorActual - 1].puedeAtacar()) {
                        System.out.println(jugadores[jugadorActual - 1].obtenerNombre() + " no tiene naves disponibles para atacar.");
                        continue;  // No permitir atacar si no tiene naves
                    }
                    posicionX = leerPosicion("Ingrese la posición en X: ", dimension);
                    posicionY = leerPosicion("Ingrese la posición en Y: ", dimension);
                    posicionZ = leerPosicion("Ingrese la posición en Z: ", dimension);
                    if (atacarSector(posicionX, posicionY, posicionZ)) {
                        jugadorActual = (jugadorActual % numJugadores) + 1; // Cambiar jugador
                    }
                    break;
                case ROBAR_CARTA:
                    jugadores[jugadorActual - 1].agregarCarta(mazo.sacarCarta());
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
        }
        finDelJuego(jugadorActual);
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

        Pieza nave = new Pieza(NAVE, x, y, z, NAVE + jugadorActual);
        tablero.asignarValor(x, y, z, nave);
        
        // Incrementar la cantidad de naves del jugador actual
        jugadores[jugadorActual - 1].agregarNave(nave);

        tablero.imprimirTablero(tablero);
        return true;
    }

    // Método para atacar un sector del tablero
    private boolean atacarSector(int x, int y, int z) {
        int XAjustado = x - 1; // Ajustar para ser cero-basado
        int YAjustado = y - 1; // Ajustar para ser cero-basado
        int ZAjustado = z - 1; // Z siempre es 0 para el ataque

        if (!coordenadasValidas(XAjustado, YAjustado, ZAjustado)) {
            System.out.println("Posición fuera de los límites.");
            return false;
        }
    
        if (!verificarDistancia(x, y, z)) {
            System.out.println("El ataque debe ser a menos de 5 casilleros de la nave.");
            return false;
        }

        Sector sector = tablero.obtenerSector(XAjustado, YAjustado, ZAjustado);
        //en Pieza guardamos una variable que sea Dueño y aca solo preguntamos que sea igual a jugActual
        if (esCasillaPropia(sector)) {
            System.out.println("No podés atacar tus propias unidades.");
            return false;
        }
    
        tipoPieza tipo = sector.obtenerValor().obtenerTipo();
        if (tipo.equals(RADIACION)) {
            System.out.println("No se puede atacar un sector con radiación.");
            return false;
        }

        Pieza ficha = sector.obtenerValor();

        if (tipo.equals(BASE)) {
            ficha.reducirEscudo();

            if (ficha.obtenerEscudo() <= 0) {
                System.out.println("¡La base ha sido destruida!");
                ficha.cambiarTipo(RADIACION);
                tablero.asignarValor(XAjustado, YAjustado, ZAjustado, ficha);
                //aca le pasamos un "base.dueño" y ese es el que pierde
                jugadores = jugadoresRestantes(jugadores, ficha.dueño);
                perdedor(ficha.dueño);
            } else {
                System.out.println("¡La base ha sido atacada! Escudo restante: " + ficha.obtenerEscudo());
            }
        }

        if (tipo.equals(NAVE)) {
            System.out.println("¡La nave enemiga ha sido destruida!");
            ficha.cambiarTipo(RADIACION);
            tablero.asignarValor(XAjustado, YAjustado, ZAjustado, ficha);
            jugadores[jugadorActual - 1].eliminarNave(XAjustado, YAjustado, ZAjustado);
        }
    
        return true;
    }
    
    private void usarCarta(Carta carta) {
        switch (carta.getTipo()) {
            case CAMPO_DE_FUERZA:
                // Lógica para usar el campo de fuerza
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
                
                int posicionX = leerPosicion("Ingrese la posición en X: ", dimension);
                int posicionY = leerPosicion("Ingrese la posición en Y: ", dimension);
                int posicionZ = leerPosicion("Ingrese la posición en Z: ", dimension);
                Sector sector = tablero.obtenerSector(posicionX - 1, posicionY - 1, posicionZ - 1);
                Pieza base = sector.obtenerValor();
                if (base.dueño.equals(jugadores[jugadorActual - 1])) {
                    base.aumentarEscudo(3);
                }
                break;
            case NAVE_HACE_DAÑO_EXTRA:
                // Lógica para hacer daño extra con la nave
                break;
            case NAVE_HACE_DAÑO_EN_AREA:
                // Lógica para hacer daño en área con la nave
                break;
            case NAVE_OBTIENE_ESCUDO:
                for (Pieza nave : jugadores[jugadorActual - 1].obtenerNaves()) {
                    nave.aumentarEscudo(3); // Aumentar el escudo de la nave en 1
                }
                break;
            default:
                System.out.println("Tipo de carta no reconocido.");
                break;
        }
        System.out.println("Usando carta: " + carta.getNombre());
    }
    
    // Método para mover naves
    /**
     * mostrar todas las naves, el usuario elige la nave y las coords nuevas
     * 
     * eliminamos del tablero esa nave en dichas coords y en su 
     * 
     */
    private void moverNave(int x, int y, int z) {
        ListaEnlazada<Pieza> naves = jugadores[jugadorActual - 1].obtenerNaves();
        System.out.println("Elija la base:");
        for (Pieza base : bases) {
            int[] coords = base.obtenerCoordenadas();
            System.out.println(base.obtenerValor() + "X: " + coords[0] + " Y: " + coords[1] + " Z: " + coords[2]);
        }
        int posicionX = leerPosicion("Ingrese la posición en X: ", dimension);
        int posicionY = leerPosicion("Ingrese la posición en Y: ", dimension);
        int posicionZ = leerPosicion("Ingrese la posición en Z: ", dimension);
    }
    // Método para finalizar el juego
    private void finDelJuego(int jugador) {
        System.out.println("¡El jugador " + ((jugador == 1) ? jugador1.nombre : jugador2.nombre) + " ha ganado!");
        System.out.println("¡Gracias por jugar!");
        System.exit(0);  // Salir del programa
    }

    /* -------------- Metodos auxiliares -------------- */
    // Verificar si las coordenadas están dentro de los límites del tablero
    private boolean coordenadasValidas(int x, int y, int z) {
        return x >= 0 && x < tablero.TAMANIO && y >= 0 && y < tablero.TAMANIO && z >= 0 && z < tablero.TAMANIO;
    }

    // Verificar si la casilla es propia
    private boolean esCasillaPropia(int x, int y, int z) {
        String valor = tablero.tablero[x][y].getValor();
        return valor.equals(BASE + jugadorActual) || valor.equals(NAVE + jugadorActual);
    }

    // Verificar si la distancia entre la base y la nave es menor o igual a 5
    private boolean verificarDistancia(int x, int y, int z) {
        // Obtener las coordenadas de la base del jugador actual (cero-basadas)
        int baseX = jugadores[jugadorActual - 1].baseX - 1;
        int baseY = jugadores[jugadorActual - 1].baseY - 1;
        int baseZ = jugadores[jugadorActual - 1].baseZ - 1;  // Añadir la coordenada Z de la base
    
        // Calcular las diferencias absolutas en cada dimensión
        int difX = Math.abs(x - baseX);
        int difY = Math.abs(y - baseY);
        int difZ = Math.abs(z - baseZ);  // Diferencia en la dimensión Z
    
        // Verificar si la diferencia en las tres dimensiones es menor o igual al límite
        return difX <= LIMITE_DISTANCIA && difY <= LIMITE_DISTANCIA && difZ <= LIMITE_DISTANCIA;
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
