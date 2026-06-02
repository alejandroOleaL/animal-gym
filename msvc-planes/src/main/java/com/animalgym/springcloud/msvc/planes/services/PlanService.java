package com.animalgym.springcloud.msvc.planes.services;

import com.animalgym.springcloud.msvc.planes.models.Usuario;
import com.animalgym.springcloud.msvc.planes.models.entity.Plan;

import java.util.List;
import java.util.Optional;

public interface PlanService {

    List<Plan> listar();
    Optional<Plan> porId(Long id);
    Plan guardar(Plan plan);
    void eliminar(Long id);
    Optional<Plan> porIdConUsuarios(Long id);

    void eliminarPlanUsuarioPorId(Long id);

    Optional<Usuario> asignarUsuario(Usuario usuario, Long planId);
    Optional<Usuario> crearUsuario(Usuario usuario, Long planId);
    Optional<Usuario> eliminarUsuario(Usuario usuario, Long planId);

}
