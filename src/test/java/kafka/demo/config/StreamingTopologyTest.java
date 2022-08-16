//package kafka.demo.config;
//
//import com.ibm.gbs.schema.Balance;
//import com.ibm.gbs.schema.Customer;
//import com.ibm.gbs.schema.CustomerBalance;
//import com.ibm.gbs.schema.CustomerRefined;
//import io.confluent.kafka.schemaregistry.testutil.MockSchemaRegistry;
//import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
//import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
//import kafka.demo.testdata.DataCustomerBalance;
//import lombok.extern.log4j.Log4j2;
//import org.apache.kafka.clients.consumer.Consumer;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.Serde;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.raft.internals.StringSerde;
//import org.apache.kafka.streams.*;
//import org.apache.kafka.streams.kstream.Consumed;
//import org.apache.kafka.streams.kstream.KStream;
//import org.apache.kafka.streams.kstream.KTable;
//import org.apache.kafka.streams.kstream.internals.ConsumedInternal;
//import org.apache.kafka.streams.test.TestRecord;
//import org.checkerframework.checker.signature.qual.ClassGetSimpleName;
//import org.junit.*;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.kafka.config.StreamsBuilderFactoryBean;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.test.EmbeddedKafkaBroker;
//import org.springframework.kafka.test.context.EmbeddedKafka;
//import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Collections;
//import java.util.Map;
//import java.util.Properties;
//
//import static org.apache.kafka.streams.kstream.Materialized.with;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.kafka.test.utils.KafkaTestUtils.consumerProps;
//
//@Log4j2
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class StreamingTopologyTest {
//
//    private static final String SCHEMA_REGISTRY_SCOPE = CustomerConfig.class.getName();
//    private static final String MOCK_SCHEMA_REGISTRY_URL = "mock://" + SCHEMA_REGISTRY_SCOPE;
//
//
//
//    @Test
//    public void streamTableJoin() {
//
//        final StreamsBuilder streamsBuilder = new StreamsBuilder();
//
//        Serde<Customer> cxSerde = new SpecificAvroSerde<>();
//        Serde<CustomerRefined> cxRefinedSerde = new SpecificAvroSerde<>();
//        Serde<Balance> avroBalanceSerde = new SpecificAvroSerde<>();
//        Serde<CustomerBalance> avroCxBalSerde = new SpecificAvroSerde<>();
//
//        // Configure Serdes to use the same mock schema registry URL
//        Map<String, String> config = Collections.singletonMap(
//                AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, MOCK_SCHEMA_REGISTRY_URL);
//        cxSerde.configure(config, false);
//        cxRefinedSerde.configure(config, false);
//        avroBalanceSerde.configure(config, false);
//        avroCxBalSerde.configure(config, false);
//
//        StreamingTopology streamingTopology = new StreamingTopology();
//
//        Properties properties = new Properties();
//        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "testing");
//        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:9092");
//
//        try(TopologyTestDriver testDriver =
//                    new TopologyTestDriver(streamsBuilder.build(), properties)){
//            final TestInputTopic<String, CustomerRefined> inputTopicUser =
//                    testDriver.createInputTopic("CustomerRefined",
//                            Serdes.String().serializer(),
//                            cxRefinedSerde.serializer());
//            final TestInputTopic<String, Balance> inputTopicBalance =
//                    testDriver.createInputTopic("Balance",
//                            Serdes.String().serializer(),
//                            avroBalanceSerde.serializer());
//
//            final TestOutputTopic<String, CustomerBalance> outputTopic =
//                    testDriver.createOutputTopic("CustomerBalance",
//                            Serdes.String().deserializer(),
//                            avroCxBalSerde.deserializer());
//
//            inputTopicBalance.pipeInput(testcaseBalance1());
//            inputTopicUser.pipeInput(testcaseCustomer1());
//
//            final KTable<String, CustomerRefined> cxKTable =
//                    streamsBuilder.table("CustomerRefined", Consumed.with(Serdes.String(), cxRefinedSerde));
//
//            final KStream<String, Balance> balanceKStream =
//                    streamsBuilder.stream("Balance", Consumed.with(Serdes.String(), avroBalanceSerde));
//
//            KStream<String, CustomerBalance> output = streamingTopology.streamTableJoin(balanceKStream, cxKTable);
//
//
//            Assertions.assertNotNull(output);
//        }
//
//    }
//
//
//    public TestRecord<String, Balance> testcaseBalance1(){
//        return new TestRecord<String, Balance>("123", DataCustomerBalance.balanceData1);
//    }
//
//    public TestRecord<String, Balance> testcaseBalance2(){
//        return new TestRecord<String, Balance>(null, DataCustomerBalance.balanceData2);
//    }
//
//    public TestRecord<String, Balance> testcaseBalance3(){
//        return new TestRecord<String, Balance>("", DataCustomerBalance.balanceData3);
//    }
//
//    public TestRecord<String, Balance> testcaseBalance4(){
//        return new TestRecord<String, Balance>("123", DataCustomerBalance.balanceData4);
//    }
//
//    public TestRecord<String, Balance> testcaseBalance5(){
//        return new TestRecord<String, Balance>("126", DataCustomerBalance.balanceData5);
//    }
//
//    public TestRecord<String, CustomerRefined> testcaseCustomer1(){
//        return new TestRecord<String, CustomerRefined>("123", DataCustomerBalance.cxRefinedData1);
//    }
//
//    public TestRecord<String, CustomerRefined> testcaseCustomer2(){
//        return new TestRecord<String, CustomerRefined>(null, DataCustomerBalance.cxRefinedData2);
//    }
//
//    public TestRecord<String, CustomerRefined> testcaseCustomer3(){
//        return new TestRecord<String, CustomerRefined>("", DataCustomerBalance.cxRefinedData3);
//    }
//
//}