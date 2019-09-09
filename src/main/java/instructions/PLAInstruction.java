package instructions;

import addressing.AddressingMode;
import domain.Motherboard;
import domain.Processor;

public class PLAInstruction extends Instruction {

    private Motherboard motherboard;

    public PLAInstruction(
           Integer opCode,
           AddressingMode addressingMode,
           Processor processor,
           Motherboard motherboard
    ) {
        this.opCode = opCode;
        this.addressingMode = addressingMode;
        this.processor = processor;
        this.motherboard = motherboard;
    }

    public void execute() {
        Integer sp = processor.getSP();
        processor.setAccumulator(motherboard.readMemoryLocation(0x0100 + sp));
        if (sp < 0xFF) {
            processor.setSP(sp + 1);
        } else {
            processor.setSP(0x0000);
        }
    }
}
