package dev.danvega.blog.model.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import dev.danvega.blog.model.Author;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.io.IOException;

@JsonComponent
public class AuthorDeserializer extends JsonDeserializer<AggregateReference<Author,Integer>> {

    @Override
    public AggregateReference<Author, Integer> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
        return AggregateReference.to(Integer.parseInt( treeNode.get("id").toString() ));
    }

}
