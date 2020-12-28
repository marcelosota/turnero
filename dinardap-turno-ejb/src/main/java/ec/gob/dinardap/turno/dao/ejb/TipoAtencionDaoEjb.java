package ec.gob.dinardap.turno.dao.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import ec.gob.dinardap.persistence.dao.ejb.GenericDaoEjb;
import ec.gob.dinardap.turno.dao.TipoAtencionDao;
import ec.gob.dinardap.turno.modelo.TipoAtencion;

@Stateless(name="TipoAtencionDao")
public class TipoAtencionDaoEjb extends GenericDaoEjb<TipoAtencion, Short> implements TipoAtencionDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoAtencion> obtenerTipoAtencionEstadoFiltrado(List<Short> tipoAtencionId, Short estado, boolean incluye) {
		StringBuilder sql = new StringBuilder("SELECT t FROM TipoAtencion t WHERE t.tipoAtencionId ");
		if(incluye)
			sql.append("IN ");
		else
			sql.append("NOT IN ");
		sql.append(":tipoAtencionId AND t.estado = :estado");
		Query query = em.createQuery(sql.toString());
		query.setParameter("tipoAtencionId", tipoAtencionId);
		query.setParameter("estado", estado);
		return query.getResultList();
	}

	public TipoAtencionDaoEjb() {
		super(TipoAtencion.class);
	}

}
