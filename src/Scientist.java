import java.util.ArrayList;
import java.util.List;

public class Scientist extends Thread {

	private int robotCreatedCounter = 0;
	private final List<Robot> robotsQueue = new ArrayList<>();
	private final Laboratory linkedLaboratory;

	public Scientist(Laboratory laboratory) {
		this.linkedLaboratory = laboratory;
	}

	private void waitToNextNight(){
		try {
			Thread.sleep(ConstValues.DAY_TIME_SWAP_TIME);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {
		this.setName(linkedLaboratory.getLaboratoryName() + " scientistThread");
		//System.out.println(Thread.currentThread().getName() + " start.");
		JunkYard junkYardThr = JunkYard.getInstance();
		robotsQueue.add(new Robot());

		while (junkYardThr.getState() != State.TERMINATED) {

			waitToNextNight();

			List<Component> components = new ArrayList<>(linkedLaboratory.getStash());

			for (Component component : components) {

				int iter = 0;

				while (true) {
					try {
						if (!robotsQueue.get(iter).addComponent(component)) {
							iter++;
						}
						else {
							if (robotsQueue.get(iter).getCountComponents() == Component.values().length){
								robotsQueue.remove(iter);
								robotCreatedCounter++;
							}
							break;
						}
					}catch (IndexOutOfBoundsException e){
						robotsQueue.add(new Robot());
					}
				}

			}
			linkedLaboratory.setCountRobots(robotCreatedCounter);
		}
		//System.out.println(Thread.currentThread().getName() + " finish.");
	}
}
