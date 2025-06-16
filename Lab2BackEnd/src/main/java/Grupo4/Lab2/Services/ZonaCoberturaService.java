package Grupo4.Lab2.Services;

import Grupo4.Lab2.DTO.CoordenadaDTO;
import Grupo4.Lab2.DTO.PedidoYZonasQueCruzaDTO;
import Grupo4.Lab2.DTO.ZonaDTO;
import Grupo4.Lab2.DTO.ZonaYDensidadXkm2DTO;
import Grupo4.Lab2.Entities.ZonaCoberturaEntity;
import Grupo4.Lab2.Repositories.ZonaCoberturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ZonaCoberturaService {
    @Autowired
    private ZonaCoberturaRepository zonaRepository;

    public List<ZonaCoberturaEntity> getAll() {
        List<ZonaCoberturaEntity> zonas = zonaRepository.findAll();
        for (ZonaCoberturaEntity zona : zonas) {
            if (zona.getGeom() != null) {
                System.out.println("Zona ID: " + zona.getZona_id() + ", Nombre: " + zona.getNombre() + ", Geom WKT: " + zona.getGeom().toText());
            } else {
                System.out.println("Zona ID: " + zona.getZona_id() + ", Nombre: " + zona.getNombre() + ", Geom WKT: null");
            }
        }
        return zonas;
    }
    public ZonaCoberturaEntity getById(long id) {
        return zonaRepository.findById(id);
    }
    public void save(ZonaCoberturaEntity zona) {
        zonaRepository.save(zona);
    }
    public void update(ZonaCoberturaEntity zona) {
        zonaRepository.update(zona);
    }
    //Consulta especial 2
    //Determinar si un cliente se encuentra dentro de una zona de cobertura
    //Se devolvera la lista zonas de cobertura en las que el cliente se encuentra
    public List<ZonaCoberturaEntity> getZonasCoberturaByClienteId(long cliente_id){
        return zonaRepository.findZonasCoberturaByClienteId( cliente_id);
    }

    // 7.
    public ZonaDTO obtenerZonaPorCliente(Long clienteId) {
        return zonaRepository.obtenerZonaPorCliente(clienteId);
    }


    // Query 8
    // Detectar zonas con alta densidad de pedidos mediante agregación de puntos.
    public List<ZonaYDensidadXkm2DTO> getZonasConAltaDensidad(){
        return zonaRepository.getZonasConAltaDensidad();
    }

    public List<ZonaCoberturaEntity> getZonasCoberturaByPedido(PedidoYZonasQueCruzaDTO pedido){
        List<ZonaCoberturaEntity> zonas = new ArrayList<>();
        try{
            for(Long id : pedido.getIds_zonas()){
                zonas.add(getById(id));
            }
            return zonas;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
