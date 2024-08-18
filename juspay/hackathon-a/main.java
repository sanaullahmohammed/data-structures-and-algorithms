import java.util.*;

class Lock {
  boolean isLocked;
  Integer owner;

  Lock(boolean isLocked, Integer owner) {
    this.isLocked = isLocked;
    this.owner = owner;
  }
}

class Tree {
  String nodeName;
  String parentNodeName = null;
  HashMap<String, Lock> lockedDescendants = new HashMap<String, Lock>();
  int lockedDescendantsSize = 0;
  Lock lock = new Lock(false, null);

  Tree(String nodeName) {
    this.nodeName = nodeName;
  }
}

class Query {
  int operationType;
  String nodeName;
  int userId;

  Query(int operationType, String nodeName, int userId) {
    this.operationType = operationType;
    this.nodeName = nodeName;
    this.userId = userId;
  }
}

class Solve {
  HashMap<String, Tree> mAryTree;
  Query[] queries;

  private void updateAncestorsOnUnlock(Tree node) {
    String parentNodeName = node.parentNodeName;

    while(parentNodeName != null) {
      Tree parentNode = mAryTree.get(parentNodeName);

      parentNode.lockedDescendants.remove(node.nodeName);
      parentNode.lockedDescendantsSize--;

      parentNodeName = parentNode.parentNodeName;
    }
  }

  private void updateAncestorsOnLock(Tree node) {
    String parentNodeName = node.parentNodeName;

    while(parentNodeName != null) {
      Tree parentNode = mAryTree.get(parentNodeName);

      parentNode.lockedDescendants.put(node.nodeName, node.lock);
      parentNode.lockedDescendantsSize++;

      parentNodeName = parentNode.parentNodeName;
    }
  }

  private boolean hasLockedAncestor(Tree node) {
    String parentNodeName = node.parentNodeName;

    while(parentNodeName != null) {
      Tree parentNode = mAryTree.get(parentNodeName);

      if(parentNode.lock.isLocked) {
        return true;
      }

      parentNodeName = parentNode.parentNodeName;
    }

    return false;
  }

  boolean upgrade(String nodeName, int userId) {
    Tree node = mAryTree.get(nodeName);

    if(node.lock.isLocked || node.lockedDescendantsSize == 0) {
      return false;
    }

    for (Lock lockedDescendantNodeLock: node.lockedDescendants.values()) {
      if(lockedDescendantNodeLock.owner != userId) {
        return false;
      }
    }

    for (String lockedDescendantNodeName: new ArrayList<>(node.lockedDescendants.keySet())) {
      unlock(lockedDescendantNodeName, userId);
    }

    lock(nodeName, userId);

    return true;
  }

  boolean unlock(String nodeName, int userId) {
    Tree node = mAryTree.get(nodeName);

    if(!node.lock.isLocked || node.lock.owner != userId) {
      return false;
    }

    node.lock.isLocked = false;
    node.lock.owner = null;

    updateAncestorsOnUnlock(node);

    return true;
  }

  boolean lock(String nodeName, int userId) {
    Tree node = mAryTree.get(nodeName);

    if(node.lock.isLocked || node.lockedDescendantsSize != 0) {
      return false;
    }

    if(hasLockedAncestor(node)) {
      return false;
    }

    node.lock.isLocked = true;
    node.lock.owner = userId;

    updateAncestorsOnLock(node);
    
    return true;
  }

  void solution() {
    for(Query query: queries) {
      int queryOperationType = query.operationType;
      String queryNodeName = query.nodeName;
      int userId = query.userId;

      boolean result;

      if(queryOperationType == 1) {
        result = lock(queryNodeName, userId);
      } else if (queryOperationType == 2) {
        result = unlock(queryNodeName, userId);
      } else {
        result = upgrade(queryNodeName, userId);
      }

      System.out.println(result);
    }
  }

  Solve(HashMap<String, Tree> mAryTree, Query[] queries) {
    this.mAryTree = mAryTree;
    this.queries = queries;
  }
}

class TestClass {
  public static void main(String args[]) throws Exception {
    int N;
    int m;
    int Q;

    Scanner sc = new Scanner(System.in);

    N = sc.nextInt();
    m = sc.nextInt();
    Q = sc.nextInt();

    sc.nextLine(); // consume new line character after integers

    String[] nodeNames = new String[N];
    HashMap<String, Tree> treeMap = new HashMap<String, Tree>(N);
    Query[] queries = new Query[Q];

    for(int i = 0; i < N; i++) {
      nodeNames[i] = sc.nextLine();
    }

    for(String nodeName: nodeNames) {
      Tree tree = new Tree(nodeName);
      treeMap.put(nodeName, tree);
    }

    for(int i = 0; i < N; i++) {
      String nodeName = nodeNames[i];

      for(int j = 0; j < m; j++) {
        int childIdx = (m * i) + j + 1;

        if(childIdx >= N ) {
          break;
        }

        String childNodeName = nodeNames[childIdx];
        Tree child = treeMap.get(childNodeName);
        child.parentNodeName = nodeName;
      }
    }

    for (int i = 0; i < Q; i++) {
      String[] queryItems = sc.nextLine().split(" ");
      queries[i] = new Query(Integer.parseInt(queryItems[0]), queryItems[1], Integer.parseInt(queryItems[2]));
    }

    sc.close();

    Solve solve = new Solve(treeMap, queries);
    solve.solution();
  }
}