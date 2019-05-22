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

package com.bloxbean.blox4j.impl.aion.pool;

import com.bloxbean.blox4j.pool.ChainConnection;
import com.bloxbean.blox4j.pool.ConnectionFactory;
import org.aion.api.IAionAPI;
import org.aion.api.type.ApiMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AionConnectionFactory implements ConnectionFactory {

    private final static Logger logger = LoggerFactory.getLogger(AionConnectionFactory.class);

    @Value("${rpc.endpoint}")
    String rpcEndPoint;

    @Override
    public ChainConnection createConnection() {
        IAionAPI api = IAionAPI.init();

        logger.info("Trying to connect to " + rpcEndPoint);
        ApiMsg apiMsg = api.connect(rpcEndPoint);

        if (apiMsg.isError()) {
            logger.error("Connect server failed, exit test! " + apiMsg.getErrString());
            return null;
        } else {
            return new AionConnection(api, apiMsg);
        }
    }
}
