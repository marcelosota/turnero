/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.dinardap.turno.converter;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;

import ec.gob.dinardap.turno.modelo.TipoRestriccion;
import ec.gob.dinardap.turno.servicio.TipoRestriccionServicio;

/**
 *
 * @author enery
 */
@SuppressWarnings("rawtypes")
@ManagedBean
@Named(value = "tipoRestriccionConverter")
public class TipoRestriccionConverter implements Converter {

    @EJB
    private TipoRestriccionServicio tipoRestriccionServicio;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        TipoRestriccion tr = new TipoRestriccion();
        if (value != null && value.trim().length() > 0) {
            try {
                tr = tipoRestriccionServicio.findByPk(Integer.parseInt(value));
                return tr;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Tipo de Restricción"));
            }
        } else {
            return tr;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return String.valueOf(((TipoRestriccion) object).getTipoRestriccionId());
        } else {
            return null;
        }
    }

}
