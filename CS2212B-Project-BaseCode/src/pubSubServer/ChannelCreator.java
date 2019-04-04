package pubSubServer;


/**
 * @author kkontog, ktsiouni, mgrigori
 * MUST IMPLEMENT the Singleton design pattern
 * this class is responsible for creating and deleting channels
 * it's also the only class that can do so
 */
public class ChannelCreator {
	
	private ChannelPoolManager cpm = null;
	private static ChannelCreator instance = null; // added this
	
	private ChannelCreator() { //added this constructor
		// what do I do here?
		// keeping the same map
		// keep a map between the name and the 9bhect that's been created
		// addChannel
		
		
		//CREATE A CHANNEL HERE
		
		
		
		cpm = ChannelPoolManager.getInstance();
		System.out.print("Channel x created"); //CHANGE x????!!!
		
	}
	
	protected static ChannelCreator getInstance() {
		if (instance == null) { // are you supposed to put the check if instance is null here?
			instance = new ChannelCreator();
		}
		return instance; // only this line was originally in this method
	}
	
	/**
	 * creates a new Channel and adds it to the list of Channels so that it can be discovered using the 
	 * {@link ChannelDiscovery} methods
	 * @param channelName name of the Channel to be created
	 * @return the new channel (of any type that extends the {@link AbstractChannel} that has been created
	 */
	protected AbstractChannel addChannel(String channelName) {
		return cpm.addChannel(channelName);
	}

	/**
	 * deletes a channel and removes it from all channels stores so that no one can access it anymore
	 * @param channelName name of the channel to be deleted
	 */
	protected void deleteChannel(String channelName) {
		cpm.deleteChannel(channelName);
	}

}
