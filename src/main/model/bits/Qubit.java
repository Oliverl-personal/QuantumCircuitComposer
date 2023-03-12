package model.bits;

import model.exceptions.NoGateFoundException;
import model.gates.*;
import org.ejml.data.CMatrixRMaj;
import org.ejml.simple.ops.SimpleOperations_CDRM;

import java.util.List;


// A qubit (short for quantum bit) is the basic unit of quantum information in quantum computing
// and quantum communication. Unlike classical bits, which can only be in a state of either
// 0 or 1, qubits can exist in a superposition of both states simultaneously, allowing
// for potentially exponential increases in computing power.
public class Qubit {
    CMatrixRMaj state;

    //MODIFIES: this
    //EFFECTS:  initializes the qubit as state as zero state: |0⟩
    public Qubit() {
        this.state = new CMatrixRMaj(2, 1);
        state.set(0,0, 1,0);
        state.set(1,0, 0,0);
    }
    
    //EFFECTS:  returns state of qubit based on gates provided
    public CMatrixRMaj getState(List<Gate> gates) throws IllegalAccessException, NoGateFoundException {
        SimpleOperations_CDRM simpOps = new SimpleOperations_CDRM();
        CMatrixRMaj tempState = new CMatrixRMaj(2, 1);

        if (gates.size() == 0) {
            throw new NoGateFoundException();
        }

        for (Gate gate : gates) {
            simpOps.mult(gate.getMatrix(), this.state, tempState);
            this.state = tempState.copy();
        }
        return this.state;
    }
}
