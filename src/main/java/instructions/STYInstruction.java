package instructions;

import addressing.AddressingMode;
import domain.Processor;

public class STYInstruction extends Instruction {

    public STYInstruction(
           Integer opCode,
           AddressingMode addressingMode,
           Processor processor
    ) {
        this.opCode = opCode;
        this.addressingMode = addressingMode;
        this.processor = processor;
    }

    public void execute() {
        addressingMode.write(processor.getY());
    }
}
