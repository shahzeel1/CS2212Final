package states.subscriber;

import events.AbstractEvent;

public class BState implements IState{
	
	public void handleEvent(AbstractEvent event, String channelName, int subID) {
		System.out.println("Subscriber " + subID + " receives event " + event.getEventID() + " and handles it at state bState");
	}
	
	public String getState() {
		return "BState";
	}
}
