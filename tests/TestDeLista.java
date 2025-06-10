package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;

import estructuras.lista.ListaSimplementeEnlazada;
import estructuras.lista.IteradorLista;

public class TestDeLista {

    private static final int CARGA_MAXIMA = 10000;
    private static final int RANGO_TESTS = 5;
    private static final int CORTE_VOLUMEN = 4000;

    private <T> void panicListaVacia(ListaSimplementeEnlazada<T> lista) {
        assertThrows(NoSuchElementException.class, () -> lista.verPrimero());
        assertThrows(NoSuchElementException.class, () -> lista.verUltimo());
        assertThrows(NoSuchElementException.class, () -> lista.borrarPrimero());
    }

    private <T> void panicIteradorSinSiguiente(IteradorLista<T> iter) {
        assertThrows(RuntimeException.class, () -> iter.siguiente());
        assertThrows(RuntimeException.class, () -> iter.verActual());
        assertThrows(RuntimeException.class, () -> iter.borrar());
    }

    private <T> void verificarPrimeroYUltimo(ListaSimplementeEnlazada<T> lista, T primero, T ultimo) {
        assertEquals(primero, lista.verPrimero());
        assertEquals(ultimo, lista.verUltimo());
    }

    private <T> void vaciarLista(ListaSimplementeEnlazada<T> lista) {
        while (!lista.estaVacia()) {
            lista.borrarPrimero();
        }
    }

    @Test
    public void testComportamientoListaVacia() {
        ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

        // ListaSimplementeEnlazada recién creada debe estar vacía.
        assertTrue(lista.estaVacia());

        // Verifico que no se puede ver el primero ni borrar un elemento de una lista vacía.
        panicListaVacia(lista);

        // Inserto un elemento, verifico que ya no está vacía y que se le puede ver el primero.
        lista.insertarPrimero(1);
        assertFalse(lista.estaVacia());
        verificarPrimeroYUltimo(lista, 1, 1);

        // Borro el elemento, verifico que está vacía nuevamente.
        assertEquals(1, lista.borrarPrimero());
        assertTrue(lista.estaVacia());

        // Verifico que se no se puede ver el primero ni borrar un elemento de una lista recientemente vaciada,
        // la cual se comporta de la misma manera que una lista recién creada.
        panicListaVacia(lista);
    }

    @Test
    public void testInsertarYBorrarUnElemento() {
        ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

        // Verifico que está vacía.
        assertTrue(lista.estaVacia());

        // Inserto un elemento, verifico que se pueda ver el primero y que la lista no está vacía.
        lista.insertarPrimero(1);
        verificarPrimeroYUltimo(lista, 1, 1);
        assertFalse(lista.estaVacia());

        // Borro el elemento, verifico que está vacía nuevamente.
        assertEquals(1, lista.borrarPrimero());
        assertTrue(lista.estaVacia());

        // Verifico que se comporta de igual forma que una lista recién creada.
        panicListaVacia(lista);
    }

    @Test
    public void testInsertarYBorrarVariosElementosPorPrimero() {
        ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

        // Verifico que está vacía.
        assertTrue(lista.estaVacia());

        // Inserto varios elementos, verifico que se pueda ver el primero y que la lista no está vacía.
        for (int i = 0; i <= 10; i++) {
            lista.insertarPrimero(i);
            verificarPrimeroYUltimo(lista, i, 0);
            assertFalse(lista.estaVacia());
        }

        // Borro los elementos, verifico que está vacía nuevamente.
        for (int i = 10; i >= 0; i--) {
            verificarPrimeroYUltimo(lista, i, 0);
            assertEquals(i, lista.borrarPrimero());
        }
        assertTrue(lista.estaVacia());

        // Verifico que se comporta de igual forma que una lista recién creada.
        panicListaVacia(lista);
    }

