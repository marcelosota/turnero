package ec.gob.dinardap.turno.dao;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.turno.modelo.Turno;
import java.util.Date;

@Local
public interface TurnoDao extends GenericDao<Turno, Integer> {

    public Integer getTurnosDisponibles(Integer ventanillas, Date dia, String hora);

    public Boolean validacionDiariaPersona(Date dia, String cedula);

}
