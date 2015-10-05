package protocol

import com.github.romangrebennikov.columnize.protocol.Opcode
import org.scalatest.{WordSpecLike, MustMatchers}

/**
 * Created by shutty on 10/5/15.
 */
class OpcodeSpec extends WordSpecLike with MustMatchers {
  "Opcode factory" must {
    "decode ERROR opcode" in {
      assert(Opcode(0.toByte) == Opcode.ERROR)
    }
    "decode PREPARE opcode" in {
      assert(Opcode(9.toByte) == Opcode.PREPARE)
    }
  }
}
