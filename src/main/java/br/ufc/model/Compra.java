package br.ufc.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
	@JoinTable(name = "compra_produto", joinColumns = @JoinColumn(name = "compra_id"),
	inverseJoinColumns = @JoinColumn(name = "produto_id"))
    private List<Produto> produtos;

    @Column(name = "created", nullable = false)
	private LocalDateTime created;

	@ManyToOne
	@JoinTable(name = "compra_usuario", joinColumns = @JoinColumn(name="codigo_compra"), 
	inverseJoinColumns = @JoinColumn(name = "codigo_usuario"))
	private Usuario usuario;
	
	private Double total;

	public Compra() {}

	public Compra(List<Produto> produtos, Usuario usuario) {
		this.produtos = produtos;
		this.usuario = usuario;
	}

    @PrePersist
    protected void onCreate() {
		created = LocalDateTime.now();
		total = calcularValorTotal();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Double getTotal() {
		return this.total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}	
	
	public Double calcularValorTotal() {
		this.total = new Double(0);
		produtos.forEach(produto -> total += produto.getPreco().doubleValue());
		return total;
	}

	public Integer getQuantidadeProdutos() {
		return produtos.size();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Compra other = (Compra) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}