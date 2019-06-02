package com.java.learn.test2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @author Karl Jin
 * @create 2019-06-01 23:15
 */

public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 打印下 处理客户端请求  执行业务逻辑
        System.out.println(ctx.channel().remoteAddress() + "," + msg);
        // 返回给客户端
        ctx.channel().writeAndFlush("from server:" + UUID.randomUUID());
    }


}
