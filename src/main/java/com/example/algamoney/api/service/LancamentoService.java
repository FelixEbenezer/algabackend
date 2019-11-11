package com.example.algamoney.api.service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.dto.LancamentoEstatisticaPessoa;
import com.example.algamoney.api.mail.Mailer;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.model.Usuario;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.repository.UsuarioRepository;
import com.example.algamoney.api.resource.LancamentoResource;
import com.example.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class LancamentoService {

	@Autowired
	private PessoaRepository pessoaRepository; 
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private LancamentoResource lancamentoResource; 
	
	private static final String DESTINATARIOS = "ROLE_PESQUISAR_LANCAMENTO";
	
	private static final Logger logger = LoggerFactory.getLogger(LancamentoService.class);

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private Mailer mailer;
	
	// actualizadao o metodo abaixo
	@Scheduled(cron = "0 30 17 11 * * ")
	public void avisarSobreLancamentosVencidos() {
		if (logger.isDebugEnabled()) {
			logger.debug("Preparando envio de "
					+ "e-mails de aviso de lançamentos vencidos.");
		}
		
		List<Lancamento> vencidos = lancamentoRepository
				.findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate.now());
		
		if (vencidos.isEmpty()) {
			logger.info("Sem lançamentos vencidos para aviso.");
			
			return;
		}
		
		logger.info("Exitem {} lançamentos vencidos.", vencidos.size());
		
		List<Usuario> destinatarios = usuarioRepository
				.findByPermissoesDescricao(DESTINATARIOS);
		
		if (destinatarios.isEmpty()) {
			logger.warn("Existem lançamentos vencidos, mas o "
					+ "sistema não encontrou destinatários.");
			
			return;
		}
		
		mailer.avisarSobreLancamentosVencidos(vencidos, destinatarios);
		
		logger.info("Envio de e-mail de aviso concluído."); 
	}
	
	
	

	
	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.getOne(lancamento.getPessoa().getCodigo());
		
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
		Optional<Lancamento> lancamentoSalva = lancamentoRepository.findById(codigo);
			
			if(!lancamentoSalva.isPresent())
			{			
				throw new EmptyResultDataAccessException(1);
			}
		return lancamentoSalva.get();
	}
	
	
	public void atualizarPropriedadeDescricao(Long codigo, String descricao) {
		// TODO Auto-generated method stub
		Lancamento lancamentoSalva = buscarLancamentoSalva(codigo);
		lancamentoSalva.setDescricao(descricao);
		lancamentoRepository.save(lancamentoSalva);
		
	}

	// para ler e gerar byte o arquivo de relatorio
	public byte [] relatorioPorPessoa(LocalDate inicio, LocalDate fim) throws Exception {
		List<LancamentoEstatisticaPessoa> dados = lancamentoRepository.porPessoa(inicio, fim);
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("DT_INICIO", Date.valueOf(inicio));  // Date daqui é o de java.Sql.Date e nao java.util.date
		                                                  //nao tem problema nenhum porque java.sql.date extend de java.util.date
		                                                // é o contrario que seria anormal
		parametros.put("DT_FIM", Date.valueOf(fim));
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR")); // isso é relativo
		//é usado se quer listar valores em moeda brasileira, se nao inserir esta linha
		//sera lista valores monetarios em formato europeu ou americano que e por defeito
		
		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/lancamentos-por-pessoa.jasper");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, 
				new JRBeanCollectionDataSource(dados)); 
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}
	
	
}
