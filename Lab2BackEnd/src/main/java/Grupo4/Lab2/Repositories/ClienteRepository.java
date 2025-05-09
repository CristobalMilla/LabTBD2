package Grupo4.Lab2.Repositories;

import Grupo4.Lab2.Entities.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class ClienteRepository {

    private final Sql2o sql2o;

    @Autowired
    public ClienteRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public ClienteEntity findById(long idCliente) {
        try (Connection conn = sql2o.open()) {
            ClienteEntity cliente;
            String query = "SELECT * FROM clientes WHERE cliente_id = :idCliente";
            cliente = conn.createQuery(query)
                    .addParameter("idCliente", idCliente)
                    .executeAndFetchFirst(ClienteEntity.class);
            return cliente;
        }
        catch (Exception e){
            return null;
        }
    }

    public List<ClienteEntity> findAll() {
        try (Connection conn = sql2o.open()) {
            List<ClienteEntity> clientes;
            String query = "SELECT * FROM clientes";
            clientes = conn.createQuery(query)
                    .executeAndFetch(ClienteEntity.class);
            return clientes;
        }
        catch (Exception e){
            return null;
        }
    }

    public void save(ClienteEntity cliente) {
        try (Connection conn = sql2o.open()) {
            String query = "INSERT INTO clientes (nombre, direccion, email, telefono) " +
                           "VALUES (:nombre, :direccion, :email, :telefono)";
            long cliente_id = conn.createQuery(query)
                    .addParameter("nombre", cliente.getNombre())
                    .addParameter("direccion", cliente.getDireccion())
                    .addParameter("email", cliente.getEmail())
                    .addParameter("telefono", cliente.getTelefono())
                    .executeUpdate()
                    .getKey(Long.class);
            cliente.setCliente_id(cliente_id);
        }
        catch (Exception e){
            System.out.println("Error al guardar el cliente");
        }
    }

    public void update(ClienteEntity cliente) {
        try (Connection conn = sql2o.open()) {
            String query = "UPDATE clientes SET nommbre = :nombre, direccion = :direccion, " +
                           "email = :email, telefono = :telefono WHERE cliente_id = :idCliente";
            conn.createQuery(query)
                    .addParameter("nombre", cliente.getNombre())
                    .addParameter("direccion", cliente.getDireccion())
                    .addParameter("email", cliente.getEmail())
                    .addParameter("telefono", cliente.getTelefono())
                    .addParameter("idCliente", cliente.getCliente_id())
                    .executeUpdate();
        }
        catch (Exception e){
            System.out.println("Error al actualizar el cliente");
        }
    }

    public void deleteById(long idCliente) {
        try (Connection conn = sql2o.open()) {
            String query = "DELETE FROM clientes WHERE cliente_id = :idCliente";
            conn.createQuery(query)
                    .addParameter("idCliente", idCliente)
                    .executeUpdate();
        }
    }
}
