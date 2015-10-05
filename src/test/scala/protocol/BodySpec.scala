package protocol

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol.body.Body
import org.scalatest.{MustMatchers, WordSpecLike}

/**
 * Created by shutty on 10/5/15.
 */
class BodySpec extends WordSpecLike with MustMatchers {
  "Body decoder" must {
    val foobar = ByteBuffer.wrap(Array(0x00, 0x06, 0x66, 0x6f, 0x6f, 0x62, 0x61, 0x72).map(_.toByte))
    val foomap = ByteBuffer.wrap(Array(0x00, 0x01, 0x00, 0x03, 0x66, 0x6f, 0x6f, 0x00, 0x03, 0x62, 0x61, 0x72).map(_.toByte))
    "decode strings" in {
      assert(Body.string(foobar.slice()) == "foobar")
    }
    "decode shorts" in {
      assert(Body.short(foobar.slice()) == 6)
    }
    "decode maps" in {
      assert(Body.map(foomap.slice()) == Map("foo" -> "bar"))
    }
  }
}
