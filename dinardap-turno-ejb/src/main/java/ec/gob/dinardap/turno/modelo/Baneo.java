/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.dinardap.turno.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author christian.gaona
 */
@Entity
@Table(name = "baneo")
@NamedQueries({
    @NamedQuery(name = "Baneo.findAll", query = "SELECT b FROM Baneo b")
    , @NamedQuery(name = "Baneo.findByBaneoId", query = "SELECT b FROM Baneo b WHERE b.baneoId = :baneoId")
    , @NamedQuery(name = "Baneo.findByValor", query = "SELECT b FROM Baneo b WHERE b.valor = :valor")
    , @NamedQuery(name = "Baneo.findByFechaInicio", query = "SELECT b FROM Baneo b WHERE b.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Baneo.findByFechaFin", query = "SELECT b FROM Baneo b WHERE b.fechaFin = :fechaFin")
    , @NamedQuery(name = "Baneo.findByEstado", query = "SELECT b FROM Baneo b WHERE b.estado = :estado")})
public class Baneo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "baneo_id")
    private Integer baneoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "valor")
    private String valor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private short estado;

    @JoinColumn(name = "tipo_restriccion_id", referencedColumnName = "tipo_restriccion_id")
    @ManyToOne
    private TipoRestriccion tipoRestriccion;

    public Baneo() {
    }

    public Baneo(Integer baneoId) {
        this.baneoId = baneoId;
    }

    public Baneo(Integer baneoId, String valor, Date fechaInicio, short estado) {
        this.baneoId = baneoId;
        this.valor = valor;
        this.fechaInicio = fechaInicio;
        this.estado = estado;
    }

    public Integer getBaneoId() {
        return baneoId;
    }

    public void setBaneoId(Integer baneoId) {
        this.baneoId = baneoId;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    public TipoRestriccion getTipoRestriccion() {
        return tipoRestriccion;
    }

    public void setTipoRestriccion(TipoRestriccion tipoRestriccion) {
        this.tipoRestriccion = tipoRestriccion;
    }

}
