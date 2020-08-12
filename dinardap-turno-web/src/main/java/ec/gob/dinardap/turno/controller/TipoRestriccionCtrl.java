package ec.gob.dinardap.turno.controller;


import ec.gob.dinardap.turno.modelo.TipoRestriccion;
import ec.gob.dinardap.turno.servicio.TipoRestriccionServicio;
import ec.gob.dinardap.util.constante.EstadoEnum;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named(value = "tipoRestriccionCtrl")
@ViewScoped
public class TipoRestriccionCtrl extends BaseCtrl implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Declaración de variables
    //Variables de control visual
    private String tituloPagina;
    private String strBtnGuardar;

    private Boolean disableDeleteTipoRestriccion;

    private Boolean renderEdition;

    private Boolean onCreate;
    private Boolean onEdit;


    //Variables de negocio
    private TipoRestriccion tipoRestriccionSelected;

    //Listas
    private List<TipoRestriccion> tipoRestriccionList;

    //
    @EJB
    private TipoRestriccionServicio tipoRestriccionServicio;

    @PostConstruct
    protected void init() {

        tituloPagina = "Tipo de Restricción";

        tipoRestriccionSelected = new TipoRestriccion();

        tipoRestriccionList = new ArrayList<TipoRestriccion>();
        tipoRestriccionList = tipoRestriccionServicio.getTipoRestriccionList();

        disableDeleteTipoRestriccion = Boolean.TRUE;
        renderEdition = Boolean.FALSE;
    }

    public void onRowSelectTipoRestriccion() {
        strBtnGuardar = "Actualizar";

        onEdit = Boolean.TRUE;
        onCreate = Boolean.FALSE;

        disableDeleteTipoRestriccion = tipoRestriccionSelected.getTipoRestriccionId() == null;
        renderEdition = Boolean.TRUE;

    }

    public void nuevoTipoRestriccion() {
        tipoRestriccionSelected = new TipoRestriccion();

        strBtnGuardar = "Guardar";

        onCreate = Boolean.TRUE;
        onEdit = Boolean.FALSE;

        disableDeleteTipoRestriccion = tipoRestriccionSelected.getTipoRestriccionId() == null;
        renderEdition = Boolean.TRUE;

    }    

    public void guardarTipoRestriccion() {        
        if (onCreate) {
            tipoRestriccionSelected.setFechaCreacion(new Date());
            tipoRestriccionSelected.setEstado(EstadoEnum.ACTIVO.getEstado());
            tipoRestriccionServicio.create(tipoRestriccionSelected);
        } else if (onEdit) {
            tipoRestriccionSelected.setFechaModificacion(new Date());
            tipoRestriccionServicio.update(tipoRestriccionSelected);
        }

        tipoRestriccionSelected = new TipoRestriccion();
        tipoRestriccionList = new ArrayList<TipoRestriccion>();
        tipoRestriccionList = tipoRestriccionServicio.getTipoRestriccionList();

        onCreate = Boolean.FALSE;
        onEdit = Boolean.FALSE;
        renderEdition = Boolean.FALSE;
    }

    public void cancelar() {
        tipoRestriccionSelected = new TipoRestriccion();

        tipoRestriccionList = tipoRestriccionServicio.getTipoRestriccionList();

        disableDeleteTipoRestriccion = tipoRestriccionSelected.getTipoRestriccionId() == null;

        onCreate = Boolean.FALSE;
        onEdit = Boolean.FALSE;
        renderEdition = Boolean.FALSE;
    }

    public void eliminarTipoRestriccion() {
        tipoRestriccionSelected.setEstado(EstadoEnum.INACTIVO.getEstado());
        tipoRestriccionServicio.update(tipoRestriccionSelected);
        tipoRestriccionSelected = new TipoRestriccion();
        tipoRestriccionList = tipoRestriccionServicio.getTipoRestriccionList();
        disableDeleteTipoRestriccion = tipoRestriccionSelected.getTipoRestriccionId() == null;
        renderEdition = Boolean.FALSE;
    }

    /*private void disableDeleteTipoRestriccion() {
        disableDeleteTipoRestriccion = tipoRestriccionSelected.getTipoRestriccionId() != null;
    }*/

    //Getters & Setters
    public String getTituloPagina() {
        return tituloPagina;
    }

    public void setTituloPagina(String tituloPagina) {
        this.tituloPagina = tituloPagina;
    }

    public List<TipoRestriccion> getTipoRestriccionList() {
        return tipoRestriccionList;
    }

    public void setTipoRestriccionList(List<TipoRestriccion> tipoRestriccionList) {
        this.tipoRestriccionList = tipoRestriccionList;
    }

    public TipoRestriccion getTipoRestriccionSelected() {
        return tipoRestriccionSelected;
    }

    public void setTipoRestriccionSelected(TipoRestriccion tipoRestriccionSelected) {
        this.tipoRestriccionSelected = tipoRestriccionSelected;
    }

    public Boolean getDisableDeleteTipoRestriccion() {
        return disableDeleteTipoRestriccion;
    }

    public void setDisableDeleteTipoRestriccion(Boolean disableDeleteTipoRestriccion) {
        this.disableDeleteTipoRestriccion = disableDeleteTipoRestriccion;
    }

    public Boolean getRenderEdition() {
        return renderEdition;
    }

    public void setRenderEdition(Boolean renderEdition) {
        this.renderEdition = renderEdition;
    }

    public String getStrBtnGuardar() {
        return strBtnGuardar;
    }

    public void setStrBtnGuardar(String strBtnGuardar) {
        this.strBtnGuardar = strBtnGuardar;
    }

}
