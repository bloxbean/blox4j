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
import com.bloxbean.blox4j.model.MsgRespBean;
import com.bloxbean.blox4j.model.TxDetails;
import com.bloxbean.blox4j.model.TxReceiptBean;
import com.bloxbean.blox4j.model.input.TxArgsInput;
import com.bloxbean.blox4j.service.TxnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TxnResolver {

    private Logger logger = LoggerFactory.getLogger(TxnResolver.class);

    private TxnService txnService;

    public TxnResolver(TxnService txnService) {
        this.txnService = txnService;
    }

    public TxDetails transaction(String txHash) {
        try {
            return txnService.getTransaction(txHash);
        } catch (Exception e) {
            logger.error("Error getting transaction", e);
            throw new DataFetchingException(e.getMessage());
        }
    }

    public List<TxDetails> transactions(long before, long first) {
        try {
            return txnService.getTransactions(before, first);
        } catch (Exception e) {
            logger.error("Error getting transactions", e);
            throw new DataFetchingException(e.getMessage());
        }
    }

    public List<TxDetails> transactionsByHash(List<String> txHash) {
        try {
            return txnService.getTransactionsByHash(txHash);
        } catch (Exception e) {
            logger.error("Error getting transactions", e);
            throw new DataFetchingException(e.getMessage());
        }
    }

    public long estimateNrg(String code) {
        return txnService.estimateNrg(code);
    }

    public long estimateNrgByTxArgs(TxArgsInput argsInput) {
        return txnService.estimateNrg(argsInput);
    }

    public String code(String address, long blockNumber) {
        if(blockNumber == -1)
            return txnService.getCode(address);
        else
            return txnService.getCode(address, blockNumber);
    }

    public MsgRespBean msgStatus(String msgHash) {
        return txnService.getMsgStatus(msgHash);
    }

    public long nrgPrice() {
        return txnService.getNrgPrice();
    }

    public String solcVersion() {
        return txnService.getSolcVersion();
    }

    public TxReceiptBean txReceipt(String txnHash) {
        return txnService.getTxReceipt(txnHash);
    }

}
