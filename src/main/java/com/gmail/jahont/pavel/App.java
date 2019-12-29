package com.gmail.jahont.pavel;

import impl.HomeWorkServiceImpl;
import service.HomeWorkService;

public class App {

    public static void main(String[] args) throws Exception {
        HomeWorkService homeWorkService = new HomeWorkServiceImpl();
        homeWorkService.runFirstSevice();
        //homeWorkService.runSecondService();
    }
}
