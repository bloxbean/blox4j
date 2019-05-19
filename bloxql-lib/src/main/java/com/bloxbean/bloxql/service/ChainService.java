package com.bloxbean.bloxql.service;

import com.bloxbean.bloxql.model.TxDetails;
import com.bloxbean.bloxql.model.Block;

import java.math.BigInteger;

public interface ChainService {

    public long blockNumber();

    public BigInteger getBalance(String address);

    public BigInteger getBalance(String address, long blockNumber);

    public Block getBlockByHash(String hash);

    public Block getBlockByNumber(long number);

    public int getBlockTrasactionCountByHash(String hash);

    public int getBlockTransactionCountByNumber(long number);

    public BigInteger getNonce(String address);

    public String getStorageAt(String address, int position);

    public String getStorageAt(String address, int position, long blockNumber);

    public TxDetails getTransactionByBlockNumberAndIndex(long blockNumber, int index);

    public TxDetails getTransactionByHash(String txnHash);

    public long getTransactionCount(String address, long blockNumber);

}
