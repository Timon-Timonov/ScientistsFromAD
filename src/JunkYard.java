import java.util.*;
import java.util.stream.IntStream;

public class JunkYard extends Thread {

	private static JunkYard instance = null;
	private final Factory factory = new Factory();
	private final List<Component> components = new ArrayList<>();
	private boolean openState = false;
	private int currentNight = 1;

	private JunkYard() {
		this.setName(ConstValues.JUNK_YARD_NAME);
	}

	public synchronized static JunkYard getInstance() {
		if (instance == null) {
			instance = new JunkYard();
		}
		return instance;
	}

	@Override
	public void run() {
		//System.out.println(Thread.currentThread().getName() + " start ");
		IntStream.range(0, ConstValues.START_COUNT_OF_COMPONENTS_ON_JUNK_YARD)
				.forEach(i -> acceptDiscardedComponent());

		for (int i = 0; i < ConstValues.COUNT_OF_DAYS; i++) {

			System.out.println(Thread.currentThread().getName() + " night " + currentNight
					+ " junkyard has " + components.size() + " part(s): " + components.toString());
			openState = true;
			toSleep();//За это время должны успеть прибежать слуги
			openState = false;
			refreshComponents();
			currentNight++;
		}
		//System.out.println(Thread.currentThread().getName() + " finish " + components.size());
	}

	private void toSleep() {
		try {
			Thread.sleep(ConstValues.DAY_TIME_SWAP);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}


	private void acceptDiscardedComponent() {
		components.add(factory.generateComponent());
	}

	public boolean isOpenState() {
		return openState;
	}

	public synchronized Component pickUpComponent() {

		try {
			int index = ConstValues.RANDOM.nextInt(components.size());
			return components.remove(index);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	private synchronized void refreshComponents() {

		int rnd = ConstValues.RANDOM.nextInt(ConstValues.MAX_COUNT_OF_COMPONENTS_EVERY_NIGHT_JUNK_YARD
				- ConstValues.MIN_COUNT_OF_COMPONENTS_EVERY_NIGHT_JUNK_YARD + 1)
				+ ConstValues.MIN_COUNT_OF_COMPONENTS_EVERY_NIGHT_JUNK_YARD;
		IntStream.range(0, rnd).forEach(i -> acceptDiscardedComponent());
	}

	public int getCurrentNight() {
		return currentNight;
	}
}
