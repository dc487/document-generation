package org.rha.services.document_generation.v2;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import org.rha.services.document_generation.v2.dto.ConvertPipelineStep;
import org.rha.services.document_generation.v2.dto.ExportPipelineStep;
import org.rha.services.document_generation.v2.dto.TemplatePipelineStep;

import java.io.IOException;

public class PipelineStepResolver extends TypeIdResolverBase {
    @Override
    public String idFromValue(Object o) {
        throw new RuntimeException("required for serialization only");
    }

    @Override
    public String idFromValueAndType(Object o, Class<?> aClass) {
        throw new RuntimeException("required for serialization only");
    }

    @Override
    public JavaType typeFromId(DatabindContext context, String id) throws IOException {
        switch (id) {
            case "TEMPLATE":
            {
                return context.getTypeFactory().constructType(new TypeReference<TemplatePipelineStep>() {});
            }
            case "CONVERT":
            {
                return context.getTypeFactory().constructType(new TypeReference<ConvertPipelineStep>() {});
            }
            case "EXPORT":
            {
                return context.getTypeFactory().constructType(new TypeReference<ExportPipelineStep>() {});
            }
            default:
            {
                throw new IllegalArgumentException(id + " not known");
            }
        }
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.CUSTOM;
    }
}
