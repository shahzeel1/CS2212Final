package states.subscriber;

import events.AbstractEvent;

/**
 * 
 * Creates the AState Object, implementing the IState interface
 *
 */
public class AState implements IState{
	
	/**
	 * @param event 
	 * @param channelName that the event is being published too
	 * @param Subscriber ID
	 * 
	 * This methods outputs the subscriber ID, eventID and the state the event is handled in
	 */
	public void handleEvent(AbstractEvent event, String channelName, int subID) {
		System.out.println("Subscriber " + subID + " receives event " + event.getEventID() + " and handles it at state aState");
	}
	
	/**
	 * @return state Type
	 */
	public String getState() {
		return "AState";
	}
}
