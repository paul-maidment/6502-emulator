package instructions;

import addressing.AddressingMode;
import domain.Processor;

public class TSXInstruction extends Instruction {

    public TSXInstruction(
           Integer opCode,
           AddressingMode addressingMode,
           Processor processor
    ) {
        this.opCode = opCode;
        this.addressingMode = addressingMode;
        this.processor = processor;
    }

    public void execute() {
        processor.setX(processor.getSP());
    }
}
