package instructions;

import addressing.AddressingMode;
import domain.Processor;

public class LDYInstruction extends Instruction {

    public LDYInstruction(
           Integer opCode,
           AddressingMode addressingMode,
           Processor processor
    ) {
        this.opCode = opCode;
        this.addressingMode = addressingMode;
        this.processor = processor;
    }

    public void execute() {
        processor.setY(addressingMode.read());
    }
}
