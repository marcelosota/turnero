package ec.gob.dinardap.turno.dao.ejb;

import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.dao.ejb.GenericDaoEjb;
import ec.gob.dinardap.turno.dao.ListaBlancaDao;
import ec.gob.dinardap.turno.modelo.ListaBlanca;

@Stateless(name="ListaBlancaDao")
public class ListaBlancaDaoEjb extends GenericDaoEjb<ListaBlanca, Integer> implements ListaBlancaDao {

	public ListaBlancaDaoEjb() {
		super(ListaBlanca.class);
	}

}
