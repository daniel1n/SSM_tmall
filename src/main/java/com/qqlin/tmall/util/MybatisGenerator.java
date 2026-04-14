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
 * Command-line tool for generating MyBatis mapper, POJO, and XML files.
 *
 * <p>This class runs the MyBatis Generator to automatically create:</p>
 * <ul>
 *   <li>POJO classes (e.g., Product.java, User.java)</li>
 *   <li>Mapper interfaces (e.g., ProductMapper.java)</li>
 *   <li>XML mapper files (e.g., ProductMapper.xml)</li>
 * </ul>
 *
 * <p>Configuration is read from generatorConfig.xml in the classpath.
 * The tool includes a safety mechanism that prevents running more than once
 * after the originally specified date, as running multiple times will overwrite
 * any custom modifications to generated files.</p>
 *
 * <p>Warning: This tool has destructive behavior - it will overwrite existing
 * mapper, pojo, and xml files. Only run when initially setting up or when
 * regeneration is needed.</p>
 *
 * @author qqlin
 * @date 2020-5-20
 * @see MyBatisGenerator
 */
public class MybatisGenerator {

    /**
     * Main entry point for MyBatis code generation.
     *
     * <p>Executes the MyBatis Generator based on configuration in generatorConfig.xml.
     * Includes a date check to prevent accidental repeated runs:</p>
     * <ul>
     *   <li>If the current date is more than 24 hours past the configured "today" date, the tool will not run</li>
     *   <li>Modify the "today" variable to today's date if you need to run again</li>
     * </ul>
     *
     * @param args command line arguments (not used)
     * @throws ParseException if the date format is invalid
     * @throws IOException if generatorConfig.xml cannot be read
     * @throws XMLParserException if the configuration XML is malformed
     * @throws InvalidConfigurationException if the configuration contains invalid settings
     * @throws SQLException if database connection fails
     * @throws InterruptedException if the generation process is interrupted
     */
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
