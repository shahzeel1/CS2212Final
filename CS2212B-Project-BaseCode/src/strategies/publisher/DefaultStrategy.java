package strategies.publisher;

import java.util.ArrayList;
import java.util.List;

import events.AbstractEvent;
import events.EventFactory;
import events.EventMessage;
import events.EventType;
import pubSubServer.ChannelEventDispatcher;
import publishers.AbstractPublisher;


public class DefaultStrategy implements IStrategy{

	public void doPublish(int publisherId) {
		List<String> listOfChannels = new ArrayList<>();
		
		// adding arbitrary channels to the channelList to post
		listOfChannels.add("DefaultStrategy NE1");
		
		ChannelEventDispatcher channelDispatch = ChannelEventDispatcher.getInstance();
		
		// random message
		EventMessage message = new EventMessage("default strategy", "message");
		
		AbstractEvent event = EventFactory.createEvent(EventType.values()[0], publisherId, message);
		
		channelDispatch.postEvent(event, listOfChannels);
		
		System.out.println("Publisher " + publisherId + " publishes event " + event.getEventID());
		
	}
	
	public void doPublish(AbstractEvent event, int publisherId) {
		
		List<String> listOfChannels = new ArrayList<>();
		
		// adding arbitrary channels to the channelList to post
		listOfChannels.add("DefaultStrategy E1");
		listOfChannels.add("DefaultStrategy E2");
				
		ChannelEventDispatcher channelDispatch = ChannelEventDispatcher.getInstance();
		channelDispatch.postEvent(event, listOfChannels);
		
		System.out.println("Publisher " + publisherId + " publishes event " + event.getEventID());
		
	}
}
