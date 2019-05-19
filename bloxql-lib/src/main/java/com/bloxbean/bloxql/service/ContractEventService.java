package com.bloxbean.bloxql.service;

import com.bloxbean.bloxql.model.ContractEventFilterBean;
import org.reactivestreams.Publisher;
import com.bloxbean.bloxql.model.ContractEventBean;
import com.bloxbean.bloxql.model.Output;

import java.util.List;

public interface ContractEventService {

    Publisher<ContractEventBean> registerEvents(String ownerAddress, String contractAddress, String abi, List<String> events,
                                                ContractEventFilterBean contractEventFilterBean, List<Output> outputTypes);

}
