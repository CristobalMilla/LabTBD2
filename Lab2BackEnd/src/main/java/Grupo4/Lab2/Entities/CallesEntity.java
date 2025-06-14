package Grupo4.Lab2.Entities;

import org.locationtech.jts.geom.LineString;
import java.math.BigDecimal;

public class CallesEntity {
    private Integer cleanedStreetId; 
    private Integer streetId;
    private Integer fid;
    private BigDecimal shapeLeng;
    private BigDecimal stLength;   
    private String nomRuta;
    private String comuna;
    private LineString geom;
    private Integer source;
    private Integer target;
    private Double cost;
    private String geomWkt;

    public CallesEntity() { }
    public CallesEntity(Integer cleanedStreetId, Integer streetId, Integer fid, BigDecimal shapeLeng, BigDecimal stLength,
                        String nomRuta, String comuna, LineString geom, Integer source, Integer target, Double cost) {
        this.cleanedStreetId = cleanedStreetId;
        this.streetId = streetId;
        this.fid = fid;
        this.shapeLeng = shapeLeng;
        this.stLength = stLength;
        this.nomRuta = nomRuta;
        this.comuna = comuna;
        this.geom = geom;
        this.source = source;
        this.target = target;
        this.cost = cost;
    }

    // Getters y Setters actualizados
    public Integer getCleanedStreetId() {
        return cleanedStreetId;
    }

    public void setCleanedStreetId(Integer cleanedStreetId) {
        this.cleanedStreetId = cleanedStreetId;
    }

    public Integer getStreetId() {
        return streetId;
    }

    public void setStreetId(Integer streetId) {
        this.streetId = streetId;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public BigDecimal getShapeLeng() {
        return shapeLeng;
    }

    public void setShapeLeng(BigDecimal shapeLeng) {
        this.shapeLeng = shapeLeng;
    }

    public BigDecimal getStLength() {
        return stLength;
    }

    public void setStLength(BigDecimal stLength) {
        this.stLength = stLength;
    }

    public String getNomRuta() {
        return nomRuta;
    }

    public void setNomRuta(String nomRuta) {
        this.nomRuta = nomRuta;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public LineString getGeom() {
        return geom;
    }

    public void setGeom(LineString geom) {
        this.geom = geom;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getGeomWkt() {
        return geomWkt;
    }

    public void setGeomWkt(String geomWkt) {
        this.geomWkt = geomWkt;
    }
}
