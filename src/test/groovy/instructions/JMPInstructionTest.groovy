package instructions

import addressing.AbsoluteAddressingMode
import addressing.IndirectAddressingMode
import domain.Motherboard
import domain.Processor
import spock.lang.Specification

/**
 * Affects Flags: none
 *
 * MODE           SYNTAX       HEX LEN TIM
 * Absolute      JMP $5597     $4C  3   3
 * Indirect      JMP ($5597)   $6C  3   5
 *
 * JMP transfers program execution to the following address (absolute) or to the location contained in the following address (indirect). Note that there is no carry associated with the indirect jump so:
 * AN INDIRECT JUMP MUST NEVER USE A
 * VECTOR BEGINNING ON THE LAST BYTE
 * OF A PAGE
 * For example if address $3000 contains $40, $30FF contains $80, and $3100 contains $50, the result of JMP ($30FF) will be a transfer of control to $4080 rather than $5080 as you intended i.e. the 6502 took the low byte of the address from $30FF and the high byte from $3000.
 */

class JMPInstructionTest extends Specification {

    def "it should jump to the specified address in absolute addressing mode"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def opcode = 0x004C
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> 0x00EF
        motherboard.readMemoryLocation(0x0002) >> 0x00BE
        def addressingMode = new AbsoluteAddressingMode(processor, motherboard)
        def jmpInstruction = new JMPInstruction(opcode, addressingMode, processor)
        when:
        jmpInstruction.execute()
        then:
        1 * processor.setPC(0xBEEF)
    }

    def "it should indirectly jump to address indicated by operand, provided that both bytes are in the same page"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def opcode = 0x006C
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> 0x0020
        motherboard.readMemoryLocation(0x0002) >> 0x00DE
        motherboard.readMemoryLocation(0xDE20) >> 0x00AA
        motherboard.readMemoryLocation(0xDE21) >> 0x00BB
        def addressingMode = new IndirectAddressingMode(processor, motherboard)
        def jmpInstruction = new JMPInstruction(opcode, addressingMode, processor)
        when:
        jmpInstruction.execute()
        then:
        1 * processor.setPC(0xBBAA)
    }

    def "it should jump indirectly to the address indicated by the last byte and first byte of the page if first byte is at end of a page"() {
        given:
        def processor = Mock(Processor)
        processor.getPC() >>> 0x0000
        def motherboard = Mock(Motherboard)
        def opcode = 0x006C
        motherboard.readMemoryLocation(0x0000) >> opcode
        motherboard.readMemoryLocation(0x0001) >> 0x00FF
        motherboard.readMemoryLocation(0x0002) >> 0x00DE
        motherboard.readMemoryLocation(0xDEFF) >> 0x00AA
        motherboard.readMemoryLocation(0xDF00) >> 0x00CC
        motherboard.readMemoryLocation(0xDE00) >> 0x00BB
        def addressingMode = new IndirectAddressingMode(processor, motherboard)
        def jmpInstruction = new JMPInstruction(opcode, addressingMode, processor)
        when:
        jmpInstruction.execute()
        then:
        1 * processor.setPC(0xBBAA)
    }

}
