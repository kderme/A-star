import java.util.ArrayList;

 class Client extends Point{
	Node closestN;
	
	public Client (double a, double b){
		super(a,b);
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
}

