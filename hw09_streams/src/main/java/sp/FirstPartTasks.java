package sp;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class FirstPartTasks {

    private FirstPartTasks() {}

    // Список названий альбомов
    public static List<String> allNames(Stream<Album> albums) {
        return albums
                .map(Album::getName)
                .collect(Collectors.toList());
    }

    // Список названий альбомов, отсортированный лексикографически по названию
    public static List<String> allNamesSorted(Stream<Album> albums) {
        return albums
                .map(Album::getName)
                .sorted()
                .collect(Collectors.toList());
    }

    // Список треков, отсортированный лексикографически по названию, включающий все треки альбомов из 'albums'
    public static List<String> allTracksSorted(Stream<Album> albums) {
        return albums
                .flatMap(x -> x.getTracks().stream())
                .map(Track::getName)
                .sorted()
                .collect(Collectors.toList());
    }

    // Список альбомов, в которых есть хотя бы один трек с рейтингом более 95, отсортированный по названию
    public static List<Album> sortedFavorites(Stream<Album> albums) {
        return albums
                .filter(album -> {
                    Track track = album.getTracks().stream().reduce(null, new BinaryOperator<Track>() {
                        @Override
                        public Track apply(Track track, Track track2) {
                            return track2.getRating() > 95 ? track2 : track;
                        }
                    });
                    return track != null && track.getRating() > 95;
                })
                .sorted(Comparator.comparing(Album::getName))
                .collect(Collectors.toList());
    }

    // Сгруппировать альбомы по артистам
    public static Map<Artist, List<Album>> groupByArtist(Stream<Album> albums) {
        return albums
                .collect(Collectors.groupingBy(Album::getArtist));
    }

    // Сгруппировать альбомы по артистам (в качестве значения вместо объекта 'sp.Artist' использовать его имя)
    public static Map<Artist, List<String>> groupByArtistMapName(Stream<Album> albums) {
        return albums
                .collect(Collectors.groupingBy(Album::getArtist,
                        Collectors.mapping(
                                Album::getName,
                                Collectors.toList()
                        )));
    }

    // Число повторяющихся альбомов в потоке
    public static long countAlbumDuplicates(Stream<Album> albums) {
        HashSet<Album> hashSet = new HashSet<>();
        return albums
                .filter(album -> !hashSet.add(album))
                .count();
    }

    public static Optional<Album> minMaxRating(Stream<Album> albums) {
        return albums.min(Comparator.comparingInt(
                (Album album) -> album
                        .getTracks()
                        .stream()
                        .max(Comparator.comparingInt(Track::getRating))
                        .map(Track::getRating)
                        .orElse(0)));
    }

    // Список альбомов, отсортированный по убыванию среднего рейтинга его треков (0, если треков нет)
    public static List<Album> sortByAverageRating(Stream<Album> albums) {
        return albums.sorted(Comparator.comparingDouble((Album album) -> album
                        .getTracks()
                        .stream()
                        .collect(Collectors.averagingInt(Track::getRating))).reversed())
                .collect(Collectors.toList());
    }

    // Произведение всех чисел потока по модулю 'modulo'
    // (все числа от 0 до 10000)
    public static int moduloProduction(IntStream stream, int modulo) {
        return stream.reduce(1, (a, b) -> (int)((long)a * b % modulo));
    }

    // Вернуть строку, состояющую из конкатенаций переданного массива, и окруженную строками "<", ">"
    // см. тесты
    public static String joinTo(String... strings) {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(strings));
        return list.stream().collect(Collectors.joining(", ", "<", ">"));
    }

    // Вернуть поток из объектов класса 'clazz'
    public static <R> Stream<R> filterIsInstance(Stream<?> s, Class<R> clazz) {
        return s.filter(clazz::isInstance).map(clazz::cast);
    }
}