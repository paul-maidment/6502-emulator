package addressing;

import domain.Motherboard;
import domain.Processor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ImpliedAddressingMode implements AddressingMode {

    private Processor processor;

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public void setMotherboard(Motherboard motherboard) {
    }

    public Integer read() {
        throw new NotImplementedException();
    }

    public void write(Integer value) {
        throw new NotImplementedException();
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
}
