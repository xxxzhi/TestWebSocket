package com.scut.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

/**
 * Servlet implementation class SensorDataWebServlet
 */
@WebServlet("/SensorDataWebServlet")
public class SensorDataWebServlet extends WebSocketServlet {
	private Executor executor = Executors.newCachedThreadPool();
	//保存线程runnable
	private Map<Integer, SendRunnable> boundMap = new HashMap<Integer, SendRunnable>();

	private static final long serialVersionUID = 1L;
       
    /**
     * @see WebSocketServlet#WebSocketServlet()
     */
	
    public SensorDataWebServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
	protected StreamInbound createWebSocketInbound(String arg0,
			HttpServletRequest arg1) {
		// TODO Auto-generated method stub
		return new MessageInbound() {
			private WsOutbound outbound;
			@Override
			protected void onClose(int status) {
				// TODO Auto-generated method stub
				super.onClose(status);
				boundMap.remove(getWsOutbound().hashCode());
			}

			@Override
			protected void onOpen(WsOutbound outbound) {
				System.out.println("open");
				super.onOpen(outbound);
				SendRunnable runnable = new SendRunnable(outbound);
				boundMap.put(outbound.hashCode(), runnable);
				executor.execute(runnable);
			}

			@Override
			protected void onTextMessage(CharBuffer charBuffer) throws IOException {
				// TODO Auto-generated method stub
				System.out.println("收到来自客户端的消息:"+charBuffer);
	            WsOutbound out=this.getWsOutbound();
	            CharBuffer buffer=CharBuffer.wrap("I'am server,I recevied yourmessage:"+charBuffer);
	            out.writeTextMessage(buffer);
	            out.flush();
			}
			
			@Override
			protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
				// TODO Auto-generated method stub
				
			}
		};
	}

}
