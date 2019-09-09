package addressing

import spock.lang.Specification

class ImpliedAddressingModeTest extends Specification {

    def "it should respond with correct opcode width in bytes when asked"() {
        given:
        def addressingMode = new ImpliedAddressingMode()
        expect:
        0 == addressingMode.getWidth()
    }
}