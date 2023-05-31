package com.example.lab7_gtics.controller;



import com.example.lab7_gtics.entity.Pagos;

import com.example.lab7_gtics.repository.PagosRepository;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@CrossOrigin
public class PagosController {

    private final PagosRepository pagosRepository;

    public PagosController(PagosRepository pagosRepository) {
        this.pagosRepository = pagosRepository;
    }


    @GetMapping("/pagos/listarPagos")
    public List<Pagos> listarPagos(){
        return pagosRepository.findAll();
    }


    @PostMapping("/pagos/registrarPago")
    public ResponseEntity<HashMap<String, Object>> guardarPago(
            @RequestBody Pagos pagos) {
        System.out.println(pagos);
        HashMap<String, Object> responseJson = new HashMap<>();
        pagosRepository.save(pagos);
        responseJson.put("id",pagos.getId());
        responseJson.put("estado","registrado");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,String>> gestionException(HttpServletRequest request){
        HashMap<String,String> responseMap = new HashMap<>();
        if(request.getMethod().equals("POST")){
            responseMap.put("estado","error");
            responseMap.put("msg","Debe enviar un pago");
        }
        return ResponseEntity.badRequest().body(responseMap);
    }

}
