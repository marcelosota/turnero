package ec.gob.dinardap.turno.servicio.impl;

import ec.gob.dinardap.persistence.constante.CriteriaTypeEnum;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.persistence.servicio.impl.GenericServiceImpl;
import ec.gob.dinardap.persistence.util.Criteria;
import ec.gob.dinardap.turno.dao.PlanificacionRegistroDao;
import ec.gob.dinardap.turno.modelo.PlanificacionRegistro;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;
import ec.gob.dinardap.turno.servicio.PlanificacionRegistroServicio;
import java.util.ArrayList;
import java.util.List;

@Stateless(name = "PlanificacionRegistroServicio")
public class PlanificacionRegistroServicioImpl extends GenericServiceImpl<PlanificacionRegistro, Integer>
        implements PlanificacionRegistroServicio {

    @EJB
    private PlanificacionRegistroDao planificacionServicioDao;

    @Override
    public GenericDao<PlanificacionRegistro, Integer> getDao() {
        return planificacionServicioDao;
    }

    @Override
    public PlanificacionRegistro getPlanificacionRegistro(Integer registroMercantilId) {
        PlanificacionRegistro planificacionRegistro = new PlanificacionRegistro();
        List<PlanificacionRegistro> planificacionRegistroList = new ArrayList<PlanificacionRegistro>();
        String[] criteriaNombres = {"registroMercantil.registroMercantilId"};
        CriteriaTypeEnum[] criteriaTipos = {CriteriaTypeEnum.INTEGER_EQUALS};
        Object[] criteriaValores = {registroMercantilId};
        String[] orderBy = {"planificacionId"};
        boolean[] asc = {true};
        Criteria criteria = new Criteria(criteriaNombres, criteriaTipos, criteriaValores, orderBy, asc);
        planificacionRegistroList = findByCriterias(criteria);
        if (planificacionRegistroList.size() != 0) {
            planificacionRegistro = planificacionRegistroList.get(0);
        }
        return planificacionRegistro;
    }
}
