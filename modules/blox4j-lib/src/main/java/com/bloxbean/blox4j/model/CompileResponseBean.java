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

public class CompileResponseBean {

    private List<ContractAbiEntryBean> abiDefinition;
    private String abiDefString;
    private String code;
    private String compilerOptions;
    private String compilerVersion;
//    private String developerDoc;
    private String language;
    private String languageVersion;
    private String source;
//    private transient String userDoc;

    public List<ContractAbiEntryBean> getAbiDefinition() {
        return abiDefinition;
    }

    public void setAbiDefinition(List<ContractAbiEntryBean> abiDefinition) {
        this.abiDefinition = abiDefinition;
    }

    public String getAbiDefString() {
        return abiDefString;
    }

    public void setAbiDefString(String abiDefString) {
        this.abiDefString = abiDefString;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompilerOptions() {
        return compilerOptions;
    }

    public void setCompilerOptions(String compilerOptions) {
        this.compilerOptions = compilerOptions;
    }

    public String getCompilerVersion() {
        return compilerVersion;
    }

    public void setCompilerVersion(String compilerVersion) {
        this.compilerVersion = compilerVersion;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageVersion() {
        return languageVersion;
    }

    public void setLanguageVersion(String languageVersion) {
        this.languageVersion = languageVersion;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

   /* public String getDeveloperDoc() {
        return developerDoc;
    }

    public void setDeveloperDoc(String developerDoc) {
        this.developerDoc = developerDoc;
    }

    public String getUserDoc() {
        return userDoc;
    }

    public void setUserDoc(String userDoc) {
        this.userDoc = userDoc;
    }*/

    public static class ContractAbiEntryBean {

        private boolean anonymous;
        private boolean constant;
        private List<ContractAbiIOParamBean> inputs;
        private String name;
        private List<ContractAbiIOParamBean> outputs;
        private boolean payable;
        private String type;

        public boolean isAnonymous() {
            return anonymous;
        }

        public void setAnonymous(boolean anonymous) {
            this.anonymous = anonymous;
        }

        public boolean isConstant() {
            return constant;
        }

        public void setConstant(boolean constant) {
            this.constant = constant;
        }

        public List<ContractAbiIOParamBean> getInputs() {
            return inputs;
        }

        public void setInputs(List<ContractAbiIOParamBean> inputs) {
            this.inputs = inputs;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ContractAbiIOParamBean> getOutputs() {
            return outputs;
        }

        public void setOutputs(List<ContractAbiIOParamBean> outputs) {
            this.outputs = outputs;
        }

        public boolean isPayable() {
            return payable;
        }

        public void setPayable(boolean payable) {
            this.payable = payable;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }


    }

    public static class ContractAbiIOParamBean {

        private String name;
        private List<Integer> paramLengths;
        private String type;
        private boolean indexed;

        public ContractAbiIOParamBean(String name, List<Integer> paramLengths, String type, boolean indexed) {
            this.name = name;
            this.paramLengths = paramLengths;
            this.type = type;
            this.indexed = indexed;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Integer> getParamLengths() {
            return paramLengths;
        }

        public void setParamLengths(List<Integer> paramLengths) {
            this.paramLengths = paramLengths;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isIndexed() {
            return indexed;
        }

        public void setIndexed(boolean indexed) {
            this.indexed = indexed;
        }
    }

}

