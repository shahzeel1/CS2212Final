package states.subscriber;

import events.AbstractEvent;

public class AState implements IState{
	
	public void handleEvent(AbstractEvent event, String channelName, int subID) {
		System.out.println("Subscriber " + subID + " receives event " + event.getEventID() + " and handles it at state aState");
	}
	
	public String getState() {
		return "AState";
	}
}
