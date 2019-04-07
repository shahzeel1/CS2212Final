package publishers;

import events.AbstractEvent;
import strategies.publisher.IStrategy;
import strategies.publisher.StrategyFactory;
import strategies.publisher.StrategyName;



/**
 * @author kkontog, ktsiouni, mgrigori
 * 
 * the WeatherPublisher class is an example of a ConcretePublisher 
 * implementing the IPublisher interface. Of course the publish 
 * methods could have far more interesting logics
 */
public class ConcretePublisher extends AbstractPublisher {

	private int pubID;
	
	/**
	 * @param concreteStrategy attaches a concreteStrategy generated from the {@link StrategyFactory#createStrategy(strategies.publisher.StrategyName)}
	 * method
	 */
	protected ConcretePublisher(IStrategy concreteStrategy) {
		this.publishingStrategy = concreteStrategy;
	}
	
	/**
	 * @param Publisher id
	 */
	@Override
	public void setID(int id) {
		 pubID = id;
	}
	
	/**
	 * @return publisher ID 
	 */
	@Override
	public int getID() {
		return pubID;
	}

	/* (non-Javadoc)
	 * @see publishers.IPublisher#publish(events.AbstractEvent)
	 */
	@Override
	public void publish(AbstractEvent event) {
		publishingStrategy.doPublish(event, this.pubID); // changed this.hashCode() to this.pubID
	}

	/* (non-Javadoc)
	 * @see publishers.IPublisher#publish()
	 */
	@Override
	public void publish() {
		publishingStrategy.doPublish(this.pubID);
	}
	
	@Override
	public IStrategy getStrategy() {
		return this.publishingStrategy;
	};
	
	public void setStrategy(StrategyName strat) {
		this.publishingStrategy = StrategyFactory.createStrategy(strat);
		System.out.println("Publisher " + pubID + " has strategy " + strat);

	};

}