    @Test
    public void testInsertarYBorrarVariosElementosPorUltimo() {
        ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

        // Verifico que está vacía.
        assertTrue(lista.estaVacia());

        // Inserto varios elementos, verifico que se pueda ver el último y que la lista no está vacía.
        for (int i = 0; i < 10; i++) {
            lista.insertarUltimo(i);
            verificarPrimeroYUltimo(lista, 0, i);
            assertFalse(lista.estaVacia());
            assertEquals(i + 1, lista.largo());
        }

        // Borro los elementos, verifico que está vacía nuevamente.
        for (int i = 0; i < 10; i++) {
            assertEquals(i, lista.borrarPrimero());
            assertEquals(9 - i, lista.largo());
            if (lista.largo() > 0) {
                assertEquals(9, lista.verUltimo());
            }
        }
        assertTrue(lista.estaVacia());

        // Verifico que se comporta de igual forma que una lista recién creada.
        panicListaVacia(lista);
    }

    @Test
    public void testInsertarYBorrarVariosElementosPorPrimeroYUltimo() {
        ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

        // Verifico que está vacía.
        assertTrue(lista.estaVacia());

        // Inserto de forma variada varios elementos
        lista.insertarPrimero(0);
        verificarPrimeroYUltimo(lista, 0, 0);
        assertFalse(lista.estaVacia());
        assertEquals(1, lista.largo());
        
        lista.insertarUltimo(1);
        verificarPrimeroYUltimo(lista, 0, 1);
        assertEquals(2, lista.largo());
        
        lista.insertarPrimero(2);
        verificarPrimeroYUltimo(lista, 2, 1);
        assertEquals(3, lista.largo());
        
        lista.insertarUltimo(43);
        verificarPrimeroYUltimo(lista, 2, 43);
        assertEquals(4, lista.largo());
        
        lista.borrarPrimero();
        verificarPrimeroYUltimo(lista, 0, 43);
        assertEquals(3, lista.largo());
        
        lista.borrarPrimero();
        assertEquals(1, lista.verPrimero());
        
        lista.borrarPrimero();
        verificarPrimeroYUltimo(lista, 43, 43);
        assertEquals(1, lista.largo());
        
        lista.borrarPrimero();
        assertTrue(lista.estaVacia());

        // Verifico que se comporta de igual forma que una lista recién creada.
        panicListaVacia(lista);
    }

    @Test
    public void testInsertarYBorrarVariosElementosFloats() {
        ListaSimplementeEnlazada<Double> lista = new ListaSimplementeEnlazada<>();

        // Verifico que está vacía.
        assertTrue(lista.estaVacia());

        // Inserto varios elementos
        for (double i = 0.5; i <= 10.5; i++) {
            lista.insertarPrimero(i);
            verificarPrimeroYUltimo(lista, i, 0.5);
            assertFalse(lista.estaVacia());
        }

        // Borro los elementos
        for (double i = 10.5; i >= 0.5; i--) {
            assertEquals(i, lista.borrarPrimero());
        }
        assertTrue(lista.estaVacia());

        // Verifico que se comporta de igual forma que una lista recién creada.
        panicListaVacia(lista);
    }

    @Test
    public void testInsertarYBorrarVariosStringsPorPrimeroYUltimo() {
        ListaSimplementeEnlazada<String> lista = new ListaSimplementeEnlazada<>();

        // Verifico que está vacía.
        assertTrue(lista.estaVacia());

        // Inserto de forma variada varios elementos
        lista.insertarPrimero("hola");
        verificarPrimeroYUltimo(lista, "hola", "hola");
        assertFalse(lista.estaVacia());
        assertEquals(1, lista.largo());
        
        lista.insertarUltimo("mundo");
        verificarPrimeroYUltimo(lista, "hola", "mundo");
        assertEquals(2, lista.largo());
        
        lista.insertarPrimero("como");
        verificarPrimeroYUltimo(lista, "como", "mundo");
        assertEquals(3, lista.largo());
        
        lista.insertarUltimo("estas");
        verificarPrimeroYUltimo(lista, "como", "estas");
        assertEquals(4, lista.largo());
        
        lista.borrarPrimero();
        verificarPrimeroYUltimo(lista, "hola", "estas");
        assertEquals(3, lista.largo());
        
        lista.borrarPrimero();
        assertEquals("mundo", lista.verPrimero());
        
        lista.borrarPrimero();
        verificarPrimeroYUltimo(lista, "estas", "estas");
        assertEquals(1, lista.largo());
        
        lista.borrarPrimero();
        assertTrue(lista.estaVacia());

        // Verifico que se comporta de igual forma que una lista recién creada.
        panicListaVacia(lista);
    }

