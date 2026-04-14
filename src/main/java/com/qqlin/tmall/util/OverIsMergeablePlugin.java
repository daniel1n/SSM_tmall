package com.qqlin.tmall.util;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.lang.reflect.Field;
import java.util.List;

/**
 * MyBatis Generator plugin that disables XML file merging during code generation.
 *
 * <p>When running the MyBatis Generator multiple times, it attempts to merge
 * newly generated XML files with existing ones to preserve custom modifications.
 * This plugin disables that behavior by setting the isMergeable flag to false,
 * ensuring the generator overwrites existing XML files completely.</p>
 *
 * <p>Configure this plugin in generatorConfig.xml:</p>
 * <pre>
 * &lt;plugin type="com.qqlin.tmall.util.OverIsMergeablePlugin"/&gt;
 * </pre>
 *
 * <p>Important: This plugin uses reflection to modify private fields and should
 * only be used when you want to ensure complete overwrite of generated files.</p>
 *
 * @author qqlin
 * @date 2020-5-20
 * @see PluginAdapter
 * @see org.mybatis.generator.api.GeneratedXmlFile
 */
public class OverIsMergeablePlugin extends PluginAdapter {
    /**
     * Validates the plugin configuration.
     *
     * <p>Always returns true as this plugin requires no special configuration.</p>
     *
     * @param warnings list of warning messages (not used)
     * @return always {@code true}
     */
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * Called after the SQL map file is generated, disables merging.
     *
     * <p>Uses reflection to access the private isMergeable field of GeneratedXmlFile
     * and sets it to false, ensuring the generator overwrites existing XML files.</p>
     *
     * @param sqlMap the generated XML file
     * @param introspectedTable the table for which XML was generated
     * @return always {@code true}
     */
    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        try {
            Field field = sqlMap.getClass().getDeclaredField("isMergeable");
            field.setAccessible(true);
            field.setBoolean(sqlMap, false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }
}
