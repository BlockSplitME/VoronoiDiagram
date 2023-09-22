package voronoi.config;

import static java.lang.Math.pow;

public class Config {
    public static double functionParabol(double x, double x0, double y0, double yDirectrix) {
        return (pow(x - x0, 2) + pow(y0,2) - pow(yDirectrix, 2) / (2*(y0 - yDirectrix)));
    }
}
