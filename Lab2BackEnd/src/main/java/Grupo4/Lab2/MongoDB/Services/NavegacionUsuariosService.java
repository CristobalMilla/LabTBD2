package Grupo4.Lab2.MongoDB.Services;

import Grupo4.Lab2.MongoDB.Entities.EventoNavegacion;
import Grupo4.Lab2.MongoDB.Entities.NavegacionUsuariosEntity;
import Grupo4.Lab2.MongoDB.Repositories.NavegacionUsuariosRepositoryImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NavegacionUsuariosService {

    @Autowired
    private NavegacionUsuariosRepositoryImpl navegacionUsuariosRepository;

    public List<NavegacionUsuariosEntity> obtenerTodasLasNavegaciones() {
        return navegacionUsuariosRepository.findAll();
    }

    public NavegacionUsuariosEntity obtenerPorId(ObjectId id) {
        return navegacionUsuariosRepository.findById(id);
    }

    public NavegacionUsuariosEntity guardar(NavegacionUsuariosEntity entidad) {
        return navegacionUsuariosRepository.save(entidad);
    }

    public NavegacionUsuariosEntity actualizar(NavegacionUsuariosEntity entidad) {
        return navegacionUsuariosRepository.update(entidad);
    }

    public void eliminar(NavegacionUsuariosEntity entidad) {
        navegacionUsuariosRepository.delete(entidad);
    }

    public void agregarEvento(String clienteId, EventoNavegacion evento) {
        // Buscar por cliente_id (suponiendo que solo hay uno por cliente)
        List<NavegacionUsuariosEntity> lista = navegacionUsuariosRepository.findAll().stream()
                .filter(n -> n.getCliente_id().equals(clienteId))
                .toList();

        NavegacionUsuariosEntity nav;
        if (lista.isEmpty()) {
            nav = new NavegacionUsuariosEntity();
            nav.setCliente_id(clienteId);
            nav.setEventos(new ArrayList<>(List.of(evento)));
            navegacionUsuariosRepository.save(nav);
        } else {
            nav = lista.get(0);
            nav.getEventos().add(evento);
            navegacionUsuariosRepository.update(nav);
        }
    }
}
