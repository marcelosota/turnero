package ec.gob.dinardap.turno.servicio.impl;

import ec.gob.dinardap.persistence.constante.CriteriaTypeEnum;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.persistence.servicio.impl.GenericServiceImpl;
import ec.gob.dinardap.persistence.util.Criteria;
import ec.gob.dinardap.turno.dao.TurnoDao;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;
import ec.gob.dinardap.turno.modelo.Turno;
import ec.gob.dinardap.turno.servicio.TurnoServicio;
import java.util.ArrayList;
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
