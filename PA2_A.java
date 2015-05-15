import java.util.*;
import java.lang.*;
import java.io.*;

/*

@author Nicholas Low A0110574N

PROBLEM STATEMENT: GIVEN A GRAPH G, AND A LIST P OF VERTICES. YOU HAVE TO CHECK WHETHER P FORMS AN UNDIRECTED
HAMILTONIAN CYCLE IN G.

Definition of a HAMILTONIAN CYCLE: A path that visits each node exactly once (other than start/end vertex) 

*/

public class PA2_A {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out))); 
        
        int T, N, M, P; //T is the number of test cases, N is the number of vertices involved in edges given, P is number of veritces in path
		String U, V, W, first, second; //U is first Vertex in Edge, V is second Vertex in Edge, W is the respective names of Vertices
		String answer;
		//ArrayList<Edge> EdgeList;
		ArrayList<String> Path;
		HashMap<String, ArrayList<String>> edgeList;
        T = sc.nextInt();
		
		//EdgeList = new ArrayList<Edge>();

        for (int i = 1; i <= T; ++i) {
        	edgeList = new HashMap<String, ArrayList<String>>(30001);
            N = sc.nextInt();
			M = sc.nextInt();
			sc.nextLine();

			for(int a = 0; a < M; a++) { //adding Edges to EdgeList
				first = sc.next();
				second = sc.next();
				//System.out.println(first);
				//System.out.println(second);
				if(!edgeList.containsKey(first)) {
					ArrayList<String> temp = new ArrayList<String>();
					temp.add(second);
					edgeList.put(first, temp); 
					
				} else {
					ArrayList<String> temp = new ArrayList<String>();
					temp = edgeList.get(first);
					temp.add(second);
					Collections.sort(temp, String.CASE_INSENSITIVE_ORDER);
					edgeList.put(first, temp);
				}
				/*EdgeList.add(new Edge(first, second));
				EdgeList.add(new Edge(second, first));*/
				/*
				Collections.sort(EdgeList, new Comparator<Edge>() {
					public int compare(Edge t1,Edge t2) {
						String x1 = t1.getFirst();
						String x2 = t2.getFirst();
						int sComp = x1.compareTo(x2);
						if(sComp != 0) {
							return sComp;
						} else {
							String x3 = t1.getSecond();
							String x4 = t2.getSecond();
							return x3.compareTo(x4);
						}
					} 
				});*/
			}

			P = sc.nextInt();

			Path = new ArrayList<String>();
			for(int b = 0; b < P; b++) {
				Path.add(sc.next());
			}
			//printArrayString(Path);

			//solution here
			answer = hamiltonianVerifier(Path, edgeList, N);
			pw.write(answer);
			pw.write("\n");
        }
        pw.close();
    }

    public static String hamiltonianVerifier(ArrayList<String> givenPath, HashMap<String, ArrayList<String>> edges, int noOfVerticesInGraph) {
    	String visitingNode;
    	String firstVertex = givenPath.get(0);
    	String lastVertex = givenPath.get(givenPath.size() -1);
    	ArrayList<String> visitedNodes = new ArrayList<String>();
    	int firstVertexCounter = 0;
    	//first: check if first vertex = last vertex
    	if(!firstVertex.equals(lastVertex)) {
    		System.out.println(firstVertex);
    		System.out.println(lastVertex);
    		return "NO1";
    	}
    	for(int a = 0; a < givenPath.size(); a++) {

    		if(a > 0) { //check if visitingNode has been visited before
    			visitingNode = givenPath.get(a);
    			int index = Collections.binarySearch(visitedNodes, visitingNode);

    			//System.out.println(index);
    			if(index >= 0) {
    				return "NO2";
    			}
    			visitedNodes.add(givenPath.get(a));
    			Collections.sort(visitedNodes, String.CASE_INSENSITIVE_ORDER);
    		}
    		if(a < givenPath.size() - 1) { 
    			//Edge temp = new Edge(givenPath.get(a), givenPath.get(a + 1));
    			if(edges.containsKey(givenPath.get(a))) {
    				int index = Collections.binarySearch(edges.get(givenPath.get(a)), givenPath.get(a+1));
    				if(index < 0) {
    					return "NO3";
    				}
    			} else if(edges.containsKey(givenPath.get(a+1))) {
    				int index = Collections.binarySearch(edges.get(givenPath.get(a+1)), givenPath.get(a));
    				if(index < 0) {
    					return "NO4";
    				}
    			} else {
    				return "NO5";
    			}
    		}
    	}
    	if(visitedNodes.size() != noOfVerticesInGraph) { //check if all vertices in graph have been visited
    			return "NO";
    	}
    	return "YES";
    }

    /* Program Verifiers */
    public static void printArrayString(ArrayList<String> a) {
    	String output = "";
    	for(int i = 0; i < a.size(); i++) {
    		output += a.get(i) + " ";
    	}
    	System.out.println(output);
    }

}

class Edge {
	String first;
	String second;

	public Edge(String f, String s) {
		first = f;
		second = s;
	}

	public String getFirst() {
		return first;
	}

	public String getSecond() {
		return second;
	}

	public void setFirst(String f) {
		first = f;
	}

	public void setSecond(String s) {
		second = s;
	}

	//Overriding equals() method for contains() in main
	public boolean equals(Object obj) {
		if (obj instanceof Edge) {
			Edge edges = (Edge) obj;
			return this.getFirst().equals(edges.getFirst())
			        && this.getSecond().equals(edges.getSecond());
		}
		else {
			return false;
		}
	}
}