package instructions;

import addressing.AddressingMode;
import domain.Motherboard;
import domain.Processor;

public class PHAInstruction extends Instruction {

    private Motherboard motherboard;

    public PHAInstruction(
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
        motherboard.writeMemoryLocation(0x0100 + sp, processor.getAccumulator());
        if (sp > 0) {
            processor.setSP(sp - 1);
        } else {
            processor.setSP(0x00FF);
        }
    }
}
