package Grupo4.Lab2.Services;

import Grupo4.Lab2.DTO.ClienteQueMasAGastadoDTO;
import Grupo4.Lab2.DTO.ResumenPedidosXClienteDTO;
import Grupo4.Lab2.Entities.ClienteEntity;
import Grupo4.Lab2.Repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepo;

    public ClienteEntity getById(long idCliente) {
        return clienteRepo.findById(idCliente);
    }

    public List<ClienteEntity> getAll() {
        return clienteRepo.findAll();
    }

    public void save(ClienteEntity cliente) {
        clienteRepo.save(cliente);
    }

    public void update(ClienteEntity cliente) {
        clienteRepo.update(cliente);
    }

    public void delete(long idCliente) {
        clienteRepo.deleteById(idCliente);
    }

    public ClienteQueMasAGastadoDTO getClienteQueMasAGastado(){
        return clienteRepo.getClienteQueMasAGastado();
    }
    //Consulta especial 6
    //Determinar la lista de clientes que se encuentren dentro a lo mas 5km de una empresa
    public List<ClienteEntity> getClientesNotWithin5KM(){
        return clienteRepo.findClientesNotWithin5KM();
    }

    public List<ResumenPedidosXClienteDTO> getResumenPedidosXCliente(){
        return clienteRepo.getResumenPedidosXCliente();
    }
}
