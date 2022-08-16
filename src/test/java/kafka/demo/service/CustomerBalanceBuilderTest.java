//package kafka.demo.service;
//
//import com.ibm.gbs.schema.Balance;
//import com.ibm.gbs.schema.CustomerBalance;
//import com.ibm.gbs.schema.CustomerRefined;
//import kafka.demo.testdata.DataCustomerBalance;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class CustomerBalanceBuilderTest {
//
//    @Test
//    void apply() {
//
//        CustomerBalance returnedCxBalance;
//
//        Balance balance = DataCustomerBalance.balanceData1;
//        CustomerRefined user = DataCustomerBalance.cxRefinedData1;
//        CustomerBalance expectedOutput = DataCustomerBalance.cxBalanceOutput1;
//
//        CustomerBalanceBuilder joiner = new CustomerBalanceBuilder();
//        returnedCxBalance = joiner.apply(balance, user);
//
//        assertEquals(returnedCxBalance, expectedOutput);
//    }
//}