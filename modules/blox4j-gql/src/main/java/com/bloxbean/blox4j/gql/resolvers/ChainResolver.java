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

import com.bloxbean.blox4j.model.Block;
import com.bloxbean.blox4j.model.TxDetails;
import com.bloxbean.blox4j.service.ChainService;
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
