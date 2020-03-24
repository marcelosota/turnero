package ec.gob.dinardap.turno.dao.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import ec.gob.dinardap.persistence.dao.ejb.GenericDaoEjb;
import ec.gob.dinardap.turno.dao.TurnoDao;
import ec.gob.dinardap.turno.dto.AgendadaAtendidasDto;
import ec.gob.dinardap.turno.modelo.Turno;

@Stateless(name = "TurnoDao")
public class TurnoDaoEjb extends GenericDaoEjb<Turno, Integer> implements TurnoDao {

    public TurnoDaoEjb() {
        super(Turno.class);
    }

    @SuppressWarnings("unchecked")
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
	public List<AgendadaAtendidasDto> reporteAgendamiento(Integer registroMercantilId, String fecha, Short estadoAgendado, Short estadoAtendido) {
		StringBuilder sql = new StringBuilder(
				"select t.registro_mercantil_id, t.hora, agendados, atendidos");
		sql.append(" from ec_dinardap_turno.turno t ");
		 sql.append("LEFT JOIN (select ta.registro_mercantil_id,count(ta.turno_id) as agendados from ec_dinardap_turno.turno ta ");
		sql.append(" where ta.estado=").append(estadoAgendado);
		sql.append(" group by ta.registro_mercantil_id) as turno1 on t.registro_mercantil_id=turno1.registro_mercantil_id "); 
		sql.append(" LEFT JOIN (SELECT tat.registro_mercantil_id,count(tat.turno_id) as atendidos  from ec_dinardap_turno.turno tat ");
		sql.append(" where tat.estado = ").append(estadoAtendido);
		sql.append(" group by tat.registro_mercantil_id) as turno2 on t.registro_mercantil_id=turno2.registro_mercantil_id "); 
		sql.append(" where t.registro_mercantil_id = ").append(registroMercantilId);
		sql.append(" and  dia = '").append(fecha).append("'");
		sql.append(" group by t.registro_mercantil_id, t.hora, agendados, atendidos "); 
		sql.append(" order by hora asc ");
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
					item.setAgendada(fila[2].toString());
				} else {
					item.setAgendada(null);
				}
				if (fila[3] != null) {
					item.setAtendido(fila[3].toString());
				} else {
					item.setAtendido(null);
				}				

				listaAgendamiento.add(item);
			}
		}
		return listaAgendamiento;

	}


}
