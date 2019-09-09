package instructions;

import addressing.AddressingMode;
import domain.Processor;

public class LDAInstruction extends Instruction {

    public LDAInstruction(
           Integer opCode,
           AddressingMode addressingMode,
           Processor processor
    ) {
        this.opCode = opCode;
        this.addressingMode = addressingMode;
        this.processor = processor;
    }

    public void execute() {
        processor.setAccumulator(addressingMode.read());
    }
}
