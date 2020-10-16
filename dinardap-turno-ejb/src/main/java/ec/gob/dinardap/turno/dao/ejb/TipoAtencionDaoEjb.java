package ec.gob.dinardap.turno.dao.ejb;

import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.dao.ejb.GenericDaoEjb;
import ec.gob.dinardap.turno.dao.TipoAtencionDao;
import ec.gob.dinardap.turno.modelo.TipoAtencion;

@Stateless(name="TipoAtencionDao")
public class TipoAtencionDaoEjb extends GenericDaoEjb<TipoAtencion, Short> implements TipoAtencionDao {

	public TipoAtencionDaoEjb() {
		super(TipoAtencion.class);
	}

}
