package com.animalgym.springcloud.msvc.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-planes")
public interface PlanClienteRest {

    @DeleteMapping("/eliminar-plan-usuario/{id}")
    void eliminarPlanUsuarioPorId(@PathVariable Long id);
}
