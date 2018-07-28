package br.com.crisaltmann;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

public class MessageSenderString {

	private String topicName;
	
	private String server;
	
	public MessageSenderString(String topicName, String server) {
		this.topicName = topicName;
		this.server = server;
	}
	
	private KafkaProducer<String, String> producer;
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		new MessageSenderString("teste-msg", "localhost:9092").enviarMensagens();
	}
	
	private void enviarMensagens() throws InterruptedException, ExecutionException {
		conectarKafka();
		
		try {
			for (int i = 0; i < 100 ; i++)
				enviarMsgAleatoria(i);
		} finally {
			producer.close();
		}
	}

	private void enviarMsgAleatoria(int key) throws InterruptedException, ExecutionException {
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(
				topicName, String.valueOf(key), String.valueOf(Math.random()));
		
		Future<RecordMetadata> future = producer.send(record);
		RecordMetadata meta = future.get();
		System.out.println(meta);
	}

	private void conectarKafka() {
		producer = createProducer(createProperties());
		
	}
	
	private Properties createProperties() {
		Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "kafka-producer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return props;
	}
	
	private KafkaProducer<String, String> createProducer(Properties prop) {
		return new KafkaProducer<String, String>(prop);
	}
}
