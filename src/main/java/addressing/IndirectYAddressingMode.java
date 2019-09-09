package addressing;

import domain.Motherboard;
import domain.Processor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class IndirectYAddressingMode implements AddressingMode {

    private Processor processor;
    private Motherboard motherboard;

    public IndirectYAddressingMode(Processor processor, Motherboard motherboard) {
        this.processor = processor;
        this.motherboard = motherboard;
    }

    public Integer read() {

        Integer low =  motherboard.readMemoryLocation(
                motherboard.readMemoryLocation(processor.getPC() + 0x0001)
        );

        Integer high =  motherboard.readMemoryLocation(
                motherboard.readMemoryLocation(processor.getPC() + 0x0001) + 0x0001
        ) << 8;

        return motherboard.readMemoryLocation(
               low + high + processor.getY()
        );
    }

    public void write(Integer value) {

        Integer low =  motherboard.readMemoryLocation(
                motherboard.readMemoryLocation(processor.getPC() + 0x0001)
        );

        Integer high =  motherboard.readMemoryLocation(
                motherboard.readMemoryLocation(processor.getPC() + 0x0001) + 0x0001
        )  << 8;

        motherboard.writeMemoryLocation(
                low + high + processor.getY(),
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
