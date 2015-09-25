import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.net2plan.interfaces.networkDesign.IAlgorithm;
import com.net2plan.interfaces.networkDesign.NetPlan;
import com.net2plan.utils.Pair;
import com.net2plan.utils.Triple;
public class NULB implements IAlgorithm{

	public String executeAlgorithm(NetPlan NetPlan, Map<String, String> arg1, Map<String, String> arg2) {
		// TODO Auto-generated method stub
		
		//System.out.println(NetPlan.getRouteAllSequenceOfLinks());
		
		Map<Long,List<Long>> newRoutes =NetPlan.getRouteAllSequenceOfLinks();
		
		
		Map<Long,List<Long>> routes = new HashMap<>();
		
		Long key = (long) 0;
		for(Long ax :newRoutes.keySet()){
			List<Long> ll = newRoutes.get(ax);
			routes.put(key,ll);
			key++;
		}
		
		Set<Long> demands = NetPlan.getDemandIds();
		
		Map<Long,List<Pair<Long,Long>>> demandsWithPath = new HashMap<>();
		
		for(Long demand : demands){ //para cada rota
			List<Long> route = routes.get(demand); //pega o caminho da rota arestas por onde passa
						
			List<Pair<Long,Long>> pairVertex = new ArrayList<Pair<Long,Long>>();
		
			for(Long link : route){  //para cada uma das arestas
				
				Pair<Long,Long> pair  = NetPlan.getLinkMap().get(link);
				
				pairVertex.add(pair);
			}
			demandsWithPath.put(demand, pairVertex);
			
		}
		
		int nNodes = NetPlan.getNumberOfNodes();
		int nDemands = NetPlan.getNumberOfDemands();
		
		int[][][] matrix = new int[nDemands][nNodes][nNodes];
		int i = 0,j,k,l;
		for(Long demand : demands){
			List<Pair<Long,Long>> vertexPairs = demandsWithPath.get(demand);
			
			
			for(Pair<Long,Long> vrt : vertexPairs){
				matrix[i][vrt.getFirst().intValue()][vrt.getSecond().intValue()] = 1;
			}
			i++;
		}
		int[][] demandMatrix = new int[nDemands][nDemands];
		for(i=0;i<nDemands-1;i++){
			for(j=i+1;j<nDemands;j++){
				for(k=0;k<nNodes;k++){
					for(l=0;l<nNodes;l++){
						if (matrix[i][k][l] == matrix[j][k][l] && matrix[i][k][l] == 1 && k!=l){
							demandMatrix[i][j] = 1;
							demandMatrix[j][i] = 1;
						}
					}
				}
			}
		}
		
		/*System.out.print("x|    ");
		for (int m = 0; m <= nNodes; m++) {
			System.out.print(""+m+" - ");
		}
		
		System.out.println("\n");
		for(i = 0;i < demandMatrix.length;i++){
			System.out.print(i+"|    ");
			for (j = 0; j < demandMatrix.length; j++) {
				System.out.print(""+demandMatrix[i][j]+" - ");
			}
			System.out.println();
		}*/

		GraphColoring gc = new GraphColoring(nDemands,demandMatrix);
		gc.graphColor(demandMatrix);
		
		return "Ok!";
	}
	
	public class GraphColoring
	{    
		
	    private int V;
	    private int[] color; 
	    private int[][] graph;
	    
	    public GraphColoring(int VC,int[][] graphC){
	    	this.V = VC;
	    	this.graph = graphC;
	    }
		 /* Function to assign color */
	    
	    public boolean graphColor(int[][] g)
	    {
	        V = g.length;
	        color = new int[V];
	        graph = g;
	 
	        try
	        {
	            solve(0);
	            System.out.println("No solution");
	            return false;
	        }
	        catch (Exception e)
	        {
	        	System.out.println(e);
	            display();
	            return true;
	        }
	        
	    }
	    /* function to assign colors recursively */
	    public void solve(int v) throws Exception
	    {
	        /* base case - solution found */
	        if (v == V)
	            throw new Exception("Solution exists!");
	        /* try all colors */
	        for (int c = 1; c <= V; c++)
	        {
	            if (isPossible(v, c))
	            {
	                /* assign and proceed with next vertex */
	                color[v] = c;
	                solve(v + 1);
	                /* wrong assignment */
	                color[v] = 0;
	            }
	        }    
	    }
	    /* function to check if it is valid to allot that color to vertex */
	    public boolean isPossible(int v, int c)
	    {
	    	//System.out.println("Possible ");
	        for (int i = 0; i < V; i++)
	            if (graph[v][i] == 1 && c == color[i])
	                return false;
	        return true;
	    }
	    /* display solution */
	    public void display()
	    {
	        System.out.print("\nDemanda -  Lambda \n");
	        int maior = -1;
	        for (int i = 0; i < V; i++){
	        	if(color[i] > maior) maior = color[i];
	            System.out.print(" "+i+"\t   "+color[i] +"\n");
	        }
	        System.out.println("\nNumero de comprimentos de ondas: "+maior);
	    }
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Triple<String, String, String>> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

}
