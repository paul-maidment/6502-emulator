import core.Mos6502Core
import domain.Motherboard
import instructions.Instruction
import spock.lang.Specification

class Mos6502CoreSpecification extends Specification {

    def mos6502Core
    def motherboard = Mock(Motherboard)

    def "it should instantiate as a singleton"() {

        setup:
        mos6502Core = new Mos6502Core(
                0x00,0x00,0x00,0x00, 0x0000, 0x0000, motherboard, []
        )
        expect:
        mos6502Core instanceof Mos6502Core

    }

    def "it should have an 8 bit accumulator"() {
        setup:
        mos6502Core = new Mos6502Core(
                0xFF11,0x00,0x00,0x00, 0x0000, 0x0000, motherboard, []
        )
        expect:
        mos6502Core.getAccumulator() instanceof Integer
        mos6502Core.getAccumulator() == 0x11
    }

    def "it should have an 8 bit X register"() {
        setup:
        mos6502Core = new Mos6502Core(
                0x0000,0xFF11,0x00,0x00, 0x0000, 0x0000, motherboard, []
        )
        expect:
        mos6502Core.getX() instanceof Integer
        mos6502Core.getX() == 0x11
    }

    def "it should have a 8 bit Y register"() {
        setup:
        mos6502Core = new Mos6502Core(
                0x0000,0x0000,0xFF11,0x00, 0x0000, 0x0000, motherboard, []
        )
        expect:
        mos6502Core.getY() instanceof Integer
        mos6502Core.getY() == 0x11
    }

    def "it should have an 8 bit  flag register"() {
        setup:
        mos6502Core = new Mos6502Core(
                0x0000,0x0000,0x00,0xFF24, 0x0000, 0x0000, motherboard, []
        )
        expect:
        mos6502Core.getFlags() instanceof Integer
        mos6502Core.getFlags() == 0x24
    }

    def "it should have a 16 bit program counter"() {
        setup:
        mos6502Core = new Mos6502Core(
                0x0000,0x0000,0x00,0x0000, 0xFD0A, 0x0000, motherboard, []
        )
        expect:
        mos6502Core.getPC() instanceof Integer
        mos6502Core.getPC() == 0xFD0A
    }

    def "it should have a 16 bit stack pointer"() {
        setup:
        mos6502Core = new Mos6502Core(
                0x0000,0x0000,0x00,0x0000, 0x0000, 0xF000, motherboard, []
        )
        expect:
        mos6502Core.getSP() instanceof Integer
        mos6502Core.getSP() == 0xF000
    }

    def "it should recognise an instruction in memory"() {
        setup:
        def instruction = Mock(Instruction)
        instruction.getOpCode() >> 0xFA
        instruction.getCycleCount() >> 5
        instruction.getWidth() >> 1
        mos6502Core = new Mos6502Core(
                0x0000,0x0000,0x00,0x0000, 0x0000, 0xF000, motherboard,
                [
                        instruction
                ]
        )
        motherboard.readMemoryLocation(0x0000) >> 0x00FA
        when:
        mos6502Core.getCurrentInstruction() == null
        mos6502Core.executeNextClockCycle()
        then:
        mos6502Core.getCurrentInstruction() == instruction
    }

    def "it should advance through memory"() {
        setup:
        def instruction1 = Mock(Instruction)
        instruction1.getOpCode() >> 0xFA
        instruction1.getCycleCount() >> 1
        instruction1.getWidth() >> 1
        def instruction2 = Mock(Instruction)
        instruction2.getOpCode() >> 0xFB
        instruction2.getCycleCount() >> 1
        instruction2.getWidth() >> 1
        mos6502Core = new Mos6502Core(
                0x0000,0x0000,0x00,0x0000, 0x0000, 0xF000, motherboard,
                [
                        instruction1, instruction2
                ]
        )
        motherboard.readMemoryLocation(0x0000) >> 0x00FA
        motherboard.readMemoryLocation(0x0001) >> 0x00FB
        when:
        mos6502Core.getCurrentInstruction() == null
        mos6502Core.executeNextClockCycle()
        def actualInstruction1 = mos6502Core.getCurrentInstruction()
        mos6502Core.executeNextClockCycle()
        def actualInstruction2 = mos6502Core.getCurrentInstruction()
        then:
        actualInstruction1 == instruction1
        actualInstruction2 == instruction2
    }

