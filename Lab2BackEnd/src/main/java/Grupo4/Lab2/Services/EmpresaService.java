package Grupo4.Lab2.Services;

import Grupo4.Lab2.DTO.CoordenadaDTO;
import Grupo4.Lab2.Entities.EmpresaEntity;
import Grupo4.Lab2.Repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;
    public EmpresaEntity findById(long id) {
        return empresaRepository.findById(id);
    }
    public List<EmpresaEntity> findAll() {
        return empresaRepository.findAll();
    }

    public void save(EmpresaEntity empresa) {
        empresaRepository.save(empresa);
    }
    public void update(EmpresaEntity empresa) {
        empresaRepository.update(empresa);
    }
    public void deleteById(long id) {
        empresaRepository.deleteById(id);
    }

    public List<CoordenadaDTO> getEntregasCercanas(Long id){
        return empresaRepository.findNearby(id).stream().map(punto -> new CoordenadaDTO(punto.getX(), punto.getY())).toList();}

    // 4.
    public CoordenadaDTO getEntregaMasLejana(long empresaId) {
        return empresaRepository.findFurthest(empresaId);
    }
}
