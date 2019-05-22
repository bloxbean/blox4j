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

package com.bloxbean.blox4j.model;

import com.bloxbean.blox4j.model.input.TxArgsInput;

public class ContractDeployPayload {

    private TxArgsInput txArgsInput;

    private CompileResponseBean compileResponseBean;

    public ContractDeployPayload() {

    }

    public ContractDeployPayload(TxArgsInput txArgsInput, CompileResponseBean compileResponseBean) {
        this.txArgsInput = txArgsInput;
        this.compileResponseBean = compileResponseBean;
    }

    public TxArgsInput getTxArgsInput() {
        return txArgsInput;
    }

    public void setTxArgsInput(TxArgsInput txArgsInput) {
        this.txArgsInput = txArgsInput;
    }

    public CompileResponseBean getCompileResponseBean() {
        return compileResponseBean;
    }

    public void setCompileResponseBean(
        CompileResponseBean compileResponseBean) {
        this.compileResponseBean = compileResponseBean;
    }
}
