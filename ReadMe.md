

基于Java-Socket的多用户即时聊天
===========================
 Copyright: Copyright (c) 2020  
 Created on 2020-4-1    
 Author:zengxiaochao666@gmail.com  
 Version 1.0  
### 环境依赖
> JDK1.8
### 部署步骤
> 1. import工程包到你的IDEA
> 2. 先运行server.class，在运行login.class
> 3. 可多次运行login（运行多个），实现多用户聊天
> 4. 在pwd.propertiess随便编写一个账号密码，并在online.properties标记相应账号为0（未在线）


###目录结构描述  
├── src                      // 代码以及资源文件  
│   ├── res                  //资源文件，用于表情包   
│   ├── client               // 客户端程序  
│   ├── login                // 登陆程序  
│   ├── mianban              // 聊天面板  
│   ├── person_list          // 用户面板  
│   ├── read                 // 读取配置文件  
│   └── write                // 写入配置文件  
│   └── server               // 服务端程序  
├── chat.properties          //配置文件  
├── online.properties        //在线标记  
├── pwd.properties              //账号密码  
├── JavaEE.iml    
└── ReadMe.md                   //help



### V1.0.0 版本内容更新
> 1. 用户可以选择发送给私人消息
> 2. 多用户在线
> 2. 用户可以发送随机表情，显示图片

 
