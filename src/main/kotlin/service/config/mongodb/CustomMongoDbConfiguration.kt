package service.config.mongodb

import com.mongodb.MongoClientSettings.Builder
import org.bson.UuidRepresentation.STANDARD
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer
import org.springframework.context.annotation.Configuration

@Configuration
class CustomMongoDbConfiguration : MongoClientSettingsBuilderCustomizer {

    override fun customize(builder: Builder) {
        builder
            .uuidRepresentation(STANDARD) // use UUID4
            .retryReads(false)
            .retryWrites(false)
    }
}
