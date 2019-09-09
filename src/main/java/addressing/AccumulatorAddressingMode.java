package addressing;

import domain.Motherboard;
import domain.Processor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class AccumulatorAddressingMode implements AddressingMode {

    private Processor processor;
    private Motherboard motherboard;

    public AccumulatorAddressingMode(Processor processor, Motherboard motherboard) {
        this.processor = processor;
        this.motherboard = motherboard;
    }

    public Integer read() {
        return  this.processor.getAccumulator();
    }

    public void write(Integer value) {
        this.processor.setAccumulator(value);
    }

    public void updatePC() {
        throw new NotImplementedException();
    }

    public Integer getCycleCount() {
        return 1;
    }

    public Integer getWidth() {
        return 0;
    }

    @Override
    public String toString() {
        return "A";
    }
}
