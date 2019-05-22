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

package com.bloxbean.blox4j.gql.autoconfig;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@AutoConfigureBefore(ChainGQLAutoConfiguration.class)
public class ChainWebAutoConfiguration {
    private final static Logger log = LoggerFactory.getLogger(ChainWebAutoConfiguration.class);


    GraphQLErrorHandler errorHandler = new GraphQLErrorHandler() {
        @Override
        public List<GraphQLError> processErrors(List<GraphQLError> errors) {
            List<GraphQLError> clientErrors = errors.stream()
                    .filter(this::isClientError)
                    .collect(Collectors.toList());

            List<GraphQLError> serverErrors = errors.stream()
                    .filter(e -> !isClientError(e))
                    .map(GraphQLErrorAdapter::new)
                    .collect(Collectors.toList());

            serverErrors.forEach(error -> {
                log.error("Error executing query ({}): {}", error.getClass().getSimpleName(), error.getMessage());
            });

            List<GraphQLError> e = new ArrayList<>();
            e.addAll(clientErrors);
            e.addAll(serverErrors);
            return e;
        }

        protected List<GraphQLError> filterGraphQLErrors(List<GraphQLError> errors) {
            return errors.stream()
                    .filter(this::isClientError)
                    .collect(Collectors.toList());
        }

        protected boolean isClientError(GraphQLError error) {
            return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
        }
    };

    @Bean
    public GraphQLErrorHandler graphQLErrorHandler() {
        return errorHandler;
    }
}
