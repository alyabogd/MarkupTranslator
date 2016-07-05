package com.company.Exceptions;

public class RecursiveBlockquoteException extends Exception {
    public RecursiveBlockquoteException(String s){
        super(s);
    }

    public RecursiveBlockquoteException(){
        super();
    }
}
