package voronoi.model.Fortune;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class FortuneTest {
    private Fortune fortune;
    @Before
    public void setup() {
        this.fortune = new Fortune(createPoints(100, 600, 600), 0, 600, 0, 600);
    }
    @Test
    public void testDoStep() {
        while(!this.fortune.getQueue().isEmpty()) {
            this.fortune.doStep();
        }
        this.fortune.lastStep();
    }
    private Set<Point> createPoints(int count, int width, int height ) {
        Set<Point> points = new HashSet<>();
        Random r = new Random(-802417307);
        for(int i = 0; i < count; i++) {
            points.add(new Point(r.nextDouble() * width, r.nextDouble() * height));
        }
        return points;
    }
}