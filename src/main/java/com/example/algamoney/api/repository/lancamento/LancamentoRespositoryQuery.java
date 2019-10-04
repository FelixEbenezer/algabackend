package com.example.algamoney.api.repository.lancamento;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.algamoney.api.dto.LancamentoEstatisticaCategoria;
import com.example.algamoney.api.dto.LancamentoEstatisticaDia;
import com.example.algamoney.api.dto.LancamentoEstatisticaPessoa;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.filter.LancamentoFilter;
import com.example.algamoney.api.repository.projection.ResumoLancamento;

public interface LancamentoRespositoryQuery {
	
 public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);	
 public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
 //vai listar com pagincacao...
 
 // consulta por grafico por categoria
 public List<LancamentoEstatisticaCategoria> porCategoria(LocalDate mesReferencia);
 // consulta por grafico por categoria por dia
 public List<LancamentoEstatisticaDia> porDia (LocalDate mesReferencia);
 // consulta para popular o relatorio
 public List<LancamentoEstatisticaPessoa> porPessoa(LocalDate inicio, LocalDate fim);
}
