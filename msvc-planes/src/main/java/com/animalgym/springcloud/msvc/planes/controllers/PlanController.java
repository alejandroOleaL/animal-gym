package com.animalgym.springcloud.msvc.planes.controllers;

import com.animalgym.springcloud.msvc.planes.models.Usuario;
import com.animalgym.springcloud.msvc.planes.models.entity.Plan;
import com.animalgym.springcloud.msvc.planes.services.PlanService;
import feign.FeignException;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class PlanController {

    @Autowired
    private PlanService service;

    @GetMapping
    public ResponseEntity<List<Plan>> listar() {

        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Plan> o = service.porIdConUsuarios(id);
        if(o.isPresent()){
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> crear(@Valid @RequestBody Plan plan, BindingResult result) {
        if(result.hasErrors()) {
            return validar(result);
        }
        Plan planDb = service.guardar(plan);
        return ResponseEntity.status(HttpStatus.CREATED).body(planDb);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Plan plan, BindingResult result,
                                    @PathVariable Long id) {
        if(result.hasErrors()) {
            return validar(result);
        }
        Optional<Plan> o = service.porId(id);
        if(o.isPresent()){
            Plan planDb = service.guardar(plan);
            planDb.setNombre(plan.getNombre());

            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(planDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Plan> o = service.porId(id);
        if(o.isPresent()){
            service.eliminar(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/asignar-usuario/{planId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long planId){
        Optional<Usuario> o;
        try {
            o = service.asignarUsuario(usuario, planId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No existe el usuario por el " +
                            "id o error en la comunicacion: " + e.getMessage()));
        }

        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-usuario/{planId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long planId){
        Optional<Usuario> o;
        try {
            o = service.crearUsuario(usuario, planId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No se pudo crear el usuario " +
                            "o error en la comunicacion: " + e.getMessage()));
        }

        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-usuario/{planId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long planId){
        Optional<Usuario> o;
        try {
            o = service.eliminarUsuario(usuario, planId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No existe el usuario por el " +
                            "id o error en la comunicacion: " + e.getMessage()));
        }

        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-plan-usuario/{id}")
    public ResponseEntity<?> eliminarPlanUsuarioPorId(@PathVariable Long id) {
        service.eliminarPlanUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }

    private static @NonNull ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
