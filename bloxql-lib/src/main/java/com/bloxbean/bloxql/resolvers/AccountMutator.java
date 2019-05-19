package com.bloxbean.bloxql.resolvers;

import com.bloxbean.bloxql.exception.DataFetchingException;
import com.bloxbean.bloxql.model.AccountKey;
import com.bloxbean.bloxql.model.AccountKeyExport;
import com.bloxbean.bloxql.model.input.AccountKeyExportInput;
import com.bloxbean.bloxql.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountMutator {

    private Logger logger = LoggerFactory.getLogger(AccountMutator.class);

    private AccountService accountService;

    public AccountMutator(AccountService accountService) {
        this.accountService = accountService;
    }

    public List<AccountKey> accountCreate(List<String> passphrase, boolean privateKey) {

        try {
            return accountService.accountCreate(passphrase, privateKey);
        } catch (Exception e) {
            logger.error("Exception while creating account", e);

            throw new DataFetchingException(e.getMessage());
        }
    }

    public AccountKeyExport accountExport(List<AccountKeyExportInput> keys) {

        try {
            return accountService.accountExport(keys);
        } catch (Exception e) {
            logger.error("Exception while exporting account keys", e);

            throw new DataFetchingException(e.getMessage());
        }

    }

    public AccountKeyExport accountBackup(List<AccountKeyExportInput> keys) {

        try {
            return accountService.accountBackup(keys);
        } catch (Exception e) {
            logger.error("Exception while backing up account keys", e);

            throw new DataFetchingException(e.getMessage());
        }

    }

    public boolean accountImport(String privateKey, String passphrase) {

        try {
            return accountService.accountImport(privateKey, passphrase);
        } catch (Exception e) {
            logger.error("Exception while importing account key", e);

            throw new DataFetchingException(e.getMessage());
        }

    }

}
