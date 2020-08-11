/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.dinardap.turno.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author christian.gaona
 */
@Entity
@Table(name = "tipo_restriccion")
@NamedQueries({
    @NamedQuery(name = "TipoRestriccion.findAll", query = "SELECT t FROM TipoRestriccion t")
    , @NamedQuery(name = "TipoRestriccion.findByTipoRestriccionId", query = "SELECT t FROM TipoRestriccion t WHERE t.tipoRestriccionId = :tipoRestriccionId")
    , @NamedQuery(name = "TipoRestriccion.findByParametro", query = "SELECT t FROM TipoRestriccion t WHERE t.parametro = :parametro")
    , @NamedQuery(name = "TipoRestriccion.findByCuotaPermitida", query = "SELECT t FROM TipoRestriccion t WHERE t.cuotaPermitida = :cuotaPermitida")
    , @NamedQuery(name = "TipoRestriccion.findByDiasAnalisis", query = "SELECT t FROM TipoRestriccion t WHERE t.diasAnalisis = :diasAnalisis")
    , @NamedQuery(name = "TipoRestriccion.findByDiasBaneo", query = "SELECT t FROM TipoRestriccion t WHERE t.diasBaneo = :diasBaneo")
    , @NamedQuery(name = "TipoRestriccion.findByFechaCreacion", query = "SELECT t FROM TipoRestriccion t WHERE t.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "TipoRestriccion.findByFechaModificacion", query = "SELECT t FROM TipoRestriccion t WHERE t.fechaModificacion = :fechaModificacion")
    , @NamedQuery(name = "TipoRestriccion.findByEstado", query = "SELECT t FROM TipoRestriccion t WHERE t.estado = :estado")})
public class TipoRestriccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tipo_restriccion_id")
    private Integer tipoRestriccionId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "parametro")
    private String parametro;

    @Basic(optional = false)
    @NotNull
    @Column(name = "cuota_permitida")
    private Integer cuotaPermitida;

    @Basic(optional = false)
    @NotNull
    @Column(name = "dias_analisis")
    private Integer diasAnalisis;

    @Column(name = "dias_baneo")
    private Integer diasBaneo;

    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private short estado;

    @OneToMany(mappedBy = "tipoRestriccion")
    private List<Baneo> baneoList;

    public TipoRestriccion() {
    }

    public TipoRestriccion(Integer tipoRestriccionId) {
        this.tipoRestriccionId = tipoRestriccionId;
    }

    public TipoRestriccion(Integer tipoRestriccionId, String parametro, int cuotaPermitida, int diasAnalisis, Date fechaCreacion, short estado) {
        this.tipoRestriccionId = tipoRestriccionId;
        this.parametro = parametro;
        this.cuotaPermitida = cuotaPermitida;
        this.diasAnalisis = diasAnalisis;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
    }

    public Integer getTipoRestriccionId() {
        return tipoRestriccionId;
    }

    public void setTipoRestriccionId(Integer tipoRestriccionId) {
        this.tipoRestriccionId = tipoRestriccionId;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public Integer getCuotaPermitida() {
        return cuotaPermitida;
    }

    public void setCuotaPermitida(Integer cuotaPermitida) {
        this.cuotaPermitida = cuotaPermitida;
    }

    public Integer getDiasAnalisis() {
        return diasAnalisis;
    }

    public void setDiasAnalisis(Integer diasAnalisis) {
        this.diasAnalisis = diasAnalisis;
    }

    public Integer getDiasBaneo() {
        return diasBaneo;
    }

    public void setDiasBaneo(Integer diasBaneo) {
        this.diasBaneo = diasBaneo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Baneo> getBaneoList() {
        return baneoList;
    }

    public void setBaneoList(List<Baneo> baneoList) {
        this.baneoList = baneoList;
    }

}
