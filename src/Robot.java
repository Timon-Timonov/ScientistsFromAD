import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Robot {

	private int countParts = 0;
	private final Map<Component, Boolean> components = new HashMap<>();

	public Robot() {
		Arrays.stream(Component.values()).forEach(comp -> components.put(comp, false));
	}

	public void addOneToCountParts() {
		countParts++;
	}

	public int getCountParts() {
		return countParts;
	}

	public Map<Component, Boolean> getComponents() {
		return components;
	}
}
