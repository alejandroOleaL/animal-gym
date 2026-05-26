package com.animalgym.springcloud.msvc.planes.controllers;

import com.animalgym.springcloud.msvc.planes.entity.Plan;
import com.animalgym.springcloud.msvc.planes.services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PlanController {

    @Autowired
    private PlanService service;

    @GetMapping
    public ResponseEntity<List<Plan>> listar() {

        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> porId(@PathVariable Long id) {
        Optional<Plan> o = service.porId(id);
        if(o.isPresent()){
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> crear(@RequestBody Plan plan) {
        Plan planDb = service.guardar(plan);
        return ResponseEntity.status(HttpStatus.CREATED).body(planDb);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Plan plan) {

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

}
