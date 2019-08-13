package sdp.queuemanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket docketConfig() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("sdp.queuemanager.controller")).build()
				.apiInfo(apiInfoBuilder());
	}

	private ApiInfo apiInfoBuilder() {
		return new ApiInfoBuilder().title("Message Queue Manager API").description("A simple message queue manager.")
				.version("0.0.1").build();
	}
}
