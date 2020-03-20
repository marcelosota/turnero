package ec.gob.dinardap.turno.servicio;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.servicio.GenericService;
import ec.gob.dinardap.turno.modelo.Turno;
import java.util.Date;

@Local
public interface TurnoServicio extends GenericService<Turno, Integer> {

    public Integer getTurnosDisponibles(Integer ventanillas, Date dia, String hora);

    public Boolean validacionDiariaPersona(Date dia, String cedula);

}
