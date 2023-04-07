//PROG2 VT2023, Inlämningsuppgift, del 1
//Grupp 057
//Alexander Ellnestam alel6431

import java.io.Serializable;
import java.util.*;

public class ListGraph<T> implements Graph<T>, Serializable {
    private final Map<T, Set<Edge<T>>> nodes = new HashMap<>();

    @Override
    public void add(T node) {
        nodes.putIfAbsent(node, new HashSet<>());

    }

    @Override
    public void remove(T node) {

        if (!nodes.containsKey(node)) {
            throw new NoSuchElementException("The node dosen't exists");
        }

        ArrayList<T> edges = new ArrayList<T>();

        for (Edge<T> e : getEdgesFrom(node)) {
            edges.add(e.getDestination());
        }

        while (edges.size() > 0) {
            disconnect(node, edges.get(0));
            edges.remove(0);
        }

        nodes.remove(node);

    }

    @Override
    public void connect(T node1, T node2, String name, int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException();
        }

        if (!nodes.containsKey(node1)) {
            throw new NoSuchElementException("Missing node " + node1);
        }

        if (!nodes.containsKey(node2)) {
            throw new NoSuchElementException("Missing node " + node2);
        }

        Set<Edge<T>> n1Edges = nodes.get(node1);
        for (Edge<T> edge : n1Edges) {
            if (edge.getDestination().equals(node2)) {
                throw new IllegalStateException("Already connected");
            }
        }

        Set<Edge<T>> n2Edges = nodes.get(node2);
        for (Edge<T> edge : n2Edges) {
            if (edge.getDestination().equals(node1)) {
                throw new IllegalStateException("Already connected");
            }
        }

        n1Edges.add(new Edge<T>(node2, weight, name));
        n2Edges.add(new Edge<T>(node1, weight, name));

    }

    @Override
    public void disconnect(T node1, T node2) {

        if (!nodes.containsKey(node1) || !nodes.containsKey(node2)) {
            throw new NoSuchElementException();
        }

        Edge<T> edgeOne = getEdgeBetween(node1, node2);
        Edge<T> edgeTwo = getEdgeBetween(node2, node1);

        if (edgeOne == null || edgeTwo == null) {
            throw new IllegalStateException();
        }
        Set<Edge<T>> edgesFromOne = nodes.get(node1);
        Set<Edge<T>> edgesFromTwo = nodes.get(node2);

        edgesFromOne.remove(edgeOne);
        edgesFromTwo.remove(edgeTwo);
    }

    @Override
    public Edge<T> getEdgeBetween(T node1, T node2) {

        if (!nodes.containsKey(node1) || !nodes.containsKey(node2)) {
            throw new NoSuchElementException();
        }

        Set<Edge<T>> node1Edges = nodes.get(node1);
        for (Edge<T> edge : node1Edges) {
            if (edge.getDestination().equals(node2)) {
                return edge;
            }

        }
        return null;
    }

    @Override
    public void setConnectionWeight(T node1, T node2, int weight) {

        Edge<T> edgeOne = getEdgeBetween(node1, node2);
        Edge<T> edgeTwo = getEdgeBetween(node2, node1);
        if (edgeOne != null && edgeTwo != null) {

            if (weight < 0) {

                throw new IllegalArgumentException();


            } else {
                edgeOne.setWeight(weight);
                edgeTwo.setWeight(weight);
            }
        } else {
            throw new NoSuchElementException();
        }

    }

    @Override
    public Set<T> getNodes() {

        return Collections.unmodifiableSet(nodes.keySet());

    }

    @Override
    public Collection<Edge<T>> getEdgesFrom(T node) {

        if (!nodes.containsKey(node)) {
            throw new NoSuchElementException();
        }

        return Collections.unmodifiableCollection(nodes.get(node));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T node : nodes.keySet()) {
            sb.append(node).append(": ").append(nodes.get(node)).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean pathExists(T node1, T node2) {

        if (!nodes.containsKey(node1) || !nodes.containsKey(node2)) {
            return false;
        }

        Set<T> visited = new HashSet<>();
        depthFirstVisitAll(node1, visited);
        return visited.contains(node2);

    }

    private void depthFirstVisitAll(T node1, Set<T> visited) {
        visited.add(node1);
        for (Edge<T> edge : nodes.get(node1)) {
            if (!visited.contains(edge.getDestination())) {
                depthFirstVisitAll(edge.getDestination(), visited);
            }
        }
    }

    @Override
    public List<Edge<T>> getPath(T node1, T node2) {

        Map<T, T> connected = new HashMap<>();
        depthFirstConnection(node1, null, connected);
        if (!connected.containsKey(node2)) {
            return null;
        }
        return gatherPath(node1, node2, connected);
    }

    private List<Edge<T>> gatherPath(T node1, T node2, Map<T, T> connected) {

        LinkedList<Edge<T>> pathWay = new LinkedList<>();

        T temp = node2;

        while (!temp.equals(node1)) {
            T next = connected.get(temp);
            Edge<T> edge = getEdgeBetween(next, temp);
            pathWay.addFirst(edge);
            temp = next;
        }
        return Collections.unmodifiableList(pathWay);
    }

    private void depthFirstConnection(T node1, T node2, Map<T, T> connected) {

        connected.put(node1, node2);

        for (Edge<T> edge : nodes.get(node1)) {
            if (!connected.containsKey(edge.getDestination())) {
                depthFirstConnection(edge.getDestination(), node1, connected);
            }
        }
    }

}
