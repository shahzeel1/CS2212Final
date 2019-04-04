package strategies.publisher;
import events.AbstractEvent;
import strategies.publisher.StrategyName;

/**
 * @author kkontog, ktsiouni, mgrigori
 * creates new {@link IStrategy } objects
 * contributes to the Strategy design pattern
 * implements the FactoryMethod design pattern   
 */
public class StrategyFactory {

	
	/**
	 * creates a new {@link IStrategy} using an entry from the {@link StrategyName} enumeration
	 * @param strategyName a value from the {@link StrategyName} enumeration specifying the strategy to be created 
	 * @return the newly created {@link IStrategy} instance 
	 */
	public static AbstractStrategy createStrategy(StrategyName strategyName, int publisherId, AbstractEvent event) {
		switch(strategyName) {
			case AStrategy:
				if (event==null)
					return new AStrategy(publisherId);
				return  new AStrategy(publisherId,event);
			case BStrategy:
				if (event==null)
					return new AStrategy(publisherId);
				return  new BStrategy(publisherId,event);
			default:
				if (event==null)
					return new AStrategy(publisherId);
				return  new DefaultStrategy(publisherId,event);
		}
	}
	
	
}
