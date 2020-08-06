package ec.gob.dinardap.turno.dao.ejb;

import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.dao.ejb.GenericDaoEjb;
import ec.gob.dinardap.turno.dao.TipoRestriccionDao;
import ec.gob.dinardap.turno.modelo.TipoRestriccion;

@Stateless(name = "TipoRestriccionDao")
public class TipoRestriccionDaoEjb extends GenericDaoEjb<TipoRestriccion, Integer> implements TipoRestriccionDao {

    public TipoRestriccionDaoEjb() {
        super(TipoRestriccion.class);
    }

}
