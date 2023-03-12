package model;

import model.bits.Qubit;
import model.exceptions.*;
import model.gates.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

// Test for QuantumCircuitComposer class
class TestQuantumCircuitComposer {

    Qubit q;
    QuantumCircuitComposer qcc1;
    HGate hg1;
    HGate hg2;
    HGate hg3;
    YGate yg1;
    YGate yg2;
    YGate yg3;
    IGate ig1;
    IGate ig2;
    IGate ig3;
    NotGate ng1;
    NotGate ng2;
    NotGate ng3;

    String rotation = "Pi/2";

    //QuantumCircuitComposer = short form is QCC
    @BeforeEach
    public void Setup() {
        q = new Qubit();
        qcc1 = new QuantumCircuitComposer("Test Circuit");
        hg1 = new HGate();
        hg2 = new HGate();
        hg3 = new HGate();
        yg1 = new YGate();
        yg2 = new YGate();
        yg3 = new YGate();
        ng1 = new NotGate();
        ng2 = new NotGate();
        ng3 = new NotGate();
        ig1 = new IGate();
        ig2 = new IGate();
        ig3 = new IGate();
    }

    @Test
    public void testEmptyQCC () {
        assertEquals(0, qcc1.getGateCount());
    }

    @Test
    public void testRepeatGateQCC () {
        assertDoesNotThrow(() -> {qcc1.addGate(hg1);});
        assertThrows(RepeatedGateException.class, () -> {qcc1.addGate(hg1);});
    }

    @Test
    public void testTooManyGatesQCC () {
        boolean throwOverAmount = false;

        //Add 8 gates, just enough to not trigger OverAmountException
        try {
            qcc1.addGate(hg1);
            qcc1.addGate(hg2);
            qcc1.addGate(hg3);
            qcc1.addGate(yg1);
            qcc1.addGate(yg2);
            qcc1.addGate(yg3);
            qcc1.addGate(ig1);
            qcc1.addGate(ig2);
        } catch (RepeatedGateException e) {
            throwOverAmount = false;
        } catch (OverAmountException e) {
            throwOverAmount = true;
        } catch (LoadGateErrorException e) {
            throw new RuntimeException(e);
        }

        assertFalse(throwOverAmount);

        //Add 1 more gate, expect over amount OverAmountException
        try {
            qcc1.addGate(ig3);
        } catch (RepeatedGateException e) {
            throwOverAmount = false;
        } catch (OverAmountException e) {
            throwOverAmount = true;
        } catch (LoadGateErrorException e) {
            throw new RuntimeException(e);
        }
        assertTrue(throwOverAmount);
    }

    @Test
    public void testGetGateCountQCC () {
        //add 2 gates, total gates: 2
        try {
            qcc1.addGate(hg1);
            qcc1.addGate(hg2);

        } catch (RepeatedGateException e) { }
        catch (OverAmountException e) { } catch (LoadGateErrorException e) {
            throw new RuntimeException(e);
        }

        assertEquals(2, qcc1.getGateCount());

        //add 6 gates, total gates: 8
        try {
            qcc1.addGate(hg3);
            qcc1.addGate(yg1);
            qcc1.addGate(yg2);
            qcc1.addGate(yg3);
            qcc1.addGate(ig1);
            qcc1.addGate(ig2);

        } catch (RepeatedGateException e) { }
        catch (OverAmountException e) { } catch (LoadGateErrorException e) {
            throw new RuntimeException(e);
        }

        assertEquals(8, qcc1.getGateCount());

        //add 1 repeated gate, total gates: 8
        try {
            qcc1.addGate(hg3);

        } catch (RepeatedGateException e) { }
        catch (OverAmountException e) { } catch (LoadGateErrorException e) {
            throw new RuntimeException(e);
        }

        assertEquals(8, qcc1.getGateCount());
    }

