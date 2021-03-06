package publishers;

import baseEntities.IEntity;
import events.AbstractEvent;
import strategies.publisher.IStrategy;
import strategies.publisher.StrategyName;

/**
 * @author kkontog, ktsiouni, mgrigori base Interface implemented by all
 *         ConcretePublisher Classes
 */
public abstract class AbstractPublisher implements IEntity {

	protected IStrategy publishingStrategy = null;
	/**
	 * all implementations of this Interface MUST contain an instance variable of 
	 * type {@link IStrategy} which has the methods:
	 * 1) {@link IStrategy#doPublish(int)}
	 * 2) {@link IStrategy#doPublish(AbstractEvent, int)}
	 * which allows for the publishing of an {@link AbstractEvent} object
	 * 
	 * @param event an event which is to be published
	 * 
	 */
	public void publish(AbstractEvent event) {};

	/**
	 * all implementations of this Interface MUST contain an instance variable of 
	 * type {@link IStrategy} which has the methods:
	 * 1) {@link IStrategy#doPublish(int)}
	 * 2) {@link IStrategy#doPublish(AbstractEvent, int)}
	 * which allows for the publishing of an {@link AbstractEvent} object
	 * 
	 * @param event an event which is to be published
	 * 
	 */
	public void publish() {}
	
	/**
	 * 
	 * @param id
	 */
	public void setID(int id) {};

	/**
	 * 
	 * @return ID
	 */
	public int getID() {
		return 0;
	};
	
	public IStrategy getStrategy() {
		return this.publishingStrategy;
	};
	
	public void setStrategy(StrategyName strat) {};

}
