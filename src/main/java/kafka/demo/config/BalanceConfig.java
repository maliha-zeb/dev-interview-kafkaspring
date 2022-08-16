package kafka.demo.config;

import com.ibm.gbs.schema.Balance;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;


@Component
@Log4j2
@Configuration
@Order(1)
public class BalanceConfig {
    @Autowired
    public StreamsBuilder streamsBuilder;

    @Bean
    public KStream<String, Balance> balanceKStream(){
        final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url",
                "http://localhost:8081");

        final Serde<Balance> balanceSpecificAvroSerde = new SpecificAvroSerde<>();
        balanceSpecificAvroSerde.configure(serdeConfig, false);

        KStream<String, Balance> balanceStream = streamsBuilder.stream("Balance", Consumed.with(Serdes.String(), balanceSpecificAvroSerde));

        KStream<String, Balance>
               rawBalStream = balanceStream.selectKey((k,v)->v.getAccountId())
                .peek((k,v) -> log.info("Key : " + k + "      value : " + v))
                .through("BalanceRefined", Produced.with(Serdes.String(), balanceSpecificAvroSerde));;

        return rawBalStream;

    }


}
