package com.taxi.web.model.dao;

public interface GenericDao<T> extends AutoCloseable{
    void close();
}