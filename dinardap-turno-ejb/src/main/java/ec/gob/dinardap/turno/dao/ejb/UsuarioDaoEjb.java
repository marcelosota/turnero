package ec.gob.dinardap.turno.dao.ejb;

import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.dao.ejb.GenericDaoEjb;
import ec.gob.dinardap.turno.dao.UsuarioDao;
import ec.gob.dinardap.turno.modelo.UsuarioT;

@Stateless(name="UsuarioDao")
public class UsuarioDaoEjb extends GenericDaoEjb<UsuarioT, Integer> implements UsuarioDao {

	public UsuarioDaoEjb() {
		super(UsuarioT.class);
	}

}
