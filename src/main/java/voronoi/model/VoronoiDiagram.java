package voronoi.model;

import lombok.*;

import java.util.HashMap;
import java.util.Random;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class VoronoiDiagram {
    private final int countPoint;
    private final int widthField;
    private final int heightField;
    private HashMap<Integer, double[]> points = new HashMap<>();
    public void getResult() {
        this.createPointsCoordinates(this.getCountPoint(), this.getWidthField(), this.getHeightField());
        this.algorithmFortune();
    }
    private void createPointsCoordinates(int count, int width, int height) {
        Random r = new Random();
        for(int i = 0; i < count; i++) {
            this.points.put(i, new double[]{r.nextDouble() * width, r.nextDouble() * height});
        }
    }
    private void algorithmFortune() {

    }
 }
