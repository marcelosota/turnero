package ec.gob.dinardap.turno.servicio.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.constante.CriteriaTypeEnum;
import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.persistence.servicio.impl.GenericServiceImpl;
import ec.gob.dinardap.persistence.util.Criteria;
import ec.gob.dinardap.turno.dao.TurnoDao;
import ec.gob.dinardap.turno.modelo.Turno;
import ec.gob.dinardap.turno.servicio.TurnoServicio;
import java.util.Date;
import java.util.List;

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
        String[] criteriasPropiedad = {"validador"};
        CriteriaTypeEnum[] citeriaOperador = {CriteriaTypeEnum.STRING_EQUALS};
        Object[] criteriaValores = {validador};

        Criteria criteria = new Criteria(criteriasPropiedad, citeriaOperador, criteriaValores);

        List<Turno> lista = findByCriterias(criteria);
        Turno turno = new Turno();
        if (lista != null && lista.size() > 0) {
            for (Turno item : lista) {
                turno = item;
            }
        }
        return turno;

    }

    @Override
    public Integer getTurnosDisponibles(Integer ventanillas, Date dia, String hora) {
        return turnoDao.getTurnosDisponibles(ventanillas, dia, hora);
    }

    @Override
    public Boolean validacionDiariaPersona(Turno turno) {
        return turnoDao.getTurnos(turno).isEmpty() ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Turno getTurno(Turno turno) {
        return turnoDao.getTurnos(turno).isEmpty() ? null : turnoDao.getTurnos(turno).get(0);
    }

}
