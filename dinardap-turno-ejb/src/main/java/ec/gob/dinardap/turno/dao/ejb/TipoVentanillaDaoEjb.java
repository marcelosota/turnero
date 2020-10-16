package ec.gob.dinardap.turno.dao.ejb;

import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.dao.ejb.GenericDaoEjb;
import ec.gob.dinardap.turno.dao.TipoVentanillaDao;
import ec.gob.dinardap.turno.modelo.TipoVentanilla;

@Stateless(name="TipoVentanillaDao")
public class TipoVentanillaDaoEjb extends
		GenericDaoEjb<TipoVentanilla, Integer> implements TipoVentanillaDao {

	public TipoVentanillaDaoEjb() {
		super(TipoVentanilla.class);
	}
}
