package instructions

import addressing.ImpliedAddressingMode
import domain.Motherboard
import domain.Processor
import spock.lang.Specification

class TXSInstructionTest extends Specification {

    def "it should transfer the contents of the X register to the stack pointer"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getX() >>> 0x003F
        def motherboard = Mock(Motherboard)
        def opcode = 0x009A
        motherboard.readMemoryLocation(0x0000) >> opcode
        def addressingMode = new ImpliedAddressingMode()
        def txsInstruction = new TXSInstruction(opcode, addressingMode, processor)
        when:
        txsInstruction.execute()
        then:
        1 * processor.setSP(0x003F)
    }

}