    def "it should respect cycle counts when advancing through memory"() {
        setup:
        def instruction1 = Mock(Instruction)
        instruction1.getOpCode() >> 0xFA
        instruction1.getCycleCount() >> 2
        instruction1.getWidth() >> 1
        def instruction2 = Mock(Instruction)
        instruction2.getOpCode() >> 0xFB
        instruction2.getCycleCount() >> 1
        instruction2.getWidth() >> 1
        mos6502Core = new Mos6502Core(
                0x0000,0x0000,0x00,0x0000, 0x0000, 0xF000, motherboard,
                [
                        instruction1, instruction2
                ]
        )
        motherboard.readMemoryLocation(0x0000) >> 0x00FA
        motherboard.readMemoryLocation(0x0001) >> 0x00FB
        when:
        mos6502Core.getCurrentInstruction() == null
        mos6502Core.executeNextClockCycle()
        def actualInstruction1 = mos6502Core.getCurrentInstruction()
        mos6502Core.executeNextClockCycle()
        def actualInstruction2 = mos6502Core.getCurrentInstruction()
        then:
        actualInstruction1 == instruction1
        actualInstruction2 == instruction1
    }

    def "it should respect width when advancing through memory"() {
        setup:
        def instruction1 = Mock(Instruction)
        instruction1.getOpCode() >> 0xFA
        instruction1.getCycleCount() >> 1
        instruction1.getWidth() >> 2
        def instruction2 = Mock(Instruction)
        instruction2.getOpCode() >> 0xFB
        instruction2.getCycleCount() >> 1
        instruction2.getWidth() >> 1
        mos6502Core = new Mos6502Core(
                0x0000,0x0000,0x00,0x0000, 0x0000, 0xF000, motherboard,
                [
                        instruction1, instruction2
                ]
        )
        motherboard.readMemoryLocation(0x0000) >> 0x00FA
        motherboard.readMemoryLocation(0x0001) >> 0x00CC
        motherboard.readMemoryLocation(0x0002) >> 0x00FB
        when:
        mos6502Core.getCurrentInstruction() == null
        mos6502Core.executeNextClockCycle()
        def actualInstruction1 = mos6502Core.getCurrentInstruction()
        mos6502Core.executeNextClockCycle()
        def actualInstruction2 = mos6502Core.getCurrentInstruction()
        then:
        actualInstruction1 == instruction1
        actualInstruction2 == instruction2
    }

    def "it should execute each instruction as it is fetched"() {
        setup:
        def instruction1 = Mock(Instruction)
        instruction1.getOpCode() >> 0xFA
        instruction1.getCycleCount() >> 1
        instruction1.getWidth() >> 2
        def instruction2 = Mock(Instruction)
        instruction2.getOpCode() >> 0xFB
        instruction2.getCycleCount() >> 1
        instruction2.getWidth() >> 1
        mos6502Core = new Mos6502Core(
                0x0000,0x0000,0x00,0x0000, 0x0000, 0xF000, motherboard,
                [
                        instruction1, instruction2
                ]
        )
        motherboard.readMemoryLocation(0x0000) >> 0x00FA
        motherboard.readMemoryLocation(0x0001) >> 0x00CC
        motherboard.readMemoryLocation(0x0002) >> 0x00FB
        when:
        mos6502Core.getCurrentInstruction() == null
        mos6502Core.executeNextClockCycle()
        def actualInstruction1 = mos6502Core.getCurrentInstruction()
        mos6502Core.executeNextClockCycle()
        def actualInstruction2 = mos6502Core.getCurrentInstruction()
        then:
        actualInstruction1 == instruction1
        actualInstruction2 == instruction2
        1 * instruction1.execute()
        1 * instruction2.execute()
    }

    def "unregistered opcodes encountered in memory should be treated as one cycle non operation"() {
        setup:
        def instruction1 = Mock(Instruction)
        instruction1.getOpCode() >> 0xFA
        instruction1.getCycleCount() >> 1
        instruction1.getWidth() >> 1
        def nopInstruction = Mock(Instruction)
        nopInstruction.getOpCode() >>0x00
        nopInstruction.getCycleCount() >> 1
        nopInstruction.getWidth() >> 1
        mos6502Core = new Mos6502Core(
                0x0000,0x0000,0x00,0x0000, 0x0000, 0xF000, motherboard,
                [
                        nopInstruction, instruction1
                ]
        )
        motherboard.readMemoryLocation(0x0000) >> 0x00FA
        motherboard.readMemoryLocation(0x0001) >> 0x00CC
        when:
        mos6502Core.getCurrentInstruction() == null
        mos6502Core.executeNextClockCycle()
        def actualInstruction1 = mos6502Core.getCurrentInstruction()
        mos6502Core.executeNextClockCycle()
        def actualInstruction2 = mos6502Core.getCurrentInstruction()
        then:
        actualInstruction1 == instruction1
        actualInstruction2 == nopInstruction
        1 * instruction1.execute()
        1 * nopInstruction.execute()
    }
}
