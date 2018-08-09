package com.piggybank.AccountService;

import com.mongodb.MongoClient;
import com.piggybank.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

@Configuration
public class DBLoader {

    @Autowired
    MongoClient mongoClient;

    @Bean
    CommandLineRunner init(CustomerRepository customerRepository,AccountRepository accountRepository , TransactionRepository transactionRepository){
        Customer aCustomer = new Customer("Rajaram","Kumar");
        EmailAddress aEmailAddress = new EmailAddress("a@b.com");
        aCustomer.setEmailAddress(aEmailAddress);
        Address aAddress = new Address("66 New Way","New York", "United States");
        aCustomer.setAddress(aAddress);
        Account aAccount = new Account(aCustomer,"CHECKING");


        Customer bCustomer = new Customer("Anu" ,"Venkat");
        EmailAddress bEmailAddress = new EmailAddress("b@c.com");
        bCustomer.setEmailAddress(bEmailAddress);
        Address bAddress = new Address("66 New Way","New York", "United States");
        bCustomer.setAddress(bAddress);
        Account bAccount = new Account(bCustomer,"CHECKING");

        Transaction t1 = new Transaction(aAccount,new BigDecimal(5000.00),TrasactionType.CREDIT, Category.INCOME);
        Transaction t2 = new Transaction(aAccount,new BigDecimal(100.00),TrasactionType.DEBIT, Category.ENTERTAINMENT);
        Transaction t3 = new Transaction(aAccount,new BigDecimal(1500.00),TrasactionType.DEBIT, Category.RENT);
        Transaction t4 = new Transaction(aAccount,new BigDecimal(50.00),TrasactionType.DEBIT, Category.GROCERY);
        Transaction t5 = new Transaction(aAccount,new BigDecimal(100.00),TrasactionType.DEBIT, Category.GROCERY);
        Transaction t6 = new Transaction(aAccount,new BigDecimal(60.00),TrasactionType.DEBIT, Category.GROCERY);

        Transaction m1 = new Transaction(bAccount,new BigDecimal(6000.00),TrasactionType.CREDIT, Category.INCOME);
        Transaction m2 = new Transaction(bAccount,new BigDecimal(200.00),TrasactionType.DEBIT, Category.ENTERTAINMENT);
        Transaction m3 = new Transaction(bAccount,new BigDecimal(3500.00),TrasactionType.DEBIT, Category.RENT);
        Transaction m4 = new Transaction(bAccount,new BigDecimal(50.00),TrasactionType.DEBIT, Category.GROCERY);
        Transaction m5 = new Transaction(bAccount,new BigDecimal(200.00),TrasactionType.DEBIT, Category.GROCERY);
        Transaction m6 = new Transaction(bAccount,new BigDecimal(70.00),TrasactionType.DEBIT, Category.GROCERY);


        return args -> {
            if(mongoClient.getDB("acct") == null || ! ( mongoClient.getDB("acct").getCollection("customer").count() > 0)) {
                Flux.just(aCustomer, bCustomer).flatMap(customerRepository::save)
                        .subscribe(System.out::println);
                Thread.sleep(1000);
                Flux.just(bAccount, aAccount).flatMap(accountRepository::save)
                        .subscribe(System.out::println);
                Thread.sleep(1000);
                Flux.just(t1, t2, t3, t4, t5, t6, m1, m2, m3, m4, m5, m6).flatMap(transactionRepository::save)
                        .subscribe(System.out::println);
            }
        };
    }
}
