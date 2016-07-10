/*
 * Copyright 1999-2012 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.joker.storage.protocol.mysql.constant;

/**
 * 事务隔离级别定义
 * 
 * @author xianmao.hexm
 */
public interface Isolations {

    /* 事务可以看到其他事务未提交的执行结果 */
    int READ_UNCOMMITTED = 1;

    /* 事务只能看到其他事务提交的执行结果, 但是事务中两次读可能返回不同结果 */
    int READ_COMMITTED = 2;

    /* 确保事务中的多个读请求读到相同的数据 */
    int REPEATED_READ = 3;

    /* 事务串行执行 */
    int SERIALIZABLE = 4;
}
