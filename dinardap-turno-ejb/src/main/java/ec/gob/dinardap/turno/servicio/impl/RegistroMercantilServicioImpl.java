package ec.gob.dinardap.turno.servicio.impl;

import ec.gob.dinardap.persistence.constante.CriteriaTypeEnum;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.dinardap.persistence.dao.GenericDao;
import ec.gob.dinardap.persistence.servicio.impl.GenericServiceImpl;
import ec.gob.dinardap.persistence.util.Criteria;
import ec.gob.dinardap.turno.dao.RegistroMercantilDao;
import ec.gob.dinardap.turno.modelo.RegistroMercantil;
import ec.gob.dinardap.turno.servicio.RegistroMercantilServicio;
import java.util.ArrayList;
import java.util.List;

@Stateless(name = "RegistroMercantilServicio")
public class RegistroMercantilServicioImpl extends GenericServiceImpl<RegistroMercantil, Integer>
        implements RegistroMercantilServicio {

    @EJB
    private RegistroMercantilDao registroMercantilDao;

    @Override
    public GenericDao<RegistroMercantil, Integer> getDao() {
        return registroMercantilDao;
    }

    @Override
    public List<RegistroMercantil> getRegistrosMercantiles() {
        List<RegistroMercantil> registroMercantilList = new ArrayList<RegistroMercantil>();
        String[] criteriaNombres = {"tipo"};
        CriteriaTypeEnum[] criteriaTipos = {CriteriaTypeEnum.INTEGER_EQUALS};
        Object[] criteriaValores = {1};
        String[] orderBy = {"registroMercantilId"};
        boolean[] asc = {true};
        Criteria criteria = new Criteria(criteriaNombres, criteriaTipos, criteriaValores, orderBy, asc);
        registroMercantilList = findByCriterias(criteria);
        return registroMercantilList;
    }

}
