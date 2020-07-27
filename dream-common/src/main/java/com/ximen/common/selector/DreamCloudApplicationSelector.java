package com.ximen.common.selector;

import com.ximen.common.config.DreamAuthExceptionConfigure;
import com.ximen.common.config.DreamOAuth2FeignConfigure;
import com.ximen.common.config.DreamServerProtectConfigure;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author zhishun.cai
 * @date 2020/7/24 18:11
 * @note
 */
public class DreamCloudApplicationSelector implements ImportSelector {
    /**
     * 通过selectImports方法，我们一次性导入了
     * DreamAuthExceptionConfigure、DreamOAuth2FeignConfigure和DreamServerProtectConfigure这三个配置类。
     * @param annotationMetadata
     * @return
     */
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                DreamAuthExceptionConfigure.class.getName(),
                DreamOAuth2FeignConfigure.class.getName(),
                DreamServerProtectConfigure.class.getName()
        };
    }
}
