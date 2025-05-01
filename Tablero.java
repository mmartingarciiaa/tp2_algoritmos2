import tdas.lista.IteradorLista;
import tdas.lista.ListaEnlazada;

public class Tablero {
    private final int TAMANIO;
	private final ListaEnlazada<Sector>[] tablero;

	@SuppressWarnings("unchecked")
	public Tablero(int dimension) {
		this.TAMANIO = dimension;
		this.tablero = (ListaEnlazada<Sector>[]) new ListaEnlazada[TAMANIO];
	}

    // Método inicializarTablero crea un tablero de tamaño ancho x alto
    // y carga a cada sector con el valor "_"
    public void inicializarTablero() {
		for (int x = 0; x < TAMANIO; x++) {
			tablero[x] = new ListaEnlazada<>();

			for (int y = 0; y < TAMANIO; y++) {
				for (int z = 0; z < TAMANIO; z++) {
					Sector sector = new Sector(x, y, z, VACIO);
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
				break;
			}
			iter.siguiente();
		}
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
}

