package com.animalgym.springcloud.msvc.usuarios.repositories;

import com.animalgym.springcloud.msvc.usuarios.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
}
