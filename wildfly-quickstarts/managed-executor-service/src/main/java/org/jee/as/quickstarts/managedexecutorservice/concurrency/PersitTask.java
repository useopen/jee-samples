/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jee.as.quickstarts.managedexecutorservice.concurrency;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.jee.as.quickstarts.managedexecutorservice.model.Product;

//This Runnable class is used to persist any Product
public class PersitTask implements Runnable {

    @Inject
    private Logger log;

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    private Product product;

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public void run() {
        try {
            log.info("Begin transaction");
            userTransaction.begin();
            log.info("Persisting a new product");
            entityManager.persist(product);
            log.info("Commit transaction");
            userTransaction.commit();
        } catch (Exception e) {
            log.info("Exception: " + e.getMessage());
            try {
                if (userTransaction != null) {
                    log.info("Rollback transaction");
                    userTransaction.rollback();
                }
            } catch (SystemException e1) {
                log.info("Failed to rollback transaction: " + e1.getMessage());
            }
        }

    }

}
