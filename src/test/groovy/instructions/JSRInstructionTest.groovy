package instructions

import addressing.AbsoluteAddressingMode
import domain.Motherboard
import domain.Processor
import spock.lang.Specification

class JSRInstructionTest extends Specification {

    def "It should push the next instruction address -1 on to the stack, update the stack pointer and then proceed to the absolute memory location"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0xC0FE
        processor.getSP() >>> 0x00FF
        def motherboard = Mock(Motherboard)
        def opcode = 0x0020
        motherboard.readMemoryLocation(0xC0FE) >> opcode
        motherboard.readMemoryLocation(0xC0FF) >> 0x00EF
        motherboard.readMemoryLocation(0xC100) >> 0x00BE
        def addressingMode = new AbsoluteAddressingMode(processor, motherboard)
        def jsrInstruction = new JSRInstruction(opcode, addressingMode, processor, motherboard)
        when:
        jsrInstruction.execute()
        then:
        1 * motherboard.writeMemoryLocation(0x1FF, 0x00C1)
        1 * motherboard.writeMemoryLocation(0x1FE, 0x0001)
        1 * processor.setSP(0x00FD)
        1 * processor.setPC(0xBEEF)
    }

    def "the stack pointer should correctly roll around if the stack has reached the bottom"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0xC0FE
        processor.getSP() >>> 0x0001
        def motherboard = Mock(Motherboard)
        def opcode = 0x0020
        motherboard.readMemoryLocation(0xC0FE) >> opcode
        motherboard.readMemoryLocation(0xC0FF) >> 0x00EF
        motherboard.readMemoryLocation(0xC100) >> 0x00BE
        def addressingMode = new AbsoluteAddressingMode(processor, motherboard)
        def jsrInstruction = new JSRInstruction(opcode, addressingMode, processor, motherboard)
        when:
        jsrInstruction.execute()
        then:
        1 * motherboard.writeMemoryLocation(0x101, 0x00C1)
        1 * motherboard.writeMemoryLocation(0x100, 0x0001)
        1 * processor.setSP(0x00FF)
        1 * processor.setPC(0xBEEF)
    }

}
