package com.dy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
class EchoController {
    @Value("${useLocalCache:}")
    private String useLocalCache;
    @RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
    public String echo(@PathVariable String string) {
        if(useLocalCache!=null){
            return "323."+useLocalCache ;
        }
        return "Hello Nacos Discovery " + string;
    }
}