    @Test
    public void testVolumenInsertarPrimero() {
        ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

        // Verifico que está vacía.
        assertTrue(lista.estaVacia());

        // Inserto muchos elementos
        for (int i = 0; i < CARGA_MAXIMA; i++) {
            lista.insertarPrimero(i);
            verificarPrimeroYUltimo(lista, i, 0);
            assertFalse(lista.estaVacia());
        }

        // Borro los elementos
        for (int i = CARGA_MAXIMA - 1; i >= 0; i--) {
            verificarPrimeroYUltimo(lista, i, 0);
            assertEquals(i, lista.borrarPrimero());
        }
        assertTrue(lista.estaVacia());

        // Verifico que se comporta de igual forma que una lista recién creada.
        panicListaVacia(lista);
    }

    @Test
    public void testVolumenInsertarUltimo() {
        ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

        // Verifico que está vacía.
        assertTrue(lista.estaVacia());

        // Inserto muchos elementos
        for (int i = 0; i < CARGA_MAXIMA; i++) {
            lista.insertarUltimo(i);
            verificarPrimeroYUltimo(lista, 0, i);
            assertFalse(lista.estaVacia());
        }

        // Borro los elementos
        for (int i = 0; i < CARGA_MAXIMA; i++) {
            verificarPrimeroYUltimo(lista, i, CARGA_MAXIMA - 1);
            assertEquals(i, lista.borrarPrimero());
        }
        assertTrue(lista.estaVacia());

        // Verifico que se comporta de igual forma que una lista recién creada.
        panicListaVacia(lista);
    }

    @Test
    public void testIteradorExternoConListaVacia() {
        ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

        // Verifico que está vacía.
        assertTrue(lista.estaVacia());

        // Creo el iterador y verifico que no hay elementos.
        IteradorLista<Integer> iter = lista.iterador();
        assertFalse(iter.haySiguiente());
        panicIteradorSinSiguiente(iter);

        // Inserto un elemento
        iter.insertar(1);
        verificarPrimeroYUltimo(lista, 1, 1);
        assertFalse(lista.estaVacia());
        assertTrue(iter.haySiguiente());
        assertEquals(1, iter.verActual());
        
        iter.borrar();
        assertTrue(lista.estaVacia());
        assertFalse(iter.haySiguiente());

        // Verifico que no se puede avanzar
        panicIteradorSinSiguiente(iter);

        // Verifico que se comporta de igual forma que una lista recién creada.
        panicListaVacia(lista);
    }

    @Test
    public void testIteradorExternoAgregarAlPrincipio() {
        ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

        // Verifico que está vacía.
        assertTrue(lista.estaVacia());

        IteradorLista<Integer> iter = lista.iterador();
        assertFalse(iter.haySiguiente());
        
        iter.insertar(1);
        assertTrue(iter.haySiguiente());
        verificarPrimeroYUltimo(lista, 1, 1);
        assertEquals(1, iter.verActual());
        
        iter.insertar(2);
        assertTrue(iter.haySiguiente());
        assertEquals(2, lista.verPrimero());
        assertEquals(2, iter.verActual());
        
        iter.siguiente();
        assertTrue(iter.haySiguiente());
        assertEquals(1, lista.verUltimo());
        assertEquals(1, iter.verActual());
        
        iter.siguiente();
        assertFalse(iter.haySiguiente());

        // Vacio la lista.
        vaciarLista(lista);

        // Verifico que no se puede avanzar
        panicIteradorSinSiguiente(iter);

        // Verifico que se comporta de igual forma que una lista recién creada.
        panicListaVacia(lista);
    }

