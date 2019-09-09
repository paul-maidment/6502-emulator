package instructions;

import addressing.AddressingMode;
import domain.Processor;

public abstract class Instruction {

    Processor processor;
    Integer opCode;
    AddressingMode addressingMode;

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public Integer getOpCode() {
        return this.opCode;
    }
    public Integer getCycleCount() {
        return addressingMode.getCycleCount();
    }
    public Integer getWidth() {
        return 1 + addressingMode.getWidth();
    }
    public abstract void execute();
}
