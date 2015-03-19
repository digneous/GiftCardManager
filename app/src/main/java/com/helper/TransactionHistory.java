package com.helper;

/**
 * Created by Deb on 3/17/2015.
 */
public class TransactionHistory {

    private String TxnDate;
    private int TxnID;
    private String cardNumber;
    private String comment;
    private int Amount;
    private String TxnType;

    public String getTxnType() {
        return TxnType;
    }

    public void setTxnType(String txnType) {
        TxnType = txnType;
    }

    @Override
    public String toString() {
        String amt = this.getTxnType().equals("DR")?"-":"+";
        amt = amt+String.valueOf(this.getAmount());
        return "Date :"+this.getTxnDate()+"\nCard#:"+this.getCardNumber()+"\nAmount:"+amt+"\nComment:"+this.getComment();
    }

    public String getTxnDate() {
        return TxnDate;
    }

    public void setTxnDate(String txnDate) {
        TxnDate = txnDate;
    }

    public int getTxnID() {
        return TxnID;
    }

    public void setTxnID(int txnID) {
        TxnID = txnID;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }



}
