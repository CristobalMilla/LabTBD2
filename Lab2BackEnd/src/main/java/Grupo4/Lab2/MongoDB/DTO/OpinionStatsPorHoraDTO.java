package Grupo4.Lab2.MongoDB.DTO;

public class OpinionStatsPorHoraDTO {
    private int hour;
    private double averageScore;
    private long count;

    // Constructor, Getters y Setters
    public OpinionStatsPorHoraDTO() {}

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
