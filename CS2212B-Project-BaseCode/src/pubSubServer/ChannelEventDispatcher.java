package pubSubServer;

import java.util.List;

import events.AbstractEvent;
import publishers.AbstractPublisher;


/**
 * @author kkontog, ktsiouni, mgrigori
 * MUST IMPLEMENT the Singleton design pattern
 * Class providing an interface for {@link AbstractPublisher} objects to cover their publishing needs 
 */
public class ChannelEventDispatcher {

	private ChannelPoolManager cpManager;
	private static ChannelEventDispatcher instance = null; 
	
	/**
	 * Constructor to create Channel Pool Manager
	 */
	private ChannelEventDispatcher() {
		cpManager = ChannelPoolManager.getInstance();
	}
	
	/**
	 * 
	 * @return instance
	 */
	public static ChannelEventDispatcher getInstance() {
		if(instance == null) {
			instance = new ChannelEventDispatcher();// initialize if not already created
		}
		return instance; 
	}

	
	
	/**
	 * @param event event to be published
	 * @param listOfChannels list of channel names to which the event must be published to 
	 */
	public void postEvent(AbstractEvent event, List<String> listOfChannels) {
		
		// Find the channel and post the event to that channel
		for(String channelName : listOfChannels) {
			AbstractChannel channel = cpManager.findChannel(channelName);
			if(channel == null) {
				channel = ChannelCreator.getInstance().addChannel(channelName);
			}
			
			// print confirmation to console
			System.out.println("Channel " + channel.getChannelTopic() + " has event " + event.getEventID() + " from publisher " + event.getPubID());
	
			channel.publishEvent(event);
			
		}
	}
	
	
}
