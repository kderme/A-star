import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class A_star{
	private Node start,target;
	private static ArrayList<Node> nodes;
	
	static private HashSet<Node> closedSet;
	static private PriorityQueue<Node> openQ;
	
	public static void ASearch(){
		
		while(!openQ.isEmpty()){
			Node current = openQ.poll();
			closedSet.add(current);
			if(current.isTarget){
				/*	FOUND	*/
				return;
			}
			for(Node neighbor : current.adj){
				if (closedSet.contains(neighbor))
					continue;
				float tentative_gScore = current.gScore + current.distance(neighbor);
				if(!openQ.contains(neighbor))
					openQ.add(neighbor);
				else if (tentative_gScore >= neighbor.gScore)
					continue;					
				neighbor.parent=current;
				neighbor.gScore=tentative_gScore;
				neighbor.fScore=neighbor.gScore+neighbor.EuclDist;
			}
			
		}
	}
	
	private void init_nodes_distances(){

		for(Node n : nodes){
			n.EuclDist = n.distance(start);
			n.fScore = Float.MAX_VALUE;
			n.gScore = Float.MAX_VALUE;
		}
		start.fScore=start.EuclDist;
		
	}	
	
	public A_star(Node s, Node t, ArrayList<Node> ns){
		start=s;
		target=t;
		nodes=ns;
	
		closedSet = new HashSet<Node>();
		openQ = new PriorityQueue<Node>();
	
		openQ.add(start);

		init_nodes_distances();
	}
}

