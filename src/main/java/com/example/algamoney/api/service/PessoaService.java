package com.example.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa atualizarPessoa(Long codigo, Pessoa pessoa)
	{
     Pessoa pessoaSalva = buscarPessoaSalva(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
	   return pessoaRepository.save(pessoaSalva);
	}

	public Pessoa buscarPessoaSalva(Long codigo) {
		Pessoa pessoaSalva = pessoaRepository.findOne(codigo);
			
			if(pessoaSalva==null)
			{			
				throw new EmptyResultDataAccessException(1);
			}
		return pessoaSalva;
	}

	public void atualizarPropriedadeAtivo(Long codigo, boolean ativo) {
		// TODO Auto-generated method stub
		Pessoa pessoaSalva = buscarPessoaSalva(codigo);
		pessoaSalva.setAtivo(ativo);
		pessoaRepository.save(pessoaSalva);
		
		
	}

	public void atualizarPropriedadeNome(Long codigo, String nome) {
		// TODO Auto-generated method stub
		Pessoa pessoaSalva = buscarPessoaSalva(codigo);
		pessoaSalva.setNome(nome);
		pessoaRepository.save(pessoaSalva);
		
	}
}
