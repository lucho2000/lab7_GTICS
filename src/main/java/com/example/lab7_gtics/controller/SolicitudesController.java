package com.example.lab7_gtics.controller;


import com.example.lab7_gtics.entity.Solicitudes;
import com.example.lab7_gtics.repository.SolicitudesRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
public class SolicitudesController {

    private final SolicitudesRepository solicitudesRepository;

    public SolicitudesController(SolicitudesRepository solicitudesRepository) {
        this.solicitudesRepository = solicitudesRepository;
    }


    @PostMapping("/solicitudes/registro")
    public ResponseEntity<HashMap<String, Object>> guardarSolicitud(
            @RequestBody Solicitudes solicitudes) {

        HashMap<String, Object> responseJson = new HashMap<>();

        if ( solicitudes.getSolicitud_estado().equals("")) {
            solicitudes.setSolicitud_estado("pendiente");
            solicitudesRepository.save(solicitudes);
            responseJson.put("Monto solicitado",solicitudes.getSolicitud_monto());
            responseJson.put("id",solicitudes.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
        }else {
            responseJson.put("error","El estado se coloca de manera automatica");
            return ResponseEntity.badRequest().body(responseJson);
        }

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,String>> gestionException(HttpServletRequest request){
        HashMap<String,String> responseMap = new HashMap<>();
        if(request.getMethod().equals("POST") || request.getMethod().equals("PUT")){
            responseMap.put("estado","error");
            responseMap.put("msg","Debe enviar una solicitud");
        }
        return ResponseEntity.badRequest().body(responseMap);
    }

    @PutMapping(value = "/solicitudes/aprobarSolicitud")
    public ResponseEntity<HashMap<String, Object>> aprobarSolicitud(
                @RequestParam("idSolicitud") String idSolicitud) {

        HashMap<String, Object> responseMap = new HashMap<>();
        System.out.println(idSolicitud);
        try {
            int id = Integer.parseInt(idSolicitud);
            if (id > 0) {
                Optional<Solicitudes> opt = solicitudesRepository.findById(id);
                if (opt.isPresent()) {
                    Solicitudes solicitudFromDb = opt.get();
                    //validar campo por campo
                    if (solicitudFromDb.getSolicitud_estado().equals("pendiente")){
                        solicitudFromDb.setSolicitud_estado("aprobado");
                        solicitudesRepository.save(solicitudFromDb);
                        responseMap.put("estado", "aprobado");
                        responseMap.put("id solicitud", id);
                        return ResponseEntity.ok(responseMap);
                    } else {
                        responseMap.put("solicitud ya atendida", id);
                        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseMap);
                    }
                } else {
                    responseMap.put("msg", "La solicitud a aprobar no existe");
                }
            } else {
                responseMap.put("msg", "Debe enviar un ID");
            }

        } catch (NumberFormatException e) {
            responseMap.put("msg","el ID debe ser un número entero positivo");
        }
        responseMap.put("result","failure");
        return ResponseEntity.badRequest().body(responseMap);
    }

    @PutMapping(value = "/solicitudes/denegarSolicitud")
    public ResponseEntity<HashMap<String, Object>> denegarSolicitud(
            @RequestParam("idSolicitud") String idSolicitud) {

        HashMap<String, Object> responseMap = new HashMap<>();
        System.out.println(idSolicitud);
        try {
            int id = Integer.parseInt(idSolicitud);
            if (id > 0) {
                Optional<Solicitudes> opt = solicitudesRepository.findById(id);
                if (opt.isPresent()) {
                    Solicitudes solicitudFromDb = opt.get();
                    //validar campo por campo
                    if (solicitudFromDb.getSolicitud_estado().equals("pendiente")){
                        solicitudFromDb.setSolicitud_estado("denegado");
                        solicitudesRepository.save(solicitudFromDb);
                        responseMap.put("estado", "denegado");
                        responseMap.put("id solicitud", id);
                        return ResponseEntity.ok(responseMap);
                    } else {
                        responseMap.put("solicitud ya atendida", id);
                        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseMap);
                    }
                } else {
                    responseMap.put("msg", "La solicitud a denegar no existe");
                }
            } else {
                responseMap.put("msg", "Debe enviar un ID");
            }

        } catch (NumberFormatException e) {
            responseMap.put("msg","el ID debe ser un número entero positivo");
        }
        responseMap.put("result","failure");
        return ResponseEntity.badRequest().body(responseMap);
    }

    @DeleteMapping(value = "/solicitudes/borrarSolicitud")
    public ResponseEntity<HashMap<String, Object>> borrarSolicitud(@RequestParam("idSolicitud") String idSolicitud) {

        HashMap<String, Object> responseMap = new HashMap<>();

        try {
            int id = Integer.parseInt(idSolicitud);
            if (solicitudesRepository.existsById(id)) {
                Optional<Solicitudes> optionalSolicitudes = solicitudesRepository.findById(id);
                Solicitudes solicitudFromDb = optionalSolicitudes.get();
                if (solicitudFromDb.getSolicitud_estado().equals("denegado")) {
                    solicitudesRepository.deleteById(id);
                    responseMap.put("estado", "borrado de solicitud exitoso");
                    responseMap.put("id solicitud borrada", id);
                    return ResponseEntity.ok(responseMap);
                } else {
                    responseMap.put("estado", "solicitud no denegada");
                    responseMap.put("id solicitud", id);
                    return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(responseMap);
                }

            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "no se encontró la solicitud con id: " + id);
                return ResponseEntity.badRequest().body(responseMap);
            }
        } catch (NumberFormatException ex) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "El ID debe ser un número");
            return ResponseEntity.badRequest().body(responseMap);
        }
    }







}
