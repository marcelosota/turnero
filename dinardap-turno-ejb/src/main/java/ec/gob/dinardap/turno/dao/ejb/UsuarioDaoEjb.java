package ec.gob.dinardap.turno.dao.ejb;

import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.dao.ejb.GenericDaoEjb;
import ec.gob.dinardap.turno.dao.UsuarioDao;
import ec.gob.dinardap.turno.modelo.Usuario;

@Stateless(name="UsuarioDao")
public class UsuarioDaoEjb extends GenericDaoEjb<Usuario, Integer> implements UsuarioDao {

	public UsuarioDaoEjb() {
		super(Usuario.class);
	}

}
