package persistence;

import model.QuantumCircuitComposer;
import model.exceptions.LoadGateErrorException;
import model.gates.*;
import model.persistence.JsonReader;
import model.persistence.JsonWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Adapted from JsonSerializationDemo Repo
// Test writer, where files are written first then read back if they are the same
public class JsonWriterReaderTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            QuantumCircuitComposer qcc = new QuantumCircuitComposer("My circuit");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCircuit() {
        try {
            QuantumCircuitComposer qcc = new QuantumCircuitComposer("My Circuit");
            JsonWriter writer = new JsonWriter("./data/testEmptyCircuit.json");
            writer.open();
            writer.write(qcc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptyCircuit.json");
            qcc = reader.read();
            assertEquals("My Circuit", qcc.getName());
            assertEquals(0, qcc.getGateCount());
        } catch (Exception e) {
            fail("Exceptions should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCircuit() {
        try {
            QuantumCircuitComposer qcc = new QuantumCircuitComposer("My Circuit");
            qcc.addGate(new HGate());
            qcc.addGate(new YGate());
            qcc.addGate(new IGate());
            qcc.addGate(new NotGate());
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCircuit.json");
            writer.open();
            writer.write(qcc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCircuit.json");
            qcc = reader.read();
            assertEquals("My Circuit", qcc.getName());
            List<Gate> gates = qcc.getGatesInCircuit();
            assertEquals(4, gates.size());
            checkGateID(HGate.ID, gates.get(0));
            checkGateID(YGate.ID, gates.get(1));
            checkGateID(IGate.ID, gates.get(2));
            checkGateID(NotGate.ID, gates.get(3));

        } catch (Exception e) {
            fail("Exceptions should not have been thrown");
        }
    }

    @Test
    void testIncorrectGateID() {
        JsonReader reader = new JsonReader("./data/testIncorrectGateID.json");
        QuantumCircuitComposer qcc = new QuantumCircuitComposer("My Circuit");
        assertThrows(LoadGateErrorException.class, () -> {reader.read();});
    }
}
