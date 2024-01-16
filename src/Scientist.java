import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Scientist extends Thread {

	private int robotCreatedCounter = 0;
	private final List<Robot> robotsQueue = new ArrayList<>();
	private final Laboratory linkedLaboratory;

	public Scientist(Laboratory laboratory) {
		this.linkedLaboratory = laboratory;
	}

	@Override
	public void run() {
		this.setName(linkedLaboratory.getFactoryName() + " scientistThread");
		//System.out.println(Thread.currentThread().getName() + " start.");
		JunkYard junkYardThr = JunkYard.getInstance();
		robotsQueue.add(new Robot());

		while (junkYardThr.getState() != State.TERMINATED) {

			try {
				Thread.sleep(ConstValues.DAY_TIME_SWAP_TIME);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

			int iter = 0;
			List<Component> components = new ArrayList<>(linkedLaboratory.getStock());

			for (Component component : components) {

				while (true) {

					try {

						if (robotsQueue.get(iter).getCountParts() != Component.values().length) {
							Map<Component, Boolean> map = robotsQueue.get(iter).getComponents();
							if (!map.get(component)) {
								map.put(component, true);
								robotsQueue.get(iter).addOneToCountParts();
								break;
							} else iter++;
						} else {
							robotCreatedCounter++;
							robotsQueue.remove(iter);
						}
					} catch (IndexOutOfBoundsException e) {
						robotsQueue.add(new Robot());
					}
				}
				iter = 0;
			}
			linkedLaboratory.setCountRobots(robotCreatedCounter);
		}
		//System.out.println(Thread.currentThread().getName() + " finish.");
	}
}
