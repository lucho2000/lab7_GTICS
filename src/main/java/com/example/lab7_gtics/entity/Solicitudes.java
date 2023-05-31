package com.example.lab7_gtics.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "solicitudes")
@Getter
@Setter
public class Solicitudes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "solicitud_producto")
    private String solicitud_producto;

    @Column(name = "solicitud_monto")
    private double solicitud_monto;

    @Column(name = "solicitud_fecha")
    private Timestamp solicitud_fecha;

    @Column(name = "solicitud_estado")
    private String solicitud_estado;

    @ManyToOne
    @JoinColumn(name = "usuarios_id", nullable = false)
    private Usuario usuarios_id;

}
