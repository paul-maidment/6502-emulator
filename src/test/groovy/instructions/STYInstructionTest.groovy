package instructions

import addressing.*
import domain.Motherboard
import domain.Processor
import spock.lang.Specification

class STYInstructionTest extends Specification {

    def "It should store the Y register in Zero Page mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getY() >>> 0x003F
        def motherboard = Mock(Motherboard)
        def opcode = 0x0084
        def operand = 0x0020
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> operand
        def addressingMode = new ZeroPageAddressingMode(processor, motherboard)
        def staInstruction = new STYInstruction(opcode, addressingMode, processor)
        when:
        staInstruction.execute()
        then:
        1 * motherboard.writeMemoryLocation(0x0020, 0x003F)
    }

    def "It should store the Y register in Zero Page, X mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getX() >>> 0x0010
        processor.getY() >>> 0x003F
        def motherboard = Mock(Motherboard)
        def opcode = 0x0094
        def operand = 0x0020
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> operand
        def addressingMode = new ZeroPageXAddressingMode(processor, motherboard)
        def staInstruction = new STYInstruction(opcode, addressingMode, processor)
        when:
        staInstruction.execute()
        then:
        1 * motherboard.writeMemoryLocation(0x0030, 0x003F)
    }

    def "It should store the Y register in Absolute mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getY() >>> 0x003F
        def motherboard = Mock(Motherboard)
        def opcode = 0x008C

        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> 0x00EF
        motherboard.readMemoryLocation(0x0002) >> 0x00BE
        def addressingMode = new AbsoluteAddressingMode(processor, motherboard)
        def staInstruction = new STYInstruction(opcode, addressingMode, processor)
        when:
        staInstruction.execute()
        then:
        1 * motherboard.writeMemoryLocation(0xBEEF, 0x003F)
    }
}
