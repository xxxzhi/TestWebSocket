package com.scut.websocket;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.Set;
import java.util.TreeMap;

import org.apache.catalina.websocket.WsOutbound;

public class SendRunnable implements Runnable {
	public WsOutbound outbound;

	public SendRunnable(WsOutbound outbound) {
		this.outbound = outbound;
	}

	private boolean running = true;

	public void cancel() {
		running = false;
	}

	@Override
	public void run() {
		while (running) {
			boolean dbUpdate = true;
			// ��ѯ���ݿ� �ж����ݿ��Ƿ����
			// TODO ��ѯ���ݿⲢ���жϵĹ���
			// ���Թ���
			Map<String, Long> map = new TreeMap<String, Long>();
			map.put("sensor1", 1l);
			map.put("sensor2", 2l);
			map.put("sensor3", 3l);
			map.put("sensor4", 4l);
			map.put("sensor5", 5l);
			map.put("sensor6", 6l);
			map.put("sensor7", 7l);
			map.put("sensor8", 8l);
			map.put("sensor9", 9l);

			// ������ݿ�������и��£�
			// ��������ظ� outbound
			char[] sensorData = new char[] {};
			StringBuilder sb = new StringBuilder();

			for (Entry<String, Long> entry : map.entrySet()) {
				sb.append(entry.getKey() + ":");
				sb.append(entry.getValue() + ";");
			}

			if (dbUpdate) {
				CharBuffer buffer = CharBuffer
						.wrap(sb.toString().toCharArray());
				try {
					outbound.writeTextMessage(buffer);
					outbound.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
