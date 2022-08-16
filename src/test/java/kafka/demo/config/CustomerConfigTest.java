//package kafka.demo.config;
//
//import com.ibm.gbs.schema.Customer;
//import com.ibm.gbs.schema.CustomerRefined;
//import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
//import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
//import kafka.demo.testdata.DataCustomerBalance;
//import lombok.extern.log4j.Log4j2;
//import org.apache.kafka.common.serialization.Serde;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.*;
//import org.apache.kafka.streams.kstream.Consumed;
//import org.apache.kafka.streams.kstream.KStream;
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import java.util.Collections;
//import java.util.Map;
//import java.util.Properties;
//
//
//@Log4j2
//@RunWith(SpringRunner.class)
//@SpringBootTest
////(
////        webEnvironment = SpringBootTest.WebEnvironment.NONE,
////        properties = {"server.port = 0"}
////)
//class CustomerConfigTest {
//
//    private static final String SCHEMA_REGISTRY_SCOPE = CustomerConfig.class.getName();
//    private static final String MOCK_SCHEMA_REGISTRY_URL = "mock://" + SCHEMA_REGISTRY_SCOPE;
//
//    @Test
//    public void createCustomerKStream() {
//
//        final StreamsBuilder streamsBuilder = new StreamsBuilder();
//
//        Serde<Customer> cxSerde = new SpecificAvroSerde<>();
//        Serde<CustomerRefined> cxRefinedSerde = new SpecificAvroSerde<>();
//
//        // Configure Serdes to use the same mock schema registry URL
//        Map<String, String> config = Collections.singletonMap(
//                AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, MOCK_SCHEMA_REGISTRY_URL);
//        cxSerde.configure(config, false);
//        cxRefinedSerde.configure(config, false);
//
//
//        final KStream<String, Customer> cxStream =
//                streamsBuilder.stream("Customer", Consumed.with(Serdes.String(), cxSerde));
//
//        final KStream<String, CustomerRefined> customerRefineFunction =
//                new CustomerConfig().customerKStream();
//
//        Properties properties = new Properties();
//        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "testing");
//        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:9092");
//
//        try(TopologyTestDriver testDriver =
//                    new TopologyTestDriver(streamsBuilder.build(), properties)){
//            final TestInputTopic<String, Customer> inputTopic =
//                    testDriver.createInputTopic("Customer",
//                            Serdes.String().serializer(),
//                            cxSerde.serializer());
//
//            inputTopic.pipeInput("152", DataCustomerBalance.customerData1);
//
//            final TestOutputTopic<String, CustomerRefined> outputTopic =
//                    testDriver.createOutputTopic("CustomerRefined",
//                            Serdes.String().deserializer(),
//                            cxRefinedSerde.deserializer());
//
//            final KeyValue<String, CustomerRefined>
//                    keyValue = outputTopic.readKeyValue();
//
//            Assertions.assertEquals(
//                    DataCustomerBalance.cxRefinedData1.getAccountId(),
//                    keyValue.key);
//        }
//
//    }
//
//
//
//}