package com.mzt.generator.mysql.mybatisplusgenerator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class MybatisPlusGeneratorApplicationTests {


	@Test
	void contextLoads() {

	}

	@Test
	public void generateCode() {
		generate("mysql", "course");
	}

	private void generate(String moduleName, String... tableNamesInclude){
		AutoGenerator mpg = new AutoGenerator();

		// 全局配置
		GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
		String projectPath = System.getProperty("user.dir");
		gc.setOutputDir(projectPath + "/src/main/java");
		gc.setAuthor("mazhitao");
		gc.setOpen(false);
		//默认不覆盖，如果文件存在，将不会再生成，配置true就是覆盖
		gc.setFileOverride(true);
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setUrl("jdbc:mysql://localhost:3306/school?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2B8");
		// dsc.setSchemaName("public");
		dsc.setDriverName("com.mysql.jdbc.Driver");
		dsc.setUsername("root");
		dsc.setPassword("123456");
		mpg.setDataSource(dsc);

		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setModuleName(moduleName);
		pc.setParent("com.baomidou.mybatisplus.samples.generator");
		mpg.setPackageInfo(pc);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		strategy.setNaming(NamingStrategy.underline_to_camel);
		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
		//strategy.setSuperEntityClass("com.baomidou.mybatisplus.samples.generator.common.BaseEntity");
		strategy.setEntityLombokModel(true);
		// strategy.setSuperControllerClass("com.baomidou.mybatisplus.samples.generator.common.BaseController");
		strategy.setInclude(tableNamesInclude);
		strategy.setSuperEntityColumns("id");
		strategy.setControllerMappingHyphenStyle(true);
		strategy.setTablePrefix(pc.getModuleName() + "_");
		strategy.entityTableFieldAnnotationEnable(true);
		mpg.setStrategy(strategy);
		// 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
		mpg.setTemplateEngine(new FreemarkerTemplateEngine());

		configCustomizedCodeTemplate(mpg);
		configInjection(mpg);

		mpg.execute();
	}

	/**
	 * 自定义模板
	 * @param mpg
	 */
	private void configCustomizedCodeTemplate(AutoGenerator mpg){
		//配置 自定义模板
		TemplateConfig templateConfig = new TemplateConfig()
				.setEntity("templates/MyEntityTemplate.java")//指定Entity生成使用自定义模板
				.setController("templates/MyControllerTemplate.java")
				.setService("templates/MyServiceTemplate.java")
				.setServiceImpl("templates/MyServiceImplTemplateTemplate.java")
				.setXml(null);//不生成xml
		mpg.setTemplate(templateConfig);
	}

	/**
	 * 配置自定义参数/属性
	 *
	 * @param mpg
	 */
	private void configInjection(AutoGenerator mpg){
		// 自定义配置
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				Map<String, Object> map = new HashMap<>();
				map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
				this.setMap(map);
                /*
                自定义属性注入: 模板配置：abc=${cfg.abc}
                 */
			}
		};
//        List<FileOutConfig> focList = new ArrayList<>();
//        focList.add(new FileOutConfig("/templates/MyEntityTemplate.java.ftl") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 指定模板生，自定义生成文件到哪个地方
//                return "D:/abc";
//            }
//        });
//        cfg.setFileOutConfigList(focList);
		mpg.setCfg(cfg);
	}

}
