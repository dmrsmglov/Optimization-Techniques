
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;
import java.util.*;

public class Main {
    static class Pair{
        Integer first, second;

        Pair(Integer first, Integer second) {
            this.first = first;
            this.second = second;
        }
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("ListOfEdges.txt"));
        Map<Integer, List<Pair>> listOfEdges = new HashMap<>();
        int n = 0;
        Set<Integer> numberOfVertexes = new HashSet<>();
        while (scanner.hasNext()) {
            int src, dst, weight;
            src = scanner.nextInt();
            dst = scanner.nextInt();
            numberOfVertexes.add(src);
            numberOfVertexes.add(dst);
            weight = scanner.nextInt();
            List<Pair> list;
            if (listOfEdges.containsKey(src)) {
                list = listOfEdges.get(src);
                list.add(new Pair(dst, weight));
            }
            else {
                list = new LinkedList<>();
                list.add(new Pair(dst, weight));
            }
            listOfEdges.put(src, list);
        }
        n = numberOfVertexes.size();
        Vector<Integer> paths = new Vector<>();
        paths.setSize(n);
        Vector<Boolean> vertexIsVisited = new Vector<>();
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
            List<Pair> edgesFromCurrentVertex = listOfEdges.get(v + 1);
            if (edgesFromCurrentVertex != null) {
                for (Pair edge : edgesFromCurrentVertex) {
                    Integer dst = edge.first;
                    Integer weight = edge.second;
                    if (paths.get(dst - 1) > paths.get(v) + weight) {
                        paths.set(dst - 1, paths.get(v) + weight);
                    }
                }
            }
        }
        for (int i = 0; i < n; ++i) {
            System.out.println((i + 1) + " " + paths.get(i));
        }
    }
}
