package voronoi.cotroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import voronoi.model.VoronoiDiagram;
import voronoi.service.MainService;

@RestController
@RequestMapping("diagram")
@RequiredArgsConstructor
public class MainController {
    private final MainService service;
    @GetMapping
    public ResponseEntity<VoronoiDiagram> getDiagram(@RequestParam int count, @RequestParam int width, @RequestParam int height){
        return ResponseEntity.ok(service.getDiagram(count,width,height));
    }

}
