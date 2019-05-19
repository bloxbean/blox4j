package com.bloxbean.bloxql.resolvers;

import com.bloxbean.bloxql.exception.DataFetchingException;
import com.bloxbean.bloxql.model.NodeInfo;
import com.bloxbean.bloxql.model.ProtocolInfo;
import com.bloxbean.bloxql.model.SyncInfoBean;
import com.bloxbean.bloxql.service.NetService;
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
