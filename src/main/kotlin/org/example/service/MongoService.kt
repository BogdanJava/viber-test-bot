package org.example.service

import com.mongodb.MongoClient

/**
 * @author ts-bahdan.shyshkin
 */
class MongoService(host: String, port: Int) {
    private val client = MongoClient(host, port)
}