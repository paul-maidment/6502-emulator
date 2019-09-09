package addressing

import domain.Motherboard
import domain.Processor
import spock.lang.Specification

class AccumulatorAddressingModeTest extends Specification {

    def "it should implement the addressing mode interface"() {
        given:
        def processor = Mock(Processor)
        def motherboard = Mock(Motherboard)
        when:
        def addressingMode = new AccumulatorAddressingMode(processor, motherboard)
        then:
        addressingMode instanceof AddressingMode

    }

    def "it should read from the accumulator of the processor"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getAccumulator() >>> 0x00FA
        def motherboard = Mock(Motherboard)
        def addressingMode = new AccumulatorAddressingMode(processor, motherboard)
        when:
        def actualData = addressingMode.read()
        then:
        actualData == processor.getAccumulator()
    }

    def "it should write to the accumulator of the processor"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def addressingMode = new AccumulatorAddressingMode(processor, motherboard)
        when:
        addressingMode.write(0x00FB)
        then:
        processor.setAccumulator(0x00FB)
    }

    def "it should print out the correct disassembly for the current context"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def addressingMode = new AccumulatorAddressingMode(processor, motherboard)
        expect:
        addressingMode.toString() == "A"
    }

    def "it should respond with correct opcode width in bytes when asked"() {
        given:
        def processor = Mock(Processor)
        def motherboard = Mock(Motherboard)
        def addressingMode = new AccumulatorAddressingMode(processor, motherboard)
        expect:
        0 == addressingMode.getWidth()
    }

}
