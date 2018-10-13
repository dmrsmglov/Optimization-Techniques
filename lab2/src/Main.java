import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;
import java.util.*;
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("ListOfEdges.txt"));
        Map<Integer, List<EdgeDirection>> listOfEdges = new HashMap<>();
        Set<Integer> numberOfVertexes = new HashSet<>();
        while (scanner.hasNext()) {
            int src, dst, weight;
            src = scanner.nextInt();
            dst = scanner.nextInt();
            numberOfVertexes.add(src);
            numberOfVertexes.add(dst);
            weight = scanner.nextInt();
            List<EdgeDirection> list;
            if (listOfEdges.containsKey(src)) {
                list = listOfEdges.get(src);
                list.add(new EdgeDirection(dst, weight));
            }
            else {
                list = new LinkedList<>();
                list.add(new EdgeDirection(dst, weight));
            }
            listOfEdges.put(src, list);
        }
        Vector<Integer> paths = Dijkstra.findBestPaths(listOfEdges, numberOfVertexes.size());
        for (int i = 0; i < paths.size(); ++i) {
            System.out.println((i + 1) + " " + paths.get(i));
        }
    }
}