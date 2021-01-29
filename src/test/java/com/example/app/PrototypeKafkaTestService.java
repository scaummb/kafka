package com.example.app;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * <p>
 *     测试多例模式下，消费kakfa消息会出现的性能问题
 * </p>
 * @author moubin.mo
 * @date: 2021/1/27 16:13
 */
@Component
@Scope("prototype")
public class PrototypeKafkaTestService {

}
