package addressing;

import domain.Motherboard;
import domain.Processor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ZeroPageYAddressingMode implements AddressingMode {

    private Processor processor;
    private Motherboard motherboard;

    public ZeroPageYAddressingMode(Processor processor, Motherboard motherboard) {
        this.processor = processor;
        this.motherboard = motherboard;
    }

    public Integer read() {
        return motherboard.readMemoryLocation(
            motherboard.readMemoryLocation(processor.getPC() + 0x0001) + (processor.getY() & 0x00FF)
        );
    }

    public void write(Integer value) {
        motherboard.writeMemoryLocation(
                motherboard.readMemoryLocation(processor.getPC() + 0x0001) + (processor.getY() & 0x00FF),
                value
        );
    }

    public void updatePC() {
        throw new NotImplementedException();
    }

    public Integer getCycleCount() {
        return 1;
    }

    public Integer getWidth() {
        return 1;
    }
}
