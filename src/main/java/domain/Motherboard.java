package domain;

public interface Motherboard {

    Integer readMemoryLocation(Integer address);
    void writeMemoryLocation(Integer address, Integer value);
}
