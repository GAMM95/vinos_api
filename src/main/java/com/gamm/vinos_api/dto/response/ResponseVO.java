package com.gamm.vinos_api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamm.vinos_api.exception.business.BusinessException;
import com.gamm.vinos_api.util.ResultadoSP;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseVO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private boolean success;
  private String message;
  private Object data;

  // Paginación
  private Integer pagina;
  private Integer limite;
  private Integer totalPaginas;
  private Long totalRegistros;

  // Constructor base privado — toda construcción pasa por los métodos estáticos
  private ResponseVO(boolean success, String message, Object data) {
    this.success = success;
    this.message = message;
    this.data = data;
  }

  // --- Fábrica: éxito ---
  public static ResponseVO success(Object data) {
    String message = resolverMensaje(data);
    return new ResponseVO(true, message, data);
  }

  public static ResponseVO success(String message, Object data) {
    return new ResponseVO(true, message, data);
  }

  // --- Fábrica: error ---
  public static ResponseVO error(String message) {
    return new ResponseVO(false, message, null);
  }

  // --- Fábrica: paginado ---
  public static ResponseVO paginated(Object data, int pagina, int limite, int totalPaginas, long totalRegistros) {
    ResponseVO vo = new ResponseVO(true, "Operación exitosa", data);
    vo.pagina = pagina;
    vo.limite = limite;
    vo.totalPaginas = totalPaginas;
    vo.totalRegistros = totalRegistros;
    return vo;
  }

  // Validar un ResultadoSP y lanza BusinessException con el mensaje del SP si falló.
  public static void validar(ResultadoSP resultado) {
    if (!resultado.esExitoso()) {
      throw new BusinessException(resultado.getMensaje());
    }
  }

  @SuppressWarnings("rawtypes")
  private static String resolverMensaje(Object data) {
    if (data == null) return "No hay datos disponibles.";
    if (data instanceof Collection c && c.isEmpty()) return "No se encontraron resultados.";
    return "Operación exitosa";
  }
}
