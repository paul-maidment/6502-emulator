package addressing

import domain.Motherboard
import domain.Processor
import spock.lang.Specification

class ZeroPageAddressingModeTest extends Specification {

    def "It should implement the addressing mode interface"() {
        given:
        def processor = Mock(Processor)
        def motherboard = Mock(Motherboard)
        when:
        def addressingMode = new ZeroPageAddressingMode(processor, motherboard)
        then:
        addressingMode instanceof AddressingMode
    }

    def "It should read from the zero page offset specified by the operand"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def dummyOpcode = 0x00FF
        def operand = 0x0020
        motherboard.readMemoryLocation(0x0000) >> dummyOpcode
        motherboard.readMemoryLocation(0x0001) >> operand
        motherboard.readMemoryLocation(0x0020) >> 0x00FF
        def addressingMode = new ZeroPageAddressingMode(processor, motherboard)
        when:
        def actual = addressingMode.read()
        then:
        actual == 0x00FF
    }

    def "It should write to the zero page offset specified by the operand"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def dummyOpcode = 0x00FF
        def operand = 0x0020
        motherboard.readMemoryLocation(0x0000) >> dummyOpcode
        motherboard.readMemoryLocation(0x0001) >> operand
        def addressingMode = new ZeroPageAddressingMode(processor, motherboard)
        when:
        addressingMode.write(0x00FF)
        then:
        1 * motherboard.writeMemoryLocation(0x0020, 0x00FF)
    }

    def "it should respond with correct opcode width in bytes when asked"() {
        given:
        def processor = Mock(Processor)
        def motherboard = Mock(Motherboard)
        def addressingMode = new ZeroPageAddressingMode(processor, motherboard)
        expect:
        1 == addressingMode.getWidth()
    }
}