package kafka.demo.testdata;

import com.ibm.gbs.schema.Balance;
import com.ibm.gbs.schema.Customer;
import com.ibm.gbs.schema.CustomerBalance;
import com.ibm.gbs.schema.CustomerRefined;
import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.avro.Schema;
import org.junit.jupiter.engine.Constants;

public class CustomKafkaAvroDeserializer extends KafkaAvroDeserializer {

    @Override
    public Object deserialize(String topic, byte[] bytes) {
        if (topic.equals(DataCustomerBalance.BALANCE_TOPIC_NAME)) {
            this.schemaRegistry = getMockClient(Balance.SCHEMA$);
        }
        if (topic.equals(DataCustomerBalance.CUSTOMER_TOPIC_NAME)) {
            this.schemaRegistry = getMockClient(Customer.SCHEMA$);
        }
        if(topic.equals(DataCustomerBalance.OUTPUT_TOPIC_NAME)){
            this.schemaRegistry = getMockClient(CustomerBalance.SCHEMA$);
        }
        if(topic.equals(DataCustomerBalance.OUTPUT_CUSTOMER_TOPIC_NAME)){
            this.schemaRegistry = getMockClient(CustomerRefined.SCHEMA$);
        }
        return super.deserialize(topic, bytes);
    }

    private static SchemaRegistryClient getMockClient(final Schema schema$) {
        return new MockSchemaRegistryClient() {
            @Override
            public synchronized Schema getById(int id) {
                return schema$;
            }
        };
    }

}
