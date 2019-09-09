package instructions;

import addressing.AddressingMode;
import domain.Processor;

public class NOPInstruction extends Instruction {

    public NOPInstruction(
           Integer opCode,
           AddressingMode addressingMode,
           Processor processor
    ) {
        this.opCode = opCode;
        this.addressingMode = addressingMode;
        this.processor = processor;
    }

    public void execute() {

    }
}
