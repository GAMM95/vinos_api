package com.gamm.vinos_api.config.properties;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumeroALetras {

  public static String convertir(BigDecimal monto) {

    if (monto == null) return "";

    long entero = monto.longValue();

    int decimales = monto
        .subtract(new BigDecimal(entero))
        .multiply(BigDecimal.valueOf(100))
        .setScale(0, RoundingMode.HALF_UP)
        .intValue();

    String letras = convertirNumero(entero);

    if (decimales == 0) {
      return letras + " SOLES";
    }

    return letras + " CON " + String.format("%02d", decimales) + "/100 SOLES";
  }

  private static String convertirNumero(long n) {
    // versión simple (puedes expandirla)
    String[] unidades = {
        "", "UNO", "DOS", "TRES", "CUATRO", "CINCO",
        "SEIS", "SIETE", "OCHO", "NUEVE", "DIEZ",
        "ONCE", "DOCE", "TRECE", "CATORCE", "QUINCE"
    };

    if (n <= 15) return unidades[(int) n];
    if (n < 20) return "DIECI" + unidades[(int) (n - 10)];
    if (n == 20) return "VEINTE";
    if (n < 30) return "VEINTI" + unidades[(int) (n - 20)];
    if (n == 30) return "TREINTA";
    if (n < 40) return "TREINTA Y " + unidades[(int) (n - 30)];
    if (n == 40) return "CUARENTA";
    if (n < 50) return "CUARENTA Y " + unidades[(int) (n - 40)];
    if (n == 50) return "CINCUENTA";
    if (n < 60) return "SESENTA Y " + unidades[(int) (n - 50)];
    if (n == 60) return "SESENTA";
    if (n < 70) return "SESENTA Y " + unidades[(int) (n - 60)];
    if (n == 70) return "SETENTA";
    if (n < 80) return "SETENTA Y " + unidades[(int) (n - 70)];
    if (n == 80) return "OCHENTA";
    if (n < 90) return "OCHENTA Y " + unidades[(int) (n - 80)];
    if (n == 90) return "NOVENTA";
    if (n < 100) return "NOVENTA Y " + unidades[(int) (n - 90)];

    return String.valueOf(n);
  }
}
