package Classes;

import Utils.Component;
import Utils.ConstValues;

public class Factory {
	public Component generateComponent(){
		return Component.values()[ConstValues.RANDOM.nextInt(Component.values().length)];
	}
}
