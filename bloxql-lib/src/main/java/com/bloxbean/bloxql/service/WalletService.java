package com.bloxbean.bloxql.service;

import com.bloxbean.bloxql.model.Account;

import java.util.List;

public interface WalletService {

    public List<String> getAddresses();

    public List<Account> getAccounts();

    public Account getDefaultAccount();

    public Account getMinerAccount();

    public boolean lockAccount(String pubAddress, String passphrase);

    public boolean unlockAccount(String pubAddress, String passphrase, int duration);
}
