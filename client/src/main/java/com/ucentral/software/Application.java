package com.ucentral.software;

import com.ucentral.software.service.ManagerService;
import com.ucentral.software.ui.Main;

public class Application {

    public static void main(String[] args) {

        ManagerService service = new ManagerService();
        new Main(service).init();

    }

}
