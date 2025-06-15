package tablero;

// Importaciones necesarias
import Coordenada.Coordenada;
import estructuras.lista.IteradorLista;
import estructuras.lista.ListaSimplementeEnlazada;
import piezas.Pieza;
import piezas.Vacio;

/**
 * Clase Tablero representa un tablero tridimensional de juego,
 * donde cada sector puede contener una pieza y se pueden realizar
 * operaciones para inicializar, asignar valores y obtener sectores.
 */

public class Tablero {
    private final int tamanio;
	private final ListaSimplementeEnlazada<Sector>[] tablero;
	private final ListaSimplementeEnlazada<Pieza> piezasNoVacias;

	/**
	 * Constructor de la clase Tablero.
	 * Inicializa un tablero de tamaño dimension x dimension x dimension.
	 *
	 * @param dimension Tamaño del tablero (debe ser positivo).
	 * @throws RuntimeException si la dimensión es menor o igual a cero.
	 */
	@SuppressWarnings("unchecked")
	public Tablero(int dimension) {
		if (dimension <= 0) {
			throw new RuntimeException("La dimensión del tablero debe ser mayor a cero");
		}
		this.tamanio = dimension;
		this.tablero = (ListaSimplementeEnlazada<Sector>[]) new ListaSimplementeEnlazada[tamanio];
		this.piezasNoVacias = new ListaSimplementeEnlazada<>();
	}

	/**
	 * Devuelve la dimensión del tablero.
	 */
	public int obtenerDimension() {
		return tamanio;
	}

    /**
	 * Inicializa el tablero creando sectores vacíos en cada posición.
	 * Cada sector se inicializa con una pieza de tipo VACIO.
	 */
    public void inicializarTablero() {
		for (int x = 0; x < tamanio; x++) {
			tablero[x] = new ListaSimplementeEnlazada<>();

			for (int y = 0; y < tamanio; y++) {
				for (int z = 0; z < tamanio; z++) {
					Coordenada coordenadas = new Coordenada(x, y, z);
					Vacio pieza = new Vacio(coordenadas);
					Sector sector = new Sector(coordenadas, pieza);
					tablero[x].insertarUltimo(sector);
				}
			}
		}
	}
    
	/**
	 * Asigna un valor a un sector específico del tablero.
	 * Si el valor es diferente de VACIO, se agrega a la lista de piezas no vacías.
	 *
	 * @param coordenadas Coordenadas del sector donde se asignará el valor.
	 * @param valor Pieza a asignar al sector.
	 * 
	 * @throws RuntimeException si las coordenadas están fuera de los límites del tablero
	 *                         o si el valor de la pieza es nulo.
	 */
	public void asignarValor(Coordenada coordenadas, Pieza valor) {
		if (coordenadas.getX() < 0 || coordenadas.getX() >= tamanio ||
			coordenadas.getY() < 0 || coordenadas.getY() >= tamanio ||
			coordenadas.getZ() < 0 || coordenadas.getZ() >= tamanio) {
			throw new RuntimeException("Coordenadas fuera de los límites del tablero");
		}
		if (valor == null) {
			throw new RuntimeException("El valor de la pieza no puede ser nulo");
		}
		if (!piezasNoVacias.contains(valor)) {
			piezasNoVacias.insertarUltimo(valor);
		}
		IteradorLista<Sector> iter = tablero[coordenadas.getX()].iterador();
		while (iter.haySiguiente()) {
			Sector sector = iter.verActual();
			if (sector.obtenerCoordenadas().equals(coordenadas)) {
				sector.asignarValor(valor);
				break;
			}
			iter.siguiente();
		}
	}

	/**
	 * Obtiene la lista de sectores en una coordenada X específica.
	 *
	 * @param x Coordenada X del tablero.
	 * 
	 * @return Lista de sectores en la coordenada X.
	 * @throws RuntimeException si la coordenada X está fuera de los límites del tablero.
	 */
	public ListaSimplementeEnlazada<Sector> obtenerSectores(int x) {
		if (x < 0 || x >= tamanio) {
			throw new RuntimeException("Coordenada X fuera de los límites del tablero");
		}
		return tablero[x];
	}

	/**
	 * Obtiene un sector específico del tablero basado en sus coordenadas.
	 *
	 * @param coordenadas Coordenadas del sector.
	 * 
	 * @return Sector correspondiente a las coordenadas dadas, o null si no existe.
	 */
	public Sector obtenerSector(Coordenada coordenadas) {
		IteradorLista<Sector> iter = tablero[coordenadas.getX()].iterador();
		while (iter.haySiguiente()) {
			Sector sector = iter.verActual();
			if (sector.obtenerCoordenadas().equals(coordenadas)) {
				return sector;
			}
			iter.siguiente();
		}
		return null;
	}

	/**
	 * Obtiene una lista de todas las piezas que no son de tipo VACIO.
	 *
	 * @return Lista de piezas no vacías.
	 */
	public ListaSimplementeEnlazada<Pieza> obtenerPiezasNoVacias() {
		return piezasNoVacias;
	}
}

