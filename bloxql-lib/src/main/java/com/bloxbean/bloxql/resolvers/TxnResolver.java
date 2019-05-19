package com.bloxbean.bloxql.resolvers;

import com.bloxbean.bloxql.exception.DataFetchingException;
import com.bloxbean.bloxql.model.MsgRespBean;
import com.bloxbean.bloxql.model.TxDetails;
import com.bloxbean.bloxql.model.TxReceiptBean;
import com.bloxbean.bloxql.model.input.TxArgsInput;
import com.bloxbean.bloxql.service.TxnService;
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
