package core;

import domain.Motherboard;
import domain.Processor;
import instructions.Instruction;

import java.util.List;

public class Mos6502Core implements Processor {

    public Mos6502Core (
            Integer a,
            Integer x,
            Integer y,
            Integer flags,
            Integer pc,
            Integer sp,
            Motherboard motherboard,
            List<Instruction> instructions
    ){
        this.a = a;
        this.x = x;
        this.y = y;
        this.pc = pc;
        this.flags = flags;
        this.sp = sp;
        this.motherboard = motherboard;
        this.instructions = new Instruction[255];
        for (Object o : instructions.toArray()) {
            Instruction instruction = (Instruction) o;
            instruction.setProcessor(this);
            this.instructions[instruction.getOpCode()] = instruction;
        }
        this.currentCycleCount = 0;
    }

    private Integer a;
    private Integer x;
    private Integer y;
    private Integer pc;
    private Integer sp;
    private Integer flags;
    private Instruction[] instructions;
    private Motherboard motherboard;
    private Integer currentCycleCount;

    public Instruction getCurrentInstruction() {
        return currentInstruction;
    }

    private Instruction currentInstruction;

    private static final Integer STATUS_OVERFLOW_BIT = 0x40;
    private static final Integer STATUS_ZERO_BIT = 0x02;


    public Integer getAccumulator() {
        return a & 0x00FF;
    }

    public void setAccumulator(Integer accumulator) {
        this.a = accumulator & 0x00FF;
        if (this.a == 0x00) {
            flags = flags & STATUS_ZERO_BIT;
        }
    }

    public Integer getX() {
        return x & 0x00FF;
    }


    public Integer getY() {
        return y & 0x00FF;
    }


    public Integer getFlags() {
        return flags & 0x00FF;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }

    public Integer getPC() {return pc & 0xFFFF;}

    public void setPC(Integer pc) { this.pc = pc;}

    public Integer getSP() {return sp & 0xFFFF;}

    public void setSP(Integer sp) { this.sp = sp;}

    public void incPC() {
        pc++;
        if (pc > 0xFFFF) {
            pc = 0x0000;
            //This is not how overflow works
            //flags = flags & STATUS_OVERFLOW_BIT;
        }
    }

    public void executeNextClockCycle() {
        if (currentCycleCount == 0) {
            fetchNextInstruction();
            currentInstruction.execute();
            for (int i = 0; i < currentInstruction.getWidth(); i++) {
                incPC();
            }
        }
        currentCycleCount--;
    }

    private void fetchNextInstruction() {
        currentInstruction = instructions[0x00];
        if (instructions[motherboard.readMemoryLocation(pc) & 0x00FF] != null) {
            currentInstruction = instructions[motherboard.readMemoryLocation(pc) & 0x00FF];
        }
        currentCycleCount = currentInstruction.getCycleCount();
    }

    public void setX(Integer value) {
        this.x = value;
    }

    public void setY(Integer value) {
        this.y = value;
    }
}
