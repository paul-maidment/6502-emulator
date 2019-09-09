package instructions

import addressing.*
import domain.Motherboard
import domain.Processor
import spock.lang.Specification

class LDXInstructionTest extends Specification {

    def "It should load the X register in immediate mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def opcode = 0x00A2
        def expectedData = 0x007F
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> expectedData
        def addressingMode = new ImmediateAddressingMode(processor, motherboard)
        def ldaInstruction = new LDXInstruction(opcode, addressingMode, processor)
        when:
        ldaInstruction.execute()
        then:
        1 * processor.setX(expectedData)
    }

    def "It should load the X register in zero page mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def opcode = 0x00A6
        def operand = 0x0020
        def expectedData = 0x003F
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> operand
        motherboard.readMemoryLocation(0x0020) >> expectedData
        def addressingMode = new ZeroPageAddressingMode(processor, motherboard)
        def ldaInstruction = new LDXInstruction(opcode, addressingMode, processor)
        when:
        ldaInstruction.execute()
        then:
        1 * processor.setX(expectedData)
    }

    def "It should load the X register in zero page, Y mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getY() >>> 0x0010
        def motherboard = Mock(Motherboard)
        def opcode = 0x00B6
        def operand = 0x0020
        def expectedData = 0x003F
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> operand
        motherboard.readMemoryLocation(0x0030) >> expectedData
        def addressingMode = new ZeroPageYAddressingMode(processor, motherboard)
        def ldaInstruction = new LDXInstruction(opcode, addressingMode, processor)
        when:
        ldaInstruction.execute()
        then:
        1 * processor.setX(expectedData)
    }

    def "It should load the X register in absolute mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def opcode = 0x00AE
        def expectedData = 0x003F
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> 0x00EF
        motherboard.readMemoryLocation(0x0002) >> 0x00BE
        motherboard.readMemoryLocation(0xBEEF) >> expectedData
        def addressingMode = new AbsoluteAddressingMode(processor, motherboard)
        def ldaInstruction = new LDXInstruction(opcode, addressingMode, processor)
        when:
        ldaInstruction.execute()
        then:
        1 * processor.setX(expectedData)
    }

    def "It should load the X register in absolute, Y mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getY() >>> 0x0010
        def motherboard = Mock(Motherboard)
        def opcode = 0x00BE
        def expectedData = 0x003F
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> 0x00EF
        motherboard.readMemoryLocation(0x0002) >> 0x00BE
        motherboard.readMemoryLocation(0xBEFF) >> expectedData
        def addressingMode = new AbsoluteYAddressingMode(processor, motherboard)
        def ldaInstruction = new LDXInstruction(opcode, addressingMode, processor)
        when:
        ldaInstruction.execute()
        then:
        1 * processor.setX(expectedData)
    }
}
