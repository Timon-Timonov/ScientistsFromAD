import java.util.ArrayList;
import java.util.List;


public class Laboratory implements Comparable<Laboratory> {

	private final String factoryName;
	private Integer countRobots = 0;
	private final List<Component> stock = new ArrayList<>();

	public Laboratory(String factoryName) {
		this.factoryName = factoryName;

		Minion minionThr = new Minion(this);
		minionThr.start();

		Scientist scientistThr = new Scientist(this);
		scientistThr.start();
	}

	public String getFactoryName() {
		return factoryName;
	}

	public int getCountRobots() {
		return countRobots;
	}

	public void setCountRobots(int countRobots) {
		this.countRobots = countRobots;
	}

	public synchronized List<Component> getStock() {
		List<Component> temp = new ArrayList<>(stock);
		stock.clear();
		return temp;
	}

	public synchronized void putComponents(List<Component> components) {
		this.stock.addAll(components);

	}

	@Override
	public int compareTo(Laboratory o) {
		return this.countRobots.compareTo(o.countRobots);
	}
}
