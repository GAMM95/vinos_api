package com.gamm.vinos_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * ResponseVO representa la respuesta estándar de la API,
 * con soporte opcional para paginación.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseVO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private boolean success;     // true si la operación fue exitosa
  private String message;      // mensaje descriptivo
  private Object data;         // datos de respuesta (puede ser cualquier objeto)

  // Campos opcionales para paginación
  private Integer pagina;
  private Integer limite;
  private Integer totalPaginas;
  private Long totalRegistros;

  // Constructor para respuestas exitosas sin paginación
  public ResponseVO(Object data) {
    this.success = true;
    this.message = "Operación exitosa";
    this.data = data;
  }

  // Constructor para respuestas de error
  public ResponseVO(String message) {
    this.success = false;
    this.message = message;
    this.data = null;
  }

  // Constructor para respuestas paginadas
  public ResponseVO(Object data, Integer pagina, Integer limite, Integer totalPaginas, Long totalRegistros) {
    this.success = true;
    this.message = "Operación exitosa";
    this.data = data;
    this.pagina = pagina;
    this.limite = limite;
    this.totalPaginas = totalPaginas;
    this.totalRegistros = totalRegistros;
  }

  // 🔹 Nuevo constructor completo
  public ResponseVO(boolean success, String message, Object data) {
    this.success = success;
    this.message = message;
    this.data = data;
  }

  // Métodos estáticos de conveniencia
  public static ResponseVO success(Object data) {
    return new ResponseVO(data);
  }

  public static ResponseVO error(String message) {
    return new ResponseVO(message);
  }

  public static ResponseVO paginated(Object data, Integer pagina, Integer limite, Integer totalPaginas, Long totalRegistros) {
    return new ResponseVO(data, pagina, limite, totalPaginas, totalRegistros);
  }
}
