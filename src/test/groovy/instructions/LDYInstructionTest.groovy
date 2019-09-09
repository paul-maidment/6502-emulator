package instructions

import addressing.*
import domain.Motherboard
import domain.Processor
import spock.lang.Specification

class LDYInstructionTest extends Specification {

    def "It should load the Y register in immediate mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def opcode = 0x00A0
        def expectedData = 0x007F
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> expectedData
        def addressingMode = new ImmediateAddressingMode(processor, motherboard)
        def ldaInstruction = new LDYInstruction(opcode, addressingMode, processor)
        when:
        ldaInstruction.execute()
        then:
        1 * processor.setY(expectedData)
    }

    def "It should load the Y register in zero page mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def opcode = 0x00A4
        def operand = 0x0020
        def expectedData = 0x003F
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> operand
        motherboard.readMemoryLocation(0x0020) >> expectedData
        def addressingMode = new ZeroPageAddressingMode(processor, motherboard)
        def ldaInstruction = new LDYInstruction(opcode, addressingMode, processor)
        when:
        ldaInstruction.execute()
        then:
        1 * processor.setY(expectedData)
    }

    def "It should load the Y register in zero page, X mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getX() >>> 0x0010
        def motherboard = Mock(Motherboard)
        def opcode = 0x00B4
        def operand = 0x0020
        def expectedData = 0x003F
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> operand
        motherboard.readMemoryLocation(0x0030) >> expectedData
        def addressingMode = new ZeroPageXAddressingMode(processor, motherboard)
        def ldaInstruction = new LDYInstruction(opcode, addressingMode, processor)
        when:
        ldaInstruction.execute()
        then:
        1 * processor.setY(expectedData)
    }

    def "It should load the Y register in absolute mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def opcode = 0x00AC
        def expectedData = 0x003F
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> 0x00EF
        motherboard.readMemoryLocation(0x0002) >> 0x00BE
        motherboard.readMemoryLocation(0xBEEF) >> expectedData
        def addressingMode = new AbsoluteAddressingMode(processor, motherboard)
        def ldaInstruction = new LDYInstruction(opcode, addressingMode, processor)
        when:
        ldaInstruction.execute()
        then:
        1 * processor.setY(expectedData)
    }

    def "It should load the Y register in absolute, X mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getX() >>> 0x0010
        def motherboard = Mock(Motherboard)
        def opcode = 0x00BC
        def expectedData = 0x003F
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> 0x00EF
        motherboard.readMemoryLocation(0x0002) >> 0x00BE
        motherboard.readMemoryLocation(0xBEFF) >> expectedData
        def addressingMode = new AbsoluteXAddressingMode(processor, motherboard)
        def ldaInstruction = new LDYInstruction(opcode, addressingMode, processor)
        when:
        ldaInstruction.execute()
        then:
        1 * processor.setY(expectedData)
    }
}
