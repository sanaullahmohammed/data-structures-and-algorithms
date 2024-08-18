// 26 out of  30 passed

/**
 PROBLEM STATEMENT 
 
 React Developer Community is a community of React developers. It allows developers to
 reach out to others and discuss various topics around JS programming. This community
 has been modelled as a directed social network graph.
 The Nagging React newbie
 A Nagging React newbieB is constantly troubling React expert A. React Expert A needs to
 know the minimum set of people following him he needs to remove from his network to 
 stop B from reaching out to him.
 
 INPUT FORMAT
 total members in UIFriendNetwork
 memberId1
 memberId2 ........... memberIdN
 Total possible edges
 ....................
 Follower
 Following

 OUTPUT FORMAT
 Set of memberId "A" needs to block
 *The problem has been curated and verified by Team Juspay
 
 SAMPLE TESTCASE 0
 Testcase Input
 4
 2
 5
 7
 9
 4
 2 9
 7 2
 7 9
 9 5
 7
 9

 Testcase Output
 2 7
 
*/
import java.util.*;


public class Problem3 {
    static List<List<Integer>> edge;
    static List<List<Integer>> edge2;
    static int enemy, person;
    static boolean[] visited;
    static boolean[] dirty;

    // DFS function to mark reachable nodes from 'enemy'
    static void dfs(int node, int par) {
        visited[node] = true;
        for (int child : edge.get(node)) {
            if (!visited[child]) {
                dirty[child] = dirty[node] || dirty[child];
                dfs(child, node);
            }
            dirty[child] = dirty[node] || dirty[child];
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        
        int n = scanner.nextInt();  // Total members in UI Friend Network
        Map<Integer, Integer> mp = new HashMap<>(); // Mapping from node ID to index
        Map<Integer, Integer> mp2 = new HashMap<>(); // Mapping from index to node ID
        int c = 0;
        
        // Read and map member IDs
        for (int i = 0; i < n; ++i) {
            int node = scanner.nextInt();
            mp.put(node, c);
            mp2.put(c, node);
            c++;
        }
        
        int sz = c + 1;
        edge = new ArrayList<>(sz);
        edge2 = new ArrayList<>(sz);
        
        // Initialize adjacency lists
        for (int i = 0; i < sz; ++i) {
            edge.add(new ArrayList<>());
            edge2.add(new ArrayList<>());
        }
        
        visited = new boolean[sz];
        dirty = new boolean[sz];
        
        int edges = scanner.nextInt();  // Total possible edges
        
        // Read edges and build adjacency lists
        while (edges-- > 0) {
            int u1 = scanner.nextInt();
            int v1 = scanner.nextInt();
            int u = mp.get(u1);
            int v = mp.get(v1);
            edge.get(u).add(v);
            edge2.get(v).add(u);
        }
        
        int enemy1 = scanner.nextInt();  // Enemy ID
        int person1 = scanner.nextInt();  // Person ID
        enemy = mp.get(enemy1);
        person = mp.get(person1);
        
        dirty[enemy] = true;
        dfs(enemy, -1);  // Find reachable nodes from 'enemy'
        
        Set<Integer> ans = new TreeSet<>();
        
        // Collect nodes that can reach 'person'
        for (int child : edge2.get(person)) {
            if (dirty[child]) {
                int val = mp2.get(child);
                ans.add(val);
            }
        }
        
        // Output results
        if (ans.isEmpty()) {
            System.out.println("-1");
        } else {
            for (int val : ans) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
        
        scanner.close();
    }
}