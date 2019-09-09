package addressing;

import domain.Motherboard;
import domain.Processor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class RelativeAddressingMode implements AddressingMode {

    private Processor processor;
    private Motherboard motherboard;

    public RelativeAddressingMode(Processor processor, Motherboard motherboard) {
        this.processor = processor;
        this.motherboard = motherboard;
    }

    public Integer read() {
        throw new NotImplementedException();
    }

    public void write(Integer value) {
        throw new NotImplementedException();
    }

    public void updatePC() {
        Integer currentPC = this.processor.getPC();
        int offsetRaw = this.motherboard.readMemoryLocation(currentPC + 1) & 0x00FF;
        byte offset = (byte) offsetRaw;
        this.processor.setPC(currentPC + offset);
    }

    public Integer getCycleCount() {
        return 1;
    }

    public Integer getWidth() {
        return 1;
    }
}
