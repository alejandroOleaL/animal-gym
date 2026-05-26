package com.animalgym.springcloud.msvc.planes.services;

import com.animalgym.springcloud.msvc.planes.entity.Plan;
import com.animalgym.springcloud.msvc.planes.repositories.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanRepository repository;

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
}
