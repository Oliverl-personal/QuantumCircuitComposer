package ui;

import model.QuantumCircuitComposer;
import model.bits.Qubit;
import model.exceptions.*;
import model.gates.*;
import model.persistence.JsonReader;
import model.persistence.JsonWriter;
import org.ejml.data.CMatrixRMaj;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Front end UI that composes circuit based on user input
public class ComposerManager {
    //The command MUST be in lowercase.

    //Navigation Command
    private static final String QUIT_COMMAND = "q";
    private static final String GO_BACK_COMMAND = "b";
    private static final String SAVE_COMMAND = "s";
    private static final String LOAD_COMMAND = "l";

    //Main Commands
    private static final String ADD_GATE = "a";
    private static final String SEE_CIRCUIT = "c";
    private static final String SEE_OUTPUT = "o";

    //Gate selection commands
    private static final String HGATE_COMMAND = HGate.ID;
    private static final String YGATE_COMMAND = YGate.ID;
    private static final String IGATE_COMMAND = IGate.ID;
    private static final String NOTGATE_COMMAND = NotGate.ID;

    private QuantumCircuitComposer qcc;
    private final Qubit qubit;
    private boolean continueProgram;
    private final Scanner input;
    private static final String FILE_STORE = "./data/circuit.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public ComposerManager(QuantumCircuitComposer qcc, Qubit qubit) {
        input = new Scanner(System.in);
        this.qcc = qcc;
        this.qubit = qubit;
        jsonWriter = new JsonWriter(FILE_STORE);
        jsonReader = new JsonReader(FILE_STORE);
        continueProgram = true;
    }

    //MODIFIES: this, QuantumCircuitComposer, Qubit
    //EFFECTS:  parses user input until user quits
    //          Code adapted from LongFormProblemStarters - FitLifeGymKiosk
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void handleUsrInput() throws RepeatedGateException,
            OverAmountException, LoadGateErrorException, IllegalAccessException, NoGateFoundException {
        displayActionOptions();
        String action;
        while (continueProgram) {
            action = getUserInputString();
            switch (action) {
                case ADD_GATE:
                    handleAddGateCommand();
                    break;
                case SEE_CIRCUIT:
                    printCircuit();
                    break;
                case SEE_OUTPUT:
                    printOutput();
                    break;
                case QUIT_COMMAND:
                    quitProgram();
                    break;
                case SAVE_COMMAND:
                    saveCircuit();
                    break;
                case LOAD_COMMAND:
                    loadCircuit();
                    break;
                default:
                    printInvalidInput();
                    break;
            }
            displayActionOptions();
        }
    }

    //REQUIRES: User input to type out the exact command prompt
    //MODIFIES: QuantumCircuitComposer
    //EFFECTS:  handles the add gate command, will change behavior based on user input
    //          Code adapted from LongFormProblemStarters - FitLifeGymKiosk
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void handleAddGateCommand() throws RepeatedGateException,
            OverAmountException, LoadGateErrorException {
        displayGateOption();
        String gate = getUserInputString();
        switch (gate) {
            case HGATE_COMMAND:
                qcc.addGate(new HGate());
                break;
            case YGATE_COMMAND:
                qcc.addGate(new YGate());
                break;
            case NOTGATE_COMMAND:
                qcc.addGate(new NotGate());
                break;
            case IGATE_COMMAND:
                qcc.addGate(new IGate());
                break;
            case GO_BACK_COMMAND:
                displayActionOptions();
                break;
            case QUIT_COMMAND:
                quitProgram();
                break;
            default:
                printInvalidInput();
                break;
        }
    }

    //EFFECTS:  prints gate options to add to circuit to screen
    private void displayGateOption() {
        System.out.println("Please choose one of the following gates");
        System.out.println("Type '" + HGATE_COMMAND + "' for " + HGate.name);
        System.out.println("Type '" + YGATE_COMMAND + "' for " + YGate.name);
        System.out.println("Type '" + IGATE_COMMAND + "' for " + IGate.name);
        System.out.println("Type '" + NOTGATE_COMMAND + "' for " + NotGate.name);
    }

    //EFFECTS:  prints gates available to add to screen
    private void displayActionOptions() {
        //TODO: Future Improvement, print out of how many circuits available to edit
        System.out.println("Enter '" + ADD_GATE + "' to add one gate.");
        System.out.println("Enter '" + SEE_CIRCUIT + "' to see circuits.");
        System.out.println("Enter '" + SEE_OUTPUT + "' to see qubit's state.");
        System.out.println("Enter '" + SAVE_COMMAND + "' to save this circuit to file.");
        System.out.println("Enter '" + LOAD_COMMAND + "' to load previously saved circuit to file.");
        printQuitOption();
    }

    //EFFECTS:  print circuit to screen
    private void printCircuit() {
        System.out.println("Printing circuit...\n");
        System.out.println("\n");
        System.out.println(qcc.getCircuitInString());
        System.out.println("\n");
    }

    private void printOutput() throws IllegalAccessException, NoGateFoundException {
        System.out.println("Printing qubit state...");
        System.out.println("\n");
        CMatrixRMaj qstate = qubit.getState(qcc.getGatesInCircuit());
        qstate.print();
        System.out.println("\n");
    }

    //EFFECTS:  print welcome msg to screen
    public void printWelcomeMsg() {
        System.out.println("Welcome! This program is a quantum circuit composer.");
        System.out.println("Please select one of the following options!\n");
    }

    //MODIFIES: this
    //EFFECTS:  Ends the program
    public void quitProgram() {
        input.close();
        System.out.println("Program is closing...");
    }

    //MODIFIES: String str
    //EFFECTS:  make input str to lower case
    //          removes whitespace around str
    //          removes quotation marks
    //          Code adapted from LongFormProblemStarters - FitLifeGymKiosk
    private String cleanStr(String str) {
        str = str.trim();
        str = str.toLowerCase();
        str = str.replaceAll("\"|'", "");
        return str;
    }

    //EFFECTS:  retrieve user string from console
    //          Code adapted from LongFormProblemStarters - FitLifeGymKiosk
    private String getUserInputString() {
        String str = "";
        if (input.hasNext()) {
            str = input.nextLine();
            str = cleanStr(str);
        }
        return str;
    }

    //EFFECTS:  print go back option to screen
    private void printGoBackOption() {
        System.out.println("To go back to previoue menu, please enter: '" + GO_BACK_COMMAND);
    }

    //EFFECTS:  print quit option to screen
    private void printQuitOption() {
        System.out.println("To close the program, please enter: '" + QUIT_COMMAND + "'");
    }

    //EFFECTS:  print invalid input message to screen
    private void printInvalidInput() {
        System.out.println("Sorry, that was an invalid command - try again. \n");
    }

    //TODO Future Improvement, allow user to select how many circuits to populate
//    private void printHowManyCircuit() {
//        System.out.println("\nHow many quantum circuits would you like to compose today?\n");
//        printQuitCommand();
//    }

    // EFFECTS: saves the workroom to file
    private void saveCircuit() {
        try {
            jsonWriter.open();
            jsonWriter.write(qcc);
            jsonWriter.close();
            System.out.println("Saved " + qcc.getName() + " to " + FILE_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + FILE_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads circuit from file
    private void loadCircuit() throws LoadGateErrorException,
            RepeatedGateException, OverAmountException {
        try {
            this.qcc = jsonReader.read();
            System.out.println("Loaded " + qcc.getName() + " from " + FILE_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + FILE_STORE);
        }
    }
}
