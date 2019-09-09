package addressing;

import domain.Motherboard;
import domain.Processor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class IndirectAddressingMode implements AddressingMode {

    private Processor processor;
    private Motherboard motherboard;

    public IndirectAddressingMode(Processor processor, Motherboard motherboard) {
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

        Integer pc = processor.getPC();
        Integer indirectAddress = motherboard.readMemoryLocation(pc + 0x0001) + (motherboard.readMemoryLocation(pc + 0x0002) << 8);
        Integer targetAddress = motherboard.readMemoryLocation(indirectAddress) + (motherboard.readMemoryLocation(indirectAddress + 1) << 8);
        if ((indirectAddress + 1) % 256 == 0) {
            targetAddress = motherboard.readMemoryLocation(indirectAddress) + (motherboard.readMemoryLocation(indirectAddress - 255) << 8);
        }

        processor.setPC(targetAddress);
    }

    public Integer getCycleCount() {
        return 1;
    }

    public Integer getWidth() {
        return 2;
    }
}
