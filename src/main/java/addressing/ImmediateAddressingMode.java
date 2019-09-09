package addressing;

import domain.Motherboard;
import domain.Processor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ImmediateAddressingMode implements AddressingMode {

    private Processor processor;
    private Motherboard motherboard;

    public ImmediateAddressingMode(Processor processor, Motherboard motherboard) {
        this.processor = processor;
        this.motherboard = motherboard;
    }

    public Integer read() {
        return  motherboard.readMemoryLocation(processor.getPC() + 0x0001);
    }

    public void write(Integer value) {
        throw new NotImplementedException();
    }

    public void updatePC() {
        throw new NotImplementedException();
    }

    public Integer getCycleCount() {
        return 2;
    }

    public Integer getWidth() {
        return 1;
    }
}
