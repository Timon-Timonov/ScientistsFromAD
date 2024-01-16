package Classess;

import Utils.Component;

import java.util.ArrayList;
import java.util.List;

public class Laboratory implements Comparable<Laboratory> {

	private final String laboratoryName;
	private Integer countRobots = 0;
	private final List<Component> stash = new ArrayList<>();

	public Laboratory(String name) {
		this.laboratoryName = name;

		Minion minionThr = new Minion(this);
		minionThr.start();

		Scientist scientistThr = new Scientist(this);
		scientistThr.start();

	}

	public String getLaboratoryName() {
		return laboratoryName;
	}

	public int getCountRobots() {
		return countRobots;
	}

	public void setCountRobots(int countRobots) {
		this.countRobots = countRobots;
	}

	public synchronized List<Component> getStash() {
		List<Component> temp = new ArrayList<>(stash);
		stash.clear();
		return temp;
	}

	public synchronized void putComponents(List<Component> components) {
		stash.addAll(components);
	}

	@Override
	public int compareTo(Laboratory o) {
		return this.countRobots.compareTo(o.countRobots);
	}
}
