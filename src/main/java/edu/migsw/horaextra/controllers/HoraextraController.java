package edu.migsw.horaextra.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import edu.migsw.horaextra.entities.HoraextraEntity;
import edu.migsw.horaextra.services.HoraextraService;

import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/horaextra")
public class HoraextraController {
    
    @Autowired
    HoraextraService HoraextraService;

    @GetMapping
    public ResponseEntity<List<HoraextraEntity>> getAll(){
        List<HoraextraEntity> Horaextras = HoraextraService.getAll();
        if(Horaextras.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(Horaextras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HoraextraEntity> getHoraextraById(@PathVariable("id") Long id){
        HoraextraEntity Horaextra = HoraextraService.getHoraextraById(id).get();
        if(Horaextra == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Horaextra);
    }

    @PostMapping()
    public ResponseEntity<HoraextraEntity> save(@RequestBody HoraextraEntity Horaextra){
        HoraextraEntity HoraextraGuardada = HoraextraService.save(Horaextra);
        return ResponseEntity.ok(HoraextraGuardada);
    }

    @GetMapping("/byrut/{rut}")
    public ResponseEntity<List<HoraextraEntity>> getByRut(@PathVariable("rut") String rut){
        List<HoraextraEntity> Horaextras = HoraextraService.getByRut(rut);
        if(Horaextras.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(Horaextras);
    }

    @GetMapping("/calcular")
    public ResponseEntity<String> calcular(){
        String msg=HoraextraService.calcularHorasExtra();
        return ResponseEntity.ok(msg);
    }

    @PutMapping("/update/{rut}")
    public ResponseEntity<String> update(@PathVariable("rut") String rut){
        HoraextraService.autorizarHorasExtra(rut);
        return ResponseEntity.ok("ok");
    }
}
