package com.bloxbean.bloxql.rest.controllers;

import com.bloxbean.bloxql.model.NetInfo;
import com.bloxbean.bloxql.model.NodeInfo;
import com.bloxbean.bloxql.model.ProtocolInfo;
import com.bloxbean.bloxql.service.NetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.bloxbean.bloxql.rest.common.RestConstants.VERSION1_BASE_PATH;

@RestController
@RequestMapping(VERSION1_BASE_PATH + "network")
@ConditionalOnProperty(name = "rest.enable", havingValue = "true")
@Api(value="network", description="Network information")
public class NetController {
    private final static Logger logger = LoggerFactory.getLogger(NetController.class);

    @Autowired
    private NetService netService;

    @GetMapping(produces = "application/json")
    @ApiOperation(value = "Get network info")
    public NetInfo getNetworkInfo() {

        NetInfo netInfo = new NetInfo();

        ProtocolInfo protocolInfo = netService.getProtocol();
        if(protocolInfo !=  null)
            netInfo.setProtocol(protocolInfo);

        netInfo.setSyncing(netService.isSyncing());

        return netInfo;
    }

    @GetMapping(value = "/active-nodes", produces = "application/json")
    @ApiOperation(value = "Get active nodes")
    public List<NodeInfo> getActiveNodes() {
        return netService.getActiveNodes();
    }

    @GetMapping(value = "/static-nodes", produces = "application/json")
    @ApiOperation(value = "Get static nodes")
    public List<NodeInfo> getStaticNodes() {
        return netService.getStaticNodes();
    }

   // @GetMapping(value = "/peer-count", produces = "application/json")
   // @ApiOperation(value = "Get peer counts")
    public JSONObject getPeerCount() {
        try {
            int peerCount = netService.getPeerCount();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("peer-count", peerCount);

            return jsonObject;
        } catch (Exception ex) {
            logger.error("Unable to get peer count", ex);
            throw new RuntimeException("Unable to get peer count", ex);
        }
    }

}
