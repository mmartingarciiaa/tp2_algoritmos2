package tdas.pila;

import java.util.Arrays;

/**
 * Implementación de una pila dinámica usando un arreglo redimensionable.
 *
 * @param <T> el tipo de elementos almacenados.
 */
public class PilaDinamica<T> implements Pila<T> {

    private static final int TAMANIO_INICIAL = 10;
    private static final int CONDICION_REDIMENSION = 4;
    private static final int FACTOR_REDIMENSION = 2;

    private T[] datos;
    private int cantidad;

    @SuppressWarnings("unchecked")
    public PilaDinamica() {
        datos = (T[]) new Object[TAMANIO_INICIAL];
        cantidad = 0;
    }

    @Override
    public boolean estaVacia() {
        return cantidad == 0;
    }

    @Override
    public T verTope() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila esta vacia");
        }
        return datos[cantidad - 1];
    }

    @Override
    public void apilar(T elem) {
        if (cantidad == datos.length) {
            redimensionar(datos.length * FACTOR_REDIMENSION);
        }
        datos[cantidad++] = elem;
    }

    @Override
    public T desapilar() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila esta vacia");
        }
        T valorTope = datos[--cantidad];
        datos[cantidad] = null; // Evitar memory leak

        if (datos.length > TAMANIO_INICIAL && cantidad <= datos.length / CONDICION_REDIMENSION) {
            redimensionar(datos.length / FACTOR_REDIMENSION);
        }

        return valorTope;
    }

    private void redimensionar(int nuevaCapacidad) {
        datos = Arrays.copyOf(datos, nuevaCapacidad);
    }

    public int cantidad() {
        return cantidad;
    }
}
