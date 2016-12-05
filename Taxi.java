import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Taxi extends Point{
	public int id;
	Node closestN;
	boolean isbest;

	A_star Astar;

	Taxi(double a, double b, int d){
		super(a,b);
		id=d;
		isbest=false;
	}
	
	void FindClosestNode(ArrayList<Node> Nodes){
		double tempmin;
		double min = this.distance(Nodes.get(0));
		closestN=Nodes.get(0);
		for (Node node : Nodes) {
			if ((tempmin=this.distance(node))<min){
				min=tempmin;
				closestN=node;
			}
		}
	}
	
	
	static class distComparator implements Comparator<Taxi>
	 {
		public int compare(Taxi one, Taxi two){
			if(one.Astar.path_length-two.Astar.path_length>0)
				return 1;
			else if(one.Astar.path_length-two.Astar.path_length<0)
				return -1;			
			return 0;
		}
	 }
	
	public String color(){
		return (isbest)?"green":"red";
	}
	
	 

	public static Taxi best(ArrayList<Taxi> Taxis){
		return Collections.min(Taxis,new distComparator());
		
	}

}

