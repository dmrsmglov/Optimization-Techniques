import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Dijkstra {
    private Map<Integer, List<EdgeDirection>> listOfEdges;
    private List<Integer> paths;
    private int n;

    Dijkstra(Map<Integer, List<EdgeDirection>> listOfEdges, int n) {
        this.listOfEdges = listOfEdges;
        this.n = n;
        paths = Stream.concat(Stream.of(0), Stream.generate(() -> Integer.MAX_VALUE / 2).limit(n - 1))
                .collect(Collectors.toList());
    }
    void findBestPaths() {
        List<Boolean> vertexIsVisited = Stream.generate(() -> false)
                .limit(n)
                .collect(Collectors.toList());
        for (int k = 0; k < n; ++k) {
            Integer v = -1;
            for (int i = 0; i < n; ++i) {
                if (!vertexIsVisited.get(i) && (v < 0 || paths.get(i) < paths.get(v))) {
                    v = i;
                }
            }
            vertexIsVisited.set(v, true);
            List<EdgeDirection> edgesFromCurrentVertex = listOfEdges.get(v + 1);
            if (edgesFromCurrentVertex != null) {
                for (EdgeDirection edge : edgesFromCurrentVertex) {
                    Integer dst = edge.getDirection();
                    Integer weight = edge.getWeight();
                    if (paths.get(dst - 1) > paths.get(v) + weight) {
                        paths.set(dst - 1, paths.get(v) + weight);
                    }
                }
            }
        }
    }
    List<Integer> getPaths(){
        return paths;
    }
}
