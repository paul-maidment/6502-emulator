package instructions;

import addressing.AddressingMode;
import domain.Processor;

public class LDXInstruction extends Instruction {

    public LDXInstruction(
           Integer opCode,
           AddressingMode addressingMode,
           Processor processor
    ) {
        this.opCode = opCode;
        this.addressingMode = addressingMode;
        this.processor = processor;
    }

    public void execute() {
        processor.setX(addressingMode.read());
    }
}
