package com.yaowang.util.sessionmanager.socket;

@SuppressWarnings("deprecation")
public class SocketClientCheckThread extends Thread {
    private static int sleepTime = 5;
    private boolean isConn = false;

    @Override
    public void run() {
        while (true) {
            if (isConn) {
                System.out.println("与eclipse插件连接成功，端口是:5678。");
                suspend();
            }
            connection();
            try {
                Thread.sleep(sleepTime * 1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void connection() {
        if (!isConn) {
            isConn = SocketClient.connection();
        }
    }

    public Boolean getIsConn() {
        return isConn;
    }

    public void closeConn() {
        this.isConn = false;
        SocketClient.close();
        resume();
    }
}
