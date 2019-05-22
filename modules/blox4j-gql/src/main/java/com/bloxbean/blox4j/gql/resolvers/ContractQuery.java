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

import com.bloxbean.blox4j.model.ContractEventFilterBean;
import com.bloxbean.blox4j.model.ContractEventBean;
import com.bloxbean.blox4j.model.ContractResponseBean;
import com.bloxbean.blox4j.model.Output;
import com.bloxbean.blox4j.model.input.ContractFunction;
import com.bloxbean.blox4j.service.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContractQuery {

    private Logger logger = LoggerFactory.getLogger(ContractQuery.class);

    private ContractService contractService;

    public ContractQuery(ContractService contractService) {
        this.contractService = contractService;
    }

    public ContractResponseBean call(String fromAddress, String contractAddress, String abiDefinition,
                                     ContractFunction contractFunction) {

        return contractService.call(fromAddress, contractAddress, abiDefinition, contractFunction);
    }

    public List<ContractEventBean> events(String fromAddress, String contractAddress, String abiDefinition,
                                          List<String> events, ContractEventFilterBean eventFilterBean, List<Output> outputTypes) {

        return contractService.getContractEvents(fromAddress, contractAddress, abiDefinition, events, eventFilterBean, outputTypes);

    }
}
