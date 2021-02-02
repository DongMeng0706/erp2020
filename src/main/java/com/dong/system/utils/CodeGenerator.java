package com.dong.system.utils;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class CodeGenerator {


    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");//得到当前项目的的根目录
        String projectPath="F:/JavaWorkSpace/erp2020";//F:/JavaWorkSpace/erp2020
        gc.setOutputDir(projectPath + "/src/main/java");//代码生成的位置
        gc.setAuthor("DM");
        gc.setOpen(false);//当生成成功之后是否打开代码所在的文件夹
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
//        gc.setActiveRecord(false) 是否启动model的Ar模式
        gc.setIdType(IdType.AUTO);

//        gc.setServiceName("%sService");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/erp?useUnicode=true&characterEncoding=utf8&useSSL=true&serverTimezone=UTC");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("system");
        pc.setParent("com.dong");

//        pc.setController("mycontroller");
        pc.setEntity("domain");
//        pc.setMapper("mymapper");
//        pc.setService("myservice");
//        pc.setServiceImpl("myservice.myimpl");
        pc.setXml("mapper.mapper");

        mpg.setPackageInfo(pc);

        // 自定义配置
//        InjectionConfig cfg = new InjectionConfig() {
//            @Override
//            public void initMap() {
//                // to do nothing
//            }
//        };
//        mpg.setCfg(cfg);

//        // 配置模板
//        TemplateConfig templateConfig = new TemplateConfig();
//
//        // 配置自定义输出模板
//        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
//        // templateConfig.setEntity("templates/entity2.java");
//        // templateConfig.setService();
//        // templateConfig.setController();
//
//        templateConfig.setXml(null);
//        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);//去除表名的下滑线，以大驼峰代替
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//去除字段名的下滑线，以小驼峰代替
//        strategy.setSuperEntityClass("com.sxt.entity.BaseEntity");//设置生成的实体的父类
        strategy.setEntityLombokModel(true);//是否启用lombok的注解
        strategy.setRestControllerStyle(true);
        // 设置controller的公共父类
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id","name");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));  //指定要生成的表，如果不指定，生成当前库里面的所有表
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix( "bus_");//设置表的前缀。生成对象时会去掉对的前缀
        mpg.setStrategy(strategy);
        mpg.execute();
    }
}
