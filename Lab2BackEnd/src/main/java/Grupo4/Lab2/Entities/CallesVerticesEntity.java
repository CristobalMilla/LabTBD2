package Grupo4.Lab2.Entities;

public class CallesVerticesEntity { 
    private Long id;
    private Integer cnt;
    private Integer chk;
    private Integer ein;
    private Integer eout;
    private String theGeomWkt;

    public CallesVerticesEntity() {
    }

    public CallesVerticesEntity(Long id, Integer cnt, Integer chk, Integer ein, Integer eout, String theGeomWkt) { // Renamed from CallesVerticesPgrEntity
        this.id = id;
        this.cnt = cnt;
        this.chk = chk;
        this.ein = ein;
        this.eout = eout;
        this.theGeomWkt = theGeomWkt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public Integer getChk() {
        return chk;
    }

    public void setChk(Integer chk) {
        this.chk = chk;
    }

    public Integer getEin() {
        return ein;
    }

    public void setEin(Integer ein) {
        this.ein = ein;
    }

    public Integer getEout() {
        return eout;
    }

    public void setEout(Integer eout) {
        this.eout = eout;
    }

    public String getTheGeomWkt() {
        return theGeomWkt;
    }

    public void setTheGeomWkt(String theGeomWkt) {
        this.theGeomWkt = theGeomWkt;
    }
}
