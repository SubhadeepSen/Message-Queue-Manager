package sdp.queuemanager.init;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import sdp.queuemanager.constant.QueueManagerConstants;
import sdp.queuemanager.model.QueueSequence;

@Component
public class DBInitializer {

	@Autowired
	private MongoTemplate mongoTemplate;

	@PostConstruct
	public void createCollections() {
		if (!mongoTemplate.collectionExists(QueueManagerConstants.QUEUE_COLLECTION)) {
			mongoTemplate.createCollection(QueueManagerConstants.QUEUE_COLLECTION);
		}
		if (!mongoTemplate.collectionExists(QueueManagerConstants.MESSAGE_COLLECTION)) {
			mongoTemplate.createCollection(QueueManagerConstants.MESSAGE_COLLECTION);
		}
		if (!mongoTemplate.collectionExists(QueueManagerConstants.SEQUENCE_COLLECTION)) {
			mongoTemplate.createCollection(QueueManagerConstants.SEQUENCE_COLLECTION);
			mongoTemplate.insert(new QueueSequence(QueueManagerConstants.QUEUE_SEQ, 0L),
					QueueManagerConstants.SEQUENCE_COLLECTION);
		}
	}
}
