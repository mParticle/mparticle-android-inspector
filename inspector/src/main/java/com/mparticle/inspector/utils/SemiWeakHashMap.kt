package com.mparticle.inspector.utils

import com.mparticle.inspector.extensions.isPrimitiveOrString
import java.util.*

class SemiWeakHashMap<Any, T>: MutableMap<Any, T> {
    val objectMap = WeakHashMap<Any, T>()
    val primitiveMap = HashMap<String, T>()

    override fun clear() {
        primitiveMap.clear()
        objectMap.clear()
    }

    override fun putAll(from: Map<out Any, T>) {
        from.forEach{ put(it.key, it.value) }
    }

    override fun remove(key: Any): T? {
        if (key == null) {
            return null
        }
        if (key.isPrimitiveOrString()) {
            return primitiveMap.remove(key.toString())
        } else {
            return objectMap.remove(key)
        }
    }

    override val entries: MutableSet<MutableMap.MutableEntry<Any, T>>
        get() = objectMap.entries.apply {
            primitiveMap.entries.forEach {
                add(AbstractMap.SimpleEntry<Any, T>(it.key as Any, it.value))
            }
        }
    override val keys: MutableSet<Any>
        get() = objectMap.keys.apply {
            primitiveMap.keys.forEach{ add(it as Any) }
        }

    override val size: Int
        get() = primitiveMap.size + objectMap.size

    override val values: MutableCollection<T>
        get() = primitiveMap.values.apply { addAll(objectMap.values) }

    override fun containsValue(value: T): Boolean {
        return primitiveMap.containsValue(value) || objectMap.containsValue(value)
    }

    override fun isEmpty(): Boolean {
        return primitiveMap.isEmpty() || objectMap.isEmpty()
    }

    override fun get(key: Any): T? {
        if (key == null) {
            return null
        }
        return if (key.isPrimitiveOrString()) {
            primitiveMap.get(key.toString())
        } else {
            objectMap.get(key)
        }
    }

    override fun containsKey(key: Any): Boolean {
        if (key == null) {
            return false
        }
        return if (key.isPrimitiveOrString()) {
            primitiveMap.contains(key.toString())
        } else {
            objectMap.contains(key)
        }
    }

    override fun put(key: Any, value: T): T? {
        if (key == null) {
            return null
        }
        return if (key.isPrimitiveOrString()) {
            primitiveMap.put(key.toString(), value)
        } else {
            objectMap.put(key, value)
        }
    }

    override fun toString(): String {
        return entries.joinToString { "${it.key}: ${it.value}" }
    }
}