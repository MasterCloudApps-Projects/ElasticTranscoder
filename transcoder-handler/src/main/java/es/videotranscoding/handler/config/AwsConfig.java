package es.videotranscoding.handler.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.QueueMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(basePackages = "es.videotranscoding.handler.repository")
@EnableAutoConfiguration(exclude = { ContextInstanceDataAutoConfiguration.class })
@EnableSqs
public class AwsConfig {

	@Value("${cloud.aws.region.static}")
	private String awsRegion;

	public AmazonSQSAsync amazonSQSAsync() {
		return AmazonSQSAsyncClientBuilder.standard().withRegion(awsRegion).build();
	}

	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		return AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_WEST_1).build();
	}

	@Bean
	public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync) {
		return new QueueMessagingTemplate(amazonSQSAsync());
	}

	@Bean
	public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory() {
		SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
		factory.setAmazonSqs(amazonSQSAsync());
		factory.setMaxNumberOfMessages(1);
		factory.setQueueMessageHandler(new QueueMessageHandler());
		factory.setWaitTimeOut(20);
		return factory;
	}

}