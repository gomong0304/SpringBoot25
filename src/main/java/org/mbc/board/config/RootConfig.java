package org.mbc.board.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 환경설정용이다~ 스프링한테 알려줌
public class RootConfig {

    @Bean // 환경 설정용 객체로 지정 xml 안쓰고 @로 만든다.
    public ModelMapper getMapper(){
        // build.gradle 에  implementation 'org.modelmapper:modelmapper:3.1.0' 내용이 들어가야한다!
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }// Entity 를 DTO 로 변환하게 환경설정함.
     // https://velog.io/@hjhearts/SpringModelMapper-ModelMapper%EC%82%AC%EC%9A%A9%EB%B2%95-%EC%B4%9D%EC%A0%95%EB%A6%AC
}
