package nz.ac.auckland.se281;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Collections;


public class Graph {
  private Map<String, List<String>> adjList;

  public Graph() {
    this.adjList = new HashMap<>();
  }

  public void addEdge(String country1, String country2) {
    adjList.putIfAbsent(country1, new LinkedList<>());
    adjList.putIfAbsent(country2, new LinkedList<>());
    adjList.get(country1).add(country2);
    adjList.get(country2).add(country1);
  }

  
}
