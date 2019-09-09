package instructions;

import addressing.AddressingMode;
import domain.Processor;

public class TXSInstruction extends Instruction {

    public TXSInstruction(
           Integer opCode,
           AddressingMode addressingMode,
           Processor processor
    ) {
        this.opCode = opCode;
        this.addressingMode = addressingMode;
        this.processor = processor;
    }

    public void execute() {
        processor.setSP(processor.getX());
    }
}
