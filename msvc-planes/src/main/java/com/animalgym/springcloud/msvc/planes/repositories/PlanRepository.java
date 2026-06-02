package com.animalgym.springcloud.msvc.planes.repositories;

import com.animalgym.springcloud.msvc.planes.models.entity.Plan;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PlanRepository extends CrudRepository<Plan, Long> {

    @Modifying
    @Query("delete from PlanUsuario pu where pu.usuarioId=?1")
    void eliminarPlanUsuario(Long id);

}
