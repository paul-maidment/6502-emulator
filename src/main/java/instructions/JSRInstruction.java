package instructions;

import addressing.AddressingMode;
import domain.Motherboard;
import domain.Processor;

public class JSRInstruction extends Instruction {

    private Motherboard motherboard;

    public JSRInstruction(
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

        Integer addressToStoreOnStack = processor.getPC() + this.getWidth();
        Integer highByte = (addressToStoreOnStack / 256); //- addressToStoreOnStack % 256) & 0x00FF;
        Integer lowByte = addressToStoreOnStack - (highByte * 256);
        Integer sp = processor.getSP();
        motherboard.writeMemoryLocation(0x100 + sp, highByte);
        sp = decrementByte(sp);
        motherboard.writeMemoryLocation(0x100 + sp, lowByte);
        processor.setSP(decrementByte(sp));
        addressingMode.updatePC();
    }

    private Integer decrementByte(Integer value) {
        if (value == 0x00) {
            value = 0xFF;
        } else {
            value--;
        }
        return value;
    }
}
