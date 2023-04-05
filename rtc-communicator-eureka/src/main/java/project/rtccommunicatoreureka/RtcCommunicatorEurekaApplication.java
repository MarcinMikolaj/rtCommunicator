package project.rtccommunicatoreureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class RtcCommunicatorEurekaApplication {
	public static void main(String[] args) {
		SpringApplication.run(RtcCommunicatorEurekaApplication.class, args);
	}
}
