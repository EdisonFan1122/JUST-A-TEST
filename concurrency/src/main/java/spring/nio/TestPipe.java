package spring.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * Created by Edison on 2018/10/25.
 *
 * 管道：pipe
 */
public class TestPipe {

    public void sink() throws IOException {
        //获取管道
        Pipe pipe = Pipe.open();

        //将缓冲区数据写入管道
        ByteBuffer bb = ByteBuffer.allocate(1024);
        Pipe.SinkChannel sinkChannel = pipe.sink();
        bb.put("erqwer".getBytes());
        bb.clear();
        sinkChannel.write(bb);
        sinkChannel.close();
    }

    public void source() throws IOException {
        //获取管道
        Pipe pipe = Pipe.open();
        ByteBuffer bb = ByteBuffer.allocate(1024);
        Pipe.SourceChannel sourceChannel = pipe.source();
        bb.flip();
        sourceChannel.read(bb);
        sourceChannel.close();
    }

}
