package com.java.learn.test3;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author Karl Jin
 * @create 2019-06-02 09:50
 */

public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("--------------");
        for (Channel ch : channelGroup) {
            System.out.println("kk: " + ch);
        }
        System.out.println("+++++++++++++++");
        System.out.println("kk2: " + channel);
        channelGroup.forEach(c -> {
                    if (c != channel) {
                        c.writeAndFlush(channel.remoteAddress() + " 发送的消息: " + msg + "\n");
                    } else {
                        c.writeAndFlush("[自己]" + msg + "\n");
                    }
                }
        );
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("【服务器】--" + channel.remoteAddress() + "加入" + "\n");// 给群组进行广播
        // 新增当前的通道到群组中
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("【服务器】--" + channel.remoteAddress() + "离开" + "\n");// 给群组进行广播
        // 从群组中删除
        channelGroup.remove(channel);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 只需要服务器知道是否上线就好了
        System.out.println(channel.remoteAddress() + " 上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " 下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
