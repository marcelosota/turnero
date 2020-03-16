package ec.gob.dinardap.turno.dao.ejb;

import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.dao.ejb.GenericDaoEjb;
import ec.gob.dinardap.turno.dao.TurnoDao;
import ec.gob.dinardap.turno.modelo.Turno;

@Stateless(name="TurnoDao")
public class TurnoDaoEjb extends GenericDaoEjb<Turno, Integer> implements TurnoDao {

	public TurnoDaoEjb() {
		super(Turno.class);
	}

}
