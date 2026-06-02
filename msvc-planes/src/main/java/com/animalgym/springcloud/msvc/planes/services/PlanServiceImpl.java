package com.animalgym.springcloud.msvc.planes.services;

import com.animalgym.springcloud.msvc.planes.clients.UsuarioClientRest;
import com.animalgym.springcloud.msvc.planes.models.Usuario;
import com.animalgym.springcloud.msvc.planes.models.entity.Plan;
import com.animalgym.springcloud.msvc.planes.models.entity.PlanUsuario;
import com.animalgym.springcloud.msvc.planes.repositories.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanRepository repository;

    @Autowired
    private UsuarioClientRest client;

    @Override
    @Transactional(readOnly = true)
    public List<Plan> listar() {
        return (List<Plan>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Plan> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Plan guardar(Plan plan) {
        return repository.save(plan);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Plan> porIdConUsuarios(Long id) {
        Optional<Plan> p = repository.findById(id);
        if(p.isPresent()) {
            Plan plan = p.get();
            if(!plan.getPlanUsuarios().isEmpty()){
                List<Long> ids = plan.getPlanUsuarios().stream().map(pu -> pu.getUsuarioId())
                        .collect(Collectors.toList());

                List<Usuario> usuarios = client.obtenerUsuariosPorPlan(ids);
                plan.setUsuarios(usuarios);
            }
            return Optional.of(plan);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void eliminarPlanUsuarioPorId(Long id) {
        repository.eliminarPlanUsuario(id);
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long planId) {

        Optional<Plan> p = repository.findById(planId);
        if(p.isPresent()) {
            Usuario usuarioMsvc = client.detalle(usuario.getId());

            Plan plan = p.get();
            PlanUsuario planUsuario = new PlanUsuario();
            planUsuario.setUsuarioId(usuarioMsvc.getId());

            plan.addPlanUsuario(planUsuario);
            repository.save(plan);
            return Optional.of(usuarioMsvc);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long planId) {

        Optional<Plan> p = repository.findById(planId);
        if(p.isPresent()) {
            Usuario usuarioNuevoMsvc = client.crear(usuario);

            Plan plan = p.get();
            PlanUsuario planUsuario = new PlanUsuario();
            planUsuario.setUsuarioId(usuarioNuevoMsvc.getId());

            plan.addPlanUsuario(planUsuario);
            repository.save(plan);
            return Optional.of(usuarioNuevoMsvc);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long planId) {

        Optional<Plan> p = repository.findById(planId);
        if(p.isPresent()) {
            Usuario usuarioMsvc = client.detalle(usuario.getId());

            Plan plan = p.get();
            PlanUsuario planUsuario = new PlanUsuario();
            planUsuario.setUsuarioId(usuarioMsvc.getId());

            plan.removePlanUsuario(planUsuario);
            repository.save(plan);
            return Optional.of(usuarioMsvc);
        }

        return Optional.empty();
    }
}
