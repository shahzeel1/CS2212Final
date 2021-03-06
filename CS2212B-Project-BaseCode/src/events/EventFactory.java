package events;

import publishers.AbstractPublisher;



/**
 * @author kkontog, ktsiouni, mgrigori 
 *
 */
public class EventFactory {


	/**
	 * This is an implementation of the Factory Method design pattern
	 * Creates an instance of any of the subclasses derived from the top level class AbstractEvent
	 * 
	 * @param eventType a member of the {@link EventType} enumeration
	 * @param eventPublisherId a number generated by invoking the  {@link AbstractPublisher#hashCode()} on the {@link AbstractPublisher} instance issuing the event
	 * @param payload an object of type {@link EventMessage}
	 * @return
	 */
	public static AbstractEvent createEvent(EventType eventType, int eventPublisherId, EventMessage payload) {
		EventIDMaker id = new EventIDMaker();
		// Use the Type given to create the appropriate event type
		switch(eventType) {
		case TypeA : 
			return new EventTypeA(id.getNewEventID(),eventPublisherId,payload);
		case TypeB :
			return new EventTypeB(id.getNewEventID(),eventPublisherId,payload);
		default:
			return new EventTypeC(id.getNewEventID(),eventPublisherId,payload);
		}

	}
}
