package edu.uob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Graph like data structure to hold the game entities parsed from DOT file
 */
public class GameGraph {

    private Map<String, GameGraphNode> gameGraphNodeMap;
    private Map<String, List<String>> fromNodeList;
    private Map<String, List<String>> toNodeList;
    private GameGraphNode firstNode;

    public GameGraph() {
        this.gameGraphNodeMap = new HashMap<>();
        this.fromNodeList = new HashMap<>();
        this.toNodeList = new HashMap<>();
    }

    public void addNode(GameGraphNode node) {
        if (this.gameGraphNodeMap.isEmpty()) {
            this.firstNode = node;
        }
        if (this.gameGraphNodeMap.containsKey(node.getName())) {
            System.out.println("Cannot add nodes with duplicate name: " + node.getName());
            return;
        }
        this.gameGraphNodeMap.put(node.getName(), node);
    }

    public void addPath(String node1, String node2) {
        if (!fromNodeList.containsKey(node2)) {
            fromNodeList.put(node2, new ArrayList<>());
        }
        fromNodeList.get(node2).add(node1);
        if (!toNodeList.containsKey(node1)) {
            toNodeList.put(node1, new ArrayList<>());
        }
        toNodeList.get(node1).add(node2);
        this.gameGraphNodeMap.get(node1).getLocationEntity().createPath(this.gameGraphNodeMap.get(node2).getLocationEntity());
    }

    public GameGraphNode getNode(String nodeName) {
        if (!gameGraphNodeMap.containsKey(nodeName)) {
            return null;
        }
        return this.gameGraphNodeMap.get(nodeName);
    }

    public List<GameGraphNode> getFromNodes(String nodeName) {
        if (!fromNodeList.containsKey(nodeName)) {
            return null;
        }
        List<GameGraphNode> fromNodes = new ArrayList<>();
        for (String fromNode : fromNodeList.get(nodeName)) {
            fromNodes.add(this.getNode(fromNode));
        }
        return fromNodes;
    }

    public List<GameGraphNode> getToNodes(String nodeName) {
        if (!toNodeList.containsKey(nodeName)) {
            return null;
        }
        List<GameGraphNode> toNodes = new ArrayList<>();
        for (String toNode : toNodeList.get(nodeName)) {
            toNodes.add(this.getNode(toNode));
        }
        return toNodes;
    }

    public GameGraphNode getFirstNode() {
        return this.firstNode;
    }

}