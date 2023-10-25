package voronoi.model.Fortune;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Comparator;
import java.util.Objects;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class Event implements Comparator, Comparable {
    private final String type;
    private final Point point;
    private final Point peak;
    private final Arc arc;

    @Override
    public int compareTo(Object o) {
        return compare(this, o);
    }

    @Override
    public int compare(Object o1, Object o2) {
        Event m1 = (Event) o1;
        Event m2 = (Event) o2;
        if(m1.equals(m2)) return 0;
        return (m1.point.y() >= m2.point.y()) ? -1 : 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(point.y(), event.point.y());
    }

    @Override
    public int hashCode() {
        return Objects.hash((Object) point);
    }
}
