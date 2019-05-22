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
import com.bloxbean.blox4j.model.NodeInfo;
import com.bloxbean.blox4j.model.ProtocolInfo;
import com.bloxbean.blox4j.model.SyncInfoBean;
import com.bloxbean.blox4j.service.NetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NetInfoResolver {

    private static Logger logger = LoggerFactory.getLogger(NetInfoResolver.class);

    private NetService netService;

    public NetInfoResolver(NetService netService) {
        this.netService = netService;
    }

    public boolean isSyncing() {
        try {
            return netService.isSyncing();
        } catch (Exception e) {
            logger.error("Error getting syncing info");
            throw new DataFetchingException(e.getMessage());
        }
    }

    public ProtocolInfo protocol() {
        try {
            return netService.getProtocol();
        } catch (Exception e) {
            logger.error("Error getting protocol info");
            throw new DataFetchingException(e.getMessage());
        }
    }

    public List<NodeInfo> activeNodes() {
        try {
            return netService.getActiveNodes();
        } catch (Exception e) {
            logger.error("Error getting active nodes");
            throw new DataFetchingException(e.getMessage());
        }
    }

    public int peerCount() {
        try {
            return netService.getPeerCount();
        } catch (Exception e) {
            logger.error("Error getting peer count");
            throw new DataFetchingException(e.getMessage());
        }
    }

    public List<NodeInfo> staticNodes() {
        try {
            return netService.getStaticNodes();
        } catch (Exception e) {
            logger.error("Error getting static nodes");
            throw new DataFetchingException(e.getMessage());
        }
    }

    public boolean isListening() {
        try {
            return netService.isListening();
        } catch (Exception e) {
            logger.error("Error getting listening status");
            throw new DataFetchingException(e.getMessage());
        }
    }

    public SyncInfoBean syncInfo() {
        try {
            return netService.getSyncInfo();
        } catch (Exception e) {
            logger.error("Error getting sync info");
            throw new DataFetchingException(e.getMessage());
        }
    }

}
