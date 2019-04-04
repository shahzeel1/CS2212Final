package publishers;

import events.AbstractEvent;
import strategies.publisher.AbstractStrategy;

/**
 * @author kkontog, ktsiouni, mgrigori
 * 
 * the WeatherPublisher class is an example of a ConcretePublisher 
 * implementing the IPublisher interface. Of course the publish 
 * methods could have far more interesting logics
 */
public class ConcretePublisher extends AbstractPublisher {

	
	
	
	
	/**
	 * @param concreteStrategy attaches a concreteStrategy generated from the {@link StrategyFactory#createStrategy(strategies.publisher.StrategyName)}
	 * method
	 */
	protected ConcretePublisher(AbstractStrategy concreteStrategy) {
		this.publishingStrategy = concreteStrategy;
	}

	/* (non-Javadoc)
	 * @see publishers.IPublisher#publish(events.AbstractEvent)
	 */
	@Override
	public void publish(AbstractEvent event) {
		this.publishingStrategy = new AbstractStrategy(this.hashCode(), event);
	}

	/* (non-Javadoc)
	 * @see publishers.IPublisher#publish()
	 */
	@Override
	public void publish() {
		publishingStrategy = new AbstractStrategy(this.hashCode());
	}

}
