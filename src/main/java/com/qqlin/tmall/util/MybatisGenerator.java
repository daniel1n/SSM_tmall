package com.qqlin.tmall.util;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author qqlin
 * @date 2020-5-20
 */
public class MybatisGenerator {

    public static void main(String[] args) throws ParseException, IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
        String today = "2020-05-20";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date now = simpleDateFormat.parse(today);
        Date date = new Date();

        if (date.getTime() > now.getTime() + 1000 * 60 * 60 * 24) {
            System.err.println("————未成功运行————");
            System.err.println("————未成功运行————");
            System.err.println("本程序具有破坏作用，只能运行一次，如果必须运行，需要修改today变量为今天，如："
                    + simpleDateFormat.format(new Date()));
            return;
        }

        if (false) {
            return;
        }
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;

        final ClassLoader classLoader = MybatisGenerator.class.getClassLoader();
        final InputStream inputStream = classLoader.getResource("generatorConfig.xml").openStream();
        final ConfigurationParser configurationParser = new ConfigurationParser(warnings);
        final Configuration configuration = configurationParser.parseConfiguration(inputStream);
        inputStream.close();

        final DefaultShellCallback defaultShellCallback = new DefaultShellCallback(overwrite);
        final MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, defaultShellCallback, warnings);
        myBatisGenerator.generate(null);

        System.out.println("生成代码成功，只执行一次，以后执行会覆盖掉mapper，pojo，xml等文件上做的修改");
    }
}
