package edu.migsw.horaextra.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.migsw.horaextra.entities.HoraextraEntity;
import edu.migsw.horaextra.repositories.HoraextraRepository;

import edu.migsw.horaextra.models.Empleado;
import edu.migsw.horaextra.models.Marca;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class HoraextraService {

    @Autowired
    HoraextraRepository horaextrasRepository;

    @Autowired RestTemplate restTemplate;
    
    public void deleteAll() {
        horaextrasRepository.deleteAll();
    }
    public void update(HoraextraEntity horaextra) {
        horaextrasRepository.save(horaextra);
    }
    public List<HoraextraEntity> getAll() {
        return horaextrasRepository.findAll();
    }

    public List<HoraextraEntity> getByRut(String rut) {
        return horaextrasRepository.findByRut(rut);
    }

    public Optional<HoraextraEntity> getHoraextraById(Long id) {
        return horaextrasRepository.findById(id);
    }
    public HoraextraEntity save(HoraextraEntity horaextra) {
        HoraextraEntity horaextraNueva=horaextrasRepository.save(horaextra);
        return horaextraNueva;
    }
    Integer horaSalida=18;
    
    public HoraextraEntity cambiarHoras(HoraextraEntity horaExtra,Integer marcaHora, Integer marcaMinuto){
        marcaHora = marcaHora - horaSalida;
        horaExtra.setCantidadHoras(horaExtra.getCantidadHoras() + marcaHora);
        horaExtra.setCantidadMinutos(horaExtra.getCantidadMinutos() + marcaMinuto);
        if (horaExtra.getCantidadMinutos() >= 60) {
            horaExtra.setCantidadHoras(horaExtra.getCantidadHoras() + 1);
            horaExtra.setCantidadMinutos(horaExtra.getCantidadMinutos() - 60);
        }
        return horaExtra;
    }
    
    public HoraextraEntity cambiarHorasExtra(Integer marcaHora, Integer marcaMinuto, HoraextraEntity horaExtra){
        if(marcaHora.equals(horaSalida) && marcaMinuto>0){
            horaExtra=cambiarHoras(horaExtra,marcaHora,marcaMinuto);
            return horaExtra;
        }
        if(marcaHora>horaSalida){
            horaExtra=cambiarHoras(horaExtra,marcaHora,marcaMinuto);
            return horaExtra;
        }
        return horaExtra;
    }

    public String calcularHorasExtra(){
        horaextrasRepository.deleteAll();
        Empleado[] empleados=restTemplate.getForObject("http://sueldo-service/empleado", Empleado[].class);
        if(empleados==null){
            return "No hay empleados";
        }
        for(Empleado empleado:empleados){
            String rut=empleado.getRut();
            Marca[] marcasRut=restTemplate.getForObject("http://marca-service/marcas/byrut/"+rut, Marca[].class);
            if(marcasRut==null){
                return "No hay marcas";
            }
            int n = marcasRut.length;
            HoraextraEntity horaExtra = new HoraextraEntity(null,rut,0,0,0);
            for(int i=1;i<n;i+=2){
                int marcaHora=Integer.parseInt((marcasRut[i]).getHora());
                int marcaMinuto=Integer.parseInt((marcasRut[i]).getMinuto());
                horaExtra=cambiarHorasExtra(marcaHora, marcaMinuto,horaExtra);
                save(horaExtra);
                }
            }
        return("Horas extra calculadas");
    }

    //cambiar autorizacion
    public String autorizarHorasExtra(String rut){
        ArrayList<HoraextraEntity> horaExtra = horaextrasRepository.findByRut(rut);
        if(horaExtra!=null){
            for(HoraextraEntity hora:horaExtra){
                if(hora.getAutorizada()==0){
                    hora.setAutorizada(1);
                    horaextrasRepository.save(hora);
                }
                else{
                    hora.setAutorizada(0);
                    horaextrasRepository.save(hora);
                }
            }
            return("Horas extra autorizadas");
        }
        return("Horas extra no encontradas");
    }
}
