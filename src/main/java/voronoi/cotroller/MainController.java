package voronoi.cotroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import voronoi.model.Fortune.Point;
import voronoi.service.MainService;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping(value ="/api/")
@RequiredArgsConstructor
public class MainController {
    private final MainService service;
    @GetMapping("initField")
    public ResponseEntity<Map<String, Object>> initField(@RequestParam int count, @RequestParam int width, @RequestParam int height){
        return ResponseEntity.ok(service.init(count, width, height));
    }
    @GetMapping("nextStep")
    public ResponseEntity<Map<String, Object>> doStep(){
        return ResponseEntity.ok(service.doStep());
    }
    @GetMapping("result")
    public ResponseEntity<Map<String, Object>> getResult(){
        return ResponseEntity.ok(service.getResult());
    }

}
