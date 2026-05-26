package com.animalgym.springcloud.msvc.planes.repositories;

import com.animalgym.springcloud.msvc.planes.entity.Plan;
import org.springframework.data.repository.CrudRepository;

public interface PlanRepository extends CrudRepository<Plan, Long> {
}
