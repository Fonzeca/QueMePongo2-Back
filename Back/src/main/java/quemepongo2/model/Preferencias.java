package main.java.quemepongo2.model;
// Generated 16-oct-2019 12:54:32 by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Preferencias generated by hbm2java
 */
@Entity
@Table(name = "Preferencias", schema = "dbo", catalog = "QueMePongo2")
public class Preferencias implements java.io.Serializable {

	private Integer id;
	private Usuario usuario;
	private boolean tieneParaguas;
	private boolean tieneProtectorSolar;

	public Preferencias() {
	}

	public Preferencias(Usuario usuario, boolean tieneParaguas, boolean tieneProtectorSolar) {
		this.usuario = usuario;
		this.tieneParaguas = tieneParaguas;
		this.tieneProtectorSolar = tieneProtectorSolar;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "Id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UsuarioId", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "TieneParaguas", nullable = false)
	public boolean isTieneParaguas() {
		return this.tieneParaguas;
	}

	public void setTieneParaguas(boolean tieneParaguas) {
		this.tieneParaguas = tieneParaguas;
	}

	@Column(name = "TieneProtectorSolar", nullable = false)
	public boolean isTieneProtectorSolar() {
		return this.tieneProtectorSolar;
	}

	public void setTieneProtectorSolar(boolean tieneProtectorSolar) {
		this.tieneProtectorSolar = tieneProtectorSolar;
	}

}