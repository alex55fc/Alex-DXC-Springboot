package com.microcompany.accountsservice.persistence;

import com.microcompany.accountsservice.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@ExtendWith(SpringExtension.class)
@DataJpaTest()
@ComponentScan(basePackages = {"com.microcompany.accountsservice"})
@AutoConfigureTestEntityManager
@Sql(value = "classpath:testing.sql")
class AccountRepositoryTest {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(AccountRepositoryTest.class);

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void findAll() {
        Account account = new Account(null, "Ahorro", new Date("2023-01-15"), 1500, 1L);
        entityManager.persist(account);
        entityManager.flush();

        List<Account> accounts = accountRepository.findAll();
        logger.info("Accounts:" + accounts);

        assertNotNull(accounts);

        assertThat(accounts.size()).isGreaterThan(0);
    }





}
