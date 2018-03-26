import java.util.Comparator;

public class ComparatorProblems {
    public static Comparator<Point> task6() {
        // масса ↑, расстояние ↓, цвет ↑
//        return Comparator
//                .comparingDouble(Point::getMass).
//                .thenComparingDouble(x -> Math.sqrt(x.x * x.x + x.y * x.y))
//                .thenComparingInt(Point::getColor);

        return Comparator
                .comparingDouble(Point::getMass)
                .thenComparing(Comparator.comparingDouble((Point x) -> x.x * x.x + x.y * x.y).reversed())
                .thenComparingInt(Point::getColor);
    }
}
