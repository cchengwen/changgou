###   说明：畅购商场微服务项目实现步骤说明

# 1、先创建一个文件夹，如：changgou，然后在idea中打开此文件，如项目中所示。

# 2、创建一个父工程，命名changgou-parent，然后在pom文件中引入依赖，不需要src包

# 3、在changgou-parent父目录下，创建子模块，命名为 changgou-gateway，不需要src包

# 4、在changgou-parent父目录下，创建子模块，命名为 changgou-service，不需要src包

# 5、在changgou-parent父目录下，创建子模块，命名为 changgou-service-api，不需要src包

# 6、在changgou-parent父目录下，创建子模块，命名为 changgou-web，不需要src包

# 7、在changgou-parent父目录下，创建eureka服务，命名为 changgou-eureka，并在子模块的pom文件中引入相关依赖, 然后编写启动类，
     如：EurekaServiceApplication，并加入注解 @EnableEurekaServer // 开启eureka服务，然后访问：http://127.0.0.1:7001 即可

# 8、在changgou-parent父目录下，创建子模块，命名为 changgou-common，并在其pom文件中导入相关依赖，然后在src-java文件下新建entity包，
    里边有相关数据，如项目中entity包下所示

# 9、在changgou-parent父目录下，创建连接数据的子模块 ，命名为 changgou-common-db，不需要写代码，因此不需要src包


