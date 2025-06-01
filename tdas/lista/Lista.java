package tdas.lista;
import tdas.nodos.NodoSimplementeEnlazado;

public abstract class Lista<T> {
	//ATRIBUTOS -----------------------------------------------------------------------------------------------

	private NodoSimplementeEnlazado<T> primero = null;
	private int largo = 0;
	private NodoSimplementeEnlazado<T> cursor = null;

	//CONSTRUCTOR -------------------------------------------------------------------------------------------

	/**
	 * pos: crea una lista vacia
	 */
	public Lista() {
		this.primero = null;
		this.largo = 0;
		this.cursor = null;
	}

	//METODOS DE CLASE ----------------------------------------------------------------------------------------

	/**
	 * pos: indica si la Lista tiene algún elemento.
	 */
	public boolean estaVacia() {
		return this.largo == 0;
	}

    /**
     * post: devuelve la cantidad de elementos que tiene la Lista.
     */
    public int largo() {
        return this.largo;
    }

    /*
	 * post: deja el cursor de la Lista preparado para hacer un nuevo
	 *       recorrido sobre sus elementos.
	 */
	public void iniciarCursor() {
		this.cursor = null;
	}

	/*
	 * pre : se ha iniciado un recorrido (invocando el método
	 *       iniciarCursor()) y desde entonces no se han agregado o
	 *       removido elementos de la Lista.
	 * post: mueve el cursor y lo posiciona en el siguiente elemento
	 *       del recorrido.
	 *       El valor de retorno indica si el cursor quedó posicionado
	 *       sobre un elemento o no (en caso de que la Lista esté vacía o
	 *       no existan más elementos por recorrer.)
	 */
	public boolean avanzarCursor() {
		if (this.cursor == null) {
			this.cursor = this.primero;
		} else {
			this.cursor = this.cursor.getSiguiente();
		}

		/* pudo avanzar si el cursor ahora apunta a un nodo */
		return (this.cursor != null);
	}

	/*
	 * pre : el cursor está posicionado sobre un elemento de la Lista,
	 *       (fue invocado el método avanzarCursor() y devolvió true)
	 * post: devuelve el elemento en la posición del cursor.
	 *
	 */
	public T obtenerCursor() {
		T elemento = null;
		if (this.cursor != null) {
			elemento = this.cursor.getDato();
		}
		return elemento;
	}

    /**
     * post: devuelve el primer elemento de la Lista.
     */
    public T verPrimero() throws Exception {
        if (this.estaVacia()) {
            throw new Exception("La lista está vacía");
        }
        return this.primero.getDato();
    }

    // METODOS ABSTRACTOS QUE IMPLEMENTA LA CLASE HIJA ----------------------------------------------------

    /**
     * post: devuelve el último elemento de la Lista.
     */
    public abstract T verUltimo() throws Exception;

    /**
     * post: agrega el elemento al principio de la Lista
     */
    public abstract void insertarPrimero(T elemento);

    /**
     * post: agrega el elemento al final de la lista
     */
    public abstract void insertarUltimo(T elemento);

    /**
     * post: borra el primer elemento de la Lista. Si la Lista está vacía, lanza una excepción.
     * @throws Exception si la lista está vacía
     */
    public abstract void borrarPrimero() throws Exception;

    /**
     * post: borra el último elemento de la Lista. Si la Lista está vacía, lanza una excepción.
     * @throws Exception si la lista está vacía
     */
    public abstract void borrarUltimo() throws Exception;

    /**
     * obtener el elemento en la posición indicada.
     * Si la posición es menor a 1 o mayor al tamaño actual de la lista, se lanza una excepción.
     * 
     * @param posicion la posición del elemento a obtener (1-indexado)
     * @return el elemento en la posición indicada
     * @throws Exception si la posición es menor a 1 o mayor al tamaño actual de la lista
     */
    public abstract T obtenerEnPosicion(int posicion) throws Exception;

    /**
     * Inserta un elemento en la lista enlazada en una posición específica.
     * Si la posición es menor a 1, se lanza una excepción. Si la posición es mayor al tamaño
     * actual de la lista, el elemento se agrega al final. Si la posición es 1, el elemento
     * se inserta al principio. En otros casos, el elemento se inserta en la posición indicada.
     *
     * @param dato el elemento a insertar
     * @param posicion la posición en la lista donde se debe insertar el elemento (1-indexado)
     * @throws RuntimeException si la posición indicada es menor a 1
     */
    public abstract void insertarEnPosicion(T dato, int posicion) throws Exception;

    /**
     * Elimina un elemento de la lista enlazada en una posición específica y lo retorna.
     * Si la posición es menor a 1, se lanza una excepción. Si la posición es mayor
     * al tamaño actual de la lista, se eliminará el último elemento. Si la posición
     * es 1, se eliminará el primer elemento. En otros casos, se eliminará el elemento
     * en la posición indicada.
     *
     * @param posicion la posición del elemento a eliminar en la lista (1-indexado)
     * @return el elemento eliminado de la lista
     * @throws RuntimeException si la posición es menor a 1
     */
    public abstract T borrarEnPosicion(int posicion) throws Exception;

    // ITERADORES EXTERNOS E INTERNOS -----------------------------------------------------------

    public void visitar(Visitador<T> visitador) {
        this.iniciarCursor();
        while (this.avanzarCursor()) {
            if (!visitador.visitar(this.obtenerCursor())) {
                break; // Si el visitador devuelve false, se detiene el recorrido
            }
        }
    }

    public abstract IteradorLista<T> iterador();
}
