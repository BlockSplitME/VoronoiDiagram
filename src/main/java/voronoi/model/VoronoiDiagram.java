package voronoi.model;

import lombok.*;
import voronoi.model.Fortune.Arc;
import voronoi.model.Fortune.Fortune;
import voronoi.model.Fortune.Point;

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

    private Set<Point> points = new HashSet<>();
    private Set<Point> arcs = new HashSet<>();
    private Set<ArrayList<Point>> lines = new HashSet<>();
    private Set<ArrayList<Object>> circles = new HashSet<>();

    private Fortune fortune;
    public void init() {
        this.createRandomPointsCoordinates(this.getCountPoint(), this.getWidthField(), this.getHeightField());
        this.fortune = new Fortune(this.points, startFieldX, this.widthField, startFieldY, this.heightField);
    }
    public void doStep() {
        this.arcs.clear();
        this.lines.clear();
        this.circles.clear();

        if(!this.fortune.getQueue().isEmpty()) {
            this.fortune.doStep();

            this.fortune.getArcs().forEach(arc -> this.arcs.addAll(Arc.getCoordinatesArc(arc, fortune.getBeachLineY(), heightField, startFieldX, widthField)));

            this.fortune.getCircles().forEach((center, r) -> this.circles.add(new ArrayList<>(){{add(center); add(r);}}));
        }
        this.fortune.getLines().forEach(line  -> this.lines.add(new ArrayList<>(){{add(line.getStartPoint()); add(line.getEndPoint());}}));

    }
    public void getResult() {
        while(!this.fortune.getQueue().isEmpty()) {
            this.fortune.doStep();
        }
        this.fortune.getLines().forEach(line  -> this.lines.add(new ArrayList<>(){{add(line.getStartPoint()); add(line.getEndPoint());}}));
    }
    private void createRandomPointsCoordinates(int count, int width, int height) {
        Random r = new Random();
        long seed = r.nextInt();
//        r.setSeed(122403814);
        System.out.println("Seed: " + seed);
        for(int i = 0; i < count; i++) {
            this.points.add(new Point(r.nextDouble() * width, r.nextDouble() * height));
        }
    }
    public double getBeachLineY() {
        return this.getFortune().getBeachLineY();
    }
 }
