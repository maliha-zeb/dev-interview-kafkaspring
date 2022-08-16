package kafka.demo.testdata;

import com.ibm.gbs.schema.Balance;
import com.ibm.gbs.schema.Customer;
import com.ibm.gbs.schema.CustomerBalance;
import com.ibm.gbs.schema.CustomerRefined;
import com.thoughtworks.qdox.builder.Builder;
import org.apache.kafka.streams.StreamsBuilder;

import java.util.Properties;

public class DataCustomerBalance {

    public static final String BALANCE_TOPIC_NAME = "Balance";
    public static final String CUSTOMER_TOPIC_NAME = "Customer";

    public static final String OUTPUT_BALANCE_TOPIC_NAME = "BalanceRefined";

    public static final String OUTPUT_CUSTOMER_TOPIC_NAME = "CustomerRefined";
    public static final String OUTPUT_TOPIC_NAME = "CustomerBalance";

    public static final String DUMMY_SCHEMA = "http://dummy.sr.confluent.cloud:8080";
    public static final String DUMMY_KAFKA_CLOUD_9092 = "dummy.kafka.confluent.cloud:9092";

    public static final  String b1 = ("15::123::10::10.2");
    public String b2 =("16::124::11::100.22");
    public String b3 =("17::125::11::99.99");
    public String b4 =("18::126::12::118.33");
    public String b5 =("19::127::13::2000.00");

    public static final Balance balanceData1 = new Balance("123", "10", (float) 10.2);
    public static final Balance balanceData2 = new Balance("124", "11", (float) 100.22);
    public static final Balance balanceData3 = new Balance("125", "11", (float) 99.99);
    public static final Balance balanceData4 = new Balance("126", "12", (float) 118.33);
    public static final Balance balanceData5 = new Balance("127", "13", (float) 2000.00);

    public String cr1 =("601::111::888-888-7777::10" );
    public String cr2 =("602::112::888-416-8888::11" );
    public String cr3 =("603::113::888-800-7167::12" );

    public static final CustomerRefined cxRefinedData1 = new CustomerRefined("111", "888-888-7777", "10" );
    public static final CustomerRefined cxRefinedData2 = new CustomerRefined("112", "888-416-8888", "11" );
    public static final CustomerRefined cxRefinedData3 = new CustomerRefined("113", "888-800-7167", "12" );

    public String c1 =("681::111::John Doe::888-888-7777::10" );
    public String c2 =("682::112::Maryam Han::888-416-8888::11" );
    public String c3 =("683::113::Sara Zee::888-800-7167::12" );
    public static final Customer customerData1 = new Customer("111", "John Doe", "888-888-7777", "10" );
    public static final Customer customerData2 = new Customer("112", "Maryam Han", "888-416-8888", "11" );
    public static final Customer customerData3 = new Customer("113", "Sara Zee", "888-800-7167", "12" );

    public String cb1 =("10::681::111::888-888-7777::10.2" );
    public String cb2 =("11::682::112::888-416-8888::99.99" );
    public String cb3 =("12::683::113::888-800-7167::118.33" );

    public static final CustomerBalance cxBalanceOutput1 =
            new CustomerBalance(
                    cxRefinedData1.getAccountId(),
                    cxRefinedData1.getCustomerId(),
                    cxRefinedData1.getPhoneNumber(),
                    balanceData1.getBalance()
                    );

    public static final CustomerBalance cxBalanceOutput2 =
            new CustomerBalance(
                    cxRefinedData2.getAccountId(),
                    cxRefinedData2.getCustomerId(),
                    cxRefinedData2.getPhoneNumber(),
                    balanceData2.getBalance()
            );

    public static final CustomerBalance cxBalanceOutput3 =
            new CustomerBalance(
                    cxRefinedData2.getAccountId(),
                    cxRefinedData2.getCustomerId(),
                    cxRefinedData2.getPhoneNumber(),
                    balanceData3.getBalance()
            );

    public static final CustomerBalance cxBalanceOutput4 =
            new CustomerBalance(
                    cxRefinedData3.getAccountId(),
                    cxRefinedData3.getCustomerId(),
                    cxRefinedData3.getPhoneNumber(),
                    balanceData5.getBalance()
            );


    public static final CustomerBalance cxBalanceOutput5 =
            new CustomerBalance(
                    null,
                    null,
                    null,
                    balanceData4.getBalance()
            );


    public static Properties buildStreamDummyConfiguration() {
        final Properties properties = new Properties();
        properties.put("application.id", "streams-join-test");
   //     properties.put("bootstrap.servers", DataCustomerBalance.DUMMY_KAFKA_CLOUD_9092);
   //    properties.put("schema.registry.url", DataCustomerBalance.DUMMY_SCHEMA);
        properties.put("default.topic.replication.factor", "1");
        properties.put("offset.reset.policy", "latest");
        return properties;
    }
}
