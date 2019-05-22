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

package com.bloxbean.blox4j.impl.aion.service.event;

import com.bloxbean.blox4j.exception.BlockChainAcessException;
import com.bloxbean.blox4j.impl.aion.service.event.rx.ContractEventHolder;
import com.bloxbean.blox4j.model.ContractEventFilterBean;
import com.bloxbean.blox4j.service.ContractEventService;
import org.aion.api.IAionAPI;
import org.aion.api.IContract;
import org.aion.api.type.ApiMsg;
import org.aion.base.type.AionAddress;
import org.aion.vm.api.interfaces.Address;
import org.reactivestreams.Publisher;
import com.bloxbean.blox4j.impl.aion.pool.AionConnection;
import com.bloxbean.blox4j.impl.aion.service.ContractServiceImpl;
import com.bloxbean.blox4j.impl.aion.service.event.rx.ContractEventPublisher;
import com.bloxbean.blox4j.impl.aion.util.ModelConverter;
import com.bloxbean.blox4j.model.ContractEventBean;
import com.bloxbean.blox4j.model.Output;
import com.bloxbean.blox4j.pool.ConnectionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.aion.api.ITx.NRG_LIMIT_TX_MAX;
import static org.aion.api.ITx.NRG_PRICE_MIN;

@Repository
public class ContractEventServiceImpl implements ContractEventService {

    private static final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);

    @Autowired
    ConnectionHelper connectionHelper;

    @Autowired
    ContractEventHolder contractEventHolder;

    @Override
    public Publisher<ContractEventBean> registerEvents(String ownerAddressStr, String contractAddressStr, String abi, List<String> events,
                                                       ContractEventFilterBean contractEventFilterBean, List<Output> outputTypes) {

        Address contractAddress = AionAddress.wrap(contractAddressStr);

        Address ownerAddress = null; //Owner address is not mandatory. Can be filled with contract adress
        if(StringUtils.isEmpty(ownerAddressStr))
            ownerAddress = contractAddress;
        else
            ownerAddress = AionAddress.wrap(ownerAddressStr);

        AionConnection aionConnection = (AionConnection) connectionHelper.getConnection();

        IAionAPI api = aionConnection.getApi();
        ApiMsg apiMsg = aionConnection.getApiMsg();

        IContract ctr = api.getContractController().getContractAt(ownerAddress, contractAddress, abi);

        ctr.newEvents(events);

        ctr.setTxNrgLimit(NRG_LIMIT_TX_MAX).setTxNrgPrice(NRG_PRICE_MIN)
                .newEvents(events);

        ApiMsg apiMsg1;
        if (contractEventFilterBean != null)
            apiMsg1 = ctr.register(ModelConverter.convert(contractEventFilterBean));
        else
            apiMsg1 = ctr.register();

        if(apiMsg1.isError()) {
            logger.error("Error registering event. {} : {} ", apiMsg1.getErrorCode(), apiMsg1.getErrString());
            throw new BlockChainAcessException(apiMsg1.getErrorCode(), apiMsg1.getErrString());
        }

        String eventHash = ContractEventHolder.hash(contractAddressStr, events, contractEventFilterBean);

        ContractEventPublisher contractEventPublisher = contractEventHolder.getPublisher(eventHash);

        if(contractEventPublisher == null) {
            logger.debug("There is no event publisher for event {} . Create a new one.", eventHash);
            contractEventPublisher = new ContractEventPublisher(aionConnection, ctr, outputTypes);

            //add the publisher to holder
            contractEventHolder.addEvent(eventHash, contractEventPublisher);
        } else
            logger.debug("Found an existing publisher for {}", eventHash);


        return contractEventPublisher.getPublisher();
    }

}
