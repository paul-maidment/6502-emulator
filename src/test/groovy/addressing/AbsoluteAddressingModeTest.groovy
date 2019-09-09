package addressing

import domain.Motherboard
import domain.Processor
import spock.lang.Specification

class AbsoluteAddressingModeTest extends Specification {

    def "It should implement the addressing mode interface"() {
        given:
        def processor = Mock(Processor)
        def motherboard = Mock(Motherboard)
        when:
        def addressingMode = new AbsoluteAddressingMode(processor, motherboard)
        then:
        addressingMode instanceof AddressingMode
    }

    def "It should change the processor PC on updatePC to the address specified by the two little-endian operand bytes"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def dummyOpcode = 0x00FF
        motherboard.readMemoryLocation(0x0000) >> dummyOpcode
        motherboard.readMemoryLocation(0x0001) >> 0x00EF
        motherboard.readMemoryLocation(0x0002) >> 0x00BE
        def addressingMode = new AbsoluteAddressingMode(processor, motherboard)
        when:
        addressingMode.updatePC()
        then:
        1 * processor.setPC(0xBEEF)
    }

    def "It should return the content of the memory location specified by the two little-endian operand bytes on read"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def dummyOpcode = 0x00FF
        motherboard.readMemoryLocation(0x0000) >> dummyOpcode
        motherboard.readMemoryLocation(0x0001) >> 0x00EF
        motherboard.readMemoryLocation(0x0002) >> 0x00BE
        motherboard.readMemoryLocation(0xBEEF) >> 0x00C0
        def addressingMode = new AbsoluteAddressingMode(processor, motherboard)
        when:
        def actual = addressingMode.read()
        then:
        actual == 0x00C0
    }

    def "it should write to the memory location specified by the two little-endian operand bytes on write"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def dummyOpcode = 0x00FF
        motherboard.readMemoryLocation(0x0000) >> dummyOpcode
        motherboard.readMemoryLocation(0x0001) >> 0x00EF
        motherboard.readMemoryLocation(0x0002) >> 0x00BE
        def addressingMode = new AbsoluteAddressingMode(processor, motherboard)
        when:
        addressingMode.write(0x00DE)
        then:
        1 * motherboard.writeMemoryLocation(0xBEEF, 0x00DE)
    }

    def "it should print out the correct disassembly for the current context"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def dummyOpcode = 0x00FF
        motherboard.readMemoryLocation(0x0000) >> dummyOpcode
        motherboard.readMemoryLocation(0x0001) >> 0x00EF
        motherboard.readMemoryLocation(0x0002) >> 0x00BE
        def addressingMode = new AbsoluteAddressingMode(processor, motherboard)
        expect:
        addressingMode.toString() == "0xBEEF"
    }

    def "it should respond with correct opcode width in bytes when asked"() {
        given:
        def processor = Mock(Processor)
        def motherboard = Mock(Motherboard)
        def addressingMode = new AbsoluteAddressingMode(processor, motherboard)
        expect:
        2 == addressingMode.getWidth()
    }

}