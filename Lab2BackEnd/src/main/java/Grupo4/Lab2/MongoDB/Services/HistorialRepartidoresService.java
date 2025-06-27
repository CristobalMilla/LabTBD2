package Grupo4.Lab2.MongoDB.Services;

import Grupo4.Lab2.MongoDB.Entities.HistorialRepartidores;
import Grupo4.Lab2.MongoDB.Entities.OpinionesClientes;
import Grupo4.Lab2.MongoDB.Repositories.HistorialRepartidoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialRepartidoresService {
    private final HistorialRepartidoresRepository historialRepartidoresRepo;

    @Autowired
    public HistorialRepartidoresService(HistorialRepartidoresRepository HistorialRepartidoresRepo) {
        this.historialRepartidoresRepo = HistorialRepartidoresRepo;
    }

    public List<HistorialRepartidores> getAllHistoriales() {
        return historialRepartidoresRepo.findAll();
    }

    public HistorialRepartidores getHistorialById(long historial_repartidor_id) {
        return historialRepartidoresRepo.findById(historial_repartidor_id);
    }

    public HistorialRepartidores getHistorialByRepartidorId(long repartidor_id) {
        return historialRepartidoresRepo.findByRepartidorId(repartidor_id);
    }

    public HistorialRepartidores createHistorial(HistorialRepartidores historial) {
        return historialRepartidoresRepo.save(historial);
    }

    public HistorialRepartidores updateHistorial(long historial_repartidor_id, HistorialRepartidores historial) {
        HistorialRepartidores existingHistorial = historialRepartidoresRepo.findById(historial_repartidor_id);
        if (existingHistorial == null) {
            System.out.println("Historial con el id " + historial_repartidor_id + " no se encontro para actualizarlo.");
            return null;
        }
        historial.setHistorial_repartidor_id(historial_repartidor_id);
        return historialRepartidoresRepo.update(historial_repartidor_id, historial);
    }

    public void deleteHistorial(long historial_repartidor_id) {
        historialRepartidoresRepo.delete(historial_repartidor_id);
    }

    public long countHistoriales() {
        return historialRepartidoresRepo.count();
    }
}
