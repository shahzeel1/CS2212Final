package strategies.publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import events.AbstractEvent;
import events.EventFactory;
import events.EventMessage;
import events.EventType;
import pubSubServer.ChannelEventDispatcher;
import publishers.AbstractPublisher;


public class AStrategy implements IStrategy{

	public void doPublish(int publisherId) {
		
		List<String> listOfChannels = new ArrayList<>();
		
		// adding arbitrary channels to the channelList to post
		listOfChannels.add("AStrategy NE1");
		listOfChannels.add("cars"); //testing to see this, will probably need to test with AStrategy NE1 by getting a subscriber to subscribe to it then seeing if it works
		
		ChannelEventDispatcher channelDispatch = ChannelEventDispatcher.getInstance();
		
		// random message
		EventMessage message = new EventMessage("default A", "message");
		
		AbstractEvent event = EventFactory.createEvent(EventType.values()[0], publisherId, message);
		
		System.out.println("Publisher " + publisherId + " publishes event " + event.getEventID());

		channelDispatch.postEvent(event, listOfChannels);
		
	}
	
	public void doPublish(AbstractEvent event, int publisherId) {
				
		List<String> listOfChannels = new ArrayList<>();
				
		// adding arbitrary channels to the channelList to post
		listOfChannels.add("AStrategy E1");
		listOfChannels.add("AStrategy E2");
		listOfChannels.add("cars");
		
		System.out.println("Publisher " + publisherId + " publishes event " + event.getEventID());
				
		ChannelEventDispatcher channelDispatch = ChannelEventDispatcher.getInstance();
		channelDispatch.postEvent(event, listOfChannels);
		
		
	}
}
