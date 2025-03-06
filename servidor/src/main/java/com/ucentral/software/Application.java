package com.ucentral.software;

import com.ucentral.software.tcp.Server;

public class Application {

    public static void main(String[] args) {

        Server server = new Server();
        server.run();
        
    }

}
