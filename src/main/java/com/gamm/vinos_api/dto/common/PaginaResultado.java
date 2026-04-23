package com.gamm.vinos_api.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginaResultado<T> {
  private List<T> data;
  private int pagina;
  private int limite;
  private int totalPaginas;
  private long totalRegistros;
}
