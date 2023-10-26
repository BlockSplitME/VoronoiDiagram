package voronoi.model.Fortune.utils;

import voronoi.model.Fortune.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static java.lang.Math.*;

public class GeometryFormulas {
    public static TreeSet<Point> constructParabola(double startX, double endX, Point focus, double yScanLine, double borderMaxY) {
        if(startX == endX) {
            return GeometryFormulas.getRay(focus.x(), focus.y(), borderMaxY);
        }
        TreeSet<Point> parabola = new TreeSet<>();
        for (double x = startX; x < endX; x++) {
            double y = GeometryFormulas.functionParabola(x, focus.x(), focus.y(), yScanLine);
            if(y < borderMaxY) {
                parabola.add(new Point(x, y));
            }

        }
        return parabola;
    }
    public static double functionParabola(double x, double x0, double y0, double c) {
        return (pow(x - x0, 2) + pow(y0,2) - pow(c, 2)) / (2*(y0 - c));
    }
    public static TreeSet<Point> getRay(double x0, double y0, double borderY) {
        TreeSet<Point> parabola = new TreeSet<>();
        for(double y = y0; y < borderY; y++) {
            parabola.add(new Point(x0, y));
        }
        return parabola;
    }
    public static TreeSet<Point> parabolasCrossX(double fx0, double fx1, double fy0, double fy1, double yLine) {
        double p1 = 2 * (fy0 - yLine);
        double p2 = 2 * (fy1 - yLine);
        if (p1 == 0) {
            System.out.println("F1 is ray.");
            return new TreeSet<>() {{add(new Point(fx0, fy0));}};
        } else if (p2 == 0) {
            System.out.println("F2 is ray.");
            return new TreeSet<>() {{add(new Point(fx1, fy1));}};
        } else {
            double a1 = 1 / p1;
            double a2 = 1 / p2;
            double a = a2 - a1;
            double b1 = - 2 * fx0 * a1;
            double b2 = - 2 * fx1 * a2;
            double b = b2 - b1;
            double c1 = pow(fx0, 2) * a1 + (fy0 + yLine) / 2;
            double c2 = pow(fx1, 2) * a2 + (fy1 + yLine) / 2;
            double c = c2 - c1;
            double D = pow(b, 2) - 4 * a * c;
            if (D < 0) {
                System.out.println("discriminant < 0");
                return null;
            } else if (D == 0) {
                double x = -b / (2 * a);
                double y = a1 * pow(x, 2) + b1 * x + c1;
                return new TreeSet<>() {{add(new Point(x, y));}};
            } else {
                double x1 = (-b - sqrt(D)) / (2 * a);
                double x2 = (-b + sqrt(D)) / (2 * a);
                double y1 = a1 * pow(x1, 2) + b1 * x1 + c1;
                double y2 = a1 * pow(x2, 2) + b1 * x2 + c1;
                return new TreeSet<>() {{add(new Point(x1, y1));add(new Point(x2, y2));}};
            }
        }
    }
    public static boolean is3Collinear(Point f1,Point f2, Point f3) {
        return 0.5 * (f1.x() * (f2.y() - f3.y()) + f2.x() * (f3.y() - f1.y()) + f3.x() * (f1.y() - f2.y())) == 0;
    }
    public static double getDistance(Point a, Point b) {
        return sqrt( pow(a.x() - b.x(), 2) + pow(a.y() - b.y(),2) );
    }
    public static Point centerCircle(Point f1, Point f2, Point f3) {
        double m1 = (f2.y() - f1.y()) / (f2.x() - f1.x());
        double m2 = (f3.y() - f2.y()) / (f3.x() - f2.x());
        double x = (m1 * m2 * (f1.y() - f3.y()) + m2 * (f1.x() + f2.x()) - m1 * (f2.x() + f3.x())) / (2 * (m2 - m1));
        double y = -1 / m1 * (x - (f1.x() + f2.x()) / 2) + (f1.y() + f2.y()) / 2;
        return new Point(x, y);
    }
    public static Set<Point> getCircle(Point center, double r) {
        return new HashSet<>(){{
            for(int i = 0; i < 360; i++) {
                add(new Point(center.x() + r * Math.cos(i), center.y() + r * Math.sin(i)));
            }
        }};
    }
    public static Set<Point> constructLine(Point startPoint, Point endPoint) {
        double x1, x2, y1, y2;
        if(startPoint.x() < endPoint.x()) {
            x1 = startPoint.x();
            x2 = endPoint.x();
            y1 = startPoint.y();
            y2 = endPoint.y();
        } else {
            x2 = startPoint.x();
            x1 = endPoint.x();
            y2 = startPoint.y();
            y1 = endPoint.y();
        }
        double k = GeometryFormulas.getK(x1, y1, x2, y2);
        return new HashSet<>(){{
            for(double x = x1; x < x2; x+= 1/ (abs(k) * 10)) {
                add(new Point(x, k * (x - x1) + y1) );
            }
        }};
    }
    public static Point getNewLinePoint(Point start, Point intermediate, double newX) {
        double k = GeometryFormulas.getK(start.x(), start.y(), intermediate.x(),  intermediate.y());
        return new Point(newX, k * (newX - start.x()) + start.y());
    }
    public static Point getNewLinePointY(Point start, Point intermediate, double newY) {
        double k = GeometryFormulas.getK(start.x(), start.y(), intermediate.x(),  intermediate.y());
        return new Point((newY - start.y() / k + start.x()), newY);
    }
    public static Point getBorderPoint (Point start, Point endPoint, double x1, double y1, double x2, double y2) {
        double k = getK(start.x(), start.y(), endPoint.x(),  endPoint.y());
        if(start.x() < endPoint.x()) {
            if(k > 0) {
                if(k > 1) {
                    return GeometryFormulas.getNewLinePointY(start, endPoint, y2);
                } else {
                    return GeometryFormulas.getNewLinePoint(start, endPoint, x2);
                }
            } else {
                if(abs(k) > 1) {
                    return GeometryFormulas.getNewLinePointY(start, endPoint, y1);
                } else {
                    return GeometryFormulas.getNewLinePoint(start, endPoint, x2);
                }
            }
        } else {
            if(k > 0) {
                if(k > 1) {
                    return GeometryFormulas.getNewLinePointY(start, endPoint, y1);
                } else {
                    return GeometryFormulas.getNewLinePoint(start, endPoint, x1);
                }
            } else {
                if(abs(k) > 1) {
                    return GeometryFormulas.getNewLinePointY(start, endPoint, y2);
                } else {
                    return GeometryFormulas.getNewLinePoint(start, endPoint, x1);
                }
            }
        }
    }
    private static double getK(double x1, double y1, double x2, double y2) {
        return (y2 - y1) / (x2 - x1);
    }
    public static Point middlePoint(Point start, Point end) {
        return new Point((start.x() + end.x()) / 2, (start.y() + end.y()) / 2);
    }
    public static boolean isPointInside(ArrayList<ArrayList<Point>> borders, Point checkedPoint) {
        int cross = 0;

        for(ArrayList<Point> segment: borders) {
            Point endPoint = new Point(Math.max(segment.get(0).x(), segment.get(1).x()) + 1, checkedPoint.y());
            if(GeometryFormulas.isSegmentsCross(segment.get(0), segment.get(1), checkedPoint, endPoint)) cross++;
        }
        return cross % 2 != 0;
    }
    public static boolean isSegmentsCross(Point startP1, Point endP1, Point startP2, Point endP2) {
        double y1 = startP1.y(); double x1 = startP1.x();
        double y2 = endP1.y();   double x2 = endP1.x();
        double y3 = startP2.y(); double x3 = startP2.x();
        double y4 = endP2.y();   double x4 = endP2.x();

        double denominator = (y4-y3)*(x1-x2)-(x4-x3)*(y1-y2);

        if (denominator == 0) {
            return (x1 * y2 - x2 * y1) * (x4 - x3) - (x3 * y4 - x4 * y3) * (x2 - x1) == 0 && (x1 * y2 - x2 * y1) * (y4 - y3) - (x3 * y4 - x4 * y3) * (y2 - y1) == 0;
        } else {
            double numerator_a = (x4 - x2) * (y4 - y3) - (x4 - x3) * (y4 - y2);
            double numerator_b = (x1 - x2) * (y4 - y2) - (x4 - x2) * (y1 - y2);
            double Ua = numerator_a / denominator;
            double Ub = numerator_b / denominator;
            return Ua >= 0 && Ua <= 1 && Ub >= 0 && Ub <= 1;
        }
    }
}
