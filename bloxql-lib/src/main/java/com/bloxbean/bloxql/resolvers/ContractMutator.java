package com.bloxbean.bloxql.resolvers;

import com.bloxbean.bloxql.model.ContractBean;
import com.bloxbean.bloxql.model.ContractResponseBean;
import com.bloxbean.bloxql.model.input.ConstructorArgs;
import com.bloxbean.bloxql.model.input.ContractFunction;
import com.bloxbean.bloxql.model.input.Param;
import com.bloxbean.bloxql.service.ContractService;
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
