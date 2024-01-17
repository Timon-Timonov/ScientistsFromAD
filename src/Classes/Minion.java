package Classes;


import Exceptions.ListExceptions;
import Utils.Component;
import Utils.ConstValues;

import java.util.ArrayList;
import java.util.List;

public class Minion extends Thread {

	private final List<Component> backpack = new ArrayList<>();
	private final Laboratory linkedLaboratory;
	private int currentNight = 0;
	private int countOfPartToTakeToNight;
	private boolean nightWorkIsExecute = false;

	public Minion(Laboratory laboratory) {
		this.linkedLaboratory = laboratory;
		setCountOfPartToTakeToNight();
	}

	@Override
	public void run() {

		this.setName(linkedLaboratory.getLaboratoryName() + " minionThread");
		//System.out.println(Thread.currentThread().getName() + " start.");
		JunkYard junkYardThr = JunkYard.getInstance();

		while (junkYardThr.getState() != State.TERMINATED) {
			if (junkYardThr.isOpenState()) {
				if (junkYardThr.getCurrentNight() == currentNight) {
					if (!nightWorkIsExecute) {
						for (int i = 0; i < countOfPartToTakeToNight; i++) {
							if (junkYardThr.isOpenState() && junkYardThr.getCurrentNight() == currentNight) {
								try {
									backpack.add(junkYardThr.pickUpComponent());
								} catch (ListExceptions.ComponentsNotFound componentsNotFound) {
									break;
								}
							} else break;
						}
						System.out.println(Thread.currentThread().getName() + " night " + currentNight + " take from dump " + backpack.size());
						nightWorkIsExecute = true;
					}
				} else {
					currentNight++;
					nightWorkIsExecute = false;
					setCountOfPartToTakeToNight();
					putComponentsToLab();
				}
			}
		}
		putComponentsToLab();
		//System.out.println(Thread.currentThread().getName() + " finish.");
	}

	private void putComponentsToLab() {
		linkedLaboratory.putComponents(backpack);
		backpack.clear();
	}

	private void setCountOfPartToTakeToNight() {
		countOfPartToTakeToNight = ConstValues.RANDOM.nextInt(ConstValues.MAX_COUNT_OF_NEW_COMPONENTS_EVERY_NIGHT_SERVANT
				- ConstValues.MIN_COUNT_OF_NEW_COMPONENTS_EVERY_NIGHT_SERVANT + 1)
				+ ConstValues.MIN_COUNT_OF_NEW_COMPONENTS_EVERY_NIGHT_SERVANT;
	}
}
