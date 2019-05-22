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

import com.bloxbean.blox4j.model.*;
import com.bloxbean.blox4j.model.input.TxArgsInput;
import com.bloxbean.blox4j.service.TxnService;
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