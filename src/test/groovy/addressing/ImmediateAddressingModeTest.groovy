package addressing

import domain.Motherboard
import domain.Processor
import spock.lang.Specification

class ImmediateAddressingModeTest extends Specification {

    def "it should implement the addressing mode interface"() {
        given:
        def processor = Mock(Processor)
        def motherboard = Mock(Motherboard)
        when:
        def addressingMode = new ImmediateAddressingMode(processor, motherboard)
        then:
        addressingMode instanceof AddressingMode

    }

    def "it should read the byte immediately after the program counter"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def dummyOpcode = 0x00FF
        def expectedData = 0x007F
        motherboard.readMemoryLocation(0x0000) >> dummyOpcode
        motherboard.readMemoryLocation(0x0001) >> 0x007F
        def addressingMode = new ImmediateAddressingMode(processor, motherboard)
        when:
        def actualData = addressingMode.read()
        then:
        actualData == expectedData
    }

    def "it should respond with correct opcode width in bytes when asked"() {
        given:
        def processor = Mock(Processor)
        def motherboard = Mock(Motherboard)
        def addressingMode = new ImmediateAddressingMode(processor, motherboard)
        expect:
        1 == addressingMode.getWidth()
    }
}
