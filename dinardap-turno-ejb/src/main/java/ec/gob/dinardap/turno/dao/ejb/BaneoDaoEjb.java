package ec.gob.dinardap.turno.dao.ejb;

import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.dao.ejb.GenericDaoEjb;
import ec.gob.dinardap.turno.dao.BaneoDao;
import ec.gob.dinardap.turno.modelo.Baneo;

@Stateless(name = "BaneoDao")
public class BaneoDaoEjb extends GenericDaoEjb<Baneo, Integer> implements BaneoDao {

    public BaneoDaoEjb() {
        super(Baneo.class);
    }

}
