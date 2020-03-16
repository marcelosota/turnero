package ec.gob.dinardap.turno.dao.ejb;

import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.dao.ejb.GenericDaoEjb;
import ec.gob.dinardap.turno.dao.RegistroMercantilDao;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;

@Stateless(name="RegistroMercantilDao")
public class RegistroMercantilDaoEjb extends GenericDaoEjb<RegistroMercantil, Integer> implements RegistroMercantilDao {

	public RegistroMercantilDaoEjb() {
		super(RegistroMercantil.class);
	}

}
