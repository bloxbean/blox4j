package com.bloxbean.bloxql.resolvers;

import com.bloxbean.bloxql.model.ContractEventFilterBean;
import com.bloxbean.bloxql.model.ContractEventBean;
import com.bloxbean.bloxql.model.ContractResponseBean;
import com.bloxbean.bloxql.model.Output;
import com.bloxbean.bloxql.model.input.ContractFunction;
import com.bloxbean.bloxql.service.ContractService;
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
