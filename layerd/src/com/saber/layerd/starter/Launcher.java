package com.saber.layerd.starter;

import com.saber.layerd.controller.UserController;

import java.util.Scanner;

public class Launcher
{
    public static void main(String[] args) {
        UserController userController = new UserController();
        Scanner sc = new Scanner(System.in);
        while(true)
        {
            System.out.println("1.注册");
            System.out.println("2.登录");
            System.out.println("3.退出");
            int key=sc.nextInt();
            switch(key)
            {
                case 1:
                    register(sc,userController);
                    break;
                case 2:
                    login(sc,userController);
                    break;
                case 3:
                    exit();
                    break;
            }
        }
    }

    public static void exit()
    {
        System.exit(0);
    }

    public static void register(Scanner sc, UserController userController)
    {
        System.out.println("请输入用户名");
        String username = sc.next();
        System.out.println("请输入密码");
        String password = sc.next();
        String result = userController.register(username, password);
        System.out.println(result);
    }

    public static void login(Scanner sc, UserController userController)
    {
        System.out.println("请输入用户名");
        String username = sc.next();
        System.out.println("请输入密码");
        String password = sc.next();
        String result=userController.login(username, password);
        System.out.println(result);
    }
}
