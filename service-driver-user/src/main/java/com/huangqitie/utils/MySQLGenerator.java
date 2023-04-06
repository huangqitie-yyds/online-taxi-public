package com.huangqitie.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.util.Collections;

public class MySQLGenerator {
    public static void main(String[] args) {

        FastAutoGenerator.create(
                        "jdbc:mysql://localhost:3306/service-driver-user?characterEncoding=utf-8&serverTimezone=GMT%2B8",
                        "root", "961489161")
                .globalConfig(builder -> {
                    builder.author("黄启贴").fileOverride().outputDir(
                            "/Users/huangqitie/IdeaProjects/online-taxi-public/service-driver-user/src/main/java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.huangqitie")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                                    "/Users/huangqitie/IdeaProjects/online-taxi-public/service-driver-user/src/main/java/com/huangqitie/mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("driver_user_work_status");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
