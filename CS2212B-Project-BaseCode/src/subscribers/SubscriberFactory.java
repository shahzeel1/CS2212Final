package subscribers;

import publishers.ConcretePublisher;
import states.subscriber.StateName;
import states.subscriber.StateFactory;


/**
 * @author kkontog, ktsiouni, mgrigori
 *  
 */
/**
 * @author kkontog, ktsiouni, mgrigori
 * creates new {@link AbstractSubscriber} objects
 * contributes to the State design pattern
 * implements the FactoryMethod design pattern   
 */
public class SubscriberFactory {

	
	/**
	 * creates a new {@link AbstractSubscriber} using an entry from the {@link SubscriberType} enumeration
	 * @param subscriberType a value from the {@link SubscriberType} enumeration specifying the type of Subscriber to be created 
	 * @return the newly created {@link AbstractSubscriber} instance 
	 */
	public static AbstractSubscriber createSubscriber(SubscriberType subscriberType, StateName stateName, int id) {
		AbstractSubscriber CSA = null;
		switch (subscriberType) {
			case alpha : 
				CSA = new ConcreteSubscriberA();
				CSA.setID(id);
				System.out.println("Subscriber " + CSA.getID() + " created");
				CSA.setState(stateName);
				return CSA;
			case beta:
				CSA = new ConcreteSubscriberA();
				CSA.setID(id);
				System.out.println("Subscriber " + CSA.getID() + " created");
				CSA.setState(stateName);
				return CSA;
			case gamma:
				CSA = new ConcreteSubscriberA();
				CSA.setID(id);
				System.out.println("Subscriber " + CSA.getID() + " created");
				CSA.setState(stateName);
				return CSA;
			default: // what does the default case do????
				CSA = new ConcreteSubscriberA();
				CSA.setID(id);
				System.out.println("Subscriber " + CSA.getID() + " created");
				CSA.setState(stateName);
				return CSA;
		}
	}
	
}
