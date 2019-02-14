package com.euhusky.remote.netty.docde;

import java.util.List;
import com.euhusky.serialization.DefaultSerializable;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MessageDecode extends ByteToMessageDecoder {
	

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if(in.readableBytes()<4) {
            in.resetReaderIndex();
			return;
		}
		int length=in.readInt();
		
		if(in.readableBytes()<length) {
			in.resetReaderIndex();
			return;
		}
		ByteBuf data=in.readBytes(length);
		
		out.add(data);
	}




}
