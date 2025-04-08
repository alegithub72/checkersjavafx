package sa.fx.draugths.screen;

public enum GLevel {
    LVL1_JUNGLE(1),
    LVL2_DESERT(2),
    LVL3_MOUNTAIN(3),
    LVL4_FOREST(4),
    LVL5_SEA(5),
    LVL6_POLE(6),
    LVL7_CITY(7),
    LVL8_SKY(8),
    LVL9_MOON(9);
    int n;

    GLevel(int n) {
        this.n = n;
    }

    public int n() {
        return n;
    }

}
