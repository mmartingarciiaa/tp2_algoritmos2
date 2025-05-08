public class ValidacionesUtils {

  public static void noNulo(Object valor, String valorString) throws RuntimeException {
    if (valor == null) {
      throw new RuntimeException("Valor " + valorString + " no puede ser nulo");
    }
  }

  public static void validarPositivo(int valor, String valorString) throws RuntimeException {
    if (valor < 0) {
      throw new RuntimeException("Valor " + valorString + " debe ser positivo");
    }
  }

  public static void validarMayorACero(int valor, String valorString) throws RuntimeException {
    if (valor <= 0) {
      throw new RuntimeException("Valor " + valorString + " debe ser mayor a cero");
    }
  }

  public static void validarMinimoDeCaracteres(String cadena, int minimoCaracteres, String valorString) throws RuntimeException {
    if (cadena.length() < minimoCaracteres) {
      throw new RuntimeException("Cadena " + valorString + " debe tener al menos " + minimoCaracteres + " caracteres");
    }
  }
}
