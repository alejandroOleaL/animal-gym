package com.animalgym.springcloud.msvc.planes.services;

import com.animalgym.springcloud.msvc.planes.entity.Plan;

import java.util.List;
import java.util.Optional;

public interface PlanService {

    List<Plan> listar();
    Optional<Plan> porId(Long id);
    Plan guardar(Plan plan);
    void eliminar(Long id);
}
