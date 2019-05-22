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

package com.bloxbean.blox4j.service;

import com.bloxbean.blox4j.model.*;
import com.bloxbean.blox4j.model.input.ConstructorArgs;
import com.bloxbean.blox4j.model.input.ContractFunction;
import com.bloxbean.blox4j.model.input.Param;
import com.bloxbean.blox4j.model.input.TxArgsInput;

import java.math.BigInteger;
import java.util.List;

public interface ContractService {

    public List<ContractBean> createFromSource​(String source, String from, long nrgLimit, long nrgPrice);

    public List<ContractBean> createFromSource​(String source, String from, long nrgLimit, long nrgPrice,
                                                BigInteger value);

    public List<ContractBean> createFromSource​(String source, String from, long nrgLimit, long nrgPrice,
                                                BigInteger value, List<Param> params);

    //Mutiple contracts with constructore args
    public List<ContractBean> createMultiContractsFromSource​(String source, String from, long nrgLimit, long nrgPrice,
                                                              BigInteger value, List<ConstructorArgs> constructorArgsList);

    public ContractResponseBean execute(String fromAddress, String contractAddress, String abiDefinition,
                                        ContractFunction contractFunction, long nrgLimit, long nrgPrice, long txValue);

    public ContractResponseBean call(String fromAddress, String contractAddress, String abiDefinition,
                                     ContractFunction contractFunction);

    public List<ContractEventBean> getContractEvents(String ownerAddress, String contractAddress, String abiDefinition,
                                                     List<String> events, ContractEventFilterBean eventFilterBean, List<Output> outputTypes);


    public boolean deregisterAllEvents();

    //Methods with data for signing
    public TxArgsInput getContractCallPayload(String fromAddress, String contractAddress, String abiDefinition,
                                              ContractFunction contractFunction, long nrgLimit, long nrgPrice, long txValue);

    public TxArgsInput getContractDeployPayload(String compileCode, String abiString, String fromAddress,
                                                long nrgLimit, long nrgPrice, List<Object> args);

    public ContractDeployPayload getContractDeployPayloadBySource(String source, String contractName, String fromAddress,
                                                                  long nrgLimit, long nrgPrice, List<Object> args);

    //deploy using default address in kernel
    public TxReceiptBean deployContractBySource(String source, String contractName, String fromAddress,
                                                long nrgLimit, long nrgPrice, List<Object> args);

    public TxReceiptBean deployContractByCode(String code, String abiString, String fromAddress,
                                              long nrgLimit, long nrgPrice, List<Object> args);

    public List<?> call(TxArgsInput txArgsInput, String abiFunction, List<Output> outputTypes);

}
