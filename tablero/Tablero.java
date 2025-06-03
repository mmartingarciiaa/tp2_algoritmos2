package tablero;

// Importaciones necesarias
import enums.TipoPieza;
import estructuras.lista.IteradorLista;
import estructuras.lista.ListaSimplementeEnlazada;
import piezas.Pieza;

public class Tablero {
    private final int TAMANIO;
	private final ListaSimplementeEnlazada<Sector>[] tablero;
	private final ListaSimplementeEnlazada<Pieza> piezasNoVacias;

	@SuppressWarnings("unchecked")
	public Tablero(int dimension) {
		this.TAMANIO = dimension;
		this.tablero = (ListaSimplementeEnlazada<Sector>[]) new ListaSimplementeEnlazada[TAMANIO];
		this.piezasNoVacias = new ListaSimplementeEnlazada<>();
	}

	public int obtenerDimension() {
		return TAMANIO;
	}

    // Método inicializarTablero crea un tablero de tamaño ancho x alto
    // y carga a cada sector con el valor "_"
    public void inicializarTablero() {
		for (int x = 0; x < TAMANIO; x++) {
			tablero[x] = new ListaSimplementeEnlazada<>();

			for (int y = 0; y < TAMANIO; y++) {
				for (int z = 0; z < TAMANIO; z++) {
					Pieza pieza = new Pieza(TipoPieza.VACIO, null, x, y, z, "_", 0, 0);
					Sector sector = new Sector(x, y, z, pieza);
					tablero[x].insertarUltimo(sector);
				}
			}
		}
	}
    
	// Método asignarValor asigna un valor al sector del tablero correspondiente a las coordenadas indicadas por parámetro
	public void asignarValor(int x, int y, int z, Pieza valor) {
		IteradorLista<Sector> iter = tablero[x].iterador();
		while (iter.haySiguiente()) {
			Sector sector = iter.verActual();
			if (sector.obtenerCoordenadas()[1] == y && sector.obtenerCoordenadas()[2] == z) {
				sector.asignarValor(valor);
				if (valor.obtenerTipo() != TipoPieza.VACIO) {
					piezasNoVacias.insertarUltimo(valor);
				}
				break;
			}
			iter.siguiente();
		}
	}

	public ListaSimplementeEnlazada<Sector> obtenerSectores(int x) {
		return tablero[x];
	}

	public Sector obtenerSector(int x, int y, int z) {
		IteradorLista<Sector> iter = tablero[x].iterador();
		while (iter.haySiguiente()) {
			Sector sector = iter.verActual();
			if (sector.obtenerCoordenadas()[1] == y && sector.obtenerCoordenadas()[2] == z) {
				return sector;
			}
			iter.siguiente();
		}
		return null;
	}

	public ListaSimplementeEnlazada<Pieza> obtenerPiezasNoVacias() {
		return piezasNoVacias;
	}
}

