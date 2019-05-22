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

package com.bloxbean.blox4j.rest.controllers;

import com.bloxbean.blox4j.model.MsgRespBean;
import com.bloxbean.blox4j.model.TxDetails;
import com.bloxbean.blox4j.rest.common.RestConstants;
import com.bloxbean.blox4j.service.TxnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.bloxbean.blox4j.rest.exception.RestResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(RestConstants.VERSION1_BASE_PATH + "transactions")
@ConditionalOnProperty(name = "rest.enable", havingValue = "true")
@Api(value = "transactions", description = "Transactions specific apis")
public class TransactionController {

    @Autowired
    private TxnService txnService;

    @GetMapping(produces = "application/hal+json")
    @ApiOperation(value = "Get transactions starting from the block number passed through \"before\" parameter")
    public ResponseEntity<Resources<TxDetails>> transactions(
        @RequestParam(value = "before", required = false, defaultValue = "-1") @ApiParam(value = "Start block number") Long before,
        @RequestParam(value = "first", required = false, defaultValue = "20") @ApiParam(value = "No of transactions to return") Long first) {
        List<TxDetails> txDetailsList = txnService.getTransactions(before, first);

        if (txDetailsList == null) {
            return ResponseEntity.ok(null);
        }

        for (TxDetails txDetails : txDetailsList) {
            addTxnDetailsLinks(txDetails.getTxHash(), txDetails);
        }

        final Resources<TxDetails> txDetailsResources = new Resources<>(txDetailsList);

        txDetailsResources.add(
            linkTo(methodOn(TransactionController.class).transactions(before, first))
                .withSelfRel()
        );

        return ResponseEntity.ok(txDetailsResources);

    }

    @GetMapping(value = "/{txHash}", produces = "application/json")
    @ApiOperation(value = "Get transaction details for the given transaction hash")
    public ResponseEntity<TxDetails> transaction(
        @PathVariable("txHash") @ApiParam("Transaction hash") String txHash) {

        TxDetails txDetails = txnService.getTransaction(txHash);

        if (txHash != null) {
            addTxnDetailsLinks(txHash, txDetails);

            return ResponseEntity.ok(txDetails);
        } else {
            throw new RestResourceNotFoundException("Transaction not found");
        }
    }

    @GetMapping(value = "/search", produces = "application/json")
    @ApiOperation(value = "Get transactions details for list of transaction hash")
    public ResponseEntity<Resources<TxDetails>> transactionsByHash(
        @RequestParam("txHash") @ApiParam("Transaction hash") List<String> txHash) {

        List<TxDetails> txDetailsList = txnService.getTransactionsByHash(txHash);

        if (txDetailsList == null) {
            return ResponseEntity.ok(null);
        }

        for (TxDetails txDetails : txDetailsList) {
            addTxnDetailsLinks(txDetails.getTxHash(), txDetails);
        }

        final Resources<TxDetails> txDetailsResources = new Resources<>(txDetailsList);

        return ResponseEntity.ok(txDetailsResources);
    }

    @PostMapping(consumes = "text/plain", produces = "application/json")
    @ApiOperation(value = "Send a encoded sign transaction to kernel")
    public ResponseEntity<MsgRespBean> sendRawTransaction(@RequestBody String encodedTx) {
        MsgRespBean resBean = txnService.sendRawTransaction(encodedTx, false);

        if(resBean != null) {
            resBean.add(
                linkTo(methodOn(TransactionController.class).transaction(resBean.getTxHash())).withSelfRel()
            );

            return new ResponseEntity(resBean, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private void addTxnDetailsLinks(
        @PathVariable("txnHash") @ApiParam("Transaction hash") String txnHash,
        TxDetails txDetails) {
        txDetails.add(
            linkTo(methodOn(TransactionController.class).transaction(txnHash)).withSelfRel());

        txDetails.add(
            linkTo(methodOn(AccountController.class).getAccount(txDetails.getFrom()))
                .withRel("from"));

        txDetails.add(
            linkTo(methodOn(AccountController.class).getAccount(txDetails.getTo()))
                .withRel("to"));

        txDetails.add(
            linkTo(methodOn(BlockController.class).getBlock(txDetails.getBlockNumber()))
                .withRel("block")
        );
    }

}
