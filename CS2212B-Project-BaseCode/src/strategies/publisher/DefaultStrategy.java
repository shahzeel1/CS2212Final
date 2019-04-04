package strategies.publisher;
import events.AbstractEvent;

public class DefaultStrategy extends AbstractStrategy{
	protected DefaultStrategy (int publisherId)
	{
		super(publisherId);
	}
	
	protected DefaultStrategy(int publisherId, AbstractEvent event)
	{
		super(publisherId, event);
	}

}