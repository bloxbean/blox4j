package com.bloxbean.bloxql.service;

import com.bloxbean.bloxql.model.*;
import com.bloxbean.bloxql.model.input.ConstructorArgs;
import com.bloxbean.bloxql.model.*;
import com.bloxbean.bloxql.model.input.ContractFunction;
import com.bloxbean.bloxql.model.input.Param;
import com.bloxbean.bloxql.model.input.TxArgsInput;

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
