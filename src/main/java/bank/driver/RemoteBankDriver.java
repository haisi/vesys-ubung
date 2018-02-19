package bank.driver;

import bank.Command;

/**
 * <p>Because there will be multiple remote drivers (i.e. clients for different technologies).
 *
 * @author Hasan Kara
 */
public interface RemoteBankDriver extends BankDriver {

    /**
     * Implements how the command from the client should be send to the server.
     *
     * @param command representing the methods of @see Bank
     * @return response from server {@link bank.Bank}
     */
    public String sendCommand(Command command);

    /**
     * Implements how the command from the client should be send to the server.
     *
     * @param command   representing the methods of @see Bank
     * @param arguments arguments to pass to server (depending on communication, different serialization)
     * @return response from server {@link bank.Bank}
     */
    public String sendCommand(Command command, String arguments);

}
