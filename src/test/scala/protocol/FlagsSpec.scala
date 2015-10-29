package protocol

import com.github.romangrebennikov.columnize.protocol.Flags
import org.scalatest.{WordSpecLike, MustMatchers}

/**
 * Created by shutty on 10/5/15.
 */
class FlagsSpec extends WordSpecLike with MustMatchers {
  "CQL Flags build itself with enabled options" must {
    "<none>" in {
      assert(Flags(0.toByte) == Flags.empty)
    }
    "compression" in {
      assert(Flags(0x01.toByte) == Flags(compression = true))
    }
    "compression and tracing" in {
      assert(Flags((0x01 | 0x08).toByte) == Flags(compression = true, warning = true))
    }
    "serialize simple flags" in {
      assert(Flags(0.toByte).toBinary == 0)
      val tftf = new Flags(true, false, true, false)
      assert(tftf.toBinary == 5.toByte)
      assert(Flags(tftf.toBinary) == tftf)
    }
  }
}
