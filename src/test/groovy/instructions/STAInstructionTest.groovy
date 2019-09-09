package instructions

import addressing.*
import domain.Motherboard
import domain.Processor
import spock.lang.Specification

class STAInstructionTest extends Specification {

    def "It should store the accumulator in Zero Page mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getAccumulator() >>> 0x003F
        def motherboard = Mock(Motherboard)
        def opcode = 0x0085
        def operand = 0x0020
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> operand
        def addressingMode = new ZeroPageAddressingMode(processor, motherboard)
        def staInstruction = new STAInstruction(opcode, addressingMode, processor)
        when:
        staInstruction.execute()
        then:
        1 * motherboard.writeMemoryLocation(0x0020, 0x003F)
    }

    def "It should store the accumulator in Zero Page, X mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getX() >>> 0x0010
        processor.getAccumulator() >>> 0x003F
        def motherboard = Mock(Motherboard)
        def opcode = 0x0095
        def operand = 0x0020
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> operand
        def addressingMode = new ZeroPageXAddressingMode(processor, motherboard)
        def staInstruction = new STAInstruction(opcode, addressingMode, processor)
        when:
        staInstruction.execute()
        then:
        1 * motherboard.writeMemoryLocation(0x0030, 0x003F)
    }

    def "It should store the accumulator in Absolute mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getAccumulator() >>> 0x003F
        def motherboard = Mock(Motherboard)
        def opcode = 0x008D

        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> 0x00EF
        motherboard.readMemoryLocation(0x0002) >> 0x00BE
        def addressingMode = new AbsoluteAddressingMode(processor, motherboard)
        def staInstruction = new STAInstruction(opcode, addressingMode, processor)
        when:
        staInstruction.execute()
        then:
        1 * motherboard.writeMemoryLocation(0xBEEF, 0x003F)
    }

    def "It should store the accumulator in Absolute, X mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getX() >>> 0x0010
        processor.getAccumulator() >>> 0x003F
        def motherboard = Mock(Motherboard)
        def opcode = 0x009D
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> 0x00EF
        motherboard.readMemoryLocation(0x0002) >> 0x00BE
        def addressingMode = new AbsoluteXAddressingMode(processor, motherboard)
        def staInstruction = new STAInstruction(opcode, addressingMode, processor)
        when:
        staInstruction.execute()
        then:
        1 * motherboard.writeMemoryLocation(0xBEFF, 0x003F)
    }

    def "It should store the accumulator in Absolute, Y mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getY() >>> 0x0010
        processor.getAccumulator() >>> 0x003F
        def motherboard = Mock(Motherboard)
        def opcode = 0x0099
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> 0x00EF
        motherboard.readMemoryLocation(0x0002) >> 0x00BE
        def addressingMode = new AbsoluteYAddressingMode(processor, motherboard)
        def staInstruction = new STAInstruction(opcode, addressingMode, processor)
        when:
        staInstruction.execute()
        then:
        1 * motherboard.writeMemoryLocation(0xBEFF, 0x003F)
    }

    def "It should store the accumulator in Indirect, X mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getX() >>> 0x0010
        processor.getAccumulator() >>> 0x003F
        def motherboard = Mock(Motherboard)
        def opcode = 0x0081
        def operand = 0x0020
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> operand
        motherboard.readMemoryLocation(0x0030) >> 0x00AA
        motherboard.readMemoryLocation(0x0031) >> 0x00BB
        def addressingMode = new IndirectXAddressingMode(processor, motherboard)
        def staInstruction = new STAInstruction(opcode, addressingMode, processor)
        when:
        staInstruction.execute()
        then:
        1 * motherboard.writeMemoryLocation(0xBBAA, 0x003F)
    }

    def "It should store the accumulator in Indirect, Y mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        processor.getY() >>> 0x0010
        processor.getAccumulator() >>> 0x003F
        def motherboard = Mock(Motherboard)
        def opcode = 0x0091
        def operand = 0x0020
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> operand
        motherboard.readMemoryLocation(0x0020) >> 0x00AA
        motherboard.readMemoryLocation(0x0021) >> 0x00BB
        def addressingMode = new IndirectYAddressingMode(processor, motherboard)
        def staInstruction = new STAInstruction(opcode, addressingMode, processor)
        when:
        staInstruction.execute()
        then:
        1 * motherboard.writeMemoryLocation(0xBBBA, 0x003F)
    }
}
