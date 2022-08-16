package kafka.demo.config;

import com.ibm.gbs.schema.Balance;
import com.ibm.gbs.schema.Customer;
import com.ibm.gbs.schema.CustomerBalance;
import com.ibm.gbs.schema.CustomerRefined;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import kafka.demo.service.CustomerBalanceBuilder;
import kafka.demo.service.InvalidBalanceException;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

@Component
@Log4j2
@Order(3)
@Configuration
public class StreamingTopology {
//
//    @Autowired
//    private Properties props;

    @Autowired
    private  BalanceConfig balanceConfig;

    @Autowired
    private CustomerConfig customerConfig;


    @Bean
    public KStream<String, CustomerBalance> cxBalKStream(){
        final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url",
                "http://localhost:8081");

        final Serde<Balance> balanceSpecificAvroSerde = new SpecificAvroSerde<>();
        final Serde<CustomerRefined> cxRefinedSpecificAvroSerde = new SpecificAvroSerde<>();
        final Serde<CustomerBalance> cxBalSpecificAvroSerde = new SpecificAvroSerde<>();
        balanceSpecificAvroSerde.configure(serdeConfig, false);

        cxRefinedSpecificAvroSerde.configure(serdeConfig, false);

        cxBalSpecificAvroSerde.configure(serdeConfig, false);



        KStream<String, Balance> balanceStream = balanceConfig.balanceKStream();
        balanceStream.peek((k, v) -> log.info("out of balance config: Key : " + k + "      value : " + v));

        KTable<String, CustomerRefined> customerTable = customerConfig.customerKStream().toTable(Named.as("Table"));

        //  joining stream + table
        KStream<String, CustomerBalance> newStream = balanceStream.join(customerTable,
                        (v1,v2) -> new CustomerBalanceBuilder().apply(v1,v2),
                        Joined.with(Serdes.String(), balanceSpecificAvroSerde, cxRefinedSpecificAvroSerde))
                .peek((k,v) -> log.info("new joined stream + table: Key : " + k + "      value : " + v))
                .through("CustomerBalance", Produced.with(Serdes.String(), cxBalSpecificAvroSerde));
        //this part is new
    //    KStream<String, CustomerBalance> newStream = streamTableJoin(balanceStream, customerTable);
//        newStream.foreach((k, v) -> {
//            try {
//                if (v.getAccountId() == null)
//                    throw new InvalidBalanceException("No Customer Exists");
//            } catch (InvalidBalanceException e) {
//                balanceStream.peek((key,value) -> {
//                    final Producer<String, String> producer = new KafkaProducer<String, String>(props);
//                    final ProducerRecord<String, String> producerRecord =
//                            new ProducerRecord<>("BalanceError",value.getAccountId(), String.valueOf(value.getBalance()));
//                    producer.send(producerRecord);
//                });
//
//
//            }
//            });

        return newStream ;

    }

//    public KStream<String, CustomerBalance> streamTableJoin(KStream<String, Balance> balanceStream, KTable<String, CustomerRefined> customerTable){
//        return balanceStream.join(customerTable,
//                        (v1,v2) -> new CustomerBalanceBuilder().apply(v1,v2),
//                        Joined.with(Serdes.String(), balanceSpecificAvroSerde, cxRefinedSpecificAvroSerde))
//                .peek((k,v) -> log.info("new joined stream + table: Key : " + k + "      value : " + v))
//                .through("CustomerBalance", Produced.with(Serdes.String(), cxBalSpecificAvroSerde));
//    }



}
