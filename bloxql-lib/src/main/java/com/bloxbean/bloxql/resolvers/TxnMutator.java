package com.bloxbean.bloxql.resolvers;

import com.bloxbean.bloxql.model.*;
import com.bloxbean.bloxql.model.input.TxArgsInput;
import com.bloxbean.bloxql.service.TxnService;
import com.bloxbean.bloxql.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public  class TxnMutator {
    private Logger logger = LoggerFactory.getLogger(TxnMutator.class);

    private TxnService txnService;

    public TxnMutator(TxnService txnService) {
        this.txnService = txnService;
    }

    public String call(TxArgsInput args) {
        return txnService.call(args);
    }

    public Map<String, CompileResponseBean> compile(String code) {
   //public String compile(String code) {
        return txnService.compile(code);
    }

    public List<DeployResponseBean> contractDeploy(ContractDeployBean cd) {
        return txnService.contractDeploy(cd);
    }

    public boolean eventDeregister(List<String> evts, String address) {
        return txnService.eventDeregister(evts, address);
    }

    public boolean eventRegister(List<String> evts, ContractEventFilterBean ef, String address) {
        return txnService.eventRegister(evts, ef, address);
    }

    public boolean fastTxBuild(TxArgsInput args, boolean call) {
        return txnService.fastTxBuild(args, call);
    }

    public MsgRespBean sendRawTransaction(String encodedTx, boolean async) {
        return txnService.sendRawTransaction(encodedTx, async);
    }

    public MsgRespBean sendSignedTransaction(TxArgsInput txArgsInput, String privateKey, boolean async) {
        return txnService.sendSignedTransaction(txArgsInput, privateKey, async);
    }

    public MsgRespBean sendTransaction(TxArgsInput txArgsInput, boolean async) {
        return txnService.sendTransaction(txArgsInput, async);
    }
}