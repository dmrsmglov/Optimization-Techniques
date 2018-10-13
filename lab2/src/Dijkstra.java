import java.util.List;
import java.util.Map;
import java.util.Vector;

class Dijkstra {

    static Vector<Integer> findBestPaths(Map<Integer, List<EdgeDirection>> listOfEdges, Integer n) {
        Vector<Integer> paths = new Vector<>();
        Vector<Boolean> vertexIsVisited = new Vector<>();

        paths.setSize(n);
        vertexIsVisited.setSize(n);
        paths.set(0, 0);
        vertexIsVisited.set(0, false);
        for (int i = 1; i < n; ++i) {
            paths.set(i, Integer.MAX_VALUE / 2);
            vertexIsVisited.set(i, false);
        }
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
        return paths;
    }
}