    @Test
    public void testIteradorExternoAgregarAlFinal() {
        ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

        // Verifico que está vacía.
        assertTrue(lista.estaVacia());

        // Creo el iterador y agrego elementos al final
        IteradorLista<Integer> iter = lista.iterador();
        assertFalse(iter.haySiguiente());
        
        iter.insertar(1);
        assertTrue(iter.haySiguiente());
        verificarPrimeroYUltimo(lista, 1, 1);
        assertEquals(1, iter.verActual());
        
        iter.siguiente();
        assertFalse(iter.haySiguiente());
        
        iter.insertar(2);
        assertTrue(iter.haySiguiente());
        verificarPrimeroYUltimo(lista, 1, 2);
        assertEquals(2, iter.verActual());
        
        iter.siguiente();
        assertFalse(iter.haySiguiente());
        
        iter.insertar(3);
        assertTrue(iter.haySiguiente());
        verificarPrimeroYUltimo(lista, 1, 3);
        assertEquals(3, iter.verActual());
        
        iter.siguiente();
        assertFalse(iter.haySiguiente());

        // Vacio la lista.
        vaciarLista(lista);

        // Verifico que no se puede avanzar
        panicIteradorSinSiguiente(iter);

        // Verifico que se comporta de igual forma que una lista recién creada.
        panicListaVacia(lista);
    }

    @Test
    public void testIteradorExternoAgregarAlMedio() {
        ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

        // Verifico que está vacía.
        assertTrue(lista.estaVacia());

        // Creo el iterador y agrego elementos en el medio
        IteradorLista<Integer> iter = lista.iterador();
        assertFalse(iter.haySiguiente());
        
        for (int i = 1; i <= 3; i++) {
            iter.insertar(i);
        }
        assertTrue(iter.haySiguiente());
        verificarPrimeroYUltimo(lista, 3, 1);
        assertEquals(3, iter.verActual());
        
        iter.siguiente();
        assertTrue(iter.haySiguiente());
        
        iter.insertar(4);
        assertTrue(iter.haySiguiente());
        verificarPrimeroYUltimo(lista, 3, 1);
        assertEquals(4, iter.verActual());
        
        iter.siguiente();
        iter.insertar(5);
        assertTrue(iter.haySiguiente());
        verificarPrimeroYUltimo(lista, 3, 1);
        assertEquals(5, iter.verActual());
        
        iter.siguiente();
        iter.siguiente();
        assertTrue(iter.haySiguiente());
        verificarPrimeroYUltimo(lista, 3, 1);
        assertEquals(1, iter.verActual());
        
        iter.siguiente();
        assertFalse(iter.haySiguiente());

        // Vacio la lista.
        vaciarLista(lista);

        // Verifico que no se puede avanzar
        panicIteradorSinSiguiente(iter);

        // Verifico que se comporta de igual forma que una lista recién creada.
        panicListaVacia(lista);
    }

    @Test
    public void testIteradorExternoBorrarAlInicio() {
        ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

        // Verifico que está vacía.
        assertTrue(lista.estaVacia());

        // Creo el iterador y agrego elementos al inicio para luego borrarlos
        IteradorLista<Integer> iter = lista.iterador();
        assertFalse(iter.haySiguiente());
        
        for (int i = 1; i <= 3; i++) {
            iter.insertar(i);
        }
        assertTrue(iter.haySiguiente());
        verificarPrimeroYUltimo(lista, 3, 1);
        assertEquals(3, iter.verActual());
        
        iter.borrar();
        assertTrue(iter.haySiguiente());
        verificarPrimeroYUltimo(lista, 2, 1);
        assertEquals(2, iter.verActual());
        
        iter.borrar();
        assertTrue(iter.haySiguiente());
        verificarPrimeroYUltimo(lista, 1, 1);
        assertEquals(1, iter.verActual());
        
        iter.borrar();
        assertFalse(iter.haySiguiente());

        // Verifico que no se puede avanzar
        panicIteradorSinSiguiente(iter);

        // Verifico que se comporta de igual forma que una lista recién creada.
        panicListaVacia(lista);
    }

