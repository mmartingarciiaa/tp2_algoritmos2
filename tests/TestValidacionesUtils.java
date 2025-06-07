package tests;

import org.junit.jupiter.api.Test;
import utils.ValidacionesUtils;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestValidacionesUtils {

  @Test
  public void testNoNulo() {
    assertDoesNotThrow(() -> ValidacionesUtils.noNulo("valor", "test"));
    assertThrows(RuntimeException.class, () -> ValidacionesUtils.noNulo(null, "test"));
  }

  @Test
  public void testValidarPositivo() {
    assertDoesNotThrow(() -> ValidacionesUtils.validarPositivo(0, "test"));
    assertDoesNotThrow(() -> ValidacionesUtils.validarPositivo(1, "test"));
    assertThrows(RuntimeException.class, () -> ValidacionesUtils.validarPositivo(-1, "test"));
  }

  @Test
  public void testValidarMayorACero() {
    assertDoesNotThrow(() -> ValidacionesUtils.validarMayorACero(1, "test"));
    assertThrows(RuntimeException.class, () -> ValidacionesUtils.validarMayorACero(0, "test"));
    assertThrows(RuntimeException.class, () -> ValidacionesUtils.validarMayorACero(-1, "test"));
  }

  @Test
  public void testValidarMinimoDeCaracteres() {
    assertDoesNotThrow(() -> ValidacionesUtils.validarMinimoDeCaracteres("test", 4, "test"));
    assertDoesNotThrow(() -> ValidacionesUtils.validarMinimoDeCaracteres("test", 3, "test"));
    assertThrows(RuntimeException.class, () -> ValidacionesUtils.validarMinimoDeCaracteres("test", 5, "test"));
  }

  @Test
  public void testValidarTerminacionDeCadena() {
    assertDoesNotThrow(() -> ValidacionesUtils.validarTerminacionDeCadena("test.txt", ".txt", "test"));
    assertThrows(RuntimeException.class, () -> ValidacionesUtils.validarTerminacionDeCadena("test.doc", ".txt", "test"));
  }
}
