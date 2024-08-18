
// 26 passed out of 30

/**
 PROBLEM STATEMENT 2
 
 React Developer Community is a community of React developers. It allows developers to
 reach out to others and discuss various topics around JS programming. This community
 has been modelled as a directed social network graph.
 Learn JS
 JS newbie A wants to learn React from B and wants to know in his network who can
 introduce him to B in the shortest time period
 
 INPUT FORMAT
 Total members in UIFriendNetwork
 memberId1
 memberId2 
 ............. 
 memberIdN
 Total possible edges
 <Node 1> <Node 2> <Time>
 ....................
 Follower(Ninja A)
 Following(JS expert B)

 OUTPUT FORMAT
 Shortest Time A takes to reach B
 *The problem has been curated and verified by Team Juspay
 
 SAMPLE TESTCASE 0
 Testcase Input
 4
 2
 5
 7
 9
 4
 2 9 2
 7 2 3
 7 9 7
 9 5 1
 7 9

 Testcase Output
 5
 
*/

import java.util.*;

class Edge {
    int targetNode;
    int weight;

    Edge(int targetNode, int weight) {
        this.targetNode = targetNode;
        this.weight = weight;
    }
}

public class Problem2 {
    private HashMap<Integer, List<Edge>> adjListMap;
    private PriorityQueue<Edge> pq;
    private int[] members;
    private int follower;
    private int following;

    private HashMap<Integer, Integer> dijkstra() {
        HashMap<Integer, Integer> shortestTimeMap = new HashMap<>();
        for (int member : members) {
            shortestTimeMap.put(member, Integer.MAX_VALUE);
        }

        pq.offer(new Edge(follower, 0));
        shortestTimeMap.put(follower, 0);

        while (!pq.isEmpty()) {
            Edge currentEdge = pq.poll();

            if (currentEdge.targetNode == following) {
                // If we reach the target node, return the shortest time to it
                return shortestTimeMap;
            }

            if (currentEdge.weight > shortestTimeMap.get(currentEdge.targetNode)) {
                // If we found a longer path to this node, skip it
                continue;
            }

            // Explore neighbors
            List<Edge> neighbors = adjListMap.getOrDefault(currentEdge.targetNode, new ArrayList<>());
            for (Edge edge : neighbors) {
                int newTime = currentEdge.weight + edge.weight;
                if (newTime < shortestTimeMap.get(edge.targetNode)) {
                    shortestTimeMap.put(edge.targetNode, newTime);
                    pq.offer(new Edge(edge.targetNode, newTime));
                }
            }
        }

        return shortestTimeMap;
    }

    public int solve() {
        HashMap<Integer, Integer> shortestTimeMap = dijkstra();
        return shortestTimeMap.getOrDefault(following, -1); // Return shortest time to following or -1 if unreachable
    }

    public Problem2(int follower, int following, int[] members, HashMap<Integer, List<Edge>> adjListMap) {
        this.follower = follower;
        this.following = following;
        this.members = members;
        this.adjListMap = adjListMap;
        this.pq = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        int membersCnt = sc.nextInt();
        int[] members = new int[membersCnt];
        for (int i = 0; i < membersCnt; i++) {
            members[i] = sc.nextInt();
        }

        int edgesCnt = sc.nextInt();
        HashMap<Integer, List<Edge>> adjListMap = new HashMap<>();
        for (int i = 0; i < edgesCnt; i++) {
            int node1 = sc.nextInt();
            int node2 = sc.nextInt();
            int weight = sc.nextInt();

            // Add edges both ways since it's an undirected graph
            adjListMap.computeIfAbsent(node1, k -> new ArrayList<>()).add(new Edge(node2, weight));
            adjListMap.computeIfAbsent(node2, k -> new ArrayList<>()).add(new Edge(node1, weight));
        }

        int follower = sc.nextInt();
        int following = sc.nextInt();

        sc.close();

        Problem2 problem2 = new Problem2(follower, following, members, adjListMap);
        int result = problem2.solve();
        System.out.println(result);
    }
}
