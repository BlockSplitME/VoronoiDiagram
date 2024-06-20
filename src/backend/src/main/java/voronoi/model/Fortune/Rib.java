package voronoi.model.Fortune;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
public class Rib implements Comparable<Rib> {
    private final Point startPoint;
    private final int id;
    private Point endPoint;
    private Point intermediatePoint;

    @Override
    public int compareTo(Rib o) {
        if(this.equals(o)) {
            return 0;
        }
        return (this.startPoint.x() < o.startPoint.x()) ? -1 : 1;
    }
    @Override
    public boolean equals(Object o) {
        if(o.getClass().equals(this.getClass())) {
            Rib rib = (Rib) o;
            return rib.id == this.id;
        } else {
            return false;
        }
    }
    public static ArrayList<Rib> searchRib(Map<ArrayList<Point>,ArrayList<Rib>> ribs, Point f1, Point f2) {
       ArrayList<Rib> pairRib = ribs.get(new ArrayList<Point>(){{add(f1); add(f2);}});
       if(pairRib == null) pairRib = ribs.get(new ArrayList<Point>(){{add(f2); add(f1);}});
       return pairRib;
    }
}
