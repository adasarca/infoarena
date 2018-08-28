package kosaraju; /**
 * Created by Ada.Sarca
 * Date: 8/28/2018
 */

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        LinkedList<Integer>[] graph;
        int n, m;

        //read
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileInputStream("ctc.in"));
            n = scanner.nextInt();
            graph = new LinkedList[n + 1];
            for (int i = 1; i <= n; i++) {
                graph[i] = new LinkedList<>();
            }

            m = scanner.nextInt();
            for (int k = 0; k < m; k++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                graph[x].add(y);
            }
        } catch (Exception exception) {
            System.out.println(exception);
            return;
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        //compute
        LinkedList<Integer> order = new LinkedList<>();
        boolean[] visited = new boolean[n + 1];
        for (int node = 1; node <= n; node++) {
            if (!visited[node]) {
                dfs(graph, node, visited, order);
            }
        }

        LinkedList<Integer>[] transpose = transpose(graph);

        LinkedList<LinkedList<Integer>> scc = new LinkedList<>();
        Arrays.fill(visited, false);
        while(!order.isEmpty()) {
            int node = order.pop();
            if (!visited[node]) {
                LinkedList<Integer> component = new LinkedList<>();
                dfs(transpose, node, visited, component);
                scc.add(component);
            }
        }

        //print
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter("ctc.out");
            printWriter.println(scc.size());
            for (LinkedList<Integer> component : scc) {
                for (Integer node : component) {
                    printWriter.print(node + " ");
                }
                printWriter.println();
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }

    private static void dfs(LinkedList<Integer>[] graph, int start, boolean[] visited, LinkedList<Integer> output) {
        visited[start] = true;

        for (int node : graph[start]) {
            if (!visited[node]) {
                dfs(graph, node, visited, output);
            }
        }
        output.push(start);
    }

    private static LinkedList<Integer>[] transpose(LinkedList<Integer>[] graph) {
        LinkedList<Integer>[] transpose = new LinkedList[graph.length];
        for (int i = 1; i < graph.length; i++) {
            transpose[i] = new LinkedList<>();
        }

        for (int i = 1; i < graph.length; i++) {
            for (int j : graph[i]) {
                transpose[j].add(i);
            }
        }
        return transpose;
    }
}
