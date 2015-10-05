package protocol

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol.{Opcode, Flags, Frame}
import org.scalatest.{MustMatchers, WordSpecLike}

/**
 * Created by shutty on 10/5/15.
 */
class FrameSpec extends WordSpecLike with MustMatchers {
  "Frame" must {
    "not spoil original bytebuffer position" in {
      val hello = ByteBuffer.wrap(Array(4,0,0,0,0,0,0,0,0).map(_.toByte))
      val frame = Frame(hello)
      assert(hello.position() == 0)
    }
    "decode frame length" in {
      val len = ByteBuffer.wrap(Array(4,0,0,0,0,0,0,0,0).map(_.toByte))
      assert(Frame(len) == Frame(Frame.Request, 4, Flags.empty, 0, Opcode.ERROR, 0, ByteBuffer.allocate(0)))
    }
    "allow large stream id" in {
      val buf = ByteBuffer.wrap(Array(4,0,127,127,0,0,0,0,0).map(_.toByte))
      assert(Frame(buf).stream > 127)
    }
  }

}
