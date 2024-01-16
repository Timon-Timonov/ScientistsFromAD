import java.util.Random;

public class Factory {
	Random random=new Random();
	public Component generateComponent(){
		return Component.values()[random.nextInt(Component.values().length)];
	}
}
