package ec.gob.dinardap.turno.dao;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.turno.modelo.TipoAtencion;

@Local
public interface TipoAtencionDao extends GenericDao<TipoAtencion, Short> {

}