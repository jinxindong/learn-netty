package com.java.learn.test3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Karl Jin
 * @create 2019-06-02 10:11
 */

public class MyChatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);//  将服务端返回的东西 在客户端的控制台打印出来
    }
}
