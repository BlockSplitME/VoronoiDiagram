package voronoi.service;

import org.springframework.stereotype.Service;
import voronoi.model.VoronoiDiagram;

import java.util.HashMap;
import java.util.Map;

@Service
public class MainService {
    public VoronoiDiagram voronoiDiagram;
    public Map<String, Object> init(int count, int width, int height) {
        System.out.println("\nTask parameters: n = " + count + " ( " + width + " x " + height + " )");
        this.voronoiDiagram = new VoronoiDiagram(count, width, height);
        this.voronoiDiagram.init();
        return new HashMap<>(){{
           put("points", voronoiDiagram.getPoints());
        }};
    }
    public Map<String, Object> doStep() {
        this.voronoiDiagram.doStep();
        return new HashMap<>(){{
            put("points", voronoiDiagram.getPoints());
            put("lines", voronoiDiagram.getLines());
            put("arcs", voronoiDiagram.getArcs());
            put("circles", voronoiDiagram.getCircles());
            put("beachLine", voronoiDiagram.getBeachLineY());
        }};
    }
    public Map<String, Object> getResult() {
        long m = System.currentTimeMillis();
        this.voronoiDiagram.getResult();
        System.out.println("Lead time: "+ (double) (System.currentTimeMillis() - m) + "ms");
        return new HashMap<>(){{
            put("points", voronoiDiagram.getPoints());
            put("lines", voronoiDiagram.getLines());
        }};
    }
}
