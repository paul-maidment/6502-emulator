package instructions

import addressing.ImpliedAddressingMode
import domain.Motherboard
import domain.Processor
import spock.lang.Specification


class NOPInstructionTest extends Specification {

    def "it should be possible to execute"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def opcode = 0x0000
        motherboard.readMemoryLocation(0x0000) >> opcode
        def addressingMode = new ImpliedAddressingMode()
        def nopInstruction = new NOPInstruction(opcode, addressingMode, processor)
        expect:
        nopInstruction.execute()
    }
}
