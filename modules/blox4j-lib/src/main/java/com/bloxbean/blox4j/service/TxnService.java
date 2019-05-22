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
import com.bloxbean.blox4j.model.input.TxArgsInput;

import java.util.List;
import java.util.Map;

public interface TxnService {

    public String call(TxArgsInput args);

    public byte[] callBytes(TxArgsInput args);

    public Map<String, CompileResponseBean> compile(String code);

    public List<DeployResponseBean> contractDeploy(ContractDeployBean cd);

    public long estimateNrg(String code);

    public long estimateNrg(TxArgsInput argsInput);

    public long estimateNrgFromSource(String source);

    public boolean eventDeregister(List<String> evts, String address);

    public boolean eventRegister(List<String> evts, ContractEventFilterBean ef, String address);

    public boolean fastTxBuild(TxArgsInput args, boolean call);

    public String getCode(String address);

    public String getCode(String address, long blockNumber);

    public MsgRespBean getMsgStatus(String msgHash);

    public long getNrgPrice();

    public String getSolcVersion();

    public TxReceiptBean getTxReceipt(String txnHash);

    public MsgRespBean sendRawTransaction(String encodedTx, boolean async);

    public MsgRespBean sendSignedTransaction(TxArgsInput txArgsInput, String privateKey, boolean async);

    public MsgRespBean sendTransaction(TxArgsInput txArgsInput, boolean async);


    //additional methods
    public List<TxDetails> getTransactions(long before, long first);

    public TxDetails getTransaction(String txHash);

    public List<TxDetails> getTransactionsByHash(List<String> txHash);


}
