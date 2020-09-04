package ec.gob.dinardap.turno.dao;

import javax.ejb.Local;

import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.turno.modelo.TipoRestriccion;

@Local
public interface TipoRestriccionDao extends GenericDao<TipoRestriccion, Integer> {

    public TipoRestriccion getTipoRestriccion(Integer tipoRestriccionId);

}
