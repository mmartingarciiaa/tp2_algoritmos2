package estructuras.nodos;

public class NodoSimplementeEnlazado<T> {
	//ATRIBUTOS -----------------------------------------------------------------------------------------------

	private T dato;
	private NodoSimplementeEnlazado<T> siguiente = null;

	//CONSTRUCTOR -------------------------------------------------------------------------------------------

	/**
	 * pre: -
	 * pos: el Nodo resulta inicializado con el dato dado
     *       y sin un Nodo siguiente.
	 */
	public NodoSimplementeEnlazado(T dato) {
		this.dato = dato;
		this.siguiente = null;
	}

	//GETTERS SIMPLES -----------------------------------------------------------------------------------------

	/**
	 * pre:
	 * pos: devuelve el dato del nodo
	 */
	public T getDato() {
		return this.dato;
	}

	/**
	 * pre:
	 * pos: cambia el dato del nodo
	 */
	public NodoSimplementeEnlazado<T> getSiguiente() {
		return this.siguiente;
	}

	//SETTERS SIMPLES -----------------------------------------------------------------------------------------	

	/**
	 * pre:
	 * pos: devuelve el siguiente nodo
	 */
	public void setDato(T dato) {
		this.dato = dato;
	}
	
	/**
	 * pre:
	 * pos: cambia el nodo siguiente
	 */
	public void setSiguiente(NodoSimplementeEnlazado<T> siguiente) {
		this.siguiente = siguiente;
	}

}