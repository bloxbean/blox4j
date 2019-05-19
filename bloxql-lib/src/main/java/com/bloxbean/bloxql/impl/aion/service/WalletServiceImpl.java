package com.bloxbean.bloxql.impl.aion.service;

import com.bloxbean.bloxql.exception.BlockChainAcessException;
import com.bloxbean.bloxql.impl.aion.service.dao.AionBlockchainAccessor;
import com.bloxbean.bloxql.model.Account;
import org.aion.base.type.AionAddress;
import org.aion.vm.api.interfaces.Address;
import com.bloxbean.bloxql.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class WalletServiceImpl implements WalletService {

    private static final Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

    private AionBlockchainAccessor accessor;

    @Value("${aion.graphql.disable-sensitive-operations:false}")
    private boolean disableSensitiveOperations;

    public WalletServiceImpl(AionBlockchainAccessor accessor) {
        this.accessor = accessor;
    }

    @Override
    public List<String> getAddresses() {
        if (logger.isDebugEnabled())
            logger.debug("Getting wallet accounts");

        return accessor.call(((apiMsg, api) -> {

            apiMsg.set(api.getWallet().getAccounts());

            if (apiMsg.isError()) {
                logger.error("Unable to get wallet accounts" + apiMsg.getErrString());
                throw new BlockChainAcessException(apiMsg.getErrString());
            }

            return ((List<Address>)apiMsg.getObject()).stream()
                .map(address -> address.toString())
                .collect(Collectors.toList());
        }));
    }

    @Override
    public List<Account> getAccounts() {
        List<String> addresses = getAddresses();

        if(addresses == null || addresses.size() == 0)
            return Collections.EMPTY_LIST;

        return addresses.stream()
            .map(address -> new Account(address))
            .collect(Collectors.toList());
    }

    @Override
    public Account getDefaultAccount() {
        if (logger.isDebugEnabled())
            logger.debug("Getting default wallet account");

        return accessor.call(((apiMsg, api) -> {

            apiMsg.set(api.getWallet().getDefaultAccount());

            if (apiMsg.isError()) {
                logger.error("Unable to get wallet accounts" + apiMsg.getErrString());
                throw new BlockChainAcessException(apiMsg.getErrString());
            }

            if(apiMsg.getObject() == null)
                return null;
            else
                return new Account(apiMsg.getObject().toString());
        }));
    }

    @Override
    public Account getMinerAccount() {
        if (logger.isDebugEnabled())
            logger.debug("Getting default wallet account");

        return accessor.call(((apiMsg, api) -> {

            apiMsg.set(api.getWallet().getMinerAccount());

            if (apiMsg.isError()) {
                logger.error("Unable to get wallet accounts" + apiMsg.getErrString());
                throw new BlockChainAcessException(apiMsg.getErrString());
            }

            if(apiMsg.getObject() == null)
                return null;
            else
                return new Account(apiMsg.getObject().toString()); //Address.toString
        }));
    }

    @Override
    public boolean lockAccount(String pubAddress, String passphrase) {
        if(logger.isDebugEnabled())
            logger.debug("Trying to lock the account");

        return accessor.call(((apiMsg, api) -> {

            apiMsg.set(api.getWallet().lockAccount(AionAddress.wrap(pubAddress), passphrase));

            if (apiMsg.isError()) {
                logger.error("Unable to lock the account" + apiMsg.getErrString());
                throw new BlockChainAcessException(apiMsg.getErrString());
            }

            return apiMsg.getObject();
        }));

    }

    @Override
    public boolean unlockAccount(String pubAddress, String passphrase, int duration) {
        if(logger.isDebugEnabled())
            logger.debug("Trying to unlock the account");

        if(disableSensitiveOperations)
            throw new BlockChainAcessException("This operation is disabled in this deployment due to security reason.");

        return accessor.call(((apiMsg, api) -> {

            apiMsg.set(api.getWallet().unlockAccount(AionAddress.wrap(pubAddress), passphrase, duration));

            if (apiMsg.isError()) {
                logger.error("Unable to unlock  account" + apiMsg.getErrString());
                throw new BlockChainAcessException(apiMsg.getErrString());
            }

            return apiMsg.getObject();
        }));
    }
}
