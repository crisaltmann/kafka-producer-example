package br.com.crisaltmann.stock;

import java.util.List;
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
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

public class MessageSenderJson {

	private static final String TOPIC = "cotacoes";

	private static final String SERVER = "localhost:9092";
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private KafkaProducer<String, String> producer;
	
	{
		mapper.registerModule(new JSR310Module());
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException, JsonProcessingException {
		new MessageSenderJson().sendMessages();
	}
	
	private void sendMessages() throws InterruptedException, ExecutionException, JsonProcessingException {
		conectKafka();
		List<Stock> stocks = new CsvStockParser().parse();
		try {
			stocks.stream().forEach(stock -> sendMessage(stock));
		} finally {
			producer.close();
		}
	}

	private void sendMessage(Stock stock) {
		ProducerRecord<String, String> record;
		try {
			record = createRecord(stock);
			Future<RecordMetadata> future = producer.send(record);
			RecordMetadata meta = future.get();
			System.out.println(meta);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private ProducerRecord<String, String> createRecord(Stock stock) throws JsonProcessingException {
		return new ProducerRecord<String, String>(TOPIC, String.valueOf(stock.getCode()), mapper.writeValueAsString(stock));
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
