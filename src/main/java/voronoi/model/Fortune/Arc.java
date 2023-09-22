package voronoi.model.Fortune;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

import static voronoi.config.Config.functionParabol;

public record Arc(double[] focus, double[] focusNext, ArrayList<double[]> coordinates) implements Comparator, Comparable {
    public Arc(double[] focusFirst, ArrayList<double[]> coordinates) {
        this(focusFirst, null, coordinates);
    }
    @Override
    public int compareTo(Object o) {
        return compare(this, o);
    }

    @Override
    public int compare(Object o1, Object o2) {
        Arc m1 = (Arc) o1;
        Arc m2 = (Arc) o2;
        return (m1.coordinates.get(0)[0] >= m2.coordinates.get(0)[0]) ? 1: -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arc arc = (Arc) o;
        return Objects.equals(coordinates.get(0)[0], arc.coordinates.get(0)[0]);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(coordinates);
        result = 31 * result + Arrays.hashCode(focus);
        result = 31 * result + Arrays.hashCode(focusNext);
        return result;
    }

    public static ArrayList<double[]> constructParabola(double startX, double endX, double[] coordinates, double yScanLine) {
        ArrayList<double[]> parabola = new ArrayList<>();
        for(double x = startX; x < endX; x++) {
            parabola.add(new double[]{x, functionParabol(x, coordinates[0], coordinates[1], yScanLine)});
        }
        return parabola;
    }
}
