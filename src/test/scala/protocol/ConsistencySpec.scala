package protocol

import com.github.romangrebennikov.columnize.protocol.Consistency
import org.scalatest.{MustMatchers, WordSpecLike}

/**
 * Created by shutty on 10/5/15.
 */
class ConsistencySpec extends WordSpecLike with MustMatchers {
  "Consistency builder" must {
    "decode ANY" in {
      assert(Consistency(0x0000.toShort) == Consistency.ANY)
    }
    "decode LOCAL_ONE" in {
      assert(Consistency(0x000a.toShort) == Consistency.LOCAL_ONE)
    }
  }
}
