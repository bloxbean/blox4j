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

package com.bloxbean.blox4j.impl.aion.service.dao;

import com.bloxbean.blox4j.exception.BlockChainAcessException;
import com.bloxbean.blox4j.exception.ConnectionException;
import org.aion.api.IAionAPI;
import org.aion.api.type.ApiMsg;
import com.bloxbean.blox4j.impl.aion.pool.AionConnection;
import com.bloxbean.blox4j.pool.ConnectionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AionBlockchainAccessor {

    private static Logger logger = LoggerFactory.getLogger(AionBlockchainAccessor.class);
    private final ConnectionHelper connectionHelper;

    public AionBlockchainAccessor(ConnectionHelper connectionHelper) {
        this.connectionHelper = connectionHelper;
    }

    public <Any> Any call(AionAccessFunction<Any> aionAccessFunction) {
        AionConnection connection = (AionConnection) connectionHelper.getConnection();

        if(connection == null)
            throw new ConnectionException("Connection could not be established");

        IAionAPI api = connection.getApi();
        ApiMsg apiMsg = connection.getApiMsg();

        try {

           return aionAccessFunction.invoke(apiMsg, api);

        } catch(BlockChainAcessException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error invoking method", e);
            throw new BlockChainAcessException(apiMsg.getErrorCode(), apiMsg.getErrString());
        } finally {
            connectionHelper.closeConnection(connection);
        }
    }
}
