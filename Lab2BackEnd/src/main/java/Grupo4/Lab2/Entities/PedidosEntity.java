package Grupo4.Lab2.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;

import java.sql.Timestamp;

public class PedidosEntity {

    private long pedido_id;
    private Long cliente_id;
    private Long empresa_id;
    private Long repartidor_id;

    @JsonIgnore
    private Point punto_inicio;
    @JsonIgnore
    private Point punto_final;
    @JsonIgnore
    private LineString ruta_estimada;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp fecha;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp fecha_entrega;

    private String estado;

    public PedidosEntity(long pedido_id, Long cliente_id, Long empresa_id, Long repartidor_id, Timestamp fecha, Timestamp fecha_entrega, String estado, Point punto_inicio, Point punto_final, LineString ruta_estimada) {
        this.pedido_id = pedido_id;
        this.cliente_id = cliente_id;
        this.empresa_id = empresa_id;
        this.repartidor_id = repartidor_id;
        this.fecha = fecha;
        this.fecha_entrega = fecha_entrega;
        this.estado = estado;
        this.punto_inicio = punto_inicio;
        this.punto_final = punto_final;
        this.ruta_estimada = ruta_estimada;
    }

    public PedidosEntity() {
    }

    public long getPedido_id() {
        return pedido_id;
    }

    public Long getCliente_id() {
        return cliente_id;
    }

    public Long getEmpresa_id() {
        return empresa_id;
    }

    public Long getRepartidor_id() {
        return repartidor_id;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public Timestamp getFecha_entrega() {
        return fecha_entrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setPedido_id(long pedido_id) {
        this.pedido_id = pedido_id;
    }

    public void setCliente_id(Long cliente_id) {
        this.cliente_id = cliente_id;
    }

    public void setEmpresa_id(Long empresa_id) {
        this.empresa_id = empresa_id;
    }

    public void setRepartidor_id(Long repartidor_id) {
        this.repartidor_id = repartidor_id;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public void setFecha_entrega(Timestamp fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @JsonIgnore
    public Point getPunto_inicio() {
        return punto_inicio;
    }

    @JsonIgnore
    public void setPunto_inicio(Point punto_inicio) {
        this.punto_inicio = punto_inicio;
    }

    @JsonIgnore
    public Point getPunto_final() {
        return punto_final;
    }

    @JsonIgnore
    public void setPunto_final(Point punto_final) {
        this.punto_final = punto_final;
    }

    @JsonIgnore
    public LineString getRuta_estimada() {
        return ruta_estimada;
    }

    @JsonIgnore
    public void setRuta_estimada(LineString ruta_estimada) {
        this.ruta_estimada = ruta_estimada;
    }

    @JsonProperty("punto_inicio")
    public String getPuntoInicioWkt() {
        return punto_inicio != null ? punto_inicio.toText() : null;
    }

    @JsonProperty("punto_inicio")
    public void setPuntoInicioWkt(String wkt) throws Exception {
        if (wkt == null || wkt.isEmpty()) {
            this.punto_inicio = null;
        } else {
            this.punto_inicio = (Point) new WKTReader().read(wkt);
        }
    }

    @JsonProperty("punto_final")
    public String getPuntoFinalWkt() {
        return punto_final != null ? punto_final.toText() : null;
    }

    @JsonProperty("punto_final")
    public void setPuntoFinalWkt(String wkt) throws Exception {
        if (wkt == null || wkt.isEmpty()) {
            this.punto_final = null;
        } else {
            this.punto_final = (Point) new WKTReader().read(wkt);
        }
    }

    @JsonProperty("ruta_estimada")
    public String getRutaEstimadaWkt() {
        return ruta_estimada != null ? ruta_estimada.toText() : null;
    }

    @JsonProperty("ruta_estimada")
    public void setRutaEstimadaWkt(String wkt) throws Exception {
        if (wkt == null || wkt.isEmpty()) {
            this.ruta_estimada = null;
        } else {
            this.ruta_estimada = (LineString) new WKTReader().read(wkt);
        }
    }
}
