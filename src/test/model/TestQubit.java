package model;

import model.bits.Qubit;
import model.exceptions.NoGateFoundException;
import model.gates.*;
import org.ejml.data.CMatrixRMaj;
import org.ejml.simple.ops.SimpleOperations_CDRM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestQubit {
    Qubit q;
    QuantumCircuitComposer qcc1;
    ArrayList<Gate> gates;
    CMatrixRMaj expectedState;
    CMatrixRMaj actualState;
    SimpleOperations_CDRM simpOps;
    HGate hg1;
    YGate yg1;
    IGate ig1;
    NotGate ng1;

    //QuantumCircuitComposer = short form is QCC
    @BeforeEach
    public void Setup() {
        q = new Qubit();
        qcc1 = new QuantumCircuitComposer("Test Circuit");
        gates = new ArrayList<>();
        expectedState = new CMatrixRMaj(2, 1);
        actualState = new CMatrixRMaj(2, 1);
        simpOps = new SimpleOperations_CDRM();
        hg1 = new HGate();
        yg1 = new YGate();
        ng1 = new NotGate();
        ig1 = new IGate();
    }

    @Test
    public void testGetQubitStateOutputNoGate() throws IllegalAccessException, NoGateFoundException {
        assertThrows(NoGateFoundException.class, () -> {actualState = q.getState(gates);});
    }

    @Test
    public void testIdentityGateState() throws IllegalAccessException, NoGateFoundException {
        gates.add(ig1);
        expectedState.set(0,0, 1,0);
        expectedState.set(1,0, 0,0);
        actualState = q.getState(gates);

        assertTrue(simpOps.isIdentical(expectedState, actualState, 0.01));
    }

    @Test
    public void testNotGateState() throws IllegalAccessException, NoGateFoundException {
        gates.add(ng1);
        expectedState.set(0,0, 0,0);
        expectedState.set(1,0, 1,0);
        actualState = q.getState(gates);

        assertTrue(simpOps.isIdentical(expectedState, actualState, 0.01));
    }

    @Test
    public void testGetQubitStateOutputAllGatesUsed() throws IllegalAccessException, NoGateFoundException {
        gates.add(hg1);
        gates.add(yg1);
        gates.add(ig1);
        gates.add(ng1);
        expectedState.set(0,0, 0, (float) 0.707106769);
        expectedState.set(1,0, 0, (float) -0.707106769);
        actualState = q.getState(gates);

        assertTrue(simpOps.isIdentical(expectedState, actualState, 0.1));
    }
}