    @Test
    public void testIteradorExternoBorrarAlFinal() {
        ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

        // Verifico que está vacía.
        assertTrue(lista.estaVacia());

        // Creo el iterador y agrego elementos al final para luego borrarlos
        IteradorLista<Integer> iter = lista.iterador();
        assertFalse(iter.haySiguiente());
        
        for (int i = 1; i <= 3; i++) {
            iter.insertar(i);
        }
        assertTrue(iter.haySiguiente());
        verificarPrimeroYUltimo(lista, 3, 1);
        assertEquals(3, iter.verActual());
        
        iter.siguiente();
        iter.siguiente();
        assertEquals(1, iter.verActual());
        
        iter.borrar();
        assertFalse(iter.haySiguiente());

        // Vacio la lista
        vaciarLista(lista);

        // Verifico que no se puede avanzar
        panicIteradorSinSiguiente(iter);

        // Verifico que se comporta de igual forma que una lista recién creada.
        panicListaVacia(lista);
    }

    @Test
    public void testIteradorExternoBorrarAlMedio() {
        ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

        // Verifico que está vacía.
        assertTrue(lista.estaVacia());

        // Creo el iterador y agrego elementos en el medio para luego borrarlos
        IteradorLista<Integer> iter = lista.iterador();
        assertFalse(iter.haySiguiente());
        
        for (int i = 1; i <= 5; i++) {
            iter.insertar(i);
        }
        assertTrue(iter.haySiguiente());
        verificarPrimeroYUltimo(lista, 5, 1);
        assertEquals(5, iter.verActual());
        
        iter.siguiente();
        iter.siguiente();
        assertEquals(3, iter.verActual());
        
        iter.borrar();
        assertTrue(iter.haySiguiente());
        verificarPrimeroYUltimo(lista, 5, 1);
        assertEquals(2, iter.verActual());
        
        iter.borrar();
        assertTrue(iter.haySiguiente());
        verificarPrimeroYUltimo(lista, 5, 1);
        assertEquals(1, iter.verActual());
        
        iter.borrar();
        assertFalse(iter.haySiguiente());

        // Verifico que no se puede avanzar
        panicIteradorSinSiguiente(iter);

        // Vacio la lista
        vaciarLista(lista);

        // Verifico que se comporta de igual forma que una lista recién creada.
        panicListaVacia(lista);
    }

    @Test
    public void testIteradorExternoAgregarYBorrarVariosElementos() {
        ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

        // Verifico que está vacía.
        assertTrue(lista.estaVacia());

        // Creo el iterador y agrego varios elementos para luego borrarlos
        IteradorLista<Integer> iter = lista.iterador();
        assertFalse(iter.haySiguiente());
        
        iter.insertar(1);
        verificarPrimeroYUltimo(lista, 1, 1);
        
        iter.insertar(2);
        verificarPrimeroYUltimo(lista, 2, 1);
        
        iter.insertar(3);
        iter.insertar(4);
        iter.borrar();
        verificarPrimeroYUltimo(lista, 3, 1);
        
        iter.borrar();
        assertEquals(2, iter.verActual());
        assertEquals(2, lista.verPrimero());
        
        iter.insertar(6);
        verificarPrimeroYUltimo(lista, 6, 1);
        assertEquals(6, iter.verActual());
        
        iter.borrar();
        iter.borrar();
        assertEquals(1, iter.verActual());
        assertEquals(1, lista.verPrimero());
        
        iter.siguiente();
        assertFalse(iter.haySiguiente());
        
        iter.insertar(8);
        verificarPrimeroYUltimo(lista, 1, 8);
        assertEquals(8, iter.verActual());
        
        iter.borrar();
        assertFalse(iter.haySiguiente());
        assertFalse(lista.estaVacia());
        
        assertEquals(1, lista.borrarPrimero());
        assertTrue(lista.estaVacia());

        // Verifico que no se puede avanzar
        panicIteradorSinSiguiente(iter);

        // Verifico que se comporta de igual forma que una lista recién creada.
        panicListaVacia(lista);
    }

    @Test
    public void testVolumenIteradorExterno() {
        ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

        // Verifico que está vacía.
        assertTrue(lista.estaVacia());

        // Inserto muchos elementos
        for (int i = 0; i < CARGA_MAXIMA; i++) {
            lista.insertarUltimo(i);
            verificarPrimeroYUltimo(lista, 0, i);
            assertFalse(lista.estaVacia());
        }

        // Creo el iterador y elimino todos los elementos de la lista.
        IteradorLista<Integer> iter = lista.iterador();
        while (iter.haySiguiente()) {
            assertEquals(iter.verActual(), lista.verPrimero());
            iter.borrar();
        }
        assertTrue(lista.estaVacia());

        // Verifico que no se puede avanzar
        panicIteradorSinSiguiente(iter);

        // Verifico que se comporta de igual forma que una lista recién creada.
        panicListaVacia(lista);
    }

