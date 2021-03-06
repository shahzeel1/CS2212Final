package orchestration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import events.AbstractEvent;
import events.EventFactory;
import events.EventMessage;
import events.EventType;
import pubSubServer.AbstractChannel;
import pubSubServer.AdministrationServer;
import pubSubServer.ChannelAccessControl;
import pubSubServer.ChannelDiscovery;
import pubSubServer.ChannelEventDispatcher;
import pubSubServer.SubscriptionManager;
import publishers.AbstractPublisher;
import publishers.PublisherFactory;
import publishers.PublisherType;
import states.subscriber.StateName;
import strategies.publisher.IStrategy;
import strategies.publisher.StrategyName;
import subscribers.AbstractSubscriber;
import subscribers.SubscriberFactory;
import subscribers.SubscriberType;

public class Orchestration {

	public static void main(String[] args) {
		//Instantiate lists to store publishers and subscribers
		List<AbstractPublisher> listOfPublishers = new ArrayList<>();
		List<AbstractSubscriber> listOfSubscribers = new ArrayList<>();
		Orchestration testHarness = new Orchestration();

		try {
			//Generate a list of publishers
			listOfPublishers = testHarness.createPublishers();
			System.out.println("");
			//Generate a list of subscribers
			listOfSubscribers = testHarness.createSubscribers();
			System.out.println("");

			List<AbstractChannel> channels = ChannelDiscovery.getInstance().listChannels();
			System.out.println("");
			//For demonstration purposes only
			try {
				BufferedReader initialChannels = new BufferedReader(new FileReader(new File("Channels.chl")));
				List<String> channelList = new ArrayList<String>();
				String line = "";
				while((line = initialChannels.readLine()) != null )
					channelList.add(line);
				int subscriberIndex = 0;
				for(AbstractSubscriber subscriber : listOfSubscribers) {
					subscriber.subscribe(channelList.get(subscriberIndex%channelList.size()));
					subscriberIndex++;
				}
				System.out.println("");
				initialChannels.close();
			}catch(IOException ioe) {
				System.out.println("Loading Channels from file failed proceeding with random selection");
				for(AbstractSubscriber subscriber : listOfSubscribers) {
					int index = (int) Math.round((Math.random()*10))/3;
					SubscriptionManager.getInstance().subscribe(channels.get(index).getChannelTopic(), subscriber);
				}
			}
			for(AbstractPublisher publisher : listOfPublishers) {
				publisher.publish();
				System.out.println("");
			}

		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			System.out.println("Will now terminate");
			return;
		}
		for(AbstractPublisher publisher : listOfPublishers) {
			publisher.publish();
			System.out.println("");

		}

		//Parsing the driver.txt file
		BufferedReader driver;
		try {
			driver = new BufferedReader(new FileReader(new File("driver.txt")));
			String newLine;
			try {
				newLine = driver.readLine();
				while(newLine != null) {
					//Variables for various driver.txt inputs
					int pub_id;
					int sub_id;
					String event_type;
					String event_header;
					String event_payload;
					String channel_name;

					StringTokenizer st = new StringTokenizer(newLine);
					String firstWord = st.nextToken();
					//For subscribing to a channel
					if (firstWord.equals("SUB")) {
						System.out.println("");
						sub_id = Integer.parseInt(st.nextToken());
						channel_name = st.nextToken();

						SubscriptionManager subManager = SubscriptionManager.getInstance();

						Iterator<AbstractSubscriber> it = listOfSubscribers.iterator();
						AbstractSubscriber sub;
						//Find the subscriber from the subscribers list
						while(it.hasNext()) {
							sub = it.next();
							if(sub.getID() == sub_id) {
								//Subscribe to the channel
								subManager.subscribe(channel_name, sub);
								break;
							}
						}
					}
					//For publishing an event
					else if(firstWord.equals("PUB")) {
						System.out.println("");
						pub_id = Integer.parseInt(st.nextToken());
						//If event type, event header, and event payload parameters are provided
						if(st.hasMoreTokens()) {
							event_type = st.nextToken();
							event_header = st.nextToken();
							event_payload = st.nextToken();
							
							//Create the event
							EventMessage msg = new EventMessage(event_header, event_payload);
							EventType type;
							if(event_type.equals("TypeA")) 
								type = EventType.values()[2];
							else if(event_type.equals( "TypeB")) 
								type = EventType.values()[1];
							else 
								type = EventType.values()[0];

							AbstractEvent event = EventFactory.createEvent(type, pub_id, msg);

							//Find the publisher from the publisher list
							Iterator<AbstractPublisher> pubItr = listOfPublishers.iterator();
							AbstractPublisher pub;
							boolean exists = false;
							while(pubItr.hasNext()) {
								pub = pubItr.next();
								if(pub.getID() == pub_id) {
									pub.publish(event);
									exists = true;
									break;
								}
							}
							//Create publisher if they couldn't be found in the list
							if(!exists)
							{
								pub = PublisherFactory.createPublisher(pub_id);
								listOfPublishers.add(pub);
								pub.publish(event);
							}
						}
						//Only the publisher ID parameter was given, so we just create the publisher if they don't exist already
						else {
							//Try finding the publisher
							Iterator<AbstractPublisher> pubItr = listOfPublishers.iterator();
							AbstractPublisher pub;

							boolean exists = false;
							while(pubItr.hasNext()) {
								pub = pubItr.next();
								if(pub.getID() == pub_id) {

									pub.publish();
									exists = true;
									break;
								}
							}
							//Create publisher if they couldn't be found in the list
							if(!exists)
							{
								pub = PublisherFactory.createPublisher(pub_id);
								listOfPublishers.add(pub);
							}
						}
					}
					//For blocking a subscriber from a channel
					else if(firstWord.equals("BLOCK")) {
						System.out.println("");
						sub_id = Integer.parseInt(st.nextToken());
						channel_name = st.nextToken();
						
						//Block the subscriber using the AdministrationServer
						AdministrationServer adminServer = new AdministrationServer();
						adminServer.block(sub_id, channel_name, listOfSubscribers);
					}
					//For unblocking a subscriber from a channel
					else if(firstWord.equals("UNBLOCK")) {
						System.out.println("");
						sub_id = Integer.parseInt(st.nextToken());
						channel_name = st.nextToken();

						//Unblock the subscriber using the AdministrationServer
						AdministrationServer adminServer = new AdministrationServer();
						adminServer.unBlock(sub_id, channel_name, listOfSubscribers);
					}
					//If the first word isn't any of the actions, there is an error
					else {
						System.out.println("Error reading driver.txt!");
						break;
					}
					//Read the next line in the text file
					newLine = driver.readLine();
				}
				//Close upon reading the entire line
				driver.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	private List<AbstractPublisher> createPublishers() throws IOException{
		//Initiate a list to store the publishers
		List<AbstractPublisher> listOfPublishers = new ArrayList<>();
		AbstractPublisher newPub;
		BufferedReader StrategyBufferedReader = new BufferedReader(new FileReader(new File("Strategies.str")));
		while(StrategyBufferedReader.ready()) {
			String PublisherConfigLine = StrategyBufferedReader.readLine();// get the values 
			String[] PublisherConfigArray = PublisherConfigLine.split("\t");
			int[] PublisherConfigIntArray = new int[2];// store the values in an array
			// covert from string to integer
			for(int i = 0; i < PublisherConfigArray.length; i++)
				PublisherConfigIntArray[i] = Integer.parseInt(PublisherConfigArray[i]);

			// if this is the first publisher that is being created, add it to the list 
			if(listOfPublishers.isEmpty()) {
				newPub = PublisherFactory.createPublisher(
						PublisherType.values()[PublisherConfigIntArray[0]],
						StrategyName.values()[PublisherConfigIntArray[1]],
						PublisherConfigIntArray[0]);
				listOfPublishers.add(newPub);
			} else {	
				// boolean to see if the publisher exists
				boolean exists = false;
				// if it exists set the strategy to the one given
				for(AbstractPublisher pub: listOfPublishers) {
					if(pub.getID() == PublisherConfigIntArray[0]) {
						pub.setStrategy(StrategyName.values()[PublisherConfigIntArray[1]]);
						exists = true;
						break;
					}
				}
				// if publisher doesn't exist, add it 
				if(!exists) {
					newPub = PublisherFactory.createPublisher(
							PublisherType.values()[PublisherConfigIntArray[0]],
							StrategyName.values()[PublisherConfigIntArray[1]],
							PublisherConfigIntArray[0]);
					listOfPublishers.add(newPub);
				}

			}

		}

		StrategyBufferedReader.close();
		return listOfPublishers;
	}

	private List<AbstractSubscriber> createSubscribers() throws IOException{
		// create the list of subscribers 
		List<AbstractSubscriber> listOfSubscribers = new ArrayList<>();
		AbstractSubscriber newSub;
		BufferedReader StateBufferedReader = new BufferedReader(new FileReader(new File("States.sts")));
		//create a counter that keep track of the subscriber type
		int stype =0;
		while(StateBufferedReader.ready()) {
			String StateConfigLine = StateBufferedReader.readLine();// read the line from the file 
			String[] StateConfigArray = StateConfigLine.split("\t");
			int[] StateConfigIntArray = new int[2];
			// store the values in an array 
			for(int i = 0; i < StateConfigArray.length; i++)
				StateConfigIntArray[i] = Integer.parseInt(StateConfigArray[i]);			

			//add the first value in the list
			if(listOfSubscribers.isEmpty()) {
				newSub = SubscriberFactory.createSubscriber(
						SubscriberType.values()[0], 
						StateName.values()[StateConfigIntArray[stype]],
						StateConfigIntArray[0]);
				listOfSubscribers.add(newSub);
			} 
			else 
			{	
				boolean exists = false;// boolean to see if the subscriber exists 
				// check to see if the subscriber exists and if it does update the state
				for(AbstractSubscriber sub: listOfSubscribers) {
					if(sub.getID() == StateConfigIntArray[0]) {
						sub.setState(StateName.values()[StateConfigIntArray[1]]);
						exists = true;
						break;
					}
				}
				if(!exists) {
					// if the subscriber doesn't exist add it to the list
					newSub = SubscriberFactory.createSubscriber(
							SubscriberType.values()[stype], 
							StateName.values()[StateConfigIntArray[1]],
							StateConfigIntArray[0]);
					listOfSubscribers.add(newSub);
				}

			}
			//increment the type 
			stype++;
			//if the type is over 4 set it back to 0
			if(stype>2)
			{
				stype=0;
			}
		}
		StateBufferedReader.close();
		return listOfSubscribers;
	}

}