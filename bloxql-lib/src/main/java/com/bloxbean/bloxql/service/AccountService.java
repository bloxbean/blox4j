package com.bloxbean.bloxql.service;

import com.bloxbean.bloxql.model.Account;
import com.bloxbean.bloxql.model.AccountKey;
import com.bloxbean.bloxql.model.AccountKeyExport;
import com.bloxbean.bloxql.model.input.AccountKeyExportInput;

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
