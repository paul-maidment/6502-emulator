package instructions

import addressing.ImpliedAddressingMode
import domain.Motherboard
import domain.Processor
import spock.lang.Specification

class TSXInstructionTest extends Specification {

    def "it should transfer the contents of the X register to the stack pointer"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getSP() >>> 0x003F
        def motherboard = Mock(Motherboard)
        def opcode = 0x00BA
        motherboard.readMemoryLocation(0x0000) >> opcode
        def addressingMode = new ImpliedAddressingMode()
        def tsxInstruction = new TSXInstruction(opcode, addressingMode, processor)
        when:
        tsxInstruction.execute()
        then:
        1 * processor.setX(0x003F)
    }

}
