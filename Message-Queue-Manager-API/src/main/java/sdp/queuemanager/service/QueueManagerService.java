package sdp.queuemanager.service;

import java.util.List;

import sdp.queuemanager.exception.QueueManagerException;
import sdp.queuemanager.model.Message;
import sdp.queuemanager.model.QueueInfo;

public interface QueueManagerService {

	public QueueInfo createMessageQueue(String queueName) throws QueueManagerException;

	public List<QueueInfo> retrieveMessageQueues();

	public Message publishMessageToQueue(Message message) throws QueueManagerException;

	public List<Message> retrieveMessages(String queueName);
	
	public List<Message> retrieveMessagesByPage(String queueName, int page, int pageSize);

	public Message removeMessageFromQueue(String queueName);
	
	public boolean deleteMessageQueue(String queueName);

}
