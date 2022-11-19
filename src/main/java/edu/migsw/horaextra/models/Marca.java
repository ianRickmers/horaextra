package edu.migsw.horaextra.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Marca {
    private Long id;

    private String fecha;
    private String hora;
    private String minuto;
    private String rut;
}
