package com.example.lab7_gtics.controller;

import com.example.lab7_gtics.entity.Usuario;
import com.example.lab7_gtics.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @GetMapping("/users/listar")
    public List<Usuario> listarUsuarios(){

        return usuarioRepository.findAll();
    }


    @PostMapping("/users/crear")
    public ResponseEntity<HashMap<String, Object>> guardarUsuario(
            @RequestBody Usuario usuario,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseJson = new HashMap<>();

        usuarioRepository.save(usuario);
        /*if(fetchId){
            responseJson.put("id",usuario.getId());
        }*/
        responseJson.put("id creado",usuario.getId());
        responseJson.put("estado","creado");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,String>> gestionException(HttpServletRequest request){
        HashMap<String,String> responseMap = new HashMap<>();
        if(request.getMethod().equals("POST")){
            responseMap.put("estado","error");
            responseMap.put("msg","Debe enviar un usuario");
        }
        return ResponseEntity.badRequest().body(responseMap);
    }


}
