package ru.appline.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SideModel implements Serializable {

    private static final SideModel instance = new SideModel();
    private final List<Side> sides;

    private SideModel() {
        sides = new ArrayList();
    }

    public static SideModel getInstance() {
        return instance;
    }

    public void addCoordinates(Side side) {
        sides.add(side);
    }

    public List<Side> getSides() {
        return sides;
    }
}
