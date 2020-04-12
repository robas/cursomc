package br.com.worx.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.worx.cursomc.domain.Categoria;
import br.com.worx.cursomc.domain.Cidade;
import br.com.worx.cursomc.domain.Cliente;
import br.com.worx.cursomc.domain.Endereco;
import br.com.worx.cursomc.domain.Estado;
import br.com.worx.cursomc.domain.ItemPedido;
import br.com.worx.cursomc.domain.Pagamento;
import br.com.worx.cursomc.domain.PagamentoComBoleto;
import br.com.worx.cursomc.domain.PagamentoComCartao;
import br.com.worx.cursomc.domain.Pedido;
import br.com.worx.cursomc.domain.Produto;
import br.com.worx.cursomc.domain.enums.EstadoPagamento;
import br.com.worx.cursomc.domain.enums.TipoCliente;
import br.com.worx.cursomc.repositories.CategoriaRepository;
import br.com.worx.cursomc.repositories.CidadeRepository;
import br.com.worx.cursomc.repositories.ClienteRepository;
import br.com.worx.cursomc.repositories.EnderecoRepository;
import br.com.worx.cursomc.repositories.EstadoRepository;
import br.com.worx.cursomc.repositories.ItemPedidoRepository;
import br.com.worx.cursomc.repositories.PagamentoRepository;
import br.com.worx.cursomc.repositories.PedidoRepository;
import br.com.worx.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	CategoriaRepository categoriaRepo;
	@Autowired
	ProdutoRepository produtoRepo;
	@Autowired
	EstadoRepository estadoRepo;
	@Autowired
	CidadeRepository cidadeRepo;
	@Autowired
	ClienteRepository clienteRepo;
	@Autowired
	EnderecoRepository enderecoRepo;
	@Autowired
	PagamentoRepository pagamentoRepo;
	@Autowired
	PedidoRepository pedidoRepo;
	@Autowired
	ItemPedidoRepository itemPedidoRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepo.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepo.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "SP");
		Estado est2 = new Estado(null, "RJ");
		
		Cidade cid1 = new Cidade(null, "São Paulo", est1);
		Cidade cid2 = new Cidade(null, "Rio de Janeiro", est2);
		Cidade cid3 = new Cidade(null, "Ribeirão Preto", est1);
		
		est1.getCidades().addAll(Arrays.asList(cid1, cid3));
		est2.getCidades().addAll(Arrays.asList(cid2));
		
		estadoRepo.saveAll(Arrays.asList(est1, est2));
		cidadeRepo.saveAll(Arrays.asList(cid1, cid2, cid3));
		
		Cliente cli1 = new Cliente(null, "Maria da Silva", "maria@gmail.com", "36371165435", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27334574", "11223344"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "1445666", cli1, cid1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "201", "Sala 800", "Centro", "0957382", cli1, cid2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepo.saveAll(Arrays.asList(cli1));
		enderecoRepo.saveAll(Arrays.asList(e1, e2));
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pgto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pgto1);
		
		Pagamento pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pgto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepo.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepo.saveAll(Arrays.asList(pgto1, pgto2));
		
		ItemPedido ip1 = new ItemPedido(p1, ped1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(p3, ped1, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(p2, ped2, 100.00, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepo.saveAll(Arrays.asList(ip1, ip2, ip3));
	}

}
