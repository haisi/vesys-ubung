package bank;

import bank.driver.LocalDriver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Hasan Kara
 */
public class LocalBank implements Bank {

    private final Map<String, LocalAccount> accounts = new HashMap<>();

    @Override
    public Set<String> getAccountNumbers() {
        return accounts.entrySet()
                       .stream()
                       // Check if account is active
                       .filter(entry -> entry.getValue().isActive())
                       .map(Map.Entry::getKey)
                       .collect(Collectors.toSet());
    }

    @Override
    public String createAccount(String owner) {
        LocalAccount newAccount = new LocalAccount(owner, Integer.toString(accounts.size()));

        accounts.put(newAccount.getNumber(), newAccount);

        return newAccount.getNumber();
    }

    @Override
    public boolean closeAccount(String number) {
        LocalAccount account = accounts.get(number);
        if (account == null) {
            return false;
        } else if (account.getBalance() != 0) {
            return false;
        }

        account.setActive(false);
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
