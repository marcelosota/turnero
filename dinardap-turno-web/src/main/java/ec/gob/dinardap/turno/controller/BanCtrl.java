package ec.gob.dinardap.turno.controller;

import ec.gob.dinardap.turno.modelo.Baneo;
import ec.gob.dinardap.turno.modelo.TipoRestriccion;
import ec.gob.dinardap.turno.servicio.BaneoServicio;
import ec.gob.dinardap.turno.servicio.TipoRestriccionServicio;
import ec.gob.dinardap.util.constante.EstadoEnum;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named(value = "banCtrl")
@ViewScoped
public class BanCtrl extends BaseCtrl implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2843864813111914703L;
	//Declaración de variables
    //Variables de control visual
    private String tituloPagina;
    private String strBtnGuardar;

    private Boolean disableDeleteBan;

    private Boolean renderEdition;

    private Boolean onCreate;
    private Boolean onEdit;

    //Variables de negocio
    private Baneo baneoSelected;
    private String fechaMin;

    //Listas
    private List<Baneo> baneoList;
    private List<TipoRestriccion> tipoRestriccionList;

    //
    @EJB
    private BaneoServicio baneoServicio;
    @EJB
    private TipoRestriccionServicio tipoRestriccionServicio;

    @PostConstruct
    protected void init() {

        tituloPagina = "Lista de Baneados";

        baneoSelected = new Baneo();

        baneoList = new ArrayList<Baneo>();
        baneoList = baneoServicio.getBanList();

        tipoRestriccionList = new ArrayList<TipoRestriccion>();
        tipoRestriccionList = tipoRestriccionServicio.getTipoRestriccionList();

        disableDeleteBan = Boolean.TRUE;
        renderEdition = Boolean.FALSE;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        fechaMin = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);

    }

    public void onRowSelectBaneo() {
        strBtnGuardar = "Actualizar";

        onEdit = Boolean.FALSE;
        onCreate = Boolean.FALSE;

        disableDeleteBan = baneoSelected.getBaneoId() == null;
        renderEdition = Boolean.FALSE;
    }

    public void nuevoBaneo() {
        baneoSelected = new Baneo();

        strBtnGuardar = "Guardar";

        onCreate = Boolean.TRUE;
        onEdit = Boolean.FALSE;

        disableDeleteBan = baneoSelected.getBaneoId() == null;
        renderEdition = Boolean.TRUE;

    }

    public void guardarBaneo() {
        if (onCreate) {
            baneoSelected.setEstado(EstadoEnum.ACTIVO.getEstado());
            baneoSelected.setFechaCreacion(new Date());
            baneoServicio.create(baneoSelected);
        } else if (onEdit) {
            baneoServicio.update(baneoSelected);
        }
        baneoSelected = new Baneo();
        baneoList = new ArrayList<Baneo>();
        baneoList = baneoServicio.getBanList();

        onCreate = Boolean.FALSE;
        onEdit = Boolean.FALSE;
        renderEdition = Boolean.FALSE;
    }

    public void cancelar() {
        baneoSelected = new Baneo();

        baneoList = baneoServicio.getBanList();

        disableDeleteBan = baneoSelected.getBaneoId() == null;

        onCreate = Boolean.FALSE;
        onEdit = Boolean.FALSE;
        renderEdition = Boolean.FALSE;
    }

    public void eliminarBaneo() {
    	baneoSelected.setFechaModificacion(new Date());
        baneoSelected.setEstado(EstadoEnum.INACTIVO.getEstado());
        baneoServicio.update(baneoSelected);
        baneoSelected = new Baneo();
        baneoList = baneoServicio.getBanList();
        disableDeleteBan = baneoSelected.getBaneoId() == null;
        renderEdition = Boolean.FALSE;
    }

    public List<TipoRestriccion> completeTipoRestriccion(String query) {
        List<TipoRestriccion> filteredTipoRestriccion = new ArrayList<TipoRestriccion>();
        for (TipoRestriccion tr : tipoRestriccionList) {
            if (tr.getParametro().toLowerCase().contains(query)
                    || tr.getParametro().toUpperCase().contains(query)) {
                filteredTipoRestriccion.add(tr);
            }
        }
        return filteredTipoRestriccion;
    }

    //Getters & Setters
    public String getTituloPagina() {
        return tituloPagina;
    }

    public void setTituloPagina(String tituloPagina) {
        this.tituloPagina = tituloPagina;
    }

    public String getStrBtnGuardar() {
        return strBtnGuardar;
    }

    public void setStrBtnGuardar(String strBtnGuardar) {
        this.strBtnGuardar = strBtnGuardar;
    }

    public Boolean getDisableDeleteBan() {
        return disableDeleteBan;
    }

    public void setDisableDeleteBan(Boolean disableDeleteBan) {
        this.disableDeleteBan = disableDeleteBan;
    }

    public Boolean getRenderEdition() {
        return renderEdition;
    }

    public void setRenderEdition(Boolean renderEdition) {
        this.renderEdition = renderEdition;
    }

    public Baneo getBaneoSelected() {
        return baneoSelected;
    }

    public void setBaneoSelected(Baneo baneoSelected) {
        this.baneoSelected = baneoSelected;
    }

    public List<Baneo> getBaneoList() {
        return baneoList;
    }

    public void setBaneoList(List<Baneo> baneoList) {
        this.baneoList = baneoList;
    }

    public String getFechaMin() {
        return fechaMin;
    }

    public void setFechaMin(String fechaMin) {
        this.fechaMin = fechaMin;
    }

}
