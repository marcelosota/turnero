package ec.gob.dinardap.turno.dao.ejb;

import javax.ejb.Stateless;
import javax.persistence.Query;

import ec.gob.dinardap.persistence.dao.ejb.GenericDaoEjb;
import ec.gob.dinardap.turno.dao.TipoRestriccionDao;
import ec.gob.dinardap.turno.modelo.TipoRestriccion;
import ec.gob.dinardap.util.constante.EstadoEnum;

@Stateless(name = "TipoRestriccionDao")
public class TipoRestriccionDaoEjb extends GenericDaoEjb<TipoRestriccion, Integer> implements TipoRestriccionDao {

    public TipoRestriccionDaoEjb() {
        super(TipoRestriccion.class);
    }

    @Override
    public TipoRestriccion getTipoRestriccion(Integer tipoRestriccionId) {
        Query query = em.createQuery("SELECT tr FROM TipoRestriccion tr WHERE tr.tipoRestriccionId=: tipoRestriccionId AND tr.estado=:estado");
        query.setParameter("tipoRestriccionId", tipoRestriccionId);
        query.setParameter("estado", EstadoEnum.ACTIVO.getEstado());
        TipoRestriccion tipoRestriccion = new TipoRestriccion();
        if (!query.getResultList().isEmpty()) {
            tipoRestriccion = (TipoRestriccion) query.getResultList().get(0);
        }
        return tipoRestriccion;
    }

}
