package com.github.platform.sf.core.job;

import lombok.*;

/**
 * 任务 基本信息 model
 * @author 张剑军
 * @Description
 * @date 2019/6/15 14:00
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JobModel<T> {
    /** 所属任务组 */
    private String group;

    /** 任务名 */
    private String name;

    /** 执行表达式 */
    private String cron;

    /** 任务描述 */
    private String description;

    /** 扩展业务 数据 */
    private T data;

}
