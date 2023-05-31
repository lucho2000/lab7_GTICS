package com.example.lab7_gtics.controller;


import com.example.lab7_gtics.entity.Acciones;
import com.example.lab7_gtics.entity.Pagos;
import com.example.lab7_gtics.repository.AccionesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@CrossOrigin
public class AccionesController {

    private final AccionesRepository accionesRepository;

    public AccionesController(AccionesRepository accionesRepository) {
        this.accionesRepository = accionesRepository;
    }

    @PostMapping("/acciones/save")
    public ResponseEntity<HashMap<String, Object>> saveAcciones(
            @RequestBody Acciones acciones) {

        HashMap<String, Object> responseJson = new HashMap<>();
        accionesRepository.save(acciones);
        responseJson.put("idCreado",acciones.getId());
        responseJson.put("estado","accionRegistrada");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }

}
