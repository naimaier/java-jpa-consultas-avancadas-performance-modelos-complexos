package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.ClienteDAO;
import br.com.alura.loja.dao.PedidoDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.CategoriaId;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.ItemPedido;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

public class CadastroDePedido {
	public static void main(String[] args) {
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		Produto produto = produtoDAO.buscarPorId(1l);
		
		em.getTransaction().begin();
		
		ClienteDAO clienteDAO = new ClienteDAO(em);
		Cliente cliente = clienteDAO.buscarPorId(1l);
		
		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(10, pedido, produto));
		
		PedidoDAO pedidoDAO = new PedidoDAO(em);
		//pedidoDAO.cadastrar(pedido);
		
		em.getTransaction().commit();
		
		BigDecimal totalVendido = pedidoDAO.valorTotalVendido();
		System.out.println("Valor total vendido: " + totalVendido);
		
		List<RelatorioDeVendasVo> relatorioDeVendas = pedidoDAO.relatorioDeVendas();
		relatorioDeVendas.forEach(System.out::println);
		
		em.find(Categoria.class, new CategoriaId("CELULARES", "xpto"));
		
		//Pedido pedido2 = em.find(Pedido.class, 2l);
		Pedido pedido2 = pedidoDAO.buscarPedidoComCliente(2l);
		em.close();

		System.out.println(pedido2.getCliente().getNome());
	}
}
