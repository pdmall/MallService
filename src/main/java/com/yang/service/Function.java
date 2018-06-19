package com.yang.service;

public interface Function<T, E> {

	public T callback(E e);

}
