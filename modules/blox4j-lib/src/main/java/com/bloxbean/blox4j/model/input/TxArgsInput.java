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

package com.bloxbean.blox4j.model.input;

import java.math.BigInteger;

public class TxArgsInput {
    private String data;
    private String from;
    private String to;
    private BigInteger value;
    private BigInteger nonce;
    private long nrgLimit;
    private long nrgPrice;

    //If data is in hex or utf-8
    private String encoding;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    public BigInteger getNonce() {
        return nonce;
    }

    public void setNonce(BigInteger nonce) {
        this.nonce = nonce;
    }

    public long getNrgLimit() {
        return nrgLimit;
    }

    public void setNrgLimit(long nrgLimit) {
        this.nrgLimit = nrgLimit;
    }

    public long getNrgPrice() {
        return nrgPrice;
    }

    public void setNrgPrice(long nrgPrice) {
        this.nrgPrice = nrgPrice;
    }

    /**
     * Get the char encoding of passed data hex or utf-8
     * @return encoded string
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Set the char encoding of passed data hex or utf-8
     * @param encoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public String toString() {
        return "TxArgsInput{" +
                "data='" + data + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", value=" + value +
                ", nonce=" + nonce +
                ", nrgLimit=" + nrgLimit +
                ", nrgPrice=" + nrgPrice +
                '}';
    }
}
