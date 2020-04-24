package org.rha.services.document_generation.v2.versioning;

import org.rha.services.document_generation.v2.dto.ConvertPipelineStep;
import org.rha.services.document_generation.v2.dto.ExportPipelineStep;
import org.rha.services.document_generation.v2.dto.PipelineStep;
import org.rha.services.document_generation.v2.dto.TemplatePipelineStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonParser;
import java.lang.reflect.Type;

public class PipelineDeserializer implements JsonbDeserializer<PipelineStep> {
    Logger logger = LoggerFactory.getLogger(PipelineDeserializer.class);

    @Override
    public PipelineStep deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Type type) {

        Jsonb jsonb = JsonbBuilder.create();
        JsonObject jsonObject = jsonParser.getObject();
        String pipelineStep = jsonObject.getString("pipelineStep");

        logger.info("Deserializing pipeline with step '" + pipelineStep);

        switch (pipelineStep) {
            case "TEMPLATE":
                logger.info("Detected as a templating step!");
                return jsonb.fromJson(jsonObject.toString(), TemplatePipelineStep.class);
            case "CONVERT":
                logger.info("Detected as a convert step!");
                return jsonb.fromJson(jsonObject.toString(), ConvertPipelineStep.class);
            case "EXPORT":
                logger.info("Detected as a export step!");
                return jsonb.fromJson(jsonObject.toString(), ExportPipelineStep.class);
            default:
                throw new JsonbException(pipelineStep + " is not a valid pipeline step type!");
        }
    }
}
