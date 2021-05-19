package cn.oxo.iworks.web.shiro.cache;

import java.io.IOException;

import org.nustaq.serialization.FSTConfiguration;
import org.redisson.client.handler.State;
import org.redisson.client.protocol.Decoder;
import org.redisson.client.protocol.Encoder;
import org.redisson.codec.FstCodec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ShiroSeesionFstCodec extends FstCodec {

      public ShiroSeesionFstCodec() {
            super();

      }

      public ShiroSeesionFstCodec(ClassLoader classLoader) {
            super(classLoader);

      }

      public ShiroSeesionFstCodec(FSTConfiguration fstConfiguration) {
            super(fstConfiguration);

      }

      @Override
      public Decoder<Object> getMapKeyDecoder() {

            return decoder;
      }

      @Override
      public Encoder getMapKeyEncoder() {

            return encoder;
      }

      private final Decoder<Object> decoder = new Decoder<Object>() {
            @Override
            public Object decode(ByteBuf buf, State state) throws IOException {

                  String str;
                  if (buf.hasArray()) { // 处理堆缓冲区
                        str = new String(buf.array(), buf.arrayOffset() + buf.readerIndex(), buf.readableBytes());
                  } else { // 处理直接缓冲区以及复合缓冲区
                        byte[] bytes = new byte[buf.readableBytes()];
                        buf.getBytes(buf.readerIndex(), bytes);
                        str = new String(bytes, 0, buf.readableBytes());
                  }
                  return str;

            }
      };

      private final Encoder encoder = new Encoder() {

            @Override
            public ByteBuf encode(Object in) throws IOException {

                  ByteBuf heapBuffer = Unpooled.buffer();

                  return heapBuffer.writeBytes(in.toString().getBytes());

            }
      };

}
