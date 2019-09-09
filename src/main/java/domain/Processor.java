package domain;

public interface Processor {
    Integer getAccumulator();
    void setAccumulator(Integer accumulator);
    void setX(Integer value);
    void setY(Integer value);
    void executeNextClockCycle();
    Integer getPC();
    Integer getX();
    Integer getY();
    void setPC(Integer pc);
    void incPC();
    Integer getSP();
    void setSP(Integer sp);
    Integer getFlags();
    void setFlags(Integer flags);
}
