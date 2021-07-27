package ru.appline.logic;

public class Side {

    private String side;
    private int from;
    private int to;

    public Side() {
    }

    public Side(String side, int from, int to) {
        this.side = side;
        this.from = from;
        this.to = to;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }
}
