package com.banana.persistence.school;


import com.banana.models.School;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Getter
@Setter
@Repository
public class SchoolsRepositoryJPA implements SchoolsRepositoryInf {
    @PersistenceContext(unitName = "school-h2")
    EntityManager em;

    @Override
    @Transactional("transactionManagerH2")
    public School add(School escuela) {
        em.persist(escuela);
        return escuela;
    }

    @Override
    @Transactional("transactionManagerH2")
    public School update(School escuela) {
        School psch = em.find(School.class, escuela.getId());

        psch.setName(escuela.getName());
        psch.setEstudiantes(escuela.getEstudiantes());
        return psch;
    }

    @Override
    public School getById(Long id) {
        return em.find(School.class, id);
    }

    @Override
    public List<School> getAll() throws RuntimeException {
        return em.createQuery("SELECT s FROM School s", School.class).getResultList();
    }
}
