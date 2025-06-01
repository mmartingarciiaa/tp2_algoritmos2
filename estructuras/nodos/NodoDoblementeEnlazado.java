package estructuras.nodos;

public class NodoDoblementeEnlazado<T> extends NodoSimplementeEnlazado<T> {
	//ATRIBUTOS -----------------------------------------------------------------------------------------------

	private NodoDoblementeEnlazado<T> anterior = null;
	
	//CONSTRUCTORES -------------------------------------------------------------------------------------------

	/**
	 * pre: -
	 * pos: el Nodo resulta inicializado con el dato dado
	 *       y sin un Nodo siguiente.
	 */
	public NodoDoblementeEnlazado(T dato) {
		super(dato);
		this.anterior = null;
	}

	//GETTERS SIMPLES -----------------------------------------------------------------------------------------
	
	/**
	 * pre: -
	 * pos: devuelve el nodo anterior
	 */
	public NodoDoblementeEnlazado<T> getAnterior() {
		return anterior;
	}
	
	//SETTERS SIMPLES -----------------------------------------------------------------------------------------	

	/**
	 * pre: -
	 * pos: cambia el nodo anterior
	 */
	public void setAnterior(NodoDoblementeEnlazado<T> anterior) {
		this.anterior = anterior;
	}

}