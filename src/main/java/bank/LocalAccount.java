package bank;

/**
 * @author Hasan Kara
 */
public class LocalAccount implements Account {

    private String number;
    private String owner;
    private double balance;
    private boolean active = true;

    LocalAccount(String owner, String number) {
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

    public void setActive(boolean active) {
        this.active = active;
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
