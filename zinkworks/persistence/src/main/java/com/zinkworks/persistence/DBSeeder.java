package com.zinkworks.persistence;

import com.zinkworks.domain.Account;
import com.zinkworks.domain.Atm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

/**
 * DBSeeder sets the database with the accounts and atm tables
 * since it starts the first in the spring context, it inits the database even before the
 * whole spring context is fully up and running
 */
@Component
public class DBSeeder implements CommandLineRunner {

    // bankAccountRepo
    private IAccountRepository bankAccountRepo;

    // atmRepository
    private IAtmRepository atmRepository;


    /**
     * Constructor
     * @param bankAccountRepo  the first parameter dependency
     * @param atmRepository  the second parameter dependency
     */
    @Autowired
    public DBSeeder(IAccountRepository bankAccountRepo, IAtmRepository atmRepository) {
        this.bankAccountRepo = bankAccountRepo;
        this.atmRepository = atmRepository;
    }

    /**
     *
     * @param args arguments
     * @throws Exception
     */
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // this runs first on the spring context

        // Clean up database tables
        atmRepository.deleteAllInBatch();
        bankAccountRepo.deleteAllInBatch();

        // Create ATM instance
        Atm atm = new Atm(1500, 10, 30, 30, 20);

        // save atm
        atmRepository.save(atm);


        //  Create two Account instances and add accounts to a list
        List<Account> accounts = List.of(
                  new Account(123456789L, "1234", 800, 200),
                  new Account(987654321L, "4321", 1230, 150));

        // save each account and its openBalance child
        // Save Parent Reference (which will save the child as well)
        bankAccountRepo.saveAll(accounts);

    }
}
