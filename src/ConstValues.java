import java.util.Random;

public class ConstValues {

	public static final Random RANDOM = new Random();

	public static final int COUNT_OF_LABORATORIES = 2;
	public static final String LABORATORY_NAME = "Laboratory";
	public static final String JUNK_YARD = "Junkyard                ";
	public static final String MINION = " minionThread";
	public static final String SCIENTIST = " scientistThread";
	public final static long DAY_TIME_SWAP = 100;
	public final static int COUNT_OF_DAYS = 100;

	public final static int START_COUNT_OF_COMPONENTS_ON_JUNK_YARD = 20;
	public final static int MAX_COUNT_OF_NEW_COMPONENTS_EVERY_NIGHT_JUNK_YARD = 4;
	public final static int MIN_COUNT_OF_NEW_COMPONENTS_EVERY_NIGHT_JUNK_YARD = 1;

	public final static int MAX_COUNT_OF_TAKEN_COMPONENTS_EVERY_NIGHT_MINION = 4;
	public final static int MIN_COUNT_OF_TAKEN_COMPONENTS_EVERY_NIGHT_MINION = 1;
}
