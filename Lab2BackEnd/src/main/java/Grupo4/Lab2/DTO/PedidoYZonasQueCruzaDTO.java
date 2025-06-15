package Grupo4.Lab2.DTO;

import java.util.List;

public class PedidoYZonasQueCruzaDTO {

    private int pedido_id;
    private int cliente_id;
    private int empresa_id;
    private int repartidor_id;
    private int zonas_que_cruza;
    private List<Long> ids_zonas;

    public PedidoYZonasQueCruzaDTO(int pedido_id, int cliente_id, int repartidor_id, int empresa_id, int zonas_que_cruza, List<Long> ids_zonas) {
        this.pedido_id = pedido_id;
        this.cliente_id = cliente_id;
        this.repartidor_id = repartidor_id;
        this.empresa_id = empresa_id;
        this.zonas_que_cruza = zonas_que_cruza;
        this.ids_zonas = ids_zonas;
    }

    public PedidoYZonasQueCruzaDTO() {}

    public int getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(int pedido_id) {
        this.pedido_id = pedido_id;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public int getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(int empresa_id) {
        this.empresa_id = empresa_id;
    }

    public int getRepartidor_id() {
        return repartidor_id;
    }

    public void setRepartidor_id(int repartidor_id) {
        this.repartidor_id = repartidor_id;
    }

    public int getZonas_que_cruza() {
        return zonas_que_cruza;
    }

    public void setZonas_que_cruza(int zonas_que_cruza) {
        this.zonas_que_cruza = zonas_que_cruza;
    }

    public List<Long> getIds_zonas() {
        return ids_zonas;
    }

    public void setIds_zonas(List<Long> ids_zonas) {
        this.ids_zonas = ids_zonas;
    }
}
