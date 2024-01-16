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
		junkYardThr.addLaboratories(laboratories);

		junkYardThr.start();
		junkYardThr.join();

		checkAndPrintResults(laboratories);
		//System.out.println(Thread.currentThread().getName()+ " finish ");
	}

	private static void checkAndPrintResults(List<Laboratory> laboratories) {

		FactoryPrinter output = (o) -> System.out.println(o.getFactoryName() + " - " + o.getCountRobots());

		int maxCountRobots = laboratories.stream()
				.max(Laboratory::compareTo).get().getCountRobots();

		if (maxCountRobots == 0) {
			System.out.print("GameDraw. All are losers: ");
			laboratories.forEach(output::printResult);

		} else {
			List<Laboratory> winners = laboratories.stream()
					.filter(lab -> lab.getCountRobots() == maxCountRobots)
					.collect(Collectors.toList());

			if (winners.size() == laboratories.size()) {
				System.out.print("GameDraw. All are winners: ");
				laboratories.forEach(output::printResult);

			} else {
				System.out.println("Winner(s): ");
				winners.forEach(output::printResult);
				System.out.println();

				System.out.println("Loser(s): ");
				laboratories.removeAll(winners);
				laboratories.forEach(output::printResult);
			}
		}
	}
}


