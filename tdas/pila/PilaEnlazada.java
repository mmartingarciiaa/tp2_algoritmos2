package tdas.pila;
import tdas.lista.Lista;
import tdas.nodos.NodoSimplementeEnlazado;

public class PilaEnlazada<T> {
	//ATRIBUTOS -----------------------------------------------------------------------------------------------

	private NodoSimplementeEnlazado<T> tope = null;
	private int tamanio = 0;

	//CONSTRUCTOR -------------------------------------------------------------------------------------------

	/**
	 * post: inicializa la pila vacia para su uso
	 */
	public PilaEnlazada() {
		this.tope = null;
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
	 * pre: el elemento no debe ser null
	 * post: agrega el elemento a la pila
	 */
	public void apilar(T elemento) throws IllegalArgumentException {
		if (elemento == null) {
			throw new IllegalArgumentException("El elemento no puede ser null");
		}
		NodoSimplementeEnlazado<T> nuevo = new NodoSimplementeEnlazado<>(elemento);
		nuevo.setSiguiente(this.tope);
		this.tope = nuevo;
		this.tamanio++;
	}

	/*
	 * pre: la lista no debe ser null
	 * post: agrega el elemento a la pila
	 */
	public void apilar(Lista<T> lista) throws IllegalArgumentException {
		if (lista == null) {
			throw new IllegalArgumentException("La lista no puede ser null");
		}
		lista.iniciarCursor();
		while (!lista.avanzarCursor()) {
			this.apilar(lista.obtenerCursor());
		}
	}

	/*
	 * pre: no se puede desapilar si la pila está vacía
	 * post: devuelve el elemento en el tope de la pila y achica la pila en 1.
	 */
	public T desapilar() throws IllegalStateException {
		if (this.estaVacia()) {
			throw new IllegalStateException("No se puede desapilar de una pila vacía");
		}
		T elemento = this.tope.getDato();
		this.tope = this.tope.getSiguiente();
		this.tamanio--;
		return elemento;
	}

	/**
	 * pre: no se puede ver el tope si la pila está vacía
	 * post: devuelve el elemento en el tope de la pila (solo lectura)
	 */
	public T verTope() throws IllegalStateException {
		if (this.estaVacia()) {
			throw new IllegalStateException("No se puede ver el tope de una pila vacía");
		}
		return this.tope.getDato();
	}

	/*
	 * post: devuelve la cantidad de elementos que tiene la cola.
	 */
	public int tamanio() {
		return this.tamanio;
	}
}