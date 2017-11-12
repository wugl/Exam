package com.wgl.exam;

import com.wgl.exam.Repository.QuestionTypeRepository;
import com.wgl.exam.Repository.UserRepository;
import com.wgl.exam.domain.QuestionType;
import com.wgl.exam.domain.User;
import com.wgl.exam.uti.Common;
import com.wgl.exam.uti.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Date;

@SpringBootApplication
@EnableAutoConfiguration
@ServletComponentScan
public class ExamApplication extends WebMvcConfigurerAdapter implements CommandLineRunner {

    @Value("${spring.profiles.active}")
    private String profile;

	@Autowired
	UserRepository userRepository;

	@Autowired
	QuestionTypeRepository questionTypeRepository;

	public static void main(String[] args) {
		SpringApplication.run(ExamApplication.class, args);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/**")
				.allowedHeaders("Access-Control-Allow-Origin")
				.allowCredentials(false).maxAge(3600);
	}

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return new EmbeddedServletContainerCustomizer(){
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
//				container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/400"));
				container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500"));
				container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));


				container.setSessionTimeout(1800);//单位为S
			}
		};
	}
	@Override
	public void run(String... strings) throws Exception {
	    if(profile.equals("prod")) return;

		User user = new User(UserType.MANAGER,"admin",Common.EncoderByMd5("111"),"","",new Date());
		userRepository.save(user);
		user = new User(UserType.STUDENT,"student",Common.EncoderByMd5("111"),"","",new Date());
		userRepository.save(user);
		user = new User(UserType.TEACHER,"teacher",Common.EncoderByMd5("111"),"","",new Date());
		userRepository.save(user);


		QuestionType questionType = new QuestionType("选择题");
		questionTypeRepository.save(questionType);
		questionType = new QuestionType("选择题1");
		questionTypeRepository.save(questionType);
		questionType = new QuestionType("选择题2");
		questionTypeRepository.save(questionType);
		questionType = new QuestionType("选择题3");
		questionTypeRepository.save(questionType);
		questionType = new QuestionType("选择题4");
		questionTypeRepository.save(questionType);
		questionType = new QuestionType("选择题5");
		questionTypeRepository.save(questionType);
		questionType = new QuestionType("选择题6");
		questionTypeRepository.save(questionType);
		questionType = new QuestionType("选择题7");
		questionTypeRepository.save(questionType);
		questionType = new QuestionType("选择题8");
		questionTypeRepository.save(questionType);
		questionType = new QuestionType("选择题9");
		questionTypeRepository.save(questionType);
		questionType = new QuestionType("选择题10");
		questionTypeRepository.save(questionType);
		questionType = new QuestionType("选择题11");
		questionTypeRepository.save(questionType);
		questionType = new QuestionType("选择题12");
		questionTypeRepository.save(questionType);


	}
}
