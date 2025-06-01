package estructuras.cola;

import estructuras.lista.Lista;
import estructuras.nodos.NodoSimplementeEnlazado;

/**
 * Implementación de una cola enlazada genérica.
 *
 * @param <T> el tipo de datos en la cola
 */
public class ColaEnlazada<T> {
	//ATRIBUTOS -----------------------------------------------------------------------------------------------

	private NodoSimplementeEnlazado<T> frente = null;
	private NodoSimplementeEnlazado<T> ultimo = null;
	private int tamanio = 0;

	//CONSTRUCTOR -------------------------------------------------------------------------------------------

	/**
	 * pre:
	 * post: inicializa la cola vacia para su uso
	 */
	public ColaEnlazada() {
        this.frente = null;
		this.ultimo = null;
		this.tamanio = 0;
	}
    
	//METODOS DE CLASE ----------------------------------------------------------------------------------------

	/*
    * post: indica si la cola tiene algún elemento.
    */
    public boolean estaVacia() {
        return (this.tamanio == 0);
	}
    
    /*
     * post: devuelve el elemento en el frente de la cola. Solo lectura
     */
    public T verPrimero() {
        T elemento = null;
        if (!this.estaVacia()) {
            elemento = this.frente.getDato();
        }
        return elemento;
    }

	/*
	 * pre: el elemento no debe ser null
	 * post: agrega el elemento a la cola
	 */
	public void encolar(T elemento) throws IllegalArgumentException {
		if (elemento == null) {
			throw new IllegalArgumentException("El elemento no puede ser null");
		}
		NodoSimplementeEnlazado<T> nuevo = new NodoSimplementeEnlazado<>(elemento);
		if (!this.estaVacia()) {
			this.ultimo.setSiguiente(nuevo);
			this.ultimo = nuevo;
		} else {
			this.frente = nuevo;
			this.ultimo = nuevo;
		}
		this.tamanio++;
	}

    /*
	 * pre: la lista no debe ser null
	 * post: agrega el elemento a la cola
	 */
	public void encolar(Lista<T> lista) throws IllegalArgumentException {
		if (lista == null) {
            throw new IllegalArgumentException("La lista no puede ser null");
        }
		lista.iniciarCursor();
		while (!lista.avanzarCursor()) {
			this.encolar(lista.obtenerCursor());
		}
	}

	/*
	 * post: devuelve el elemento en el frente de la cola quitandolo.
	 */
	public T desencolar() {
		T elemento = null;
		if (!this.estaVacia()) {
			elemento = this.frente.getDato();
			this.frente = this.frente.getSiguiente();
			this.tamanio--;
			if (estaVacia()) {
				this.ultimo = null;
			}
		}
		return elemento;
	}

	/*
	 * post: devuelve la cantidad de elementos que tiene la cola.
	 */
	public int tamanio() {
		return this.tamanio;
	}
}
