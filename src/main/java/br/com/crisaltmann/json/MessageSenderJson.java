package br.com.crisaltmann.json;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageSenderJson {

	private static final String TOPIC = "teste-json";

	private static final String SERVER = "localhost:9092";
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private KafkaProducer<String, String> producer;
	
	public static void main(String[] args) throws InterruptedException, ExecutionException, JsonProcessingException {
		new MessageSenderJson().sendMessages();
	}
	
	private void sendMessages() throws InterruptedException, ExecutionException, JsonProcessingException {
		conectKafka();
		
		try {
			for (int i = 0; i < 100 ; i++)
				sendRandomMsg(i);
		} finally {
			producer.close();
		}
	}

	private void sendRandomMsg(int key) throws InterruptedException, ExecutionException, JsonProcessingException {
		ProducerRecord<String, String> record = createExemploRecord(String.valueOf(key));
		
		Future<RecordMetadata> future = producer.send(record);
		RecordMetadata meta = future.get();
		System.out.println(meta);
	}
	
	private ProducerRecord<String, String> createExemploRecord(String key) throws JsonProcessingException {
		Exemplo exemplo = new Exemplo(key, String.valueOf(Math.random()));
		return new ProducerRecord<String, String>(
				TOPIC, String.valueOf(key), mapper.writeValueAsString(exemplo));
	}

	private void conectKafka() {
		producer = createProducer(createProperties());
	}
	
	private Properties createProperties() {
		Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVER);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "kafka-producer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return props;
	}
	
	private KafkaProducer<String, String> createProducer(Properties prop) {
		return new KafkaProducer<String, String>(prop);
	}
}
