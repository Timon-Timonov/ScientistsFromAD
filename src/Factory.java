import java.util.Random;

public class Factory {
	public Component generateComponent(){
		return Component.values()[ConstValues.RANDOM.nextInt(Component.values().length)];
	}
}
