import java.util.*;
import java.io.*;

class UsageException extends Exception
{
 	private static final long serialVersionUID = 1L;

public UsageException()
   {
     super("Usage: java Main [path to input folder]\n"+
		"If path is not defined ./TN2016-CW1 is the default input folder\n"+
		"Input folder should have:\n client.csv\n nodes.csv\n taxis.csv");
   }
}

public class Main{

	private static String path;

	private static Client client;
	private static ArrayList<Taxi> taxis;
	private static ArrayList<Node> nodes;

	private static void read_data() throws UnsupportedEncodingException, IOException, UsageException{
		double x,y;
		int id;
		String name;
		String [] line;
		Scanner sc = null;
		Taxi taxi;
		Node node;

		sc=new Scanner(new File(path+"/client.csv"));
		sc.next();
		line=sc.next().split(",");
		x=Double.parseDouble(line[0]);
		y=Double.parseDouble(line[1]);
		client=new Client(x,y);
		sc.close();

		taxis = new ArrayList<Taxi>();
		sc=new Scanner(new File(path+"/taxis.csv"));
		sc.next();
		while(sc.hasNext()){
			line=sc.next().split(",");
			x=Double.parseDouble(line[0]);
			y=Double.parseDouble(line[1]);
			id=Integer.parseInt(line[2]);
			taxi = new Taxi(x,y,id);
			taxis.add(taxi);
		}
		sc.close();
		
		int i;
		nodes = new ArrayList<Node>();
//		sc=safe_open(path+"/nodes.csv", "ISO-8859-1");
//		sc.next();
		String charset = "ISO-8859-1";
		InputStreamReader isr;
		isr=new InputStreamReader (new FileInputStream(path+"/nodes.csv"), charset);

		BufferedReader in = new BufferedReader(isr);
		in.readLine();
		String ln;
		ln=in.readLine();

  	while( ln != null) { 
			line=ln.split(",");
			x=Double.parseDouble(line[0]);
			y=Double.parseDouble(line[1]);
			id=Integer.parseInt(line[2]);
			name="";
			/*	Just in case a name consists of ","		*/
			for(i=3; i<line.length;i++)
				name+=line[i];
			node=new Node(x,y,id,name);
			nodes.add(node);
			ln=in.readLine();
		}
		in.close();
	}

	private static void _read_data_() throws UsageException{
	try {	read_data();	}
	catch(UnsupportedEncodingException e){
		System.out.println(e.getMessage());
		throw new UsageException();
	}
	catch(IOException e){
		System.out.println(e.getMessage());
		throw new UsageException();
	}

	}

	public static void main(String[] args)
	{
		System.out.println(new java.io.File("").getAbsolutePath());
	try{
		if(args.length==0)
			path="./TN2016-CW1";
		else if(args.length==1){
			if (args[0].equals("-help") || args[0].equals("--help"))
				throw new UsageException();
			path=args[0];
		}
		else
			throw new UsageException();
		
		_read_data_();
		System.out.println("Client is at:");
		client.print();
		System.out.println("Taxis are at is at:");
		for(Taxi t :taxis){
			t.print();
		}
		
		client.FindClosestNode(nodes);
		System.out.println("Client closest Node is at:");
		client.closestN.print();
		System.out.println("Taxis closest Nodes are at:");
		for(Taxi t : taxis){
			t.FindClosestNode(nodes);
			t.closestN.print();
		}
		
		for(Node n : nodes){
			n.hEuclDist = n.distance(client.closestN);
		}
		
		Node.findAdj(nodes);
		
		int i=1;
		for(Taxi t: taxis){
			System.out.println();
			System.out.println("Searching path for Taxi "+i+"...");
			t.Astar = new A_star (t.closestN, client.closestN, nodes);
			t.Astar.ASearch();
			System.out.println("Creating path for Taxi "+i+"...");
			t.Astar.path();	
//			t.Astar.print_path();
			i++;
		}
		System.out.println();
		System.out.println("All done.\nLets find the best taxi...");
		Taxi t=Taxi.best(taxis);
		t.isbest=true;
		System.out.println("Best Taxi had id "+t.id);
		System.out.println();
		CreateKml();
		System.out.println("You can find the kml output file at "+path+"/map.kml");
		
	}
	catch (UsageException e){
			System.out.println(e.getMessage());
			System.exit(0);
	}
	
	}
	
	static private String Placemark(int i){
		String color=taxis.get(i).color();
				return 
				  "		<Placemark>\n"
				+ "			<name>Taxi "+i+"</name>\n"
				+ "			<styleUrl>#"+color+"</styleUrl>\n"
				+ "			<LineString>\n"
				+ "				<altitudeMode>relative</altitudeMode>\n"
				+ "				<coordinates>";
	}
	
	static private void CreateKml() throws UsageException{
		String start=
				  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "	<kml xmlns=\"http://earth.google.com/kml/2.1\">\n"
				+ "	<Document>\n"
				+ "		<name>Taxi Routes</name>\n"
				+ "		<Style id=\"green\">\n"
				+ "			<LineStyle>\n"
				+ "				<color>ff009900</color>\n"
				+ "				<width>4</width>\n"
				+ "			</LineStyle>\n"
				+ "		</Style>\n"
				+ "		<Style id=\"red\">\n"
				+ "			<LineStyle>\n"
				+ "				<color>ff0000ff</color>\n"
				+ "				<width>4</width>\n"
				+ "			</LineStyle>\n"
				+ "		</Style>";
		
		String Placemark2=
				  "				</coordinates>\n"
				+ "			</LineString>\n"
				+ "		</Placemark>";
		
		String end=
				  "	</Document>\n"
				+ "</kml>";
		
		try{
		    PrintWriter writer = new PrintWriter(path+"/map.kml", "UTF-8");
		    
		    writer.println(start);
		    for(int i=0; i<taxis.size();i++){
		    	writer.println(Placemark(i));
		    	taxis.get(i).Astar.print_path(writer,client,taxis.get(i));
		    	writer.println(Placemark2);
		    }
		    writer.println(end);
		    
		    writer.close();
		    
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw new UsageException();
		}
	}
	
}
