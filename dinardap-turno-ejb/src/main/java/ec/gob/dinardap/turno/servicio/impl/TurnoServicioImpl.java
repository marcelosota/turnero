package ec.gob.dinardap.turno.servicio.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.constante.CriteriaTypeEnum;
import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.persistence.servicio.impl.GenericServiceImpl;
import ec.gob.dinardap.persistence.util.Criteria;
import ec.gob.dinardap.turno.dao.TurnoDao;
import ec.gob.dinardap.turno.modelo.Turno;
import ec.gob.dinardap.turno.servicio.TurnoServicio;

@Stateless(name = "TurnoServicio")
public class TurnoServicioImpl extends GenericServiceImpl<Turno, Integer> implements TurnoServicio {

	@EJB
	private TurnoDao turnoDao;

	@Override
	public GenericDao<Turno, Integer> getDao() {
		return turnoDao;
	}

	@Override
	public Boolean actualizarAtendido(Turno turno) {
		try {
			update(turno);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Turno buscarTurno(String validador) {
		try {
			String[] criteriasPropiedad = { "validador" };
			CriteriaTypeEnum[] citeriaOperador = { CriteriaTypeEnum.STRING_EQUALS };
			Object[] criteriaValores = { validador };

			Criteria criteria = new Criteria(criteriasPropiedad, citeriaOperador, criteriaValores);

			List<Turno> lista = findByCriterias(criteria);
			Turno turno = new Turno();
			if (lista.isEmpty()) {				
				return null;
			} else {				
				turno = lista.get(0);				
				return turno;
			}
		} catch (Exception e) {

			System.out.println(e.toString());
			return null;
		}

	}

}
