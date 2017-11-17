package com.wgl.exam;

import com.wgl.exam.Repository.*;
import com.wgl.exam.domain.*;
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

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	OptionRepository optionRepository;

	@Autowired
	ExamRepository examRepository;

	@Autowired
	ExamQuestionRepository examQuestionRepository;

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

		User user = new User(UserType.MANAGER,"admin",Common.EncoderByMd5("111"),"xxx@xxx.com","123456789",new Date());
		userRepository.save(user);
		user = new User(UserType.STUDENT,"student",Common.EncoderByMd5("111"),"","",new Date());
		userRepository.save(user);

		user = new User(UserType.STUDENT,"test",Common.EncoderByMd5("111"),"","",new Date());
		userRepository.save(user);

		user = new User(UserType.TEACHER,"teacher",Common.EncoderByMd5("111"),"","",new Date());
		userRepository.save(user);


		QuestionType questionType = new QuestionType("单选题");
		QuestionType qt =  questionTypeRepository.save(questionType);

		Question question = new Question("选择题一","1下列句子中不该用问号的一项是____",5f,"",qt.getId(),"D");
		Question q = questionRepository.save(question);
		Option option = new Option("为什么我的眼里常含泪水？因为我对这土地爱得深沉……",q.getId());
		optionRepository.save(option);
		option = new Option("难道你认为一场考试的失误就意味着输掉了整个人生吗？",q.getId());
		optionRepository.save(option);
		option = new Option("生活因有音乐而变得更美好，不是吗？",q.getId());
		optionRepository.save(option);
		option = new Option("我们要思考怎样才能做一个对社会有用的人？",q.getId());
		optionRepository.save(option);

        question = new Question("选择题五","5下列句子中不该用问号的一项是____",5f,"",1L,"D");
        q = questionRepository.save(question);
        option = new Option("为什么我的眼里常含泪水？因为我对这土地爱得深沉……",q.getId());
        optionRepository.save(option);
        option = new Option("难道你认为一场考试的失误就意味着输掉了整个人生吗？",q.getId());
        optionRepository.save(option);
        option = new Option("生活因有音乐而变得更美好，不是吗？",q.getId());
        optionRepository.save(option);
        option = new Option("我们要思考怎样才能做一个对社会有用的人？",q.getId());
        optionRepository.save(option);

		questionType = new QuestionType("多选题");
		qt = questionTypeRepository.save(questionType);

        question = new Question("多选题","下列正确的选项有____",5f,"",qt.getId(),"A|D");
        q = questionRepository.save(question);
        option = new Option("3*5=15",q.getId());
        optionRepository.save(option);
        option = new Option("2+2=3",q.getId());
        optionRepository.save(option);
        option = new Option("3*3=8",q.getId());
        optionRepository.save(option);
        option = new Option("3+1=4",q.getId());
        optionRepository.save(option);
        option = new Option("5+9=15",q.getId());
        optionRepository.save(option);


        question = new Question("选择题二","2下列句子中不该用问号的一项是____",5f,"",qt.getId(),"D");
        q = questionRepository.save(question);
        option = new Option("为什么我的眼里常含泪水？因为我对这土地爱得深沉……",q.getId());
        optionRepository.save(option);
        option = new Option("难道你认为一场考试的失误就意味着输掉了整个人生吗？",q.getId());
        optionRepository.save(option);
        option = new Option("生活因有音乐而变得更美好，不是吗？",q.getId());
        optionRepository.save(option);
        option = new Option("我们要思考怎样才能做一个对社会有用的人？",q.getId());
        optionRepository.save(option);


        question = new Question("选择题四","4下列句子中不该用问号的一项是____",5f,"",qt.getId(),"D");
        q = questionRepository.save(question);
        option = new Option("为什么我的眼里常含泪水？因为我对这土地爱得深沉……",q.getId());
        optionRepository.save(option);
        option = new Option("难道你认为一场考试的失误就意味着输掉了整个人生吗？",q.getId());
        optionRepository.save(option);
        option = new Option("生活因有音乐而变得更美好，不是吗？",q.getId());
        optionRepository.save(option);
        option = new Option("我们要思考怎样才能做一个对社会有用的人？",q.getId());
        optionRepository.save(option);

        question = new Question("选择题六","6下列句子中不该用问号的一项是____",5f,"",1L,"D");
        q = questionRepository.save(question);
        option = new Option("为什么我的眼里常含泪水？因为我对这土地爱得深沉……",q.getId());
        optionRepository.save(option);
        option = new Option("难道你认为一场考试的失误就意味着输掉了整个人生吗？",q.getId());
        optionRepository.save(option);
        option = new Option("生活因有音乐而变得更美好，不是吗？",q.getId());
        optionRepository.save(option);
        option = new Option("我们要思考怎样才能做一个对社会有用的人？",q.getId());
        optionRepository.save(option);

		questionType = new QuestionType("问答题");
        qt = questionTypeRepository.save(questionType);

        question = new Question("问答题一","请用“一边..一边..”造句",15f,"点评",qt.getId(),"我一边走路，一边听音乐。");
        q = questionRepository.save(question);


//		questionType = new QuestionType("选择题3");
//        qt = questionTypeRepository.save(questionType);
//
//
//
//
//		questionType = new QuestionType("选择题4");
//        qt = questionTypeRepository.save(questionType);
//
//
//
//		questionType = new QuestionType("选择题5");
//        qt = questionTypeRepository.save(questionType);
//
//
//
//
//		questionType = new QuestionType("选择题6");
//        qt = questionTypeRepository.save(questionType);
//
//
//
//
//
//		questionType = new QuestionType("选择题7");
//        qt = questionTypeRepository.save(questionType);
//
//
//
//		questionType = new QuestionType("选择题8");
//        qt = questionTypeRepository.save(questionType);
//
//
//
//		questionType = new QuestionType("选择题9");
//        qt = questionTypeRepository.save(questionType);
//
//
//
//		questionType = new QuestionType("选择题10");
//        qt = questionTypeRepository.save(questionType);
//
//
//
//		questionType = new QuestionType("选择题11");
//        qt = questionTypeRepository.save(questionType);
//
//
//
//		questionType = new QuestionType("选择题12");
//        qt = questionTypeRepository.save(questionType);


		Exam exam = new Exam("期中测试卷",15f,5f,90,new Date());

		Exam e = examRepository.save(exam);

		ExamQuestion eq = new ExamQuestion(e.getId(),question.getId());

		examQuestionRepository.save(eq);

		exam = new Exam("期末测试卷",25f,10f,90,new Date(System.currentTimeMillis()+1000000000L));
		e = examRepository.save(exam);
		eq = new ExamQuestion(e.getId(),question.getId());

        examQuestionRepository.save(eq);

        eq = new ExamQuestion(e.getId(),2L);
        examQuestionRepository.save(eq);

        eq = new ExamQuestion(e.getId(),3L);

		examQuestionRepository.save(eq);




	}
}