    @Test
    public void removeGatesOnCircuitWithNoGates () {
        boolean hasGate;
        try {
            qcc1.removeGate(hg1);
            hasGate = true;
        } catch (NoGateFoundException e) {
            hasGate = false;
        }
        assertFalse(hasGate);
    }

    @Test
    public void removeGateWithGate () {
        // Add 2 gates
        try {
            qcc1.addGate(hg1);
            qcc1.addGate(hg2);

        } catch (Exception e) {
        }

        //Remove one of previously added gate
        boolean hasGate;
        try {
            qcc1.removeGate(hg1);
            hasGate = true;
        } catch (NoGateFoundException e) {
            hasGate = false;
        }
        assertTrue(hasGate);

    }

    @Test
    public void testRemoveSameGateTwiceWithGate() {
        // Add 2 gates
        try {
            qcc1.addGate(hg1);
            qcc1.addGate(hg2);

        } catch (Exception e) {
        }

        //Remove one of previously added gate
        boolean hasGate;
        try {
            qcc1.removeGate(hg1);
            hasGate = true;
        } catch (NoGateFoundException e) {
            hasGate = false;
        }
        assertTrue(hasGate);

        //Remove a gate that doesn't exist
        try {
            qcc1.removeGate(hg1);
            hasGate = true;
        } catch (NoGateFoundException e) {
            hasGate = false;
        }
        assertFalse(hasGate);
    }

    @Test
    public void removeGatesOnCircuitWithIllegalIndex () {
        boolean indexInBound;
        int index = 1;
        try {
            qcc1.removeGate(index);
            indexInBound = true;
        } catch (IllegalIndexException e) {
            indexInBound = false;
        }
        assertFalse(indexInBound);

        index = 0;
        try {
            qcc1.removeGate(index);
            indexInBound = true;
        } catch (IllegalIndexException e) {
            indexInBound = false;
        }
        assertFalse(indexInBound);
    }

    @Test
    public void removeGatesOnCircuitWithLegalIndex () {
        boolean indexInBound;
        // Add 2 gates
        try {
            qcc1.addGate(hg1);
            qcc1.addGate(hg2);
        } catch (Exception e) {
        }

        //Remove one of previously added gate

        int index = 1;
        try {
            qcc1.removeGate(index);
            indexInBound = true;
        } catch (IllegalIndexException e) {
            indexInBound = false;
        }
        assertEquals(1, qcc1.getGateCount());
        assertTrue(qcc1.getGatesInCircuit().contains(hg2));
        assertTrue(indexInBound);

        //Remove the last gate
        try {
            qcc1.removeGate(index);
            indexInBound = true;
        } catch (IllegalIndexException e) {
            indexInBound = false;
        }
        assertTrue(indexInBound);
        assertEquals(0, qcc1.getGateCount());
        assertFalse(qcc1.getGatesInCircuit().contains(hg2));
    }

    @Test
    public void testGetCircuitOutputNoGate() {
        String actualCircuit = qcc1.getCircuitInString();
        String expectedCircuit = "----";

        assertEquals(expectedCircuit, actualCircuit);
    }

    @Test
    public void testGetCircuitOutputOneGate() {
        try {
            qcc1.addGate(hg1);
        } catch (Exception e) {
        }

        String actualCircuit = qcc1.getCircuitInString();
        String expectedCircuit = "----H[ " + hg1.getRotation() + " ]----";

        assertEquals(expectedCircuit, actualCircuit);
    }

    @Test
    public void testGetCircuitOutputAllGatesUsed() {
        try {
            qcc1.addGate(hg1);
            qcc1.addGate(yg1);
            qcc1.addGate(ig1);
            qcc1.addGate(ng1);
        } catch (Exception e) {
        }

        String actualCircuit = qcc1.getCircuitInString();
        String expectedCircuit = "----H[ " + hg1.getRotation() + " ]----Y[ " + yg1.getRotation() +
                " ]----I[ " + ig1.getRotation() + " ]----Not[ " + ng1.getRotation() + " ]----";

        assertEquals(expectedCircuit, actualCircuit);
    }


}