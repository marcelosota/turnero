package ec.gob.dinardap.turno.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the lista_blanca database table.
 * 
 */
@Entity
@Table(name="lista_blanca")
@NamedQuery(name="ListaBlanca.findAll", query="SELECT l FROM ListaBlanca l")
public class ListaBlanca implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LISTA_BLANCA_LISTABLANCAID_GENERATOR", sequenceName="LISTA_BLANCA_LISTA_BLANCA_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LISTA_BLANCA_LISTABLANCAID_GENERATOR")
	@Column(name="lista_blanca_id")
	private Integer listaBlancaId;

	private Short estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_fin")
	private Date fechaFin;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_inicio")
	private Date fechaInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_modificacion")
	private Date fechaModificacion;

	private String valor;

	//bi-directional many-to-one association to TipoRestriccion
	@ManyToOne
	@JoinColumn(name="tipo_restriccion_id")
	private TipoRestriccion tipoRestriccion;

	public ListaBlanca() {
	}

	public Integer getListaBlancaId() {
		return this.listaBlancaId;
	}

	public void setListaBlancaId(Integer listaBlancaId) {
		this.listaBlancaId = listaBlancaId;
	}

	public Short getEstado() {
		return this.estado;
	}

	public void setEstado(Short estado) {
		this.estado = estado;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public TipoRestriccion getTipoRestriccion() {
		return this.tipoRestriccion;
	}

	public void setTipoRestriccion(TipoRestriccion tipoRestriccion) {
		this.tipoRestriccion = tipoRestriccion;
	}

}