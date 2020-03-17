# job任务使用

基于quartz 及springboot 对quartz 自动装配实现

## 配置

    spring:
        quartz:
        #相关属性配置
            properties:
                org:
                    quartz:
                        scheduler:
                            instanceName: clusteredScheduler
                            instanceId: DEMO_JOB
                        jobStore:
                            class: org.quartz.impl.jdbcjobstore.JobStoreTX
                            driverDelegateClass: org.quartz.impl.jdbcjobstore.StdJDBCDelegate
                            tablePrefix: DEMO_JOB_QRTZ_
                            isClustered: true
                            clusterCheckinInterval: 10000
                            useProperties: false
                            dataSource: datasource
                        threadPool:
                            class: org.quartz.simpl.SimpleThreadPool
                            threadCount: 10
                            threadPriority: 5
                            threadsInheritContextClassLoaderOfInitializingThread: true
            #数据库方式
            job-store-type: jdbc
            Jdbc:
                initializeSchema: NEVER
                
## 注意事项
+ spring.quartz.Jdbc.initializeSchema配置不生效，默认系统启动时会自动初始化quartz的数据库表，
如果不需要创建需要在启动类中增加`@SpringBootApplication(exclude = LiquibaseAutoConfiguration.class)`

+ 如果使用单机任务则不需要配置数据库与yaml配置


## quartz 表结构
参考`./quartz_dll.sql`