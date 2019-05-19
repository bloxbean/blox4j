package com.bloxbean.bloxql.resolvers;

import com.bloxbean.bloxql.exception.DataFetchingException;
import com.bloxbean.bloxql.model.Account;
import com.bloxbean.bloxql.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountResolver {

    private Logger logger = LoggerFactory.getLogger(AccountResolver.class);

    private static AccountResolver _resolver;

    private AccountService accountService;

    public AccountResolver(AccountService accountService) {
        this.accountService = accountService;

        if(_resolver == null)
            _resolver = this;
    }

    public Account account(String address, long blockNumber) {
        try {
            List<String> fields = new ArrayList<>();
            fields.add("balance");

            return accountService.getAccount(address, fields, blockNumber);
        } catch (Exception e) {
            logger.error("Error getting transaction", e);
            throw new DataFetchingException(e.getMessage());
        }
    }

    public static AccountResolver getInstance() {
        return _resolver;
    }
}
