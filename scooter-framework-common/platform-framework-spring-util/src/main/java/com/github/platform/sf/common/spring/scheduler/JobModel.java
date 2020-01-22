package com.github.platform.sf.common.spring.scheduler;


import lombok.*;

import java.util.SortedSet;
import java.util.TreeMap;

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
    private String cronExpression;

    /** 任务描述 */
    private String JobDescription;

    /** 扩展业务数据 */
    private T extData;

}
