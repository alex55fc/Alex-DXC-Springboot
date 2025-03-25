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

import java.util.Collections;
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

    @Test
    void save(){
        Account account = new Account(null, "Ahorro", new Date("2024-02-16"), 1500, 2L);

        accountRepository.save(account);

        assertThat(account.getId()).isGreaterThan(0);
        Account emAccount = entityManager.find(Account.class, account.getId());
        assertThat(emAccount.getOpeningDate().compareTo(account.getOpeningDate()));

    }

        @Test
    void findByType() {
    // given
    Account account1 = new Account(null, "Ahorro", new Date("2023-01-15"), 1500, 1L);
    Account account2 = new Account(null, "Corriente", new Date("2023-01-16"), 2000, 2L);
    entityManager.persist(account1);
    entityManager.persist(account2);
    entityManager.flush();

    // when
    List<Account> accounts = accountRepository.findByType("Ahorro");
    logger.info("Accounts: " + accounts);

    // then
    assertNotNull(accounts);
    assertFalse(accounts.isEmpty());
    assertEquals(1, accounts.size());
    assertEquals("Ahorro", accounts.get(0).getType());
    }

    @Test
    void deleteById() {
      // given
      Account account = new Account(null, "Ahorro", new Date("2024-02-16"), 1500, 2L);
      entityManager.persist(account);
      entityManager.flush();

      // when
      accountRepository.deleteById(account.getId());
      entityManager.flush();

      // then
      Account deletedAccount = entityManager.find(Account.class, account.getId());
      assertNull(deletedAccount, "Account should be null after deletion");

    }

    @Test
    void findById_NotFound() {
        // given
        Long nonExistentId = 999L;

        // when
        List<Account> accounts = accountRepository.findAllById(Collections.singletonList(nonExistentId));
        logger.info("Accounts: " + accounts);

        // then
        assertNotNull(accounts);
        assertTrue(accounts.isEmpty());
    }


}
