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

import com.bloxbean.blox4j.model.Account;
import com.bloxbean.blox4j.model.AccountKey;
import com.bloxbean.blox4j.model.AccountKeyExport;
import com.bloxbean.blox4j.model.input.AccountKeyExportInput;

import java.math.BigInteger;
import java.util.List;

public interface AccountService {

    public List<AccountKey> accountCreate(List<String> passphrase, boolean privateKey);

    public AccountKeyExport accountExport(List<AccountKeyExportInput> keys);

    public AccountKeyExport accountBackup(List<AccountKeyExportInput> keys);

    public boolean accountImport(String privateKey, String passphrase);

    //Get services
    public Account getAccount(String address, List<String> fields, long blockNumber);

    public BigInteger getBalance(String address, long blockNumber);

    public BigInteger getNonce(String address);

}
