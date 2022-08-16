//package kafka.demo.config;
//
//import com.ibm.gbs.schema.Balance;
//import io.confluent.kafka.schemaregistry.testutil.MockSchemaRegistry;
//import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
//import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
//import kafka.demo.testdata.DataCustomerBalance;
//import lombok.extern.log4j.Log4j2;
//import org.apache.kafka.common.serialization.Serde;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.raft.internals.StringSerde;
//import org.apache.kafka.streams.*;
//import org.apache.kafka.streams.test.TestRecord;
//import org.junit.Test;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Collections;
//import java.util.Map;
//import java.util.Properties;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//@Log4j2
//@RunWith(SpringRunner.class)
//@SpringBootTest(
//        webEnvironment = SpringBootTest.WebEnvironment.NONE,
//        properties = {"server.port = 0"}
//)
//public class BalanceConfigTest {
//
//
//        private static final String SCHEMA_REGISTRY_SCOPE = BalanceConfigTest.class.getName();
//        private static final String MOCK_SCHEMA_REGISTRY_URL = "mock://" + SCHEMA_REGISTRY_SCOPE;
//
//        private TopologyTestDriver testDriver;
//
//        private TestInputTopic<String, Balance> inBalanceTopic;
//        private TestOutputTopic<String, Balance> outBalanceTopic;
//
//        @BeforeEach
//        void beforeEach() throws Exception {
//            // Create topology to handle stream of users
//            StreamsBuilder builder = new StreamsBuilder();
//            new BalanceConfig().balanceKStream();
//            Topology topology = builder.build();
//
//            // Dummy properties needed for test diver
//            Properties props = new Properties();
//            props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test");
//            props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
//            props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, StringSerde.class);
//            props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
//            props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, MOCK_SCHEMA_REGISTRY_URL);
//
//            // Create test driver
//            testDriver = new TopologyTestDriver(topology, props);
//
//            // Create Serdes used for test record keys and values
//            Serde<String> stringSerde = Serdes.String();
//            Serde<Balance> avroBalanceSerde = new SpecificAvroSerde<>();
//
//            // Configure Serdes to use the same mock schema registry URL
//            Map<String, String> config = Collections.singletonMap(
//                    AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, MOCK_SCHEMA_REGISTRY_URL);
//            avroBalanceSerde.configure(config, false);
//
//            // Define input and output topics to use in tests
//            inBalanceTopic = testDriver.createInputTopic(
//                    "Balance",
//                    stringSerde.serializer(),
//                    avroBalanceSerde.serializer());
//            outBalanceTopic = testDriver.createOutputTopic(
//                    "BalanceRefined",
//                    stringSerde.deserializer(),
//                    avroBalanceSerde.deserializer());
//        }
//
//        @AfterEach
//        void afterEach() {
//            testDriver.close();
//            MockSchemaRegistry.dropScope(SCHEMA_REGISTRY_SCOPE);
//        }
//
//        @Test
//        void testStream() throws Exception {
//
//            inBalanceTopic.pipeInput(testcase1());
//
//            KeyValue<String, Balance> output = outBalanceTopic.readKeyValue();
//
//            assertEquals(DataCustomerBalance.balanceData1.getAccountId(), output.key );
//        }
//
//        public TestRecord<String, Balance> testcase1(){
//            return new TestRecord<String, Balance>("123", DataCustomerBalance.balanceData1);
//        }
//
//        public TestRecord<String, Balance> testcase2(){
//            return new TestRecord<String, Balance>(null, DataCustomerBalance.balanceData2);
//        }
//
//        public TestRecord<String, Balance> testcase3(){
//            return new TestRecord<String, Balance>("", DataCustomerBalance.balanceData3);
//        }
//
//        public TestRecord<String, Balance> testcase4(){
//            return new TestRecord<String, Balance>("123", DataCustomerBalance.balanceData4);
//        }
//
//        public TestRecord<String, Balance> testcase5(){
//            return new TestRecord<String, Balance>("126", DataCustomerBalance.balanceData5);
//        }
//
//
//
//
//
//}
//
