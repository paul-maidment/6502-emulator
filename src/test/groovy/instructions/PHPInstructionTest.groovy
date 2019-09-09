package instructions

import addressing.ImpliedAddressingMode
import domain.Motherboard
import domain.Processor
import spock.lang.Specification

class PHPInstructionTest extends Specification {

    def "It should push the flags onto the stack memory location at SP and reduce the stack pointer by one"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getFlags() >>> 0x003F
        processor.getSP() >> 0x00FF
        def motherboard = Mock(Motherboard)
        def opcode = 0x0008
        motherboard.readMemoryLocation(0x0000) >> opcode
        def addressingMode = new ImpliedAddressingMode()
        def phpInstruction = new PHPInstruction(opcode, addressingMode, processor, motherboard)
        when:
        phpInstruction.execute()
        then:
        1 * motherboard.writeMemoryLocation(0x01FF, 0x003F)
        1 * processor.setSP(0x00FE)
    }

    def "If the SP is depleted, it should roll round"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getFlags() >>> 0x003F
        processor.getSP() >> 0x0000
        def motherboard = Mock(Motherboard)
        def opcode = 0x0008
        motherboard.readMemoryLocation(0x0000) >> opcode
        def addressingMode = new ImpliedAddressingMode()
        def phpInstruction = new PHPInstruction(opcode, addressingMode, processor, motherboard)
        when:
        phpInstruction.execute()
        then:
        1 * motherboard.writeMemoryLocation(0x0100, 0x003F)
        1 * processor.setSP(0x00FF)
    }
}
