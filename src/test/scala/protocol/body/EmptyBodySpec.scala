package protocol.body

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol._
import com.github.romangrebennikov.columnize.protocol.Frame._
import com.github.romangrebennikov.columnize.protocol.Opcode._
import com.github.romangrebennikov.columnize.protocol.body._
import org.apache.commons.codec.binary.Hex
import org.scalatest.{MustMatchers, WordSpecLike}

/**
 * Created by shutty on 10/13/15.
 */
class EmptyBodySpec extends WordSpecLike with MustMatchers {
  "an empty result frame" must {
    "deserialize itself" in {
      val body = ByteBuffer.wrap(Hex.decodeHex("040000000500000000".toCharArray))
      assert(Frame(body) == new Frame(Request,4,new Flags(false,false,false,false),0,OPTIONS,0,EmptyBody))
    }
  }
}
