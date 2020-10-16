package ec.gob.dinardap.turno.dao;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.turno.modelo.TipoVentanilla;

@Local
public interface TipoVentanillaDao extends GenericDao<TipoVentanilla, java.lang.Integer> {

}
