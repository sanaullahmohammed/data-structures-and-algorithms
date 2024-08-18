// All tests have passed


/*
    React Developer Community is a community of React developers. It allows developers to 
    reach out to others and discuss various topics around JS programming. This community 
    has been modelled as a directed social network graph. 
    Find Reachability 
    JS newbie A wants to check if he can reach out to a React expert B using his network. 
 
    Input Format:
    ------------
    * total members in React Developer Community 
    * memberId1 
    * memberId2 
    * ................ 
    * memberIdN 
    * Total possible edges 
    * Edge1
    * .................. 
    * EdgeN
    * Follower 
    * Following 
    
    Output Format:
    -------------
    “0” OR “1” 
    *The problem has been curated and verified by Team Juspay. 
    
    Sample Testcase 0:
    -----------------
    
    * Testcase Input:
      --------------
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
    
    * Testcase Output:
      ---------------
        1
 */

 import java.util.HashMap;
 import java.util.LinkedList;
 import java.util.List;
 import java.util.Scanner;
 
 class Problem1 {
   private int follower;
   private int following;
   private HashMap<Integer, LinkedList<Integer>> adjListMap;
   private HashMap<Integer, Boolean> visitedMap;
 
   private boolean dfs(int follower, int following) {
     if (follower == following) {
       return true;
     }
 
     this.visitedMap.put(follower, true);

     if (!this.adjListMap.containsKey(follower)) {
      return false; // If the node has no outgoing edges
    }
 
     for (var node : this.adjListMap.get(follower)) {
       if (!this.visitedMap.get(node)) {
         if (dfs(node, following)) {
           return true;
         }
       }
     }
 
     return false;
   }
 
   public int solve() {
     return dfs(this.follower, this.following) == true ? 1 : 0;
   }
 
   public Problem1(int follower, int following, HashMap<Integer, LinkedList<Integer>> adjListMap) {
     this.follower = follower;
     this.following = following;
     this.adjListMap = adjListMap;
     this.visitedMap = new HashMap<Integer, Boolean>();
 
     for (var node : adjListMap.keySet()) {
       this.visitedMap.put(node, false);
     }
   }
 
   public static void main(String args[]) {
     var sc = new Scanner(System.in);
 
     var membersCnt = sc.nextInt();
 
     var members = new int[membersCnt];
 
     for (int i = 0; i < membersCnt; i++) {
       members[i] = sc.nextInt();
     }
 
     var edgesCnt = sc.nextInt();
 
     var adjListMap = new HashMap<Integer, LinkedList<Integer>>(edgesCnt);
 
     for (int i = 0; i < edgesCnt; i++) {
       var node1 = sc.nextInt();
       var node2 = sc.nextInt();
 
       if (adjListMap.containsKey(node1)) {
         adjListMap.get(node1).add(node2);
       } else {
         adjListMap.put(node1, new LinkedList<>(List.of(node2)));
       }
     }
 
     var follower = sc.nextInt();
     var following = sc.nextInt();
 
     sc.close();
 
     var problem1 = new Problem1(follower, following, adjListMap);
     var result = problem1.solve();
 
     System.out.println(result);
   }
 }