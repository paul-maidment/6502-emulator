package addressing;

import domain.Motherboard;
import domain.Processor;

public interface AddressingMode {

    Integer read();
    void write(Integer value);
    void updatePC();
    Integer getCycleCount();
    Integer getWidth();

}
