package Classess.Models;

import Utils.Component;

import java.util.*;

public class Robot {
	private final Set<Component> components = new HashSet<>();

	public Robot() {
	}

	public boolean addComponent(Component component){
		return components.add(component);
	}

	public int getCountComponents(){
		return components.size();
	}

}
