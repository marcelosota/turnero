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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the atencion database table.
 * 
 */
@Entity
@NamedQuery(name="Atencion.findAll", query="SELECT a FROM Atencion a")
public class Atencion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ATENCION_ATENCIONID_GENERATOR", sequenceName="ATENCION_ATENCION_ID_SEQ", allocationSize = 1, schema = "ec_dinardap_turno")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ATENCION_ATENCIONID_GENERATOR")
	@Column(name="atencion_id")
	private Integer atencionId;

	@Column(name="atencion_suspension")
	private Short atencionSuspension;

	private Short estado;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_desde")
	private Date fechaDesde;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_hasta")
	private Date fechaHasta;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_modificacion")
	private Date fechaModificacion;

	@Temporal(TemporalType.TIME)
	@Column(name="hora_desde")
	private Date horaDesde;

	@Column(name="hora_hasta")
	private Date horaHasta;

	//bi-directional many-to-one association to TipoAtencion
	@ManyToOne
	@JoinColumn(name="tipo_atencion_id")
	private TipoAtencion tipoAtencion;

	//bi-directional many-to-one association to RegistroMercantil
	@ManyToOne
	@JoinColumn(name="registro_mercantil_id")
	private RegistroMercantil registroMercantil;

	public Atencion() {
	}

	public Integer getAtencionId() {
		return this.atencionId;
	}

	public void setAtencionId(Integer atencionId) {
		this.atencionId = atencionId;
	}

	public Short getAtencionSuspension() {
		return this.atencionSuspension;
	}

	public void setAtencionSuspension(Short atencionSuspension) {
		this.atencionSuspension = atencionSuspension;
	}

	public Short getEstado() {
		return this.estado;
	}

	public void setEstado(Short estado) {
		this.estado = estado;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaDesde() {
		return this.fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return this.fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Date getHoraDesde() {
		return this.horaDesde;
	}

	public void setHoraDesde(Date horaDesde) {
		this.horaDesde = horaDesde;
	}

	public Date getHoraHasta() {
		return this.horaHasta;
	}

	public void setHoraHasta(Date horaHasta) {
		this.horaHasta = horaHasta;
	}

	public TipoAtencion getTipoAtencion() {
		return this.tipoAtencion;
	}

	public void setTipoAtencion(TipoAtencion tipoAtencion) {
		this.tipoAtencion = tipoAtencion;
	}

	public RegistroMercantil getRegistroMercantil() {
		return this.registroMercantil;
	}

	public void setRegistroMercantil(RegistroMercantil registroMercantil) {
		this.registroMercantil = registroMercantil;
	}

}