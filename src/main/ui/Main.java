package ui;

import model.QuantumCircuitComposer;
import model.bits.Qubit;
import model.exceptions.LoadGateErrorException;
import model.exceptions.NoGateFoundException;
import model.exceptions.OverAmountException;
import model.exceptions.RepeatedGateException;

public class Main {

    //EFFECTS:  Init quantum circuit, qubit and the UI composer manager, prints welcome message and
    //          waits for user input
    public static void main(String[] args) throws RepeatedGateException,
            OverAmountException, LoadGateErrorException, IllegalAccessException, NoGateFoundException {
        Qubit q = new Qubit();
        QuantumCircuitComposer qcc1 = new QuantumCircuitComposer("My Quqntum Circuit");
        ComposerManager composerManager = new ComposerManager(qcc1, q);
        composerManager.printWelcomeMsg();
        composerManager.handleUsrInput();
    }
}

