/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.dinardap.turno.converter;

import ec.gob.dinardap.turno.modelo.RegistroMercantil;
import ec.gob.dinardap.turno.servicio.RegistroMercantilServicio;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;

/**
 *
 * @author enery
 */
@ManagedBean
@Named(value = "registroMercantilConverter")
public class RegistroMercantilConverter implements Converter {

    @EJB
    private RegistroMercantilServicio registroMercantilServicio;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        RegistroMercantil rm = new RegistroMercantil();
        if (value != null && value.trim().length() > 0) {
            try {
                rm = registroMercantilServicio.findByPk(Integer.parseInt(value));
                return rm;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Registro Mercantil"));
            }
        } else {
            return rm;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return String.valueOf(((RegistroMercantil) object).getRegistroMercantilId());
        } else {
            return null;
        }
    }

}
