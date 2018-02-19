/*
 * Copyright (c) 2000-2018 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package bank.driver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import bank.InactiveException;
import bank.OverdrawException;

/**
 * A local implementation of the Bank.
 * i.e. if there is no server
 */
public class LocalDriver implements bank.BankDriver {
    private Bank bank = null;

    @Override
    public void connect(String[] args) {
        bank = new Bank();
        System.out.println("connected...");
    }

    @Override
    public void disconnect() {
        bank = null;
        System.out.println("disconnected...");
    }

    @Override
    public Bank getBank() {
        return bank;
    }

    static class Bank implements bank.Bank {

        private final Map<String, Account> accounts = new HashMap<>();

        @Override
        public Set<String> getAccountNumbers() {
            return accounts.entrySet()
                           .stream()
                           // Check if account is active
                           .filter(entry -> entry.getValue().active)
                           .map(Map.Entry::getKey)
                           .collect(Collectors.toSet());
        }

        @Override
        public String createAccount(String owner) {
            Account newAccount = new Account(owner, Integer.toString(accounts.size()));

            accounts.put(newAccount.getNumber(), newAccount);

            return newAccount.number;
        }

        @Override
        public boolean closeAccount(String number) {
            Account account = accounts.get(number);
            if (account == null) {
                return false;
            } else if (account.getBalance() != 0) {
                return false;
            }

            account.active = false;
            return true;
        }

        @Override
        public bank.Account getAccount(String number) {
            return accounts.get(number);
        }

        @Override
        public void transfer(bank.Account from, bank.Account to, double amount)
            throws IOException, InactiveException, OverdrawException {
            if (amount < 0) {
                throw new IllegalArgumentException("Withdrawing negative amounts is illegal");
            } else if (!from.isActive()) {
                throw new InactiveException("From account is in-active");
            } else if (!to.isActive()) {
                throw new InactiveException("To account is in-active");
            }

            from.withdraw(amount);
            to.deposit(amount);
        }

    }

    static class Account implements bank.Account {
        private String number;
        private String owner;
        private double balance;
        private boolean active = true;

        Account(String owner, String number) {
            this.owner = owner;
            this.number = number;
        }

        @Override
        public double getBalance() {
            return balance;
        }

        @Override
        public String getOwner() {
            return owner;
        }

        @Override
        public String getNumber() {
            return number;
        }

        @Override
        public boolean isActive() {
            return active;
        }

        @Override
        public void deposit(double amount) throws InactiveException {
            if (!active) {
                throw new InactiveException();
            } else if (amount < 0) {
                throw new IllegalArgumentException("Withdrawing negative amounts is illegal");
            }

            balance += amount;
        }

        @Override
        public void withdraw(double amount) throws InactiveException, OverdrawException {
            if (!active) {
                throw new InactiveException();
            } else if (amount < 0) {
                throw new IllegalArgumentException("Withdrawing negative amounts is illegal");
            } else if (balance - amount < 0) {
                throw new OverdrawException();
            }

            balance -= amount;
        }

    }

}
