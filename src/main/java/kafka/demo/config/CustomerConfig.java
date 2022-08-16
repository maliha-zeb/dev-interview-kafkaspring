package kafka.demo.config;

import com.ibm.gbs.schema.Balance;
import com.ibm.gbs.schema.Customer;
import com.ibm.gbs.schema.CustomerBalance;
import com.ibm.gbs.schema.CustomerRefined;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.Map;
@Component
@Log4j2
@Order(2)
@Configuration
public class CustomerConfig {
    @Autowired
    private StreamsBuilder streamsBuilder;

    @Bean
    public KStream<String, CustomerRefined> customerKStream() {
        final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url",
                "http://localhost:8081");

        final Serde<Customer> customerSpecificAvroSerde = new SpecificAvroSerde<>();
        customerSpecificAvroSerde.configure(serdeConfig, false);
        final Serde<CustomerRefined> cxRefinedSpecificAvroSerde = new SpecificAvroSerde<>();
        cxRefinedSpecificAvroSerde.configure(serdeConfig, false);


       KStream<String, Customer> customerStream = streamsBuilder.stream("Customer", Consumed.with(Serdes.String(), customerSpecificAvroSerde));


       customerStream.peek((k,v) -> log.info("Key : " + k + "      value : " + v));

//        KStream<String, Customer> rawCusStream =
//                customerStream.selectKey((k,v)->v.getAccountId());
//
//        KGroupedStream<String, Customer> table = rawCusStream.groupByKey();

        KStream<String, CustomerRefined> rawCusStream =
                customerStream.selectKey((k,v)->v.getAccountId())
                        .mapValues((k,v)->
                                CustomerRefined.newBuilder()
                                        .setCustomerId(v.getCustomerId())
                                        .setPhoneNumber(v.getPhoneNumber())
                                        .setAccountId(v.getAccountId())
                                        .build())
                        .peek((k,v) -> log.info("Key : " + k + "      value : " + v));

        return rawCusStream;

    }


}
