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

package com.bloxbean.blox4j.impl.aion.service;

import com.bloxbean.blox4j.exception.BlockChainAcessException;
import com.bloxbean.blox4j.impl.aion.service.dao.AionBlockchainAccessor;
import com.bloxbean.blox4j.model.TxDetails;
import com.bloxbean.blox4j.service.ChainService;
import org.aion.api.type.Transaction;
import org.aion.base.type.AionAddress;
import org.aion.base.type.Hash256;
import com.bloxbean.blox4j.impl.aion.util.ModelConverter;
import com.bloxbean.blox4j.model.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public class ChainServiceImpl implements ChainService {

    private Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    private AionBlockchainAccessor accessor;

    public ChainServiceImpl(AionBlockchainAccessor accessor){
        this.accessor = accessor;
    }

    @Override
    public long blockNumber() {
        return accessor.call(((apiMsg, api) -> {
            if (logger.isDebugEnabled())
                logger.debug("Getting latest block number");

            apiMsg.set(api.getChain().blockNumber());

            if (apiMsg.isError()) {
                logger.error("Unable to get the latest block" + apiMsg.getErrString());
                throw new BlockChainAcessException(apiMsg.getErrString());
            }

            return apiMsg.getObject();
        }));
    }

    @Override
    public BigInteger getBalance(String address) {
        return getBalance(address, -1);

    }

    @Override
    public BigInteger getBalance(String address, long blockNumber) {
        if (logger.isDebugEnabled())
            logger.debug("Getting balance for account: " + address + "  at block number: " + blockNumber);

        if (address == null || address.isEmpty())
            throw new BlockChainAcessException("Can't get balance for null account");


        return accessor.call(((apiMsg, api) -> {
            if (blockNumber == -1) //get latest balance
                apiMsg.set(api.getChain().getBalance(AionAddress.wrap(address)));
            else
                apiMsg.set(api.getChain().getBalance(AionAddress.wrap(address), blockNumber));

            if (apiMsg.isError()) {
                logger.error("Unable to get balance for account {} : " + apiMsg.getErrString(), address);
                throw new BlockChainAcessException(apiMsg.getErrString());
            }

            BigInteger balance = apiMsg.getObject();

            return balance;

        }));

    }

    @Override
    public Block getBlockByHash(String hash) {
        if(logger.isDebugEnabled())
            logger.debug("Getting block for " + hash);

        return accessor.call(((apiMsg, api) -> {

            apiMsg.set(api.getChain().getBlockByHash(Hash256.wrap(hash)));
            if (apiMsg.isError()) {
                logger.error("Unable to get the block" + apiMsg.getErrString());
                throw new BlockChainAcessException(apiMsg.getErrString());
            }

            if (logger.isDebugEnabled())
                logger.debug("Block details got" + apiMsg.getObject().getClass());

            org.aion.api.type.Block block = apiMsg.getObject();

            if (block == null)
                throw new BlockChainAcessException("No block found with hash : " + hash);

            Block b = ModelConverter.convert(block);
            return b;
        }));
    }

    @Override
    public Block getBlockByNumber(long number) {
        if(logger.isDebugEnabled())
            logger.debug("Getting block for " + number);

        return accessor.call(((apiMsg, api) -> {

            apiMsg.set(api.getChain().getBlockByNumber(number));
            if (apiMsg.isError()) {
                logger.error("Unable to get the block" + apiMsg.getErrString());
                throw new BlockChainAcessException(apiMsg.getErrString());
            }

            if (logger.isDebugEnabled())
                logger.debug("Block details got" + apiMsg.getObject().getClass());

            org.aion.api.type.Block block = apiMsg.getObject();

            if (block == null)
                throw new BlockChainAcessException("No block found with number : " + number);

            Block b = ModelConverter.convert(block);
            return b;
        }));
    }

    @Override
    public int getBlockTrasactionCountByHash(String hash) {
        if(logger.isDebugEnabled())
            logger.debug("Getting transaction count for block " + hash);

        return accessor.call(((apiMsg, api) -> {

            apiMsg.set(api.getChain().getBlockTransactionCountByHash(Hash256.wrap(hash)));
            if (apiMsg.isError()) {
                logger.error("Unable to get the transaction count for block" + apiMsg.getErrString());
                throw new BlockChainAcessException(apiMsg.getErrString());
            }

            if (logger.isDebugEnabled())
                logger.debug("Transaction count for block " + apiMsg.getObject().getClass());

            return apiMsg.getObject();
        }));
    }

    @Override
    public int getBlockTransactionCountByNumber(long number) {
        if(logger.isDebugEnabled())
            logger.debug("Getting transaction count for block " + number);

        return accessor.call(((apiMsg, api) -> {

            apiMsg.set(api.getChain().getBlockTransactionCountByNumber(number));
            if (apiMsg.isError()) {
                logger.error("Unable to get the transaction count for block" + apiMsg.getErrString());
                throw new BlockChainAcessException(apiMsg.getErrString());
            }

            if (logger.isDebugEnabled())
                logger.debug("Transaction count for block " + apiMsg.getObject().getClass());

            return apiMsg.getObject();
        }));
    }

    @Override
    public BigInteger getNonce(String address) {
        if(logger.isDebugEnabled())
            logger.debug("Getting nonce for " + address);

        return accessor.call(((apiMsg, api) -> {

            apiMsg.set(api.getChain().getNonce(AionAddress.wrap(address)));
            if (apiMsg.isError()) {
                logger.error("Unable to get nonce for the address" + apiMsg.getErrString());
                throw new BlockChainAcessException(apiMsg.getErrString());
            }

            if (logger.isDebugEnabled())
                logger.debug("Got nonce for the address " + apiMsg.getObject().getClass());

            return apiMsg.getObject();
        }));
    }

    @Override
    public String getStorageAt(String address, int position) {

        if(logger.isDebugEnabled())
            logger.debug("Getting storage for " + address);

        return accessor.call(((apiMsg, api) -> {

            apiMsg.set(api.getChain().getStorageAt(AionAddress.wrap(address), position));
            if (apiMsg.isError()) {
                logger.error("Unable to get storage for the address" + apiMsg.getErrString());
                throw new BlockChainAcessException(apiMsg.getErrString());
            }

            if (logger.isDebugEnabled())
                logger.debug("Got storage for the address " + apiMsg.getObject().getClass());

            return apiMsg.getObject();
        }));
    }

    @Override
    public String getStorageAt(String address, int position, long blockNumber) {

        if(logger.isDebugEnabled())
            logger.debug("Getting storage for " + address);

        return accessor.call(((apiMsg, api) -> {

            apiMsg.set(api.getChain().getStorageAt(AionAddress.wrap(address), position, blockNumber));
            if (apiMsg.isError()) {
                logger.error("Unable to get storage for the address" + apiMsg.getErrString());
                throw new BlockChainAcessException(apiMsg.getErrString());
            }

            if (logger.isDebugEnabled())
                logger.debug("Got storage for the address " + apiMsg.getObject().getClass());

            return apiMsg.getObject();
        }));
    }

    @Override
    public TxDetails getTransactionByBlockNumberAndIndex(long blockNumber, int index) {
        if(logger.isDebugEnabled())
            logger.debug("Getting transaction by block number {} and index {} ", blockNumber, index);

        return accessor.call(((apiMsg, api) -> {

            apiMsg.set(api.getChain().getTransactionByBlockNumberAndIndex(blockNumber, index));
            if (apiMsg.isError()) {
                logger.error("Unable to get transaction" + apiMsg.getErrString());
                throw new BlockChainAcessException(apiMsg.getErrString());
            }

            if (logger.isDebugEnabled())
                logger.debug("Got transaction object " + apiMsg.getObject().getClass());

            Transaction transaction = apiMsg.getObject();

            return ModelConverter.convert(transaction);
        }));
    }

    @Override
    public TxDetails getTransactionByHash(String txnHash) {
        if(logger.isDebugEnabled())
            logger.debug("Getting transaction by hash {} ", txnHash);

        return accessor.call(((apiMsg, api) -> {

            apiMsg.set(api.getChain().getTransactionByHash(Hash256.wrap(txnHash)));
            if (apiMsg.isError()) {
                logger.error("Unable to get transaction" + apiMsg.getErrString());
                throw new BlockChainAcessException(apiMsg.getErrString());
            }

            if (logger.isDebugEnabled())
                logger.debug("Got transaction object " + apiMsg.getObject().getClass());

            Transaction transaction = apiMsg.getObject();

            return ModelConverter.convert(transaction);
        }));
    }

    @Override
    public long getTransactionCount(String address, long blockNumber) {
        if(logger.isDebugEnabled())
            logger.debug("Getting transaction count for address {} at blocknumber {} ", address, blockNumber);

        return accessor.call(((apiMsg, api) -> {

            apiMsg.set(api.getChain().getTransactionCount(AionAddress.wrap(address), blockNumber));
            if (apiMsg.isError()) {
                logger.error("Unable to get transaction count" + apiMsg.getErrString());
                throw new BlockChainAcessException(apiMsg.getErrString());
            }

            if (logger.isDebugEnabled())
                logger.debug("Got transaction object " + apiMsg.getObject().getClass());

            return apiMsg.getObject();
        }));
    }
}
