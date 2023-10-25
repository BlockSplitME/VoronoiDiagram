package voronoi.model.Fortune;

import lombok.*;
import voronoi.model.Fortune.utils.GeometryFormulas;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class Arc implements Comparator, Comparable {
    private Point focus;
    private double leftBP, rightBP;
    private final int id;

    public Arc(Point focus, int id) {
        this(focus, focus.x(), focus.x(), id);
    }
    @Override
    public int compareTo(Object o) {
        return compare(this, o);
    }

    @Override
    public int compare(Object o1, Object o2) {
        Arc m1 = (Arc) o1;
        Arc m2 = (Arc) o2;
        if(m1.equals(m2)) {
            return 0;
        }
        if(m1.leftBP > m2.leftBP) {
            return 1;
        } else if (m1.leftBP == m2.leftBP) {
            if(m1.rightBP > m2.rightBP) {
                return 1;
            } else if(m1.rightBP == m2.rightBP) {
                if(m2.getFocus() != null) {
                    if(m1.getFocus().x() > m2.getFocus().x()) {
                        return 1;
                    }
                }
            }
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if(o.getClass().equals(this.getClass())) {
            Arc arc = (Arc) o;
            return arc.id == this.id;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    public static Set<Point> getCoordinatesArc (Arc arc, double scanLine, double borderMaxY, double leftBP, double rightBP) {
        if(arc.leftBP > leftBP) leftBP = arc.leftBP;
        if(arc.rightBP < rightBP) rightBP = arc.rightBP;
        return GeometryFormulas.constructParabola(leftBP, rightBP, arc.focus, scanLine, borderMaxY);
    }

    public static Arc searchArc(TreeSet<Arc> arcs, double x) {
        SortedSet<Arc> sorted = arcs.headSet(new Arc(null, x, 0, -1));
        if(sorted.isEmpty()) {
            return null;
        } else if (sorted.last().getRightBP() < x ) {
            return null;
        }
        return sorted.last();
    }
    public String toString() {
        return "Arc(focus=" + focus +  " id=" + id;
    }
}
