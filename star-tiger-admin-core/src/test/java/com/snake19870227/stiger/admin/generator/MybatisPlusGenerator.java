package com.snake19870227.stiger.admin.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.mysql.cj.jdbc.Driver;

/**
 * @author Bu HuaYang
 */
public class MybatisPlusGenerator {

    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:13306/stigeradmin?useUnicode=true&serverTimezone=GMT&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName(Driver.class.getName());
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/star-tiger-admin-core/src/main/java");
        gc.setAuthor("buhuayang");
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setIdType(IdType.ASSIGN_UUID);
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        PackageConfig pc = new PackageConfig();
        pc.setParent("com.snake19870227.stiger.admin");
        pc.setEntity("entity.po");
        mpg.setPackageInfo(pc);

        TemplateConfig tc = new TemplateConfig();
        tc.setService(null).setServiceImpl(null).setController(null).setMapper(null).setXml(null);
        mpg.setTemplate(tc);

        StrategyConfig strategy = new StrategyConfig();
        strategy.setEntityBuilderModel(true);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        mpg.setStrategy(strategy);

        mpg.execute();
    }
}
