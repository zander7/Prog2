//PROG2 VT2023, Inlämningsuppgift, del 1
//Grupp 057
//Alexander Ellnestam alel6431

import java.io.Serializable;

public class Edge<T> implements Serializable {
    private T destination;
    private int weight;
    private String name;

    public Edge(T destination, int weight, String name){
        this.destination = destination;
        this.weight = weight;
        this.name = name;
    }
    public T getDestination(){
        return destination;
    }

    public int getWeight(){
        return weight;
    }

    public int setWeight(int newWeight){
        weight = newWeight;
        return weight;
    }

    public String getName(){
        return name;
    }

    public String toString(){
        return "till " + destination.toString() + " med " + name + " tar " + weight;
    }
}
