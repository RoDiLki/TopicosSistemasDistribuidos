import java.util.List;
import java.util.Map;

import com.net2plan.interfaces.networkDesign.IAlgorithm;
import com.net2plan.interfaces.networkDesign.NetPlan;
import com.net2plan.utils.Pair;
import com.net2plan.utils.Triple;


public class Main implements IAlgorithm {

	@Override
	public String executeAlgorithm(NetPlan netPlan, Map<String, String> algParams,
			Map<String, String> net2PlanParams) {
		Map<Long, Pair<Long, Long>> m = netPlan.getLinkMap();
		for (Long l : m.keySet()) {
			System.out.println("key=" + l);
			System.out.println("map " + m.get(l).getFirst() + " - " + m.get(l).getSecond());
			System.out.println();
		}
		return "aaaa";
	}

	@Override
	public String getDescription() {
		return "Bonassi-Secchi Algorithm";
	}

	@Override
	public List<Triple<String, String, String>> getParameters() {
		return null;
	}

}
