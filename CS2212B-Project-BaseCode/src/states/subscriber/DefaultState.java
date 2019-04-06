package states.subscriber;

import events.AbstractEvent;

public class DefaultState implements IState{
	
	public void handleEvent(AbstractEvent event, String channelName, int subID) {
		System.out.println("Subscriber " + subID + " receives event " + event.getEventID() + " and handles it at state defaultState");
	}
	
	public String getState() {
		return "DefaultState";
	}
}
