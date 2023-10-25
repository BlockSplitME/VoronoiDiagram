package voronoi.model.Fortune.utils;

import voronoi.model.Fortune.Arc;
import voronoi.model.Fortune.Point;
import voronoi.model.Fortune.Rib;

public class Factory {
    private static int idArc = 0;
    private static int idRib = 0;
    public static Arc newArc(Point focus, double leftBP, double rightBP) {
        return new Arc(focus, leftBP, rightBP, idArc++);
    }
    public static Rib newRib(Point startPoint) {
        return new Rib(startPoint, idRib++);
    }
}
