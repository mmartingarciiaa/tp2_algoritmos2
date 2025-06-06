package tests;

import estructuras.cola.ColaEnlazada;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestDeCola {
  private static final int CARGA_MAXIMA = 10000;

  @Test
  public void testComportamientoDeColaVacia() {
    ColaEnlazada<Integer> cola = new ColaEnlazada<>();

    // Cola recién creada debe estar vacía
    assertTrue(cola.estaVacia());

    // Verifico que no se puede ver el primero ni desencolar una cola vacía
    assertThrows(RuntimeException.class, () -> cola.verPrimero());
    assertThrows(RuntimeException.class, () -> cola.desencolar());

    // Encolo un elemento, verifico que ya no está vacia y que se le puede ver el primero
    cola.encolar(1);
    assertFalse(cola.estaVacia());
    assertEquals(Integer.valueOf(1), cola.verPrimero());

    // Desencolo el elemento, verifico que está vacía nuevamente
    assertEquals(Integer.valueOf(1), cola.desencolar());
    assertTrue(cola.estaVacia());

    // Verifico que se comporta igual que una cola recién creada
    assertThrows(RuntimeException.class, () -> cola.desencolar());
    assertThrows(RuntimeException.class, () -> cola.verPrimero());
  }

  @Test
  public void testEncolarYDesencolarUnElemento() {
    ColaEnlazada<Integer> cola = new ColaEnlazada<>();

    // Verifico que está vacía
    assertTrue(cola.estaVacia());

    // Encolo un elemento, verifico que se pueda ver el primero y que la cola no está vacia
    cola.encolar(1);
    assertEquals(Integer.valueOf(1), cola.verPrimero());
    assertFalse(cola.estaVacia());

    // Desencolo el elemento, verifico que está vacía nuevamente
    assertEquals(Integer.valueOf(1), cola.desencolar());
    assertTrue(cola.estaVacia());

    // Verifico que se comporta igual que una cola recién creada
    assertThrows(RuntimeException.class, () -> cola.desencolar());
    assertThrows(RuntimeException.class, () -> cola.verPrimero());
  }

  @Test
  public void testEncolarYDesencolarVariosElementos() {
    ColaEnlazada<Integer> cola = new ColaEnlazada<>();

    assertTrue(cola.estaVacia());
    cola.encolar(14);
    assertEquals(Integer.valueOf(14), cola.verPrimero());
    assertFalse(cola.estaVacia());
    cola.encolar(23);
    assertEquals(Integer.valueOf(14), cola.verPrimero());
    assertFalse(cola.estaVacia());
    cola.encolar(7);
    assertEquals(Integer.valueOf(14), cola.verPrimero());
    cola.desencolar();
    assertEquals(Integer.valueOf(23), cola.verPrimero());
    assertFalse(cola.estaVacia());
    cola.encolar(3);
    assertEquals(Integer.valueOf(23), cola.verPrimero());
    cola.desencolar();
    cola.desencolar();
    assertEquals(Integer.valueOf(3), cola.verPrimero());
    assertFalse(cola.estaVacia());
    cola.desencolar();

    // Verifico que se vació correctamente
    assertTrue(cola.estaVacia());
    assertThrows(RuntimeException.class, () -> cola.desencolar());
    assertThrows(RuntimeException.class, () -> cola.verPrimero());
  }

  @Test
  public void testEncolarYDesencolarStrings() {
    ColaEnlazada<String> cola = new ColaEnlazada<>();

    // Verifico que está vacía
    assertTrue(cola.estaVacia());

    // Encolo varios elementos, verifico que se pueda ver el primero y que la cola no está vacía
    String[] strings = {"hola", "mundo", "!", "como", "estas"};
    for (int i = 0; i < strings.length; i++) {
      cola.encolar(strings[i]);
      assertEquals(strings[0], cola.verPrimero());
      assertFalse(cola.estaVacia());
    }

    // Desencolo varios elementos, verificando que lo hagan en orden
    for (int i = 0; i < strings.length; i++) {
      assertFalse(cola.estaVacia());
      assertEquals(strings[i], cola.verPrimero());
      assertEquals(strings[i], cola.desencolar());
    }

    // Verifico que se vació correctamente
    assertTrue(cola.estaVacia());

    // Verifico que se comporta igual que una cola recién creada
    assertThrows(RuntimeException.class, () -> cola.desencolar());
    assertThrows(RuntimeException.class, () -> cola.verPrimero());
  }

  @Test
  public void testEncolarYDesencolarFloats() {
    ColaEnlazada<Double> cola = new ColaEnlazada<>();

    // Verifico que está vacía
    assertTrue(cola.estaVacia());

    // Encolo varios elementos, verifico que se pueda ver el primero y que la cola no está vacía
    double[] floats = {11.2, 34.3, 8.2, 20.3, 73.2, 91.2983, 53.7382, 7.231111};
    for (int i = 0; i < floats.length; i++) {
      cola.encolar(floats[i]);
      assertEquals(Double.valueOf(floats[0]), cola.verPrimero());
      assertFalse(cola.estaVacia());
    }

    // Desencolo varios elementos, verificando que lo hagan en orden
    for (int i = 0; i < floats.length; i++) {
      assertFalse(cola.estaVacia());
      assertEquals(Double.valueOf(floats[i]), cola.verPrimero());
      assertEquals(Double.valueOf(floats[i]), cola.desencolar());
    }

    // Verifico que se vació correctamente
    assertTrue(cola.estaVacia());

    // Verifico que se comporta igual que una cola recién creada
    assertThrows(RuntimeException.class, () -> cola.desencolar());
    assertThrows(RuntimeException.class, () -> cola.verPrimero());
  }

  @Test
  public void testVolumen() {
    ColaEnlazada<Integer> cola = new ColaEnlazada<>();

    // Verifico que está vacía
    assertTrue(cola.estaVacia());

    // Encolo muchos elementos, verifico que se pueda ver el primero y que la cola no está vacía
    for (int i = 0; i < CARGA_MAXIMA; i++) {
      cola.encolar(i);
      assertEquals(Integer.valueOf(0), cola.verPrimero());
      assertFalse(cola.estaVacia());
    }

    // Desencolo muchos elementos, verificando que lo hagan en orden
    for (int i = 0; i < CARGA_MAXIMA; i++) {
      assertFalse(cola.estaVacia());
      assertEquals(Integer.valueOf(i), cola.verPrimero());
      assertEquals(Integer.valueOf(i), cola.desencolar());
    }

    // Verifico que se vació correctamente
    assertTrue(cola.estaVacia());

    // Verifico que se comporta igual que una cola recién creada
    assertThrows(RuntimeException.class, () -> cola.desencolar());
    assertThrows(RuntimeException.class, () -> cola.verPrimero());
  }
}
