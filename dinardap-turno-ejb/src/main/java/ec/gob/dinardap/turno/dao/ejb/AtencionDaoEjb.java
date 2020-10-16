package ec.gob.dinardap.turno.dao.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import ec.gob.dinardap.persistence.dao.ejb.GenericDaoEjb;
import ec.gob.dinardap.turno.dao.AtencionDao;
import ec.gob.dinardap.turno.modelo.Atencion;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;
import ec.gob.dinardap.util.constante.EstadoEnum;

@Stateless(name="AtencionDao")
public class AtencionDaoEjb extends GenericDaoEjb<Atencion, Integer> implements AtencionDao {

	public AtencionDaoEjb() {
		super(Atencion.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Atencion> obtenerAtencionParaTodaInstitucion() {
		Query query = em.createQuery("SELECT a FROM Atencion a WHERE "
                + "a.registroMercantil.registroMercantilId is null "
                + "AND a.estado =:estado");
        query.setParameter("estado", EstadoEnum.ACTIVO.getEstado());
        
		return query.getResultList();
	}

}
