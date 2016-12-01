import java.util.ArrayList;

public class Taxi extends Point{
	public int id;
	Node closestN;

	Taxi(float a, float b, int d){
		super(a,b);
		d=id;
	}
	
	void FindClosestNode(ArrayList<Node> Nodes){
		float tempmin;
		float min = this.distance(Nodes.get(0));
		closestN=Nodes.get(0);
		for (Node node : Nodes) {
			if ((tempmin=this.distance(node))<min){
				min=tempmin;
				closestN=node;
			}
		}
		
		closestN.isStart=true;
	}
}

