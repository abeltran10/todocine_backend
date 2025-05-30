package com.todocine.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class BaseRepoImpl {

    @PersistenceContext
    protected EntityManager entityManager;

}
