package instructions;

import addressing.AddressingMode;
import domain.Processor;

public class JMPInstruction extends Instruction {

    public JMPInstruction(
           Integer opCode,
           AddressingMode addressingMode,
           Processor processor
    ) {
        this.opCode = opCode;
        this.addressingMode = addressingMode;
        this.processor = processor;
    }

    public void execute() {
        addressingMode.updatePC();
    }
}
