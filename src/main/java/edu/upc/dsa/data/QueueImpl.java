package edu.upc.dsa.data;

import edu.upc.dsa.exceptions.*;

import java.util.Arrays;
import java.util.List;

public class QueueImpl<E> implements Queue<E>{
    private E[] data;
    private int p;

    public QueueImpl(int len) {
        this.p = 0;
        this.data = (E[])new Object[len];
    }

    public void push(E e) throws FullQueueException {
        if (isFull()) throw new FullQueueException();

        this.data[this.p++]=e;

    }

    public E pop() throws EmptyQueueException {
        if(isEmpty()) throw new EmptyQueueException();
        E e = this.data[0];
        for(int i = 1; i < p; i++)
        {
            this.data[i-1] = this.data[i];
        }
        p--;

        if (p == 0) {
            this.data[0] = null;
        }

        return e;
    }

    private boolean isFull() {
        return this.p == this.data.length;
    }

    private boolean isEmpty() {
        return this.p == 0;
    }

    public int size() {
        return this.p;
    }

    public List<E> toList() {
        return Arrays.asList(data);
    }
}