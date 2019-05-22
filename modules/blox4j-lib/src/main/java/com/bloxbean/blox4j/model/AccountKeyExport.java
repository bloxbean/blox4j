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

import java.util.List;

public class AccountKeyExport {

    private List<String> keyfiles;
    private List<String> invalidAddr;

    public AccountKeyExport(List<String> keyfiles, List<String> invalidAddr) {
        this.keyfiles = keyfiles;
        this.invalidAddr = invalidAddr;
    }

    public List<String> getKeyfiles() {
        return keyfiles;
    }

//    public void setKeyfiles(List<String> keyfiles) {
//        this.keyfiles = keyfiles;
//    }

    public List<String> getInvalidAddr() {
        return invalidAddr;
    }

//    public void setInvalidAddr(List<String> invalidAddr) {
//        this.invalidAddr = invalidAddr;
//    }


    @Override
    public String toString() {
        return "AccountKeyExport{" +
                "keyfiles=" + keyfiles +
                ", invalidAddr=" + invalidAddr +
                '}';
    }
}
