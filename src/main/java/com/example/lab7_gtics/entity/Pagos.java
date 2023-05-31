package com.example.lab7_gtics.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Getter
@Setter

public class Pagos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "monto")
    private double monto;

    @Column(name = "tipo_pago")
    private String tipo_pago;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "usuarios_id", nullable = false)
    private Usuario usuarios_id;

    @ManyToOne
    @JoinColumn(name = "creditos_id", nullable = false)
    private Creditos creditos_id;
}
