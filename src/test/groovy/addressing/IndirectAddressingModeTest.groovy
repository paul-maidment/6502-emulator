package addressing

import domain.Motherboard
import domain.Processor
import spock.lang.Specification

class IndirectAddressingModeTest extends Specification {

    def "It should implement the addressing mode interface"() {
        given:
        def processor = Mock(Processor)
        def motherboard = Mock(Motherboard)
        when:
        def addressingMode = new IndirectAddressingMode(processor, motherboard)
        then:
        addressingMode instanceof AddressingMode
    }

    def "It should update the program counter of the processor to the address pointed to by the memory location indicated by the 16 bit operand on update pc"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def dummyOpcode = 0x00FF
        motherboard.readMemoryLocation(0x0000) >> dummyOpcode
        motherboard.readMemoryLocation(0x0001) >> 0x0020
        motherboard.readMemoryLocation(0x0002) >> 0x00DE
        motherboard.readMemoryLocation(0xDE20) >> 0x00AA
        motherboard.readMemoryLocation(0xDE21) >> 0x00BB
        def addressingMode = new IndirectAddressingMode(processor, motherboard)
        when:
        addressingMode.updatePC()
        then:
        1 * processor.setPC(0xBBAA)
    }

    def "If the address pointed to by the program counter is on an exact page boundary then the MSB should be read from the start of that page"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def dummyOpcode = 0x00FF
        motherboard.readMemoryLocation(0x0000) >> dummyOpcode
        motherboard.readMemoryLocation(0x0001) >> 0x00FF
        motherboard.readMemoryLocation(0x0002) >> 0x00DE
        motherboard.readMemoryLocation(0xDEFF) >> 0x00AA
        motherboard.readMemoryLocation(0xDF00) >> 0x00CC
        motherboard.readMemoryLocation(0xDE00) >> 0x00BB
        def addressingMode = new IndirectAddressingMode(processor, motherboard)
        when:
        addressingMode.updatePC()
        then:
        1 * processor.setPC(0xBBAA)
    }

    def "it should respond with correct opcode width in bytes when asked"() {
        given:
        def processor = Mock(Processor)
        def motherboard = Mock(Motherboard)
        def addressingMode = new IndirectAddressingMode(processor, motherboard)
        expect:
        2 == addressingMode.getWidth()
    }
}