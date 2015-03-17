package com.helper;

/**
 * Created by Sathish on 3/4/2015.
 */
public class Card implements Comparable<Card>
{
    private String cardNumber;
    private String cvv;
    private String expirydate;
    private int balance;
    private int debitAmount;
    private int balanceAfterDebit;

    public int getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(int debitAmount) {
        this.debitAmount = debitAmount;
    }

    public int getBalanceAfterDebit() {
        return balanceAfterDebit;
    }

    public void setBalanceAfterDebit(int balanceAfterDebit) {
        this.balanceAfterDebit = balanceAfterDebit;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    private String cardType;
    private String cardStatus;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public int compareTo(Card card) {
        return this.balance - card.getBalance();
    }
}
