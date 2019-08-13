package sdp.queuemanager.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import sdp.queuemanager.constant.ErrorCodes;
import sdp.queuemanager.constant.QueueManagerConstants;
import sdp.queuemanager.exception.QueueManagerException;
import sdp.queuemanager.model.Message;
import sdp.queuemanager.model.MessageSequence;
import sdp.queuemanager.model.QueueInfo;
import sdp.queuemanager.model.QueueSequence;

@Service
public class QueueManagerServiceImpl implements QueueManagerService {

	private static final Logger LOG = LoggerFactory.getLogger(QueueManagerServiceImpl.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public QueueInfo createMessageQueue(String queueName) throws QueueManagerException {
		if (isQueueExist(queueName)) {
			LOG.info("Duplicate queue name: " + queueName + " > " + ErrorCodes.DUPLICATE);
			throw new QueueManagerException("Duplicate queue name: " + queueName, ErrorCodes.DUPLICATE,
					HttpStatus.BAD_REQUEST);
		}
		return mongoTemplate.insert(new QueueInfo(String.valueOf(getNextQueueId()), queueName),
				QueueManagerConstants.QUEUE_COLLECTION);
	}

	@Override
	public List<QueueInfo> retrieveMessageQueues() {
		return mongoTemplate.findAll(QueueInfo.class, QueueManagerConstants.QUEUE_COLLECTION);
	}

	@Override
	public Message publishMessageToQueue(Message message) throws QueueManagerException {
		if (!isQueueExist(message.getQueueName())) {
			LOG.info("Queue does not exist: " + message.getQueueName() + " > " + ErrorCodes.NOT_EXIST);
			throw new QueueManagerException("Queue does not exist: " + message.getQueueName(), ErrorCodes.NOT_EXIST,
					HttpStatus.BAD_REQUEST);
		}
		if (isMessageExist(message)) {
			LOG.info("Duplicate message : " + message.getMessage() + " > " + ErrorCodes.DUPLICATE);
			throw new QueueManagerException("Duplicate message : " + message.getMessage(), ErrorCodes.DUPLICATE,
					HttpStatus.BAD_REQUEST);
		}
		message.setMessageId(String.valueOf(getNextMessageId(message.getQueueName())));
		return mongoTemplate.insert(message, QueueManagerConstants.MESSAGE_COLLECTION);
	}

	@Override
	public List<Message> retrieveMessages(String queueName) {
		if (!isQueueExist(queueName)) {
			LOG.info("Queue does not exist: " + queueName + " > " + ErrorCodes.NOT_EXIST);
			throw new QueueManagerException("Queue does not exist: " + queueName, ErrorCodes.NOT_EXIST,
					HttpStatus.BAD_REQUEST);
		}
		Query query = new Query();
		query.addCriteria(Criteria.where(QueueManagerConstants.QUEUE_NAME).is(queueName));
		return mongoTemplate.find(query, Message.class, QueueManagerConstants.MESSAGE_COLLECTION);
	}

	@Override
	public List<Message> retrieveMessagesByPage(String queueName, int page, int pageSize) {
		if (!isQueueExist(queueName)) {
			LOG.info("Queue does not exist: " + queueName + " > " + ErrorCodes.NOT_EXIST);
			throw new QueueManagerException("Queue does not exist: " + queueName, ErrorCodes.NOT_EXIST,
					HttpStatus.BAD_REQUEST);
		}
		Query query = new Query();
		query.addCriteria(Criteria.where(QueueManagerConstants.QUEUE_NAME).is(queueName));
		query.with(PageRequest.of(page, pageSize));
		return mongoTemplate.find(query, Message.class, QueueManagerConstants.MESSAGE_COLLECTION);
	}

	@Override
	public Message removeMessageFromQueue(String queueName) {
		Query query = new Query();
		query.addCriteria(Criteria.where(QueueManagerConstants.QUEUE_NAME).is(queueName));
		query.addCriteria(
				Criteria.where(QueueManagerConstants.MESSAGE_ID).is(String.valueOf(getFrontIndex(queueName))));
		return mongoTemplate.findAndRemove(query, Message.class, QueueManagerConstants.MESSAGE_COLLECTION);
	}

	@Override
	public boolean deleteMessageQueue(String queueName) {
		Query query = new Query();
		query.addCriteria(Criteria.where(QueueManagerConstants.QUEUE_NAME).is(queueName));
		return mongoTemplate.remove(query, QueueManagerConstants.MESSAGE_COLLECTION).wasAcknowledged()
				&& mongoTemplate.remove(query, QueueManagerConstants.QUEUE_COLLECTION).wasAcknowledged()
				&& mongoTemplate.remove(query, QueueManagerConstants.SEQUENCE_COLLECTION).wasAcknowledged();
	}

	private boolean isQueueExist(String queueName) {
		Query query = new Query();
		query.addCriteria(Criteria.where(QueueManagerConstants.QUEUE_NAME).is(queueName));
		if (CollectionUtils
				.isEmpty(mongoTemplate.find(query, QueueInfo.class, QueueManagerConstants.QUEUE_COLLECTION))) {
			return false;
		}
		return true;
	}

	private boolean isMessageExist(Message message) {
		Query query = new Query();
		query.addCriteria(Criteria.where(QueueManagerConstants.QUEUE_NAME).is(message.getQueueName()));
		query.addCriteria(Criteria.where(QueueManagerConstants.MESSAGE).is(message.getMessage()));
		if (CollectionUtils
				.isEmpty(mongoTemplate.find(query, Message.class, QueueManagerConstants.MESSAGE_COLLECTION))) {
			return false;
		}
		return true;
	}

	private long getNextQueueId() {
		Query query = new Query();
		query.addCriteria(Criteria.where(QueueManagerConstants.SEQUENEC_NAME).is(QueueManagerConstants.QUEUE_SEQ));

		Update update = new Update();
		update.inc(QueueManagerConstants.NEXT_QUEUE_ID);

		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);

		return mongoTemplate
				.findAndModify(query, update, options, QueueSequence.class, QueueManagerConstants.SEQUENCE_COLLECTION)
				.getNextQueueId();
	}

	private long getNextMessageId(String queueName) {
		if (CollectionUtils
				.isEmpty(mongoTemplate.find(Query.query(Criteria.where(QueueManagerConstants.QUEUE_NAME).is(queueName)),
						MessageSequence.class, QueueManagerConstants.SEQUENCE_COLLECTION))) {
			mongoTemplate.insert(new MessageSequence(queueName, 0L, 1L), QueueManagerConstants.SEQUENCE_COLLECTION);
		}
		Query query = new Query();
		query.addCriteria(Criteria.where(QueueManagerConstants.QUEUE_NAME).is(queueName));

		Update update = new Update();
		update.inc(QueueManagerConstants.NEXT_MESSAGE_ID);

		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);

		return mongoTemplate
				.findAndModify(query, update, options, MessageSequence.class, QueueManagerConstants.SEQUENCE_COLLECTION)
				.getNextMessageId();
	}

	private long getFrontIndex(String queueName) {
		Query query = new Query();
		query.addCriteria(Criteria.where(QueueManagerConstants.QUEUE_NAME).is(queueName));

		Update update = new Update();
		update.inc(QueueManagerConstants.FRONT_INDEX);

		return mongoTemplate
				.findAndModify(query, update, MessageSequence.class, QueueManagerConstants.SEQUENCE_COLLECTION)
				.getFrontIndex();
	}

}
