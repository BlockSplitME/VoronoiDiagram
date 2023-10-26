package voronoi.model.Fortune;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import voronoi.model.Fortune.utils.Factory;
import voronoi.model.Fortune.utils.GeometryFormulas;

import java.util.*;

@Getter
@Setter
@ToString
public class Fortune {
    private int step = 0;
    private PriorityQueue<Event> queue = new PriorityQueue<>();
    private TreeSet<Arc> arcs = new TreeSet<>();
    private Map<ArrayList<Point>, ArrayList<Rib>> halfRibs = new HashMap<>();
    private Set<Rib> lines = new HashSet<>();
    private HashMap<Point, Double> circles = new HashMap<>();
    private double beachLineY;

    private final Set<Point> points;
    private final double startFieldX;
    private final double widthField;
    private final double startFieldY;
    private final double heightField;

    public Fortune(Set<Point> points, double startFieldX, double widthField, double startFieldY, double heightField) {
        this.points = points;
        this.startFieldX = startFieldX;
        this.widthField = widthField;
        this.startFieldY = startFieldY;
        this.heightField = heightField;
        this.generateQueue();
    }
    private void generateQueue() {
        this.points.forEach((point) -> {
            this.queue.add(new Event("point", point, null, null));
        });
        //service event
        //left
        this.queue.add(new Event("point", new Point(this.startFieldX - this.widthField, this.startFieldY + (this.heightField + this.startFieldY) / 2), null, null));
        //right
        this.queue.add(new Event("point", new Point(this.widthField + this.widthField, this.startFieldY + 1 + (this.heightField + this.startFieldY) / 2), null, null));
//        center under
        this.queue.add(new Event("point", new Point(this.startFieldX + (this.widthField + this.startFieldX) / 2, this.startFieldY - this.heightField), null, null));
//        //left under
//        this.queue.add(new Event("point", new Point(this.startFieldX - this.widthField, this.startFieldY - (this.heightField + this.startFieldY) / 2), null, null));
//        //right under
//        this.queue.add(new Event("point", new Point(this.widthField + this.widthField, this.startFieldY - (this.heightField + this.startFieldY) / 2), null, null));
    }
    public void doStep() {
        this.step++;
        Event currentEvent = this.queue.poll();
        this.beachLineY = currentEvent.getPoint().y();
        if (currentEvent.getType().equals("point")) {
            this.pointEvent(currentEvent.getPoint());
        } else {
            this.circleEvent(currentEvent.getPeak(), currentEvent.getArc());
        }
    }
    private void pointEvent(Point pointCrd) {
        if(!this.arcs.isEmpty()) {
            this.updateArcs();
            this.splitArc(pointCrd);
        } else this.arcs.add(Factory.newArc(pointCrd, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));

        if(this.arcs.size() >= 3) {
            this.checkCircleEvent();
        }
    }
    private void addArc(Point pointCrd) {
        Arc newArc = Factory.newArc(pointCrd, pointCrd.x(), pointCrd.x());
        this.arcs.add(newArc);
    }
    private void updateArcs() {
        Iterator<Arc> itArcs = this.arcs.iterator();
        Arc tmp1 = itArcs.next();
        if(itArcs.hasNext()) {
            while (itArcs.hasNext()){
                Arc tmp2 = itArcs.next();
                this.crossArcs(tmp1, tmp2);
                tmp1 = tmp2;
            }
        }
    }

