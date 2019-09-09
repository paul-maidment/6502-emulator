package instructions

import addressing.ImpliedAddressingMode
import domain.Motherboard
import domain.Processor
import spock.lang.Specification

class PLAInstructionTest extends Specification {

    def "It should pull from the location indicated by the stack pointer and then increase the stack pointer by one"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getAccumulator() >>> 0x003F
        processor.getSP() >> 0x00FE
        def motherboard = Mock(Motherboard)
        def opcode = 0x0068
        motherboard.readMemoryLocation(0x0000) >> opcode
        def addressingMode = new ImpliedAddressingMode()
        def plaInstruction = new PLAInstruction(opcode, addressingMode, processor, motherboard)
        motherboard.readMemoryLocation(0x01FE) >>> 0x003F
        when:
        plaInstruction.execute()
        then:
        1 * processor.setAccumulator(0x003F)
        1 * processor.setSP(0x00FF)
    }

    def "If the SP is incremented past 0xFF, it should roll round to 0x00"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getAccumulator() >>> 0x003F
        processor.getSP() >> 0x00FF
        def motherboard = Mock(Motherboard)
        def opcode = 0x0068
        motherboard.readMemoryLocation(0x0000) >> opcode
        def addressingMode = new ImpliedAddressingMode()
        def plaInstruction = new PLAInstruction(opcode, addressingMode, processor, motherboard)
        motherboard.readMemoryLocation(0x01FF) >>> 0x003F
        when:
        plaInstruction.execute()
        then:
        1 * processor.setAccumulator(0x003F)
        1 * processor.setSP(0x0000)

    }
}
