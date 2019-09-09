package instructions

import addressing.AbsoluteAddressingMode
import addressing.AbsoluteXAddressingMode
import addressing.AbsoluteYAddressingMode
import addressing.ImmediateAddressingMode
import addressing.IndirectXAddressingMode
import addressing.IndirectYAddressingMode
import addressing.ZeroPageAddressingMode
import addressing.ZeroPageXAddressingMode
import domain.Motherboard
import domain.Processor
import spock.lang.Specification

class LDAInstructionTest extends Specification {

    def "It should load the accumulator in immediate mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def opcode = 0x00A9
        def expectedData = 0x007F
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> expectedData
        def addressingMode = new ImmediateAddressingMode(processor, motherboard)
        def ldaInstruction = new LDAInstruction(opcode, addressingMode, processor)
        when:
        ldaInstruction.execute()
        then:
        1 * processor.setAccumulator(expectedData)
    }

    def "It should load the accumulator in zero page mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def opcode = 0x00A5
        def operand = 0x0020
        def expectedData = 0x003F
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> operand
        motherboard.readMemoryLocation(0x0020) >> expectedData
        def addressingMode = new ZeroPageAddressingMode(processor, motherboard)
        def ldaInstruction = new LDAInstruction(opcode, addressingMode, processor)
        when:
        ldaInstruction.execute()
        then:
        1 * processor.setAccumulator(expectedData)
    }

    def "It should load the accumulator in zero page, X mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getX() >>> 0x0010
        def motherboard = Mock(Motherboard)
        def opcode = 0x00B5
        def operand = 0x0020
        def expectedData = 0x003F
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> operand
        motherboard.readMemoryLocation(0x0030) >> expectedData
        def addressingMode = new ZeroPageXAddressingMode(processor, motherboard)
        def ldaInstruction = new LDAInstruction(opcode, addressingMode, processor)
        when:
        ldaInstruction.execute()
        then:
        1 * processor.setAccumulator(expectedData)
    }

    def "It should load the accumulator in absolute mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def opcode = 0x00AD
        def expectedData = 0x003F
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> 0x00EF
        motherboard.readMemoryLocation(0x0002) >> 0x00BE
        motherboard.readMemoryLocation(0xBEEF) >> expectedData
        def addressingMode = new AbsoluteAddressingMode(processor, motherboard)
        def ldaInstruction = new LDAInstruction(opcode, addressingMode, processor)
        when:
        ldaInstruction.execute()
        then:
        1 * processor.setAccumulator(expectedData)
    }

    def "It should load the accumulator in absolute, X mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getX() >>> 0x0010
        def motherboard = Mock(Motherboard)
        def opcode = 0x00BD
        def expectedData = 0x003F
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> 0x00EF
        motherboard.readMemoryLocation(0x0002) >> 0x00BE
        motherboard.readMemoryLocation(0xBEFF) >> expectedData
        def addressingMode = new AbsoluteXAddressingMode(processor, motherboard)
        def ldaInstruction = new LDAInstruction(opcode, addressingMode, processor)
        when:
        ldaInstruction.execute()
        then:
        1 * processor.setAccumulator(expectedData)
    }

    def "It should load the accumulator in absolute, Y mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getY() >>> 0x0010
        def motherboard = Mock(Motherboard)
        def opcode = 0x00B9
        def expectedData = 0x003F
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> 0x00EF
        motherboard.readMemoryLocation(0x0002) >> 0x00BE
        motherboard.readMemoryLocation(0xBEFF) >> expectedData
        def addressingMode = new AbsoluteYAddressingMode(processor, motherboard)
        def ldaInstruction = new LDAInstruction(opcode, addressingMode, processor)
        when:
        ldaInstruction.execute()
        then:
        1 * processor.setAccumulator(expectedData)
    }

    def "It should load the accumulator in Indirect, X mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getX() >>> 0x0010
        def motherboard = Mock(Motherboard)
        def opcode = 0x00A1
        def operand = 0x0020
        def expectedData = 0x003F
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> operand
        motherboard.readMemoryLocation(0x0030) >> 0x00AA
        motherboard.readMemoryLocation(0x0031) >> 0x00BB
        motherboard.readMemoryLocation(0xBBAA) >> expectedData
        def addressingMode = new IndirectXAddressingMode(processor, motherboard)
        def ldaInstruction = new LDAInstruction(opcode, addressingMode, processor)
        when:
        ldaInstruction.execute()
        then:
        1 * processor.setAccumulator(expectedData)
    }

    def "It should load the accumulator in Indirect, Y mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getY() >>> 0x0010
        def motherboard = Mock(Motherboard)
        def opcode = 0x00B1
        def operand = 0x0020
        def expectedData = 0x003F
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> operand
        motherboard.readMemoryLocation(0x0020) >> 0x00AA
        motherboard.readMemoryLocation(0x0021) >> 0x00BB
        motherboard.readMemoryLocation(0xBBBA) >> expectedData
        def addressingMode = new IndirectYAddressingMode(processor, motherboard)
        def ldaInstruction = new LDAInstruction(opcode, addressingMode, processor)
        when:
        ldaInstruction.execute()
        then:
        1 * processor.setAccumulator(expectedData)
    }
}
