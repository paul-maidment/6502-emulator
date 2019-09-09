package addressing

import domain.Motherboard
import domain.Processor
import spock.lang.Specification

class RelativeAddressingModeTest extends Specification {

    def "It should implement the addressing mode interface"() {
        given:
        def processor = Mock(Processor)
        def motherboard = Mock(Motherboard)
        when:
        def addressingMode = new RelativeAddressingMode(processor, motherboard)
        then:
        addressingMode instanceof AddressingMode
    }

    def "If the operand is positive by 8 bit 2s compliment then the PC of the processor should be moved forward on updatePC"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0020
        def motherboard = Mock(Motherboard)
        def dummyOpcode = 0x00FF
        def operand = 0x0020
        motherboard.readMemoryLocation(0x0020) >> dummyOpcode
        motherboard.readMemoryLocation(0x0021) >> operand
        def addressingMode = new RelativeAddressingMode(processor, motherboard)
        when:
        addressingMode.updatePC()
        then:
        1 * processor.setPC(0x0040)
    }

    def "If the operand is negative by 8 bit 2s compliment then the PC of the processor should be moved backwards on updatePC"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0020
        def motherboard = Mock(Motherboard)
        def dummyOpcode = 0x00FF
        def operand = 0x00EC
        motherboard.readMemoryLocation(0x0020) >> dummyOpcode
        motherboard.readMemoryLocation(0x0021) >> operand
        def addressingMode = new RelativeAddressingMode(processor, motherboard)
        when:
        addressingMode.updatePC()
        then:
        1 * processor.setPC(0x000C)
    }

    def "it should respond with correct opcode width in bytes when asked"() {
        given:
        def processor = Mock(Processor)
        def motherboard = Mock(Motherboard)
        def addressingMode = new RelativeAddressingMode(processor, motherboard)
        expect:
        1 == addressingMode.getWidth()
    }

}