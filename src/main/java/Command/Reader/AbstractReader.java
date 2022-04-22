package Command.Reader;

import Command.CommandFactory.CommandFactory;
import Manager.CollectionManager;
import Messager.Messenger;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class AbstractReader implements Reader {
    protected BufferedReader reader;
    private boolean isRunning;
    protected CommandFactory commandFactory;
    protected CollectionManager manager;
    protected Messenger messenger;

    /**
     * reads commands in console mode
     */
    @Override
    public void readCommand() {
        isRunning = true;
        while (isRunning) {
            if (readyInput()) {
                try{
                    String input = reader.readLine();
                    String[] command = new String[0];
                    if (input == null){
                        System.err.println("Incorrectness finished executing");
                        System.exit(0);
                    }
                    else command = input.trim().split("[ ]+");
                    if (command.length == 1)
                        commandFactory.executeCommand(command[0], this, null, manager);
                    else if (command.length == 2)
                        commandFactory.executeCommand(command[0], this, command[1], manager);
                    else System.err.println("No such this command!");
                } catch (IOException e){
                    e.printStackTrace();
                    exit();
                }
            } else isRunning = false;
        }
    }

    /**
     *  finishes waiting for commands to be entered
     */
    public void exit () {
        isRunning = false;
    }

    /**
     * @return input readiness
     */
    protected abstract boolean readyInput();

}