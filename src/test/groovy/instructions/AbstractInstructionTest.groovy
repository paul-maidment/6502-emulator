package instructions

import addressing.AddressingMode
import domain.Processor
import spock.lang.Specification

class AbstractInstructionTest extends Specification {

    def "it should calculate the width of the instruction correctly"() {
        given:
        def processor = Mock(Processor)
        def addressingMode = Mock(AddressingMode)
        addressingMode.getWidth() >>> 21
        def instruction = new JMPInstruction(0x000A, addressingMode, processor)
        expect:
        22 == instruction.getWidth()
    }

}
