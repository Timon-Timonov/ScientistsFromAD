import Classess.JunkYard;
import Classess.Laboratory;
import Utils.ConstValues;
import Utils.LaboratoryPrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Run {

	public static void main(String[] args) throws InterruptedException {
		//System.out.println(Thread.currentThread().getName()+ " start ");
		List<Laboratory> laboratories = new ArrayList<>();

		IntStream.range(0, ConstValues.COUNT_OF_LABORATORIES)
				.forEach(i -> laboratories
						.add(new Laboratory(ConstValues.LABORATORY_NAME + (i + 1))));

		JunkYard junkYardThr = JunkYard.getInstance();
		junkYardThr.start();
		junkYardThr.join();

		//Thread.sleep(Utils.ConstValues.DAY_TIME_SWAP);

		checkAndPrintResults(laboratories);
		//System.out.println(Thread.currentThread().getName()+ " finish ");
	}

	private static void checkAndPrintResults(List<Laboratory> laboratories) {
		System.out.println();
		System.out.println("Results:");

		LaboratoryPrinter output = (o) -> System.out.println(o.getLaboratoryName() + " - " + o.getCountRobots());

		int maxCountRobots = laboratories.stream()
				.max(Laboratory::compareTo).get().getCountRobots();

		//int maxCountRobots1 = laboratories.stream().map(Classess.Laboratory::getCountRobots)
		//		.max(Integer::compareTo).orElse(-1);

		if (maxCountRobots == 0) {
			System.out.print("GameDraw. All are losers: ");
		} else {
			List<Laboratory> winners = laboratories.stream()
					.filter(lab -> lab.getCountRobots() == maxCountRobots)
					.collect(Collectors.toList());
			if (winners.size() == laboratories.size()) {
				System.out.println("GameDraw. All are winners: ");
			} else {
				System.out.println("Winner(s): ");
				winners.forEach(output::printResult);
				System.out.println();

				System.out.println("Loser(s): ");
				laboratories.removeAll(winners);
			}
		}
		laboratories.forEach(output::printResult);
	}
}


