package com.example.repository;

import com.example.dto.EmailHistoryFilterDTO;
import com.example.dto.FilterResultDTO;
import com.example.entity.EmailHistoryEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class CustomEmailHistoryRepository {
    @Autowired
    private EntityManager entityManager;


    public FilterResultDTO<EmailHistoryEntity> filter(EmailHistoryFilterDTO filterDTO, Integer page, Integer size) {
        StringBuilder selectQueryBuilder = new StringBuilder("select c from EmailHistoryEntity as c where 1=1 ");
        StringBuilder countQueryBuilder = new StringBuilder("select count(s) from EmailHistoryEntity as s where 1=1");
        StringBuilder whereQuery = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if(filterDTO.getId()!=null){
            selectQueryBuilder.append(" and c.id =:id ");
            params.put("id",filterDTO.getId());
        }
        if(filterDTO.getToEmail()!=null){
            selectQueryBuilder.append(" and lower(c.to_email) =:to_email ");
            params.put("to_email",filterDTO.getToEmail().toLowerCase());
        }
        if(filterDTO.getCreatedDateFrom()!=null){
            selectQueryBuilder.append(" and c.createdDate>:createdDateFrom ");
            params.put("createdDateFrom",filterDTO.getCreatedDateFrom());
        }
        if(filterDTO.getCreatedDateTo()!=null){
            selectQueryBuilder.append(" and c.createdDate>:createdDateTo ");
            params.put("createdDateTo",filterDTO.getCreatedDateTo());
        }
        whereQuery.append(" and true=true");

        Query selectQuery = entityManager.createQuery(selectQueryBuilder.append(whereQuery).toString());
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);

        Query countQuery = entityManager.createQuery(countQueryBuilder.append(whereQuery).toString());

        //params
        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }

        List<EmailHistoryEntity> entityList = selectQuery.getResultList();
        Long totalCount = (Long) countQuery.getSingleResult();

        return new FilterResultDTO<>(entityList, totalCount);
    }

}
