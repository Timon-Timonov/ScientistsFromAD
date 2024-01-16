package Classess;

import Classess.JunkYard;
import Utils.Component;
import Utils.ConstValues;

import java.util.ArrayList;
import java.util.List;

public class Minion extends Thread {

	private final List<Component> backpack = new ArrayList<>();
	private final Laboratory linkedLaboratory;
	private final JunkYard junkYardThr = JunkYard.getInstance();
	private int currentNight = 1;
	private int countOfPartToTakeThatNight;
	private boolean nightWorkIsExecute = false;

	public Minion(Laboratory laboratory) {
		this.linkedLaboratory = laboratory;
		this.setName(linkedLaboratory.getLaboratoryName() + ConstValues.MINION_NAME);
		setCountOfPartToTakeToNight();
	}

	@Override
	public void run() {

		//System.out.println(Thread.currentThread().getName() + " start.");
		while (junkYardThr.getState() != State.TERMINATED) {
			if (junkYardThr.isOpenState()) {
				if (junkYardThr.getCurrentNight() == currentNight) {
					if (!nightWorkIsExecute) {
						doEveryNightWork();
					}
				} else {
					doEveryDayWork();
				}
			}
		}
		putComponentsToLab();
		//System.out.println(Thread.currentThread().getName() + " finish.");
	}

	private void doEveryNightWork() {

		for (int i = 0; i < countOfPartToTakeThatNight; i++) {
			if (junkYardThr.isOpenState() && junkYardThr.getCurrentNight() == currentNight) {
				Component component = junkYardThr.pickUpComponent();
				if (component != null) {
					backpack.add(component);
				} else break;
			} else break;
		}
		System.out.println(Thread.currentThread().getName() + " night "
				+ currentNight + " take from junkyard " + backpack.size()+" part(s): " + backpack.toString());
		nightWorkIsExecute = true;
	}

	private void doEveryDayWork() {
		currentNight++;
		nightWorkIsExecute = false;
		setCountOfPartToTakeToNight();
		putComponentsToLab();
	}

	private void putComponentsToLab() {
		linkedLaboratory.putComponents(backpack);
		backpack.clear();
	}

	private void setCountOfPartToTakeToNight() {
		countOfPartToTakeThatNight = ConstValues.RANDOM.nextInt(ConstValues.MAX_COUNT_OF_TAKEN_COMPONENTS_EVERY_NIGHT_MINION
				- ConstValues.MIN_COUNT_OF_TAKEN_COMPONENTS_EVERY_NIGHT_MINION + 1)
				+ ConstValues.MIN_COUNT_OF_TAKEN_COMPONENTS_EVERY_NIGHT_MINION;
	}
}
