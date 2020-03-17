package ec.gob.dinardap.turno.dao.ejb;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ec.gob.dinardap.persistence.dao.ejb.GenericEmDaoEjb;


public class TurnoGenericDao<T, PK extends Serializable> extends GenericEmDaoEjb<T, PK> {
	/**
	 * @param type
	 */
	public TurnoGenericDao(Class<T> type) {
		super(type);
	}

	@PersistenceContext(unitName = "turnoPU")
	protected EntityManager em;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ec.gob.dinardap.persistence.dao.ejb.GenericEmDaoEjb#getEm()
	 */
	@Override
	protected EntityManager getEm() {
		return this.em;
	}
}