package kafka.demo.service;

import com.ibm.gbs.schema.Balance;
import com.ibm.gbs.schema.Customer;
import com.ibm.gbs.schema.CustomerBalance;
import com.ibm.gbs.schema.CustomerRefined;
import org.apache.kafka.streams.kstream.ValueJoiner;
import org.springframework.stereotype.Service;


@Service
public class CustomerBalanceBuilder implements ValueJoiner<Balance, CustomerRefined, CustomerBalance>
{

    @Override
    public CustomerBalance apply(Balance balance, CustomerRefined customer){

        return CustomerBalance.newBuilder()
                .setAccountId(balance.getAccountId())
                .setBalance(balance.getBalance())
                .setCustomerId(customer.getCustomerId())
                .setPhoneNumber(customer.getPhoneNumber())
                .build();
    }

}
