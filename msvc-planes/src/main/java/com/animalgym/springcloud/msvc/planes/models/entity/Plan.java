package com.animalgym.springcloud.msvc.planes.models.entity;

import com.animalgym.springcloud.msvc.planes.models.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "planes")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "plan_id")
    private List<PlanUsuario> planUsuarios;

    @Transient
    private List<Usuario> usuarios;

    public Plan() {
        planUsuarios = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    public void addPlanUsuario(PlanUsuario planUsuario) {
        planUsuarios.add(planUsuario);
    }

    public void removePlanUsuario(PlanUsuario planUsuario) {
        planUsuarios.remove(planUsuario);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PlanUsuario> getPlanUsuarios() {
        return planUsuarios;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void setPlanUsuarios(List<PlanUsuario> planUsuarios) {
        this.planUsuarios = planUsuarios;
    }
}
