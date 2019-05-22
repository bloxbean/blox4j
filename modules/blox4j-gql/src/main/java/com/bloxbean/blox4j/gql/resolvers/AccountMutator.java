/*
 * MIT License
 *
 * Copyright (c) 2019 BloxBean Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bloxbean.blox4j.gql.resolvers;

import com.bloxbean.blox4j.exception.DataFetchingException;
import com.bloxbean.blox4j.model.AccountKey;
import com.bloxbean.blox4j.model.AccountKeyExport;
import com.bloxbean.blox4j.model.input.AccountKeyExportInput;
import com.bloxbean.blox4j.service.AccountService;
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
