package com.gamm.vinos_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

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
    this(true, "Operación exitosa", data);
  }

  // Constructor para respuestas de error
  public ResponseVO(String message) {
    this(false, message, null);
  }

  // Nuevo constructor completo
  public ResponseVO(boolean success, String message, Object data) {
    this.success = success;
    this.message = message;
    this.data = data;
  }

  // Constructor para respuestas paginadas
  public ResponseVO(Object data, Integer pagina, Integer limite, Integer totalPaginas, Long totalRegistros) {
    this(true, "Operación exitosa", data);
    this.pagina = pagina;
    this.limite = limite;
    this.totalPaginas = totalPaginas;
    this.totalRegistros = totalRegistros;
  }

  // Métodos estáticos de conveniencia
  @SuppressWarnings("rawtypes")
  public static ResponseVO success(Object data) {
    // Determinar mensaje según el contenido
    String message = (data == null)
        ? "No hay datos disponibles."
        : (data instanceof Collection && ((Collection) data).isEmpty())
        ? "No se encontraron resultados."
        : "Operación exitosa";

    return new ResponseVO(true, message, data);
  }

  public static ResponseVO success(String message, Object data) {
    return new ResponseVO(true, message, data);
  }

  public static ResponseVO error(String message) {
    return new ResponseVO(false, message, null);
  }

  public static ResponseVO error(String message, Object detalles) {
    return new ResponseVO(false, message, detalles);
  }

  public static ResponseVO paginated(Object data, Integer pagina, Integer limite, Integer totalPaginas, Long totalRegistros) {
    return new ResponseVO(data, pagina, limite, totalPaginas, totalRegistros);
  }
}
