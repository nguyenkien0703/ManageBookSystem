package com.example.borrowingservice.config;

import com.thoughtworks.xstream.XStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Bean

    public XStream xStream (){
        XStream xStream = new XStream();
        xStream.allowTypesByWildcard(new String[] {
                // convert tất cả các package trong com.example và các package con nữa convert thành xml
                "com.example.**"
        });
        return xStream;// trả về đối tượng sau khi đã chuyển dodooir
    }






}
