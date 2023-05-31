package com.example.lab7_gtics.repository;

import com.example.lab7_gtics.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
