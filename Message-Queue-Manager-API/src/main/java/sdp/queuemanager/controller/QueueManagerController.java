package sdp.queuemanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import sdp.queuemanager.model.Message;
import sdp.queuemanager.model.QueueInfo;
import sdp.queuemanager.service.QueueManagerServiceImpl;

@RestController
public class QueueManagerController {

	@Autowired
	private QueueManagerServiceImpl queueManagerService;

	@ApiOperation(value = "Create Message Queue")
	@ApiImplicitParam(required = true, name = "Authorization", paramType = "header")
	@RequestMapping(value = "/queue/{queueName}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public QueueInfo createMessageQueue(@PathVariable String queueName) {
		return queueManagerService.createMessageQueue(queueName);
	}

	@ApiOperation(value = "Retrieve All Message Queues")
	@ApiImplicitParam(required = true, name = "Authorization", paramType = "header")
	@RequestMapping(value = "/queues", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<QueueInfo> retrieveAllMessageQueues() {
		return queueManagerService.retrieveMessageQueues();
	}

	@ApiOperation(value = "Publish Message To Queue")
	@ApiImplicitParam(required = true, name = "Authorization", paramType = "header")
	@RequestMapping(value = "/message", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Message publishMessageToQueue(@RequestBody Message message) {
		return queueManagerService.publishMessageToQueue(message);
	}

	@ApiOperation(value = "Retreive Messages From Queue")
	@ApiImplicitParam(required = true, name = "Authorization", paramType = "header")
	@RequestMapping(value = "/messages/{queueName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Message> retrieveAllMessages(@PathVariable String queueName) {
		return queueManagerService.retrieveMessages(queueName);
	}

	@ApiOperation(value = "Retreive Messages From Queue in Chunks")
	@ApiImplicitParam(required = true, name = "Authorization", paramType = "header")
	@RequestMapping(value = "/messages/{queueName}/{page}/{pageSize}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Message> retrieveAllMessagesByPage(@PathVariable String queueName, @PathVariable int page,
			@PathVariable int pageSize) {
		return queueManagerService.retrieveMessagesByPage(queueName, page, pageSize);
	}

	@ApiOperation(value = "Consume Message From Queue")
	@ApiImplicitParam(required = true, name = "Authorization", paramType = "header")
	@RequestMapping(value = "/message/{queueName}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Message removeMessageFromQueue(@PathVariable String queueName) {
		return queueManagerService.removeMessageFromQueue(queueName);
	}

	@ApiOperation(value = "Delete Queue")
	@ApiImplicitParam(required = true, name = "Authorization", paramType = "header")
	@RequestMapping(value = "/queue/{queueName}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean deleteMessageQueue(@PathVariable String queueName) {
		return queueManagerService.deleteMessageQueue(queueName);
	}
}
