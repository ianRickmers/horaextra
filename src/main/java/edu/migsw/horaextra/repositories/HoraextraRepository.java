package edu.migsw.horaextra.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.migsw.horaextra.entities.HoraextraEntity;

@Repository
public interface HoraextraRepository extends JpaRepository<HoraextraEntity, Long> {

    @Query("SELECT m from HoraextraEntity m WHERE m.rut = :rut")
    ArrayList<HoraextraEntity> findByRut(@Param("rut") String rut);

    @Query("SELECT m from HoraextraEntity m WHERE m.id = :id")
    Optional<HoraextraEntity> findById(@Param("id") Long id);
}