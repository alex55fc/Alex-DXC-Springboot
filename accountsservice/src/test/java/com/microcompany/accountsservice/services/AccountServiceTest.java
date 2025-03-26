package com.microcompany.accountsservice.services;

import com.microcompany.accountsservice.exception.GlobalException;
import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.persistence.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.persistence.EntityManagerFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AccountServiceTest {

    @InjectMocks
    AccountService as;

    @Mock
    AccountRepository arMock;

    @Mock
    EntityManagerFactory emf;

    @BeforeEach
    public void setUp() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Account> accountsFake = List.of(
                new Account (1L, "Fake type", dateFormat.parse("2023-01-15"), 400, 1L ),
                new Account (2L, "Fake type2", dateFormat.parse("2023-01-16"), 500, 2L ),
                new Account (3L, "Fake type3", dateFormat.parse("2023-01-17"), 300, 1L )
        );
        Mockito.when(arMock.findByOwnerId(1L)).thenReturn(accountsFake);
        Mockito.when(arMock.findByOwnerId(222L)).thenReturn(null);

        Mockito.when(arMock.save(Mockito.any(Account.class)))
                .thenAnswer(elem ->{
                    Account ac = (Account) elem.getArguments()[0];
                    ac.setId(111L);
                    return ac;
                });

    }
    @Test
    void givenAccountsWhenSearchByLongIdIsNotNull(){
        List<Account> accounts = as.getAccounts(1L);
        assertThat(accounts).isNotNull();
        assertThat(accounts.size()).isGreaterThan(0);
    }
    @Test
    void givenAccountsWhenSearchByLongIdNotExistThenException(){
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(()->{
            List<Account> accounts = as.getAccounts(222L);
        });
    }
    @Test
    void givenValidAccount_WhenCreate_ThenThenIsNotNull(){
        Account newAccount = new Account(null,"Fake type",null, 0, 5);
        as.create(newAccount, newAccount.getOwner().getId());
        assertThat(newAccount.getId()).isGreaterThan(0L);
        assertThat(newAccount.getId()).isEqualTo(111L);
    }

    }


