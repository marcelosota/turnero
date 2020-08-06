package ec.gob.dinardap.turno.servicio.impl;

import ec.gob.dinardap.persistence.constante.CriteriaTypeEnum;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.persistence.servicio.impl.GenericServiceImpl;
import ec.gob.dinardap.persistence.util.Criteria;
import ec.gob.dinardap.turno.dao.TipoRestriccionDao;
import ec.gob.dinardap.turno.modelo.TipoRestriccion;
import ec.gob.dinardap.turno.servicio.TipoRestriccionServicio;
import ec.gob.dinardap.util.constante.EstadoEnum;
import java.util.ArrayList;
import java.util.List;

@Stateless(name = "TipoRestriccionServicio")
public class TipoRestriccionServicioImpl extends GenericServiceImpl<TipoRestriccion, Integer> implements TipoRestriccionServicio {

    @EJB
    private TipoRestriccionDao tipoRestriccionDao;

    @Override
    public GenericDao<TipoRestriccion, Integer> getDao() {
        return tipoRestriccionDao;
    }

    @Override
    public List<TipoRestriccion> getTipoRestriccionList() {
        List<TipoRestriccion> tipoRestriccionList = new ArrayList<TipoRestriccion>();
        String[] criteriaNombres = {"estado"};
        CriteriaTypeEnum[] criteriaTipos = {CriteriaTypeEnum.SHORT_EQUALS};
        Object[] criteriaValores = {EstadoEnum.ACTIVO.getEstado()};
        String[] orderBy = {"tipoRestriccionId"};
        boolean[] asc = {true};
        Criteria criteria = new Criteria(criteriaNombres, criteriaTipos, criteriaValores, orderBy, asc);
        if (!findByCriterias(criteria).isEmpty()) {
            tipoRestriccionList = findByCriterias(criteria);
        }
        return tipoRestriccionList;
    }

//    @Override
//    public void crearTurno(Turno turno) {
//        try {
//            turno.setFechaGeneracion(new Date());
//            update(turno);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public Boolean actualizarAtendido(Turno turno) {
//        try {
//            turno.setFechaModificacion(new Date());
//            update(turno);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public Turno buscarTurno(String validador) {
//        try {
//            String[] criteriasPropiedad = {"validador"};
//            CriteriaTypeEnum[] citeriaOperador = {CriteriaTypeEnum.STRING_EQUALS};
//            Object[] criteriaValores = {validador};
//
//            Criteria criteria = new Criteria(criteriasPropiedad, citeriaOperador, criteriaValores);
//
//            List<Turno> lista = findByCriterias(criteria);
//            Turno turno = new Turno();
//            if (lista.isEmpty()) {
//                return null;
//            } else {
//                for (Turno item : lista) {
//                    turno = item;
//                }
//                return turno;
//            }
//
//        } catch (Exception e) {
//            return null;
//        }
//
//    }
//    
//    @Override
//    public Turno buscarTurno(String validador, Integer registroMercantilId) {
//        try {
//            String[] criteriasPropiedad = {"validador", "registroMercantil.registroMercantilId"};
//            CriteriaTypeEnum[] citeriaOperador = {CriteriaTypeEnum.STRING_EQUALS, CriteriaTypeEnum.INTEGER_EQUALS};
//            Object[] criteriaValores = {validador, registroMercantilId};
//
//            Criteria criteria = new Criteria(criteriasPropiedad, citeriaOperador, criteriaValores);
//
//            List<Turno> lista = findByCriterias(criteria);
//            Turno turno = new Turno();
//            if (lista.isEmpty()) {
//                return null;
//            } else {
//                for (Turno item : lista) {
//                    turno = item;
//                }
//                return turno;
//            }
//
//        } catch (Exception e) {
//            return null;
//        }
//
//    }
//
//    @Override
//    public Integer getTurnosDisponibles(Integer ventanillas, Date dia, String hora, Integer registroMercantilId) {
//        return turnoDao.getTurnosDisponibles(ventanillas, dia, hora, registroMercantilId);
//    }
//
//    @Override
//    public Boolean validacionDiariaPersona(Turno turno) {
//        return turnoDao.getTurnos(turno).isEmpty() ? Boolean.TRUE : Boolean.FALSE;
//    }
//
//    @Override
//    public Turno getTurno(Turno turno) {
//        return turnoDao.getTurnos(turno).isEmpty() ? null : turnoDao.getTurnos(turno).get(0);
//    }
//
//    @Override
//    public List<Turno> getTurnos(Integer registroMercantilId, Date dia, String hora) {
//        return turnoDao.getTurnos(registroMercantilId, dia, hora);
//    }
}