    private void splitArc(Point pointCrd) {
        Arc changedArc = Arc.searchArc(this.arcs, pointCrd.x());
        if(changedArc != null) {
            // right
            Arc right = Factory.newArc(changedArc.getFocus(), pointCrd.x(), changedArc.getRightBP());
            this.arcs.add(right);
            //left
            changedArc.setRightBP(pointCrd.x());

            Point point = new Point(pointCrd.x(), GeometryFormulas.functionParabola(pointCrd.x(), changedArc.getFocus().x(), changedArc.getFocus().y(), this.beachLineY));
            //center
            this.addArc(pointCrd);

            this.halfRibs.put(new ArrayList<>(){{add(changedArc.getFocus()); add(pointCrd);}},
                              new ArrayList<>(){{add(Factory.newRib(point));add(Factory.newRib(point));}}
            );
        }

    }
    private void crossArcs(Arc arc1, Arc arc2) {
        TreeSet<Point> crossPoints = GeometryFormulas.parabolasCrossX(arc1.getFocus().x(), arc2.getFocus().x(), arc1.getFocus().y(), arc2.getFocus().y(), this.beachLineY);
        if (crossPoints != null) {
            double x = 0;
            if(crossPoints.size() == 2) {
                if(arc1.getLeftBP() == crossPoints.first().x()) {
                    x = crossPoints.last().x();
                } else {
                    Arc crossArcFirst = Arc.searchArc(this.arcs, crossPoints.first().x());
                    Arc crossArcLast = Arc.searchArc(this.arcs, crossPoints.last().x());
                    if(crossArcFirst != null && !crossArcFirst.equals(arc1) && !crossArcFirst.equals(arc2)) {
                        if(crossArcLast != null && !crossArcLast.equals(arc1) && !crossArcLast.equals(arc2)) {
                            return;
                        }
                        x = crossPoints.last().x();
                    } else x = crossPoints.first().x();
                }
            } else x = crossPoints.first().x();
            arc1.setRightBP(x);
            arc2.setLeftBP(x);
        }
    }
    private void checkCircleEvent() {
        this.circles.clear();
        Iterator<Arc> itArcs = this.arcs.iterator();
        Arc tmp1 = itArcs.next();
        Arc tmp = itArcs.next();
        for(int i = 0; i < this.arcs.size() - 2; i++) {
            Arc tmp2 = tmp;
            Arc tmp3 = itArcs.next();
            if(!GeometryFormulas.is3Collinear(tmp1.getFocus(),tmp2.getFocus(),tmp3.getFocus())) {
                Point center = GeometryFormulas.centerCircle(tmp1.getFocus(),tmp2.getFocus(),tmp3.getFocus());
                double r = GeometryFormulas.getDistance(center, tmp1.getFocus());
                double eventY = center.y() - r;
                if(eventY < this.beachLineY && this.checkValidCircleEvent(tmp1.getFocus(), tmp2.getFocus(), tmp3.getFocus(), center)) {
                    this.queue.add(new Event("circle", new Point(center.x(), eventY), center, tmp2));
                }
                this.circles.put(center, r);
            }
            tmp1 = tmp2;
            tmp = tmp3;
        }
    }
    private void circleEvent(Point peak, Arc delArc) {
        if(!this.arcs.contains(delArc)) {
            this.updateArcs();
            this.checkCircleEvent();
            return;
        }
        Arc leftArc = this.arcs.lower(delArc);
        Arc rightArc = this.arcs.higher(delArc);
        if (leftArc == null || rightArc == null) {
            System.out.println("Arc is null");
            return;
        }
        this.splitRibs(peak, leftArc.getFocus(), delArc.getFocus(), rightArc.getFocus());

        this.arcs.remove(delArc);

        this.queue.removeIf(event -> {
            if(event.getType().equals("circle")) return event.getArc().equals(delArc);
            else return false;
        });
        this.updateArcs();
        this.checkCircleEvent();
    }
    private void splitRibs(Point peak, Point f1, Point f2, Point f3) {
        //Update left rib
        this.updateRibs(peak, f1, f2);
        //Update right rib
        this.updateRibs(peak, f2, f3);
        //Add new half rib
        this.halfRibs.put(new ArrayList<>(){{add(f1);add(f3);}}, new ArrayList<>(){{add(Factory.newRib(peak));}});
    }
    private void updateRibs(Point peak, Point f1, Point f2) {
        ArrayList<Rib> pairRib = Rib.searchRib(this.halfRibs, f1, f2);
        if(pairRib != null) {
            if(pairRib.isEmpty()) {
                System.out.println(pairRib);
            }
            pairRib.get(0).setEndPoint(peak);
            if(pairRib.size() == 2) {
                pairRib.get(1).setIntermediatePoint(new Point(2 * pairRib.get(0).getStartPoint().x() - peak.x(), 2 * pairRib.get(0).getStartPoint().y() - peak.y()));
            }
            this.lines.add(pairRib.remove(0));
            if(pairRib.isEmpty()){
                if(this.halfRibs.remove(new ArrayList<Point>(){{add(f1);add(f2);}}) == null) this.halfRibs.remove(new ArrayList<Point>(){{add(f2);add(f1);}});
            }
        }
    }
    public boolean checkValidCircleEvent(Point lf, Point mf, Point rf, Point center) {
        boolean leftBreakpointMovingRight = isMovingRight(lf, mf);
        boolean rightBreakpointMovingRight = isMovingRight(mf, rf);
        double leftInitialX = getInitialX(lf, mf, leftBreakpointMovingRight);
        double rightInitialX = getInitialX(mf, rf, rightBreakpointMovingRight);
        return ((leftBreakpointMovingRight && leftInitialX < center.x()) ||
               (!leftBreakpointMovingRight && leftInitialX > center.x())) &&
               ((rightBreakpointMovingRight && rightInitialX < center.x()) ||
               (!rightBreakpointMovingRight && rightInitialX > center.x()));
    }
    private boolean isMovingRight(Point lp, Point rp) {
        return lp.y() < rp.y();
    }
    private double getInitialX(Point lp, Point rp, boolean movingRight ) {
        return movingRight ? lp.x() : rp.x();
    }

}
