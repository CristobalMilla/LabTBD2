package Grupo4.Lab2.Repositories;

import Grupo4.Lab2.DTO.ClienteQueMasAGastadoDTO;
import Grupo4.Lab2.DTO.ResumenPedidosXClienteDTO;
import Grupo4.Lab2.Entities.ClienteEntity;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ClienteRepository {

    private final Sql2o sql2o;
    private final GeometryFactory geometryFactory;

    @Autowired
    public ClienteRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
        this.geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    }

    public ClienteEntity mapRowToClienteEntity(Map<String, Object> row) {
        if (row == null) {
            return null;
        }
        ClienteEntity client = new ClienteEntity();
        client.setCliente_id((Long) row.get("cliente_id"));
        client.setNombre((String) row.get("nombre"));
        client.setDireccion((String) row.get("direccion"));
        client.setEmail((String) row.get("email"));
        client.setTelefono((String) row.get("telefono"));
        String ubicacionWkt = (String) row.get("ubicacion_wkt");

        if (ubicacionWkt != null && !ubicacionWkt.isEmpty()) {
            try {
                WKTReader reader = new WKTReader(this.geometryFactory);
                client.setUbicacion((Point) reader.read(ubicacionWkt));
            } catch (ParseException e) {
                System.err.println("Error al parsear WKT de ubicación: " + e.getMessage());
                client.setUbicacion(null);
            }
        } else {
            client.setUbicacion(null);
        }
        return client;
    }

    public ClienteEntity findById(long idCliente) {
        try (Connection conn = sql2o.open()) {
            ClienteEntity cliente;
            String query = "SELECT cliente_id, nombre, direccion, email, telefono, ST_AsText(ubicacion) as ubicacion_wkt FROM clientes WHERE cliente_id = :idCliente";
            List<Map<String, Object>> rows = conn.createQuery(query)
                    .addParameter("idCliente", idCliente)
                    .executeAndFetchTable().asList();
            cliente = mapRowToClienteEntity(rows.get(0));
            return cliente;
        }
        catch (Exception e){
            return null;
        }
    }

    public List<ClienteEntity> findAll() {
        try (Connection conn = sql2o.open()) {
            List<ClienteEntity> clientes;
            String query = "SELECT cliente_id, nombre, direccion, email, telefono, ST_AsText(ubicacion) as ubicacion_wkt FROM clientes";
            List<Map<String, Object>> rows = conn.createQuery(query).executeAndFetchTable().asList();
            clientes = rows.stream()
                    .map(this::mapRowToClienteEntity)
                    .collect(Collectors.toList());
            return clientes;
        }
        catch (Exception e){
            return null;
        }
    }

    public void save(ClienteEntity cliente) {
        String ubicacionWkt = (cliente.getUbicacion() != null) ? cliente.getUbicacion().toText() : null;
        try (Connection conn = sql2o.open()) {
            String query = "INSERT INTO clientes (nombre, direccion, email, telefono, ubicacion) " +
                           "VALUES (:nombre, :direccion, :email, :telefono, ST_GeomFromText(:ubicacionWkt, 4326))";
            long cliente_id = conn.createQuery(query)
                    .addParameter("nombre", cliente.getNombre())
                    .addParameter("direccion", cliente.getDireccion())
                    .addParameter("email", cliente.getEmail())
                    .addParameter("telefono", cliente.getTelefono())
                    .addParameter("ubicacionWkt", ubicacionWkt)
                    .executeUpdate()
                    .getKey(Long.class);
            cliente.setCliente_id(cliente_id);
        }
        catch (Exception e){
            System.out.println("Error al guardar el cliente");
        }
    }

    public void update(ClienteEntity cliente) {
        String ubicacionWkt = (cliente.getUbicacion() != null) ? cliente.getUbicacion().toText() : null;
        try (Connection conn = sql2o.open()) {
            String query = "UPDATE clientes SET nommbre = :nombre, direccion = :direccion, " +
                           "email = :email, telefono = :telefono, ubicacion = ST_GeomFromText(:ubicacionWkt, 4326) WHERE cliente_id = :idCliente";
            conn.createQuery(query)
                    .addParameter("nombre", cliente.getNombre())
                    .addParameter("direccion", cliente.getDireccion())
                    .addParameter("email", cliente.getEmail())
                    .addParameter("telefono", cliente.getTelefono())
                    .addParameter("idCliente", cliente.getCliente_id())
                    .addParameter("ubicacionWkt", ubicacionWkt)
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

    // Query 1
    public ClienteQueMasAGastadoDTO getClienteQueMasAGastado(){
        try (Connection conn = sql2o.open()) {
            ClienteQueMasAGastadoDTO cliente;
            String query =
                    "SELECT sp.id_cliente, c.nombre AS nombre, sp.num_pedidos AS num_pedidos_pagados, sp.suma_pagos " +
                    "FROM clientes c " +
                    "INNER JOIN (SELECT p.cliente_id AS id_cliente, COUNT(*) AS num_pedidos, SUM(pa.monto) AS suma_pagos " +
                        "FROM pedidos p " +
                        "INNER JOIN pagos pa ON p.pedido_id = pa.pedido_id " +
                        "WHERE p.estado = 'entregado' " +
                        "GROUP BY p.cliente_id) AS sp " +
                    "ON c.cliente_id = sp.id_cliente " +
                    "WHERE sp.suma_pagos = (SELECT MAX(suma_pagos) AS max_pagos " +
                                            "FROM (SELECT p.cliente_id, SUM(pa.monto) AS suma_pagos " +
                                            "FROM pedidos p " +
                                            "INNER JOIN pagos pa ON p.pedido_id = pa.pedido_id " +
                                            "WHERE p.estado = 'entregado' " +
                                            "GROUP BY p.cliente_id))";
            cliente = conn.createQuery(query).executeAndFetchFirst(ClienteQueMasAGastadoDTO.class);
            return cliente;
        }
        catch (Exception e){
            return null;
        }
    }

    // View 13
    public List<ResumenPedidosXClienteDTO> getResumenPedidosXCliente() {
        try (Connection conn = sql2o.open()) {
            List<ResumenPedidosXClienteDTO> resumenes;
            String query = "SELECT * FROM resumen_pedidos_x_cliente";
            resumenes = conn.createQuery(query).executeAndFetch(ResumenPedidosXClienteDTO.class);
            return resumenes;
        }
        catch (Exception e){
            return null;
        }
    }
    //Consulta especial 6
    //Determinar la lista de clientes que se encuentren dentro a lo mas 5km de una empresa
    public List<ClienteEntity> findClientesNotWithin5KM(){
        String sql = "SELECT c.cliente_id as cliente_id, c.nombre as nombre, c.direccion as direccion, c.email as email, c.telefono as telefono, ST_AsText(c.ubicacion) as ubicacion_wkt " +
                "FROM cliente c WHERE NOT EXISTS" +
                "(SELECT 1 FROM empresa e WHERE ST_DWithin(c.ubicacion::geography, e.ubicacion::geography, 5000))";
        try (Connection con = sql2o.open()) {
            List<Map<String, Object>> results = con.createQuery(sql)
                    .executeAndFetchTable()
                    .asList();
            return results.stream().map(this::mapRowToClienteEntity).toList();
        }
    }
}
