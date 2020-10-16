package ec.gob.dinardap.turno.dao;

import java.util.List;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.turno.modelo.Atencion;

@Local
public interface AtencionDao extends GenericDao<Atencion, Integer> {

	public List<Atencion> obtenerAtencionParaTodaInstitucion();
}
