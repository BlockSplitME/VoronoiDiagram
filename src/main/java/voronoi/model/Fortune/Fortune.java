package voronoi.model.Fortune;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Fortune {
    private Queue<Event> queue = new LinkedList<>();
    private ArrayList<double[]> peaks = new ArrayList<>();
    private TreeSet<Arc> arcs = new TreeSet<>();
    private double beachLine;

    private final HashMap<Integer, double[]> points;
    private final double startFieldX;
    private final double widthField;
    private final double startFieldY;
    private final double heightField;
    public ArrayList<double[]> getResult() {
        this.generateQueue();
        this.scanLine();
        return new ArrayList<>();
    }
    public void scanLine() {
        this.beachLine = this.heightField;
        while(this.queue.isEmpty()) {
            Event currentEvent = queue.remove();
            if(currentEvent.type().equals("point")) {
                this.pointEvent(currentEvent.coordinates());
            } else {
                this.circleEvent();
            }
        }
    }
    private void generateQueue() {
        this.points.forEach((id, value) -> {
            this.queue.add(new Event("point", value));
        });
    }
    private void pointEvent(double[] pointCrd) {
        this.scanArcs(pointCrd);
    }
    private void circleEvent() {

    }
    private void addArcs() {

    }
    private void scanArcs(double[] pointCrd){
        if(this.arcs.isEmpty()) {
            this.arcs.add(new Arc(pointCrd, Arc.constructParabola(this.startFieldX, this.widthField, pointCrd, pointCrd[1])));
        } else {
            this.splitArc(pointCrd);
            this.checkCircleEvent();
        }
    }
    private void splitArc(double[] pointCrd) {
//        int index = searchArc(this.arcs, pointCrd[0]);
//        Arc tmp = this.arcs.remove(index);
//        //right
//        Arc right = new Arc(tmp.focus(), this.arcs.get(index+1).focus(), Arc.constructParabola(pointCrd[0], tmp.coordinates().get(tmp.coordinates().size() -1)[0], pointCrd, pointCrd[1]));
//        //left
//        Arc left = new Arc(tmp.focus(), pointCrd, Arc.constructParabola(tmp.coordinates().get(0)[0], pointCrd[0], pointCrd, pointCrd[1]) );
//        //center
//        Arc center = new Arc(pointCrd, tmp.focus(), Arc.constructParabola(pointCrd[0], pointCrd[0], pointCrd, pointCrd[1]));
//        this.arcs.add(index, right);
//        this.arcs.add(index, center);
//        this.arcs.add(index, left);
    }
    private void checkCircleEvent() {

    }
    private static Arc searchArc(TreeSet<Arc> arcs, double x) {
        ArrayList<double[]> a = new ArrayList<double[]>();
        a.add(new double[]{x});
        return (arcs.headSet(new Arc(null, a))).last();
    }


}