    @Test
    public void testIteradorInternoSumaTodosLosElementos() {
        ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

        for (int i = 0; i < RANGO_TESTS; i++) {
            lista.insertarPrimero(i);
        }

        int[] suma = {0};
        lista.iterar(valor -> {
            suma[0] += valor;
            return true;
        });
        assertEquals(10, suma[0]);
    }

    @Test
    public void testIteradorInternoSumasPares() {
        ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

        for (int i = 0; i < RANGO_TESTS; i++) {
            lista.insertarPrimero(i);
        }

        int[] sumaPares = {0};
        lista.iterar(valor -> {
            if (valor % 2 == 0) {
                sumaPares[0] += valor;
            }
            return true;
        });
        assertEquals(6, sumaPares[0]);
    }

    @Test
    public void testIteradorInternoCorteEnElMedio() {
        ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

        lista.insertarUltimo(4);
        lista.insertarUltimo(4);
        lista.insertarUltimo(6);
        lista.insertarUltimo(0);
        lista.insertarUltimo(3);
        lista.insertarUltimo(12);
        lista.insertarUltimo(2);
        lista.insertarUltimo(8);

        int[] elemImpar = {0};

        lista.iterar(valor -> {
            if (valor % 2 != 0) {
                elemImpar[0] = valor;
                return false;
            }
            return true;
        });
        assertEquals(3, elemImpar[0]);
    }

    @Test
    public void testIteradorInternoNoModificaLosElementosOriginales() {
        ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

        for (int i = 0; i < RANGO_TESTS; i++) {
            lista.insertarPrimero(i);
        }

        lista.iterar(valor -> {
            valor *= 2;
            return true;
        });

        int[] elemIterados = new int[5];
        int[] index = {0};
        lista.iterar(valor -> {
            elemIterados[index[0]++] = valor;
            return true;
        });

        assertArrayEquals(new int[]{4, 3, 2, 1, 0}, elemIterados);
    }

    @Test
    public void testIteradosInternoConListaVacia() {
        ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

        // Verifico que está vacía.
        assertTrue(lista.estaVacia());

        int[] elemIterados = new int[0];
        int[] index = {0};
        lista.iterar(valor -> {
            if (elemIterados.length == 0) {
                elemIterados = new int[1];
            } else {
                int[] newArray = new int[elemIterados.length + 1];
                System.arraycopy(elemIterados, 0, newArray, 0, elemIterados.length);
                elemIterados = newArray;
            }
            elemIterados[index[0]++] = valor;
            return true;
        });
        assertEquals(0, elemIterados.length);
    }

    @Test
    public void testVolumenIteradorInterno() {
        ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

        // Verifico que está vacía.
        assertTrue(lista.estaVacia());

        // Inserto muchos elementos
        for (int i = 0; i < CARGA_MAXIMA; i++) {
            lista.insertarUltimo(i);
            verificarPrimeroYUltimo(lista, 0, i);
            assertFalse(lista.estaVacia());
        }

        int[] suma = {0};
        lista.iterar(valor -> {
            suma[0] += valor;
            return true;
        });
        assertEquals(49995000, suma[0]);
    }

    @Test
    public void testVolumenIteradorInternoConCorte() {
        ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();

        // Verifico que está vacía.
        assertTrue(lista.estaVacia());

        // Inserto muchos elementos
        for (int i = 0; i < CARGA_MAXIMA; i++) {
            lista.insertarUltimo(i);
            verificarPrimeroYUltimo(lista, 0, i);
            assertFalse(lista.estaVacia());
        }

        int[] suma = {0};
        lista.iterar(valor -> {
            suma[0] += valor;
            return valor < CORTE_VOLUMEN;
        });
        assertEquals(8002000, suma[0]);
    }
}