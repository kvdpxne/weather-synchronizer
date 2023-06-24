package me.kvdpxne.ws;

public interface Requester<T> extends Runnable {

  T getRequestResult();
}
