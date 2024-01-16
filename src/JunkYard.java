import java.util.*;

public class JunkYard extends Thread {

	private static JunkYard instance = null;
	private Factory factory = new Factory();
	private final Random random = new Random();
	private final List<Component> components = new ArrayList<>();
	private boolean openState = false;
	private final List<Laboratory> laboratories = new ArrayList<>();
	private int currentNight = 0;

	private JunkYard() {
		this.setName(ConstValues.DUMP_NAME);
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

		for (int i = 0; i < ConstValues.START_COUNT_OF_COMPONENTS_ON_JUNK_YARD; i++) {
			components.add(acceptDiscardedComponent());
		}

		for (int i = 0; i < ConstValues.COUNT_DAYS; i++) {
			//System.out.println(Thread.currentThread().getName() + " night " + (i + 1) + " count of parts:" + components.size());
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
			Thread.sleep(ConstValues.DAY_TIME_SWAP_TIME);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public void addLaboratories(List<Laboratory> laboratories) {
		this.laboratories.addAll(laboratories);
	}

	public Laboratory getLaboratoryOnPos(int pos) throws IndexOutOfBoundsException {
		return laboratories.get(pos);
	}

	private Component acceptDiscardedComponent() {
		return factory.generateComponent();
	}

	public boolean isOpenState() {
		return openState;
	}

	public Component pickUpComponent() throws Exceptions.ComponentsNotFound {

		try {
			synchronized (this.components) {
				int index = random.nextInt(components.size());
				return components.remove(index);
			}
		} catch (IllegalArgumentException e) {
			throw new Exceptions.ComponentsNotFound();
		}
	}

	private void refreshComponents() {

		int rnd = random.nextInt(ConstValues.MAX_COUNT_OF_NEW_COMPONENTS_EVERY_NIGHT_DUMP
				- ConstValues.MIN_COUNT_OF_NEW_COMPONENTS_EVERY_NIGHT_DUMP + 1)
				+ ConstValues.MIN_COUNT_OF_NEW_COMPONENTS_EVERY_NIGHT_DUMP;

		synchronized (this.components) {
			for (int i = 0; i < rnd; i++) {
				components.add(acceptDiscardedComponent());
			}
		}
	}

	public int getCurrentNight() {
		return currentNight;
	}
}
