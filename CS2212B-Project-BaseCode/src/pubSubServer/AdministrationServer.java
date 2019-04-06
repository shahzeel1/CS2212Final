package pubSubServer;

import java.util.Iterator;
import java.util.List;

import subscribers.AbstractSubscriber;

public class AdministrationServer {
	
	ChannelPoolManager cpm;
	ChannelAccessControl cam;
	
	public AdministrationServer() {
		cpm = ChannelPoolManager.getInstance();
		cam = ChannelAccessControl.getInstance();
	}
	
	public Object block(int subID, String channelName, List<AbstractSubscriber> listOfSubscribers) {
		
		List<AbstractChannel> channelList = cpm.listChannels();
		for(AbstractChannel list : channelList) {
			//why do we need access to the Channel Pool Manager Module for obtaining the list of all available channels and their subscribers?
		}
		
		Iterator<AbstractSubscriber> it = listOfSubscribers.iterator();
		AbstractSubscriber sub = null;
		
		boolean subscriberFound = false;
		
		while(it.hasNext()) {
			 sub = it.next();
			 if(sub.getID() == subID) {
				 subscriberFound = true;
				 
				 if(cam.checkIfBlocked(sub, channelName) == false) {
						cam.blockSubcriber(sub, channelName);
					}
			 }
			 

		}
		
		if(!subscriberFound) {
			return null;
		}
		
		return true;
	}
	
	public Object unBlock(int subID, String channelName, List<AbstractSubscriber> listOfSubscribers) { //changed to Object
		
		Iterator<AbstractSubscriber> it = listOfSubscribers.iterator();
		AbstractSubscriber sub = null;
		
		boolean subscriberFound = false;
		
		while(it.hasNext()) {
			 sub = it.next();
			 if(sub.getID() == subID) {
				 subscriberFound = true;
				 
				 if(cam.checkIfBlocked(sub, channelName) == true) {
						cam.unBlockSubscriber(sub, channelName);
				}
			 }
		}
		
		if(!subscriberFound) {
			return null;
		}
		
	
		return true;
	}
	
	

}
