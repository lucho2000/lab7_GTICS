package com.example.lab7_gtics.controller;

import com.example.lab7_gtics.entity.Usuario;
import com.example.lab7_gtics.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "/users")
@CrossOrigin
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @GetMapping("/listar")
    public List<Usuario> listarUsuarios(){

        return usuarioRepository.findAll();
    }

}
