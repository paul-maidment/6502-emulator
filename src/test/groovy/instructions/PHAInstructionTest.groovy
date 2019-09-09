package instructions

import addressing.ImpliedAddressingMode
import domain.Motherboard
import domain.Processor
import spock.lang.Specification

class PHAInstructionTest extends Specification {

    def "It should push the accumulator value onto the stack memory location at SP and reduce the stack pointer by one"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getAccumulator() >>> 0x003F
        processor.getSP() >> 0x00FF
        def motherboard = Mock(Motherboard)
        def opcode = 0x0048
        motherboard.readMemoryLocation(0x0000) >> opcode
        def addressingMode = new ImpliedAddressingMode()
        def txsInstruction = new PHAInstruction(opcode, addressingMode, processor, motherboard)
        when:
        txsInstruction.execute()
        then:
        1 * motherboard.writeMemoryLocation(0x01FF, 0x003F)
        1 * processor.setSP(0x00FE)
    }

    def "If the SP is depleted, it should roll round"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getAccumulator() >>> 0x003F
        processor.getSP() >> 0x0000
        def motherboard = Mock(Motherboard)
        def opcode = 0x0048
        motherboard.readMemoryLocation(0x0000) >> opcode
        def addressingMode = new ImpliedAddressingMode()
        def phaInstruction = new PHAInstruction(opcode, addressingMode, processor, motherboard)
        when:
        phaInstruction.execute()
        then:
        1 * motherboard.writeMemoryLocation(0x0100, 0x003F)
        1 * processor.setSP(0x00FF)
    }
}
