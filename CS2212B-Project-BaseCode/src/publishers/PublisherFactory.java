package publishers;

import strategies.publisher.StrategyFactory;
import strategies.publisher.StrategyName;


/**
 * @author kkontog, ktsiouni, mgrigori
 * creates new {@link AbstractPublisher} objects
 * contributes to the Strategy design pattern
 * implements the FactoryMethod design pattern   
 */
public class PublisherFactory {


	/**
	 * This is an implementation of the Factory Method design pattern
	 * Creates an instance of any of the classes implementing the top level Interface IPublisher
	 * 
	 * note we have multiple entries that return instances of the same ConcretePublisher class
	 * 
	 * @param publisherType an entry from the {@link PublisherType} enumeration
	 * @param strategyName an entry from the {@link StrategyName} enumeration
	 * @return an instance of the specified IPublisher implementation with the specified strategyName attached to it
	 */
	public static AbstractPublisher createPublisher(PublisherType publisherType, StrategyName strategyName, int id) {
		AbstractPublisher ip;
		switch (publisherType) {
		case alphaPub : 
			ip = new ConcretePublisher(StrategyFactory.createStrategy(strategyName));
			ip.setID(id);
			//print info onto the console 
			printOutput(id,strategyName);
			return ip;
		case betaPub : 
			ip = new ConcretePublisher(StrategyFactory.createStrategy(strategyName));
			ip.setID(id);
			printOutput(id,strategyName);
			return ip;
		case gammaPub : 
			ip = new ConcretePublisher(StrategyFactory.createStrategy(strategyName));
			ip.setID(id);
			printOutput(id,strategyName);
			return ip;
		case deltaPub : 
			ip = new ConcretePublisher(StrategyFactory.createStrategy(strategyName));
			ip.setID(id);
			printOutput(id,strategyName);
			return ip;
		default:
			ip = new ConcretePublisher(StrategyFactory.createStrategy(strategyName));
			ip.setID(id);
			printOutput(id,strategyName);
			return ip;
		}
	}

	// createPublisher when only id is given
	public static AbstractPublisher createPublisher(int id) {
		AbstractPublisher ip;
		ip = new ConcretePublisher(StrategyFactory.createStrategy(StrategyName.values()[2]));
		ip.setID(id);
		printOutput(id,StrategyName.values()[2]);
		return ip;
	}
// output function 
	private static void printOutput(int id,StrategyName strategyName)
	{
		System.out.println("Publisher " + id + " created");
		System.out.println("Publisher " + id + " has strategy " + strategyName);
	}

}
