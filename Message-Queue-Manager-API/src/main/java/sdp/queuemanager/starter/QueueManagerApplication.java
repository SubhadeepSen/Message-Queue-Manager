package sdp.queuemanager.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "sdp.queuemanager.*")
public class QueueManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(QueueManagerApplication.class, args);
	}

}
