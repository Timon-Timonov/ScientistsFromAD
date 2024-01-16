public class Run {

	public static void main(String[] args) throws InterruptedException {
		//System.out.println(Thread.currentThread().getName()+ " start ");
		Laboratory[] factories = new Laboratory[ConstValues.COUNT_OF_FACTORIES];
		for (int i = 0; i < ConstValues.COUNT_OF_FACTORIES; i++) {
			factories[i] = new Laboratory(ConstValues.FACTORY_NAME + (i + 1));
		}

		JunkYard junkYardThr = JunkYard.getInstance();
		junkYardThr.addFactories(factories);

		junkYardThr.start();
		junkYardThr.join();

		FactoryPrinter output = (o) -> System.out.println(o.getFactoryName() + " - " + o.getCountRobots());

		if (junkYardThr.getFactoryOnPos(0).getCountRobots() != junkYardThr.getFactoryOnPos(factories.length - 1).getCountRobots()) {
			boolean isFirstWinner = true;
			boolean isFirstLooser = true;
			for (int i = 0; i < factories.length; i++) {
				if (isFirstWinner) {
					System.out.print("Winner(s): ");
					isFirstWinner = false;
				}
				if (junkYardThr.getFactoryOnPos(i).getCountRobots() == junkYardThr.getFactoryOnPos(0).getCountRobots()) {
					output.printResult(junkYardThr.getFactoryOnPos(i));
				} else {
					if (isFirstLooser) {
						System.out.println();
						System.out.print("Loser(s): ");
						isFirstLooser = false;
					}
					output.printResult(junkYardThr.getFactoryOnPos(i));
				}
			}
		} else {
			System.out.print("GameDraw. All are loosers: ");
			for (int i = 0; i < factories.length; i++) {
				output.printResult(junkYardThr.getFactoryOnPos(i));
			}
		}
		//System.out.println(Thread.currentThread().getName()+ " finish ");
	}
}

@FunctionalInterface
interface FactoryPrinter {
	void printResult(Laboratory laboratory);
}
