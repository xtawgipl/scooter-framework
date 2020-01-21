# api版本
+ 在Controller类或方法上增加@ApiVersion注解 。
+ RequestMapping、GetMapping、PostMapping等注解的路径上，增加${xxx}占位符。
+ 路径的占位符可以是任意字符串。请求时http地址增加对应的V1、V2等版本号即可。