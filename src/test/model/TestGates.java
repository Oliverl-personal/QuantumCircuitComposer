package model;

import model.gates.HGate;
import model.gates.IGate;
import model.gates.NotGate;
import model.gates.YGate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Test for gate class
public class TestGates {
    HGate hg1;
    YGate yg1;
    IGate ig1;
    NotGate ng1;

    //QuantumCircuitComposer = short form is QCC
    @BeforeEach
    public void Setup() {
        hg1 = new HGate();
        yg1 = new YGate();
        ng1 = new NotGate();
        ig1 = new IGate();
    }

    @Test
    public void testGetIDAndName () {
        assertEquals(HGate.ID, hg1.getID());
        assertEquals(YGate.ID, yg1.getID());
        assertEquals(NotGate.ID, ng1.getID());
        assertEquals(IGate.ID, ig1.getID());
        assertEquals("Hadamard Gate", hg1.getName());
        assertEquals("Pauli-Y Gate", yg1.getName());
        assertEquals("Not Gate", ng1.getName());
        assertEquals("Identity Gate", ig1.getName());
    }
}
