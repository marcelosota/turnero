package ec.gob.dinardap.turno.dao;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;

@Local
public interface RegistroMercantilDao extends GenericDao<RegistroMercantil, Integer> {

}
