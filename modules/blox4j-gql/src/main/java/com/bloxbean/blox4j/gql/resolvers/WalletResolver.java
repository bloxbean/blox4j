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
import com.bloxbean.blox4j.model.Account;
import com.bloxbean.blox4j.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WalletResolver {

    private static final Logger logger = LoggerFactory.getLogger(WalletResolver.class);

    private WalletService walletService;

    public WalletResolver(WalletService walletService) {
        this.walletService = walletService;
    }

    //Wallet get apis
    public List<String> addresses() {
        try {
            return walletService.getAddresses();
        } catch(Exception e) {
            logger.error("Error getting wallet accounts");
            throw new DataFetchingException(e.getMessage());
        }
    }

    public List<Account> accounts() {
        try {
            return walletService.getAccounts();
        } catch(Exception e) {
            logger.error("Error getting wallet accounts");
            throw new DataFetchingException(e.getMessage());
        }
    }

    public Account defaultAccount() {
        try {
            return walletService.getDefaultAccount();
        } catch(Exception e) {
            logger.error("Error getting wallet default account");
            throw new DataFetchingException(e.getMessage());
        }
    }

    public Account getMinerAccount() {
        try {
            return walletService.getMinerAccount();
        } catch(Exception e) {
            logger.error("Error getting wallet miner account");
            throw new DataFetchingException(e.getMessage());
        }
    }

}
