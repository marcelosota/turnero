package ec.gob.dinardap.turno.dao.ejb;

import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.dao.ejb.GenericDaoEjb;
import ec.gob.dinardap.turno.dao.TurnoDao;
import ec.gob.dinardap.turno.modelo.Turno;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

@Stateless(name = "TurnoDao")
public class TurnoDaoEjb extends GenericDaoEjb<Turno, Integer> implements TurnoDao {

    public TurnoDaoEjb() {
        super(Turno.class);
    }

    @Override
    public Integer getTurnosDisponibles(Integer ventanillas, Date dia, String hora) {
        Integer turnosDisponibles = ventanillas;
        Query query = em.createQuery("SELECT t FROM Turno t WHERE t.dia=:dia AND t.hora=:hora AND t.estado=1");
        query.setParameter("dia", dia);
        query.setParameter("hora", hora);
        List<Turno> turnoList = new ArrayList<Turno>();
        turnoList = query.getResultList();
        if (!turnoList.isEmpty()) {
            turnosDisponibles = turnosDisponibles - turnoList.size();
        }
        return turnosDisponibles;
    }

    @Override
    public Boolean validacionDiariaPersona(Date dia, String cedula) {
        Query query = em.createQuery("SELECT t FROM Turno t WHERE t.cedula=:cedula AND t.dia=:dia");
        query.setParameter("dia", dia);
        query.setParameter("cedula", cedula);
        List<Turno> turnoList = new ArrayList<Turno>();
        turnoList = query.getResultList();
        return turnoList.isEmpty() ? Boolean.TRUE : Boolean.FALSE;
    }

}
