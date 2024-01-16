import java.util.ArrayList;
import java.util.List;

public class Scientist extends Thread {

	private int robotCreatedCounter = 0;
	private final List<Robot> robotsQueue = new ArrayList<>();
	private final Laboratory linkedLaboratory;
	private final JunkYard junkYardThr = JunkYard.getInstance();

	public Scientist(Laboratory laboratory) {

		this.linkedLaboratory = laboratory;
		this.setName(linkedLaboratory.getLaboratoryName() + ConstValues.SCIENTIST);
		robotsQueue.add(new Robot());
	}

	private void waitToNextNight() {
		try {
			Thread.sleep(ConstValues.DAY_TIME_SWAP);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {

		//System.out.println(Thread.currentThread().getName() + " start.");
		while (junkYardThr.getState() != State.TERMINATED) {

			waitToNextNight();
			List<Component> todayComponents = new ArrayList<>(linkedLaboratory.getStash());

			for (Component component : todayComponents) {

				int iter = 0;
				while (true) {
					try {
						if (!robotsQueue.get(iter).addComponent(component)) {
							iter++;
						} else {
							if (robotsQueue.get(iter).getCountComponents() == Component.values().length) {
								robotsQueue.remove(iter);
								robotCreatedCounter++;
								System.out.println(Thread.currentThread().getName() + " create "
										+ robotCreatedCounter + "th robot.");
							}
							break;
						}
					} catch (IndexOutOfBoundsException e) {
						robotsQueue.add(new Robot());
					}
				}
			}
			linkedLaboratory.setCountRobots(robotCreatedCounter);
		}
		//System.out.println(Thread.currentThread().getName() + " finish.");
	}
}
