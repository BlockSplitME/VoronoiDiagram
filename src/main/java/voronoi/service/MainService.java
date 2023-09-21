package voronoi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import voronoi.model.VoronoiDiagram;
@Service
public class MainService {
//    private final VoronoiDiagram diagram;
    public VoronoiDiagram getDiagram(int count, int width, int height) {
        VoronoiDiagram obj = new VoronoiDiagram(count, width, height);
        obj.getResult();
        return obj;
    }
}
