package timemoney;
import java.util.HashMap;
import java.util.Map;

public class Configs {
	private Map<String, CustomConfig> sfg = new HashMap<String, CustomConfig>();

	public CustomConfig get(String name) {
		if (sfg.get(name) == null)
			add(name);
		return sfg.get(name);
	}

	public Map<String, CustomConfig> getConfigs() {
		return sfg;
	}

	public void add(String name) {
		CustomConfig custom = new CustomConfig(name);
		sfg.put(name, custom);
	}

	public void save(String name) {
		if (sfg.get(name) == null)
			add(name);
		try {
			sfg.get(name).save();
			sfg.remove(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveAll() {
		for (String name : sfg.keySet()) {
			sfg.get(name).save();
		}
	}
}