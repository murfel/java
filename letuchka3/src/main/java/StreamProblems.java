import com.sun.javafx.geom.transform.Identity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.*;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamProblems {

    /**
     У вас есть stream строк. Напишите одну цепочку команд, которая (каждый следующий
     пункт - продолжение предыдущего):
     ● получит stream всех символов, из которых состоят изначальные строки
     ● оставит из этого потока только цифры
     ● удалит повторы цифр идущие подряд (т.е. 1 2 2 2 1 1 2 3 превратит в 1 2 1 2 3).
     ● используя reduce найдет среднее арифметическое всех оставшихся цифр.
     */
    public static double task1(Stream<String> stream) {
        return stream
                .flatMapToInt(String::chars)
                .filter(Character::isDigit)
                .filter(new IntPredicate() {
            Integer prev;

            @Override
            public boolean test(int value) {
                boolean ans = prev == null || prev != value;
                prev = value;
                return ans;
            }
        })
                .map(x -> x - '0')
                .asDoubleStream()
                .reduce(0, new DoubleBinaryOperator() {
                    double sum;
                    int cnt;

                    @Override
                    public double applyAsDouble(double left, double right) {
                        sum += right;
                        cnt++;
                        return sum / cnt;
                    }
                });
    }

    /**
     * Числа от 2 до n,
     * фильтр, не пропускающий не простые,
     * map<количество сотен, число простых в сотне>
     */
    public static Map<Integer, Long> task1V2(int n) {
        return IntStream
                .rangeClosed(2, n)
                .filter(x -> IntStream.rangeClosed(2, (int)Math.sqrt(x)).allMatch(d -> x % d != 0))
                .boxed()
                .collect(Collectors.groupingBy((Integer x) -> x / 100, Collectors.counting()));
    }

    /**
     У вас есть Iterator (потенциально бесконечный) по объектам типа Point, в которых есть
     поля x, y, mass. Напишите одну цепочку команд, которая (каждый следующий пункт -
     продолжение предыдущего):
     ● получит stream всех точек
     ● выведет на экран расстояние до каждой из точек
     ● отфильтрует точки с массой, которая уже была (помните, про то, что stream
     может быть бесконечным)
     ● результат работы - точка, чьи координаты - центр масс точек, а масса - сумма
     масс всех точек.
     */
    public static Point task2(Iterator<Point> iterator) {
        // случай бесконечного итератора
//        Stream.generate(iterator::next);
        // случай конечного итератора
        List<Point> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);

        Stream<Point> stream = list.stream();
        return stream
                .peek(x -> System.out.println(Math.sqrt(x.x * x.x + x.y * x.y)))
                .filter(new Predicate<Point>() {
                    TreeSet<Double> masses = new TreeSet<>();

                    @Override
                    public boolean test(Point point) {
                        Double element = masses.higher(point.mass - 1e-5);
                        boolean ans = element == null || Math.abs(element - point.mass) >= 1e-5;
                        if (ans)
                            masses.add(point.mass);
                        return ans;
                    }
                })
                // центр масс = взвешенное среднее, отдельно по x и по y
                .reduce(null, new BinaryOperator<Point>(){
                    double x;
                    double y;
                    double mass;

                    @Override
                    public Point apply(Point point, Point point2) {
                        mass += point2.mass;
                        x += point2.mass * point2.x;
                        y += point2.mass * point2.y;
                        return new Point(x / mass, y / mass, mass);
                    }
                });
    }

    /**
     В файле numbers.txt находится текст, содержащий помимо слов еще и натуральные
     числа (возможно текст записан в несколько строк). Напишите одну команду, которая,
     использую stream, выведет на экран 9 чисел - количество чисел в файле
     начинающихся на 1, 2, 3, ... 9, соответственно.
     */
    public static int[] task5(String filename) throws IOException {
        Pattern IS_NATURAL = Pattern.compile("\\d+$");
        Files.lines(Paths.get(filename))
                .flatMap(x -> Arrays.stream(x.split("\\s+")))
                .filter(IS_NATURAL.asPredicate())
                .collect(Collectors.groupingBy(
                        x -> x.charAt(0),
                        () -> {
                            Map<Character, Long> map = new HashMap<>();
                            for (int i = 0; i < 10; i++) {
                                map.put((char) (i + '0'), 0L);
                            }
                            return map;
                        },
                        Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(new Consumer<Map.Entry<Character, Long>>() {
                    int last = -1;

                    @Override
                    public void accept(Map.Entry<Character, Long> characterLongEntry) {
                        int current = characterLongEntry.getKey() - '0';
                        for (int i = last + 1; i < current; i++) {
                            System.out.println(0);
                        }
                        System.out.println(characterLongEntry.getValue());
                        last = current;
                    }
                });
        return null;
    }

}
