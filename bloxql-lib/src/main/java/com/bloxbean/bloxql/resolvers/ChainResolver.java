package com.bloxbean.bloxql.resolvers;

import com.bloxbean.bloxql.model.Block;
import com.bloxbean.bloxql.model.TxDetails;
import com.bloxbean.bloxql.service.ChainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class ChainResolver {

    private Logger logger = LoggerFactory.getLogger(ChainResolver.class);

    private final ChainService chainService;

    public ChainResolver(ChainService chainService) {
        this.chainService = chainService;
    }

    public Long blockNumber() {
        return chainService.blockNumber();
    }

    public BigInteger balance(String address) {
        return chainService.getBalance(address);
    }

    public BigInteger balanceByBlockNumber(String address, long blockNumber) {
        return chainService.getBalance(address, blockNumber);
    }

    public Block blockByHash(String hash) {
        return chainService.getBlockByHash(hash);
    }

    public Block blockByNumber(long number) {
        return chainService.getBlockByNumber(number);
    }

    public int blockTransactionCountByHash(String hash) {
        return chainService.getBlockTrasactionCountByHash(hash);
    }

    public int blockTransactionCountByNumber(long number) {
        return chainService.getBlockTransactionCountByNumber(number);
    }

    public BigInteger nonce(String address) {
        return chainService.getNonce(address);
    }

    public String storageAt(String address, int position, long blockNumber) {
        if(blockNumber == 0)
            return chainService.getStorageAt(address, position);
        else
            return chainService.getStorageAt(address, position, blockNumber);
    }

    public TxDetails transactionByBlockNumberAndIndex(long blockNumber, int index) {
        return chainService.getTransactionByBlockNumberAndIndex(blockNumber, index);
    }

    public TxDetails transactionByHash(String txnHash) {
        return chainService.getTransactionByHash(txnHash);
    }

    public long transactionCount(String address, long blockNumber) {
        return chainService.getTransactionCount(address, blockNumber);
    }

}
