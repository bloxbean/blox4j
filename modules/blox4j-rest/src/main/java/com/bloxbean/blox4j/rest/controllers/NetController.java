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

package com.bloxbean.blox4j.rest.controllers;

import com.bloxbean.blox4j.model.NetInfo;
import com.bloxbean.blox4j.model.NodeInfo;
import com.bloxbean.blox4j.model.ProtocolInfo;
import com.bloxbean.blox4j.rest.common.RestConstants;
import com.bloxbean.blox4j.service.NetService;
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

@RestController
@RequestMapping(RestConstants.VERSION1_BASE_PATH + "network")
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
