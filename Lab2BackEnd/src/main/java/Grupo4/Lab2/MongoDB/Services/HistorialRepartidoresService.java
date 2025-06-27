package Grupo4.Lab2.MongoDB.Services;

import Grupo4.Lab2.MongoDB.Entities.HistorialRepartidores;
import Grupo4.Lab2.MongoDB.Repositories.HistorialRepartidoresRepository;
import org.bson.types.ObjectId;
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

    public HistorialRepartidores getHistorialById(String historial_repartidor_id) {
        try {
            ObjectId id = new ObjectId(historial_repartidor_id);
            return historialRepartidoresRepo.findById(id);
        }
        catch (Exception e) {
            System.out.println("Id ingresado invalido");
            return null;
        }
    }

    public HistorialRepartidores getHistorialByRepartidorId(long repartidor_id) {
        return historialRepartidoresRepo.findByRepartidorId(repartidor_id);
    }

    public HistorialRepartidores createHistorial(HistorialRepartidores historial) {
        return historialRepartidoresRepo.save(historial);
    }

    public HistorialRepartidores updateHistorial(String historial_repartidor_id, HistorialRepartidores historial) {
        try {
            ObjectId id = new ObjectId(historial_repartidor_id);
            HistorialRepartidores existingHistorial = historialRepartidoresRepo.findById(id);
            if (existingHistorial == null) {
                System.out.println("Historial con el id " + historial_repartidor_id + " no se encontro para actualizarlo.");
                return null;
            }
            historial.setHistorial_repartidor_id(id);
            return historialRepartidoresRepo.update(id, historial);
        }
        catch (Exception e) {
            System.out.println("Id ingresado invalido");
            return null;
        }
    }

    public void deleteHistorial(String historial_repartidor_id) {
        try {
            ObjectId id = new ObjectId(historial_repartidor_id);
            historialRepartidoresRepo.delete(id);
        }
        catch (Exception e) {
            System.out.println("Id ingresado invalido");
        }
    }

    public long countHistoriales() {
        return historialRepartidoresRepo.count();
    }
}
