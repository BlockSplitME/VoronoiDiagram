package voronoi.model;

import lombok.*;
import voronoi.model.Fortune.Fortune;

import java.util.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class VoronoiDiagram {
    private final int countPoint;
    private final int widthField;
    private final int heightField;
    private static int startFieldX = 0;
    private static int startFieldY = 0;

    private HashMap<Integer, double[]> points = new HashMap<>();
    private ArrayList<double[]> lines;
    public void getResult() {
        this.createPointsCoordinates(this.getCountPoint(), this.getWidthField(), this.getHeightField());
        this.lines = new Fortune(this.points, startFieldX, this.widthField, this.startFieldY, this.heightField).getResult();
    }
    private void createPointsCoordinates(int count, int width, int height) {
        Random r = new Random();
        double[][] y = new double[count][2];
        for(int i = 0; i < count; i++) {
            y[i][0] = r.nextDouble() * width;
            y[i][1] = r.nextDouble() * height;
        }
        Comparator<double[]> comp = Comparator.comparingDouble(a -> a[1]);
        Arrays.sort(y, comp); //.reversed()
        for(int i = 0; i < count; i++) {
            this.points.put(i, new double[]{y[i][0], y[i][1]});
        }
    }
 }
