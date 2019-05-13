package com.example.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.resource.LancamentoResource;
import com.example.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private PessoaRepository pessoaRepository; 
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private LancamentoResource lancamentoResource; 
	
	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		
		if(pessoa ==null || pessoa.isInativo() )
		{
			throw new PessoaInexistenteOuInativaException();
		}
		return lancamentoRepository.save(lancamento);
	}
	
	
	
	
	//----------------------------------------------------------------------
	
	public Lancamento atualizarLancamento(Long codigo, Lancamento lancamento)
	{
     Lancamento lancamentoSalva = buscarLancamentoSalva(codigo);
		BeanUtils.copyProperties(lancamento, lancamentoSalva, "codigo");
	   return lancamentoRepository.save(lancamentoSalva);
	}
	
	
	public Lancamento buscarLancamentoSalva(Long codigo) {
		Lancamento lancamentoSalva = lancamentoRepository.findOne(codigo);
			
			if(lancamentoSalva==null)
			{			
				throw new EmptyResultDataAccessException(1);
			}
		return lancamentoSalva;
	}
	
	
	public void atualizarPropriedadeDescricao(Long codigo, String descricao) {
		// TODO Auto-generated method stub
		Lancamento lancamentoSalva = buscarLancamentoSalva(codigo);
		lancamentoSalva.setDescricao(descricao);
		lancamentoRepository.save(lancamentoSalva);
		
	}

	
	
	
}
