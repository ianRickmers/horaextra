package edu.migsw.horaextra.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado{

    private String rut;
    private String apellidos;
    private String nombres;
    private String fechaNacimiento;
    private String categoria;
    private String fechaIngreso;
}