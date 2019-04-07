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

		List<AbstractPublisher> listOfPublishers = new ArrayList<>();
		List<AbstractSubscriber> listOfSubscribers = new ArrayList<>();
		Orchestration testHarness = new Orchestration();

		try {
			listOfPublishers = testHarness.createPublishers();
			System.out.println("");
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
		
		//Method to parse the driver.txt file
		BufferedReader driver;
		try {
			driver = new BufferedReader(new FileReader(new File("driver.txt")));
			String newLine;
			try {
				newLine = driver.readLine();
				while(newLine != null) {

					String action;
					int pub_id;
					int sub_id;
					String event_type;
					String event_header;
					String event_payload;
					String channel_name;

					StringTokenizer st = new StringTokenizer(newLine);
					String firstWord = st.nextToken();

					if (firstWord.equals("SUB")) {
						action = "subscribe";
						sub_id = Integer.parseInt(st.nextToken());
						channel_name = st.nextToken();

						SubscriptionManager subManager = SubscriptionManager.getInstance();

						Iterator<AbstractSubscriber> it = listOfSubscribers.iterator();
						AbstractSubscriber sub;

						while(it.hasNext()) {
							sub = it.next();
							if(sub.getID() == sub_id) {
								subManager.subscribe(channel_name, sub);
								break;
							}
						}
					}
					else if(firstWord.equals("PUB")) {
						System.out.println("");
						action = "publish";
						pub_id = Integer.parseInt(st.nextToken());
						//if MORE parameters are provided
						if(st.hasMoreTokens()) {
							event_type = st.nextToken();
							event_header = st.nextToken();
							event_payload = st.nextToken();
							//Create the event
							EventMessage msg = new EventMessage(event_header, event_payload);
							EventType type;
							switch(event_type) {
							case "TypeA": 
								type = EventType.values()[2];
							case "TypeB": 
								type = EventType.values()[1];
							default: 
								type = EventType.values()[0];
							}
							AbstractEvent event = EventFactory.createEvent(type, pub_id, msg);

							//Find the publisher

							Iterator<AbstractPublisher> pubItr = listOfPublishers.iterator();
							AbstractPublisher pub;

							while(pubItr.hasNext()) {
								pub = pubItr.next();
								if(pub.getID() == pub_id) {

									pub.publish(event);
									break;
								}
							}
						}

						else {
							// Creation of a publisher

							AbstractPublisher publisher = PublisherFactory.createPublisher(pub_id);
							listOfPublishers.add(publisher);
						}
					}
					else if(firstWord.equals("BLOCK")) {
						action = "block";
						sub_id = Integer.parseInt(st.nextToken());
						channel_name = st.nextToken();

						AdministrationServer adminServer = new AdministrationServer();
						adminServer.block(sub_id, channel_name, listOfSubscribers);
					}
					else if(firstWord.equals("UNBLOCK")) {
						action = "unblock";
						sub_id = Integer.parseInt(st.nextToken());
						channel_name = st.nextToken();

						AdministrationServer adminServer = new AdministrationServer();
						adminServer.unBlock(sub_id, channel_name, listOfSubscribers);
					}
					else {
						System.out.println("Error reading driver.txt!");
						break;
					}
					newLine = driver.readLine();
				}
				driver.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


	private List<AbstractPublisher> createPublishers() throws IOException{
		List<AbstractPublisher> listOfPublishers = new ArrayList<>();
		AbstractPublisher newPub;
		BufferedReader StrategyBufferedReader = new BufferedReader(new FileReader(new File("Strategies.str")));
		while(StrategyBufferedReader.ready()) {
			String PublisherConfigLine = StrategyBufferedReader.readLine();
			String[] PublisherConfigArray = PublisherConfigLine.split("\t");
			int[] PublisherConfigIntArray = new int[2];
			for(int i = 0; i < PublisherConfigArray.length; i++)
				PublisherConfigIntArray[i] = Integer.parseInt(PublisherConfigArray[i]);

			if(listOfPublishers.isEmpty()) {
				newPub = PublisherFactory.createPublisher(
						PublisherType.values()[PublisherConfigIntArray[0]],
						StrategyName.values()[PublisherConfigIntArray[1]],
						PublisherConfigIntArray[0]);
				listOfPublishers.add(newPub);
			} else {				
				boolean exists = false;
				for(AbstractPublisher pub: listOfPublishers) {
					if(pub.getID() == PublisherConfigIntArray[0]) {
						pub.setStrategy(StrategyName.values()[PublisherConfigIntArray[1]]);
						exists = true;
						break;
					}
				}
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
		List<AbstractSubscriber> listOfSubscribers = new ArrayList<>();
		AbstractSubscriber newSub;
		BufferedReader StateBufferedReader = new BufferedReader(new FileReader(new File("States.sts")));
		while(StateBufferedReader.ready()) {
			String StateConfigLine = StateBufferedReader.readLine();
			String[] StateConfigArray = StateConfigLine.split("\t");
			int[] StateConfigIntArray = new int[2];
			for(int i = 0; i < StateConfigArray.length; i++)
				StateConfigIntArray[i] = Integer.parseInt(StateConfigArray[i]);			

			if(listOfSubscribers.isEmpty()) {
				newSub = SubscriberFactory.createSubscriber(
						SubscriberType.values()[StateConfigIntArray[0]], 
						StateName.values()[StateConfigIntArray[1]],
						StateConfigIntArray[0]);
				listOfSubscribers.add(newSub);
			} else {				
				boolean exists = false;
				for(AbstractSubscriber sub: listOfSubscribers) {
					if(sub.getID() == StateConfigIntArray[0]) {
						sub.setState(StateName.values()[StateConfigIntArray[1]]);
						exists = true;
						break;
					}
				}
				if(!exists) {
					newSub = SubscriberFactory.createSubscriber(
							SubscriberType.values()[StateConfigIntArray[0]], 
							StateName.values()[StateConfigIntArray[1]],
							StateConfigIntArray[0]);
					listOfSubscribers.add(newSub);
				}
			}
		}
		StateBufferedReader.close();
		return listOfSubscribers;
	}

}