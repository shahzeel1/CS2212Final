package pubSubServer;

import java.util.Iterator;
import java.util.List;

import subscribers.AbstractSubscriber;

public class AdministrationServer {
	
	ChannelPoolManager cpm;
	ChannelAccessControl cam;
	
	/*
	 * Initialize the Channel pool manager and Channel access control
	 */
	public AdministrationServer() {
		cpm = ChannelPoolManager.getInstance();
		cam = ChannelAccessControl.getInstance();
	}
	
	/**
	 * This function creates the blocking operation between the subscriber and the channel
	 * @param subID
	 * @param channelName
	 * @param listOfSubscribers
	 * @return whether the channel was blocked or not
	 */
	public Object block(int subID, String channelName, List<AbstractSubscriber> listOfSubscribers) {
		
		
		Iterator<AbstractSubscriber> it = listOfSubscribers.iterator();
		AbstractSubscriber sub = null;
		
		boolean subscriberFound = false;
		
		// make sure the subscriber exists and perform the blocking operation 
		while(it.hasNext()) {
			 sub = it.next();
			 if(sub.getID() == subID) {
				 subscriberFound = true;
				 // make sure the channel is unblocked
				 if(cam.checkIfBlocked(sub, channelName) == false) {
						cam.blockSubcriber(sub, channelName);
					}
			 }
			 

		}
		// if the subscriber does not exist return false
		if(!subscriberFound) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Performs the unblocking operation between the channel and the subscriber
	 * @param subID
	 * @param channelName
	 * @param listOfSubscribers
	 * @return true is successfully un blocked
	 */
	public Object unBlock(int subID, String channelName, List<AbstractSubscriber> listOfSubscribers) { //changed to Object
		
		Iterator<AbstractSubscriber> it = listOfSubscribers.iterator();
		AbstractSubscriber sub = null;
		
		boolean subscriberFound = false;
		
		// make sure the subscriber exists and do the unblocking operation
		while(it.hasNext()) {
			 sub = it.next();
			 if(sub.getID() == subID) {
				 subscriberFound = true;
				 // make sure the channel is blocked
				 if(cam.checkIfBlocked(sub, channelName) == true) {
						cam.unBlockSubscriber(sub, channelName);
				}
			 }
		}
		
		// if subscriber does not exists return false
		if(!subscriberFound) {
			return false;
		}
		
	
		return true;
	}
	
	

}
