package com.mparticle.shared

import com.mparticle.shared.events.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer

class Serializer {

    val eventModule = SerializersModule { // 1
        polymorphic(Event::class) { // 2
            ApiCall::class with ApiCall.serializer()
            Kit::class with Kit.serializer()
            KitApiCall::class with KitApiCall.serializer()
            MessageEvent::class with MessageEvent.serializer()
            MessageTable::class with MessageTable.serializer()
            NetworkRequest::class with NetworkRequest.serializer()
            StateAllSessions::class with StateAllSessions.serializer()
            StateAllUsers::class with StateAllUsers.serializer()
            StateCurrentUser::class with StateCurrentUser.serializer()
            StateEvent::class with StateEvent.serializer()
            StateStatus::class with StateStatus.serializer()
            User::class with User.serializer()
        }
        polymorphic(Any::class) {
            Int::class with Int.serializer()
            Double::class with Double.serializer()
            ObjectArgument::class with ObjectArgument.serializer()
            Boolean::class with Boolean.serializer()
            Long::class with Long.serializer()
        }
        polymorphic(ObjectValue::class) {
            Primitive::class with Primitive.serializer()
            EnumObject::class with EnumObject.serializer()
            Obj::class with Obj.serializer()
            MapObject::class with MapObject.serializer()
            CollectionObject::class with CollectionObject.serializer()
            NullObject::class with NullObject.serializer()
        }
    }

    fun serialize(events: List<Event>): String {
        val collection = EventCollection(events)
        val json = Json(configuration = JsonConfiguration(useArrayPolymorphism = true), context = eventModule)
        return json.stringify(EventCollection.serializer(), collection)
    }

    fun deserialize(jsonString: String): List<Event> {
        val json = Json(configuration = JsonConfiguration(useArrayPolymorphism = true), context = eventModule)
        val eventCollection = json.parse(EventCollection.serializer(), jsonString)
        return eventCollection.list
    }



}
