package br.com.alura.loja.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "valor_total")
	private BigDecimal valorTotal = BigDecimal.ZERO;
	private LocalDate data = LocalDate.now();
	
	//É uma boa prática deixar todos os relacionamentos ToOne como lazy
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

	// Cascade ALL para refletir todas as operações em ItemPedido (se remover o Pedido pode remover o ItensPedido tb)
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens = new ArrayList<>();
	
	public Pedido(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Pedido() {
	}
	
	//É uma boa prática criarmos um método utilitário para setar ao mesmo tempo os dois lados do relacionamento
	public void adicionarItem(ItemPedido item) {
		item.setPedido(this);
		this.itens.add(item);
		this.valorTotal = this.valorTotal.add(item.getValor());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
