package com.example.algamoney.api.service;

import java.util.Optional;

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
	
	public Pessoa salvar(Pessoa pessoa) {
		 pessoa.getContatos().forEach(c -> c.setPessoa(pessoa));
		 return pessoaRepository.save(pessoa);
		}


	
	public Pessoa atualizarPessoa(Long codigo, Pessoa pessoa)
	{
     Pessoa pessoaSalva = buscarPessoaSalva(codigo);
     
     pessoaSalva.getContatos().clear();
     pessoaSalva.getContatos().addAll(pessoa.getContatos());
     pessoaSalva.getContatos().forEach(c -> c.setPessoa(pessoaSalva));
     
     BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo", "contatos");
	 return pessoaRepository.save(pessoaSalva);
	}

	public Pessoa buscarPessoaSalva(Long codigo) {
		Optional<Pessoa> pessoaSalva = pessoaRepository.findById(codigo);
			
			if(!pessoaSalva.isPresent())
			{			
				throw new EmptyResultDataAccessException(1);
			}
		return pessoaSalva.get();
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
