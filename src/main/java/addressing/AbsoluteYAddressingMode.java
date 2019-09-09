package addressing;

import domain.Motherboard;
import domain.Processor;

public class AbsoluteYAddressingMode implements AddressingMode {

    private Processor processor;
    private Motherboard motherboard;

    public AbsoluteYAddressingMode(Processor processor, Motherboard motherboard) {
        this.processor = processor;
        this.motherboard = motherboard;
    }

    public Integer read() {
        Integer currentPC = this.processor.getPC();
        Integer low = (this.motherboard.readMemoryLocation(currentPC + 1) & 0x00FF);
        Integer high = (this.motherboard.readMemoryLocation(currentPC + 2) & 0x00FF) << 8;
        return this.motherboard.readMemoryLocation(low + high + (processor.getY() & 0x00FF));
    }

    public void write(Integer value) {
        Integer currentPC = this.processor.getPC();
        Integer low = (this.motherboard.readMemoryLocation(currentPC + 1) & 0x00FF);
        Integer high = (this.motherboard.readMemoryLocation(currentPC + 2) & 0x00FF) << 8;
        this.motherboard.writeMemoryLocation(low + high + (processor.getY() & 0x00FF), value);
    }

    public void updatePC() {
        Integer currentPC = this.processor.getPC();
        Integer low = (this.motherboard.readMemoryLocation(currentPC + 1) & 0x00FF);
        Integer high = (this.motherboard.readMemoryLocation(currentPC + 2) & 0x00FF) << 8;
        this.processor.setPC(low + high + (processor.getY() & 0x00FF));
    }

    public Integer getCycleCount() {
        return 1;
    }

    public Integer getWidth() {
        return 2;
    }

    @Override
    public String toString() {
        Integer currentPC = this.processor.getPC();
        Integer low = (this.motherboard.readMemoryLocation(currentPC + 1) & 0x00FF);
        Integer high = (this.motherboard.readMemoryLocation(currentPC + 2) & 0x00FF) << 8;
        Integer address = low + high;
        return "0x" + String.format("%4X", address) + ", Y";
    }
}
