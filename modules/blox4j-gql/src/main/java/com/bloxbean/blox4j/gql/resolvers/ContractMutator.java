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

import com.bloxbean.blox4j.model.ContractBean;
import com.bloxbean.blox4j.model.ContractResponseBean;
import com.bloxbean.blox4j.model.input.ConstructorArgs;
import com.bloxbean.blox4j.model.input.ContractFunction;
import com.bloxbean.blox4j.model.input.Param;
import com.bloxbean.blox4j.service.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public class ContractMutator {

    private Logger logger = LoggerFactory.getLogger(ContractMutator.class);

    private ContractService contractService;

    public ContractMutator(ContractService contractService) {
        this.contractService = contractService;
    }

    public List<ContractBean> createFromSource(String source, String from, long nrgLimit, long nrgPrice, BigInteger value, List<Param> constructorArgs) {

        return contractService.createFromSource​(source, from, nrgLimit, nrgPrice, value, constructorArgs);

    }

    public List<ContractBean> createMultiContractsFromSource​(String source, String from, long nrgLimit, long nrgPrice, BigInteger value,
                                                              List<ConstructorArgs> constructorArgsList) {

        return contractService.createMultiContractsFromSource​(source, from, nrgLimit, nrgPrice, value, constructorArgsList);

    }

    public ContractResponseBean execute(String fromAddress, String contractAddress, String abiDefinition,
                                        ContractFunction contractFunction, long nrgLimit, long nrgPrice, long txValue) {

        if(logger.isDebugEnabled()) {
            logger.debug("From address: {}", fromAddress);
            logger.debug("Contract Address: {}" + contractAddress);
            logger.debug("Abi Definition: {}" + abiDefinition);

            logger.debug("Contract function: {}" + contractFunction);
        }

        return contractService.execute(fromAddress, contractAddress, abiDefinition, contractFunction, nrgLimit, nrgPrice, txValue);

    }

    public boolean deregisterAllEvents() {
        return contractService.deregisterAllEvents();
    }
}
