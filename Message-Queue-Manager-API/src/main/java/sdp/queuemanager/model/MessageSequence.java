package sdp.queuemanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageSequence {

	private String queueName;
	private long nextMessageId;
	private long frontIndex;
}
