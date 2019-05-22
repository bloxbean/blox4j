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

import com.bloxbean.blox4j.model.Account;
import com.bloxbean.blox4j.rest.common.RestConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.bloxbean.blox4j.rest.exception.RestResourceNotFoundException;
import com.bloxbean.blox4j.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(RestConstants.VERSION1_BASE_PATH + "accounts")
@ConditionalOnProperty(name = "rest.enable", havingValue = "true")
@Api(value="accounts", description="Account specific apis")
public class AccountController {

    private final static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/{address}", produces = "application/json")
    @ApiOperation(value = "Get account by address")
    public ResponseEntity<Account> getAccount(@PathVariable("address") String address) {

        try {
            List<String> fields = new ArrayList<>();
            fields.add("balance");
            fields.add("nonce");

            Account account = accountService.getAccount(address, fields, -1);

            if (account != null) {
                account.add(
                    linkTo(methodOn(AccountController.class).getAccount(address))
                        .withSelfRel()
                );

                return ResponseEntity.ok(account);
            } else {
                throw new RestResourceNotFoundException("Account not found");
            }
        }catch (Exception e) {
            logger.error("Error getting account details", e);
            throw e;
        }

    }
}
