package com.bloxbean.bloxql.service;

import com.bloxbean.bloxql.model.NodeInfo;
import com.bloxbean.bloxql.model.ProtocolInfo;
import com.bloxbean.bloxql.model.SyncInfoBean;

import java.util.List;

public interface NetService {

    public boolean isSyncing();

    public ProtocolInfo getProtocol();

    public List<NodeInfo> getActiveNodes();

    public int getPeerCount();

    public List<NodeInfo> getStaticNodes();

    public boolean isListening();

    public SyncInfoBean getSyncInfo();
}
