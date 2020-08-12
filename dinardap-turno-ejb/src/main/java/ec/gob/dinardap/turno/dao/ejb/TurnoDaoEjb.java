package ec.gob.dinardap.turno.dao.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import ec.gob.dinardap.persistence.dao.ejb.GenericDaoEjb;
import ec.gob.dinardap.turno.constante.EstadoTurnoEnum;
import ec.gob.dinardap.turno.constante.TipoRestriccionEnum;
import ec.gob.dinardap.turno.dao.TurnoDao;
import ec.gob.dinardap.turno.dto.AgendadaAtendidasDto;
import ec.gob.dinardap.turno.modelo.Turno;
import java.util.Calendar;

@Stateless(name = "TurnoDao")
public class TurnoDaoEjb extends GenericDaoEjb<Turno, Integer> implements TurnoDao {

    public TurnoDaoEjb() {
        super(Turno.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer getTurnosDisponibles(Integer ventanillas, Date dia, String hora, Integer registroMercantilId) {
        Integer turnosDisponibles = ventanillas;
        Query query = em.createQuery("SELECT t FROM Turno t WHERE t.registroMercantil.registroMercantilId =: registroMercantilId AND t.dia=:dia AND t.hora=:hora AND t.estado <> 3");
        query.setParameter("dia", dia);
        query.setParameter("hora", hora);
        query.setParameter("registroMercantilId", registroMercantilId);
        List<Turno> turnoList = new ArrayList<Turno>();
        turnoList = query.getResultList();
        if (!turnoList.isEmpty()) {
            turnosDisponibles = turnosDisponibles - turnoList.size();
        }
        return turnosDisponibles;
    }

//    @Override
//    public Boolean validacionDiariaPersona(Turno turno) {
//        Query query = em.createQuery("SELECT t FROM Turno t WHERE t.cedula=:cedula AND t.dia=:dia AND t.registroMercantil.registroMercantilId=:registroMercantil");
//        query.setParameter("dia", turno.getDia());
//        query.setParameter("cedula", turno.getCedula());
//        query.setParameter("registroMercantil", turno.getRegistroMercantil().getRegistroMercantilId());
//        List<Turno> turnoList = new ArrayList<Turno>();
//        turnoList = query.getResultList();
//        return turnoList.isEmpty() ? Boolean.TRUE : Boolean.FALSE;
//    }
    @SuppressWarnings("unchecked")
    @Override
    public List<Turno> getTurnos(Turno turno) {
        Query query = em.createQuery("SELECT t FROM Turno t WHERE t.cedula=:cedula AND t.dia=:dia AND t.registroMercantil.registroMercantilId=:registroMercantil AND t.estado=1");
        query.setParameter("dia", turno.getDia());
        query.setParameter("cedula", turno.getCedula());
        query.setParameter("registroMercantil", turno.getRegistroMercantil().getRegistroMercantilId());
        List<Turno> turnoList = new ArrayList<Turno>();
        turnoList = query.getResultList();
        return turnoList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Turno> getTurnos(Integer registroMercantilId, Date dia, String hora) {
        Query query = em.createQuery("SELECT t FROM Turno t WHERE "
                + "t.registroMercantil.registroMercantilId =:registroMercantilId "
                + "AND t.dia=:dia "
                + "AND t.hora=:hora "
                + "AND t.estado<>3");
        query.setParameter("registroMercantilId", registroMercantilId);
        query.setParameter("dia", dia);
        query.setParameter("hora", hora);
        List<Turno> turnoList = new ArrayList<Turno>();
        turnoList = query.getResultList();
        System.out.println("Turnos: " + turnoList.size());
        return turnoList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AgendadaAtendidasDto> reporteAgendamiento(Integer registroMercantilId, String fecha, Short estadoAgendado, Short estadoAtendido) {
        StringBuilder sql = new StringBuilder(
                " select registro_mercantil_id, hora, sum(estado1) estado1, sum(estado2) estado2, sum(estado1 + estado2) estado3");
        sql.append(" from ( ");
        sql.append(" select count(turno1.estado) estado1, 0 estado2, turno1.registro_mercantil_id, turno1.hora ");
        sql.append(" from ec_dinardap_turno.turno as turno1 ");
        sql.append(" where turno1.estado = ").append(estadoAgendado);
        sql.append(" and turno1.registro_mercantil_id = ").append(registroMercantilId);
        sql.append(" and turno1.dia= '").append(fecha).append("'");
        sql.append(" group by turno1.registro_mercantil_id, turno1.estado, turno1.hora ");
        sql.append(" union all ");
        sql.append(" select 0 estado1, count(turno2.estado) estado2, turno2.registro_mercantil_id, hora ");
        sql.append(" from ec_dinardap_turno.turno as turno2");
        sql.append(" where turno2.estado = ").append(estadoAtendido);
        sql.append(" and turno2.registro_mercantil_id= ").append(registroMercantilId);
        sql.append(" and turno2.dia = '").append(fecha).append("'");
        sql.append(" group by turno2.registro_mercantil_id, turno2.estado, turno2.hora ) a ");
        sql.append(" group by hora, registro_mercantil_id ");
        sql.append(" order by hora, registro_mercantil_id ");
        Query query = em.createNativeQuery(sql.toString());
        List<Object[]> lista = query.getResultList();
        //FechaHoraSistema fechaHora = new FechaHoraSistema();
        List<AgendadaAtendidasDto> listaAgendamiento = new ArrayList<AgendadaAtendidasDto>();
        if (lista != null && lista.size() > 0) {
            for (Object[] fila : lista) {
                AgendadaAtendidasDto item = new AgendadaAtendidasDto();
                if (fila[0] != null) {
                    item.setRegistroMercantilId(Integer.parseInt(fila[0].toString()));
                } else {
                    item.setRegistroMercantilId(null);
                }
                if (fila[1] != null) {
                    item.setHora(fila[1].toString());
                } else {
                    item.setHora(null);
                }
                if (fila[2] != null) {
                    item.setPendiente(fila[2].toString());
                } else {
                    item.setPendiente(null);
                }
                if (fila[3] != null) {
                    item.setAtendido(fila[3].toString());
                } else {
                    item.setAtendido(null);
                }
                if (fila[4] != null) {
                    item.setAgendada(fila[4].toString());
                } else {
                    item.setAgendada(null);
                }

                listaAgendamiento.add(item);
            }
        }
        return listaAgendamiento;

    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer getContador(Date fechaTurno,
            Integer registroMercantilId,
            String valor,
            Integer parametro,
            Integer cuota) {
        String queryStr = "SELECT t FROM Turno t "
                + "WHERE t.registroMercantil.registroMercantilId =:registroMercantilId "
                + "AND t.dia BETWEEN :fechaInicial AND :fechaFinal "
                + "AND t.estado <>:estado ";
        switch (parametro) {
            case 1:
                queryStr += "AND t.cedula=:valor";
                break;
            case 2:
                queryStr += "AND t.correoElectronico=:valor";
                break;
            case 3:
                queryStr += "AND t.celular=:valor";
                break;
        }
        Query query = em.createQuery(queryStr);
        query.setParameter("registroMercantilId", registroMercantilId);
        query.setParameter("fechaInicial", addDays(fechaTurno, -cuota));
        query.setParameter("fechaFinal", addDays(fechaTurno, cuota));
        query.setParameter("estado", EstadoTurnoEnum.CANCELADO.getEstado());
        query.setParameter("valor", valor);

        List<Turno> turnoList = new ArrayList<Turno>();
        turnoList = query.getResultList();
        return turnoList.size();
    }

    private Date addDays(Date initialDate, Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(initialDate);
        calendar.add(calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

}
