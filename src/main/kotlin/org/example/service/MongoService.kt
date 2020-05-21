package org.example.service

import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.mongodb.client.result.DeleteResult
import org.example.model.ViberAccount


const val db = "viber-bot"
const val usersCollection = "users"

/**
 * @author ts-bahdan.shyshkin
 */
class MongoService(host: String, port: Int) {
    private var users: MongoCollection<ViberAccount>
    private var botDb: MongoDatabase
    private val client = MongoClient(host, port)

    init {
        botDb = client.getDatabase(db)
        users = botDb.getCollection(usersCollection, ViberAccount::class.java)
    }

    fun getById(id: String): ViberAccount? {
        return users.find(Filters.eq("id", id), ViberAccount::class.java).first()
    }

    fun deleteById(id: String, onDelete: (deleteResult: DeleteResult) -> Unit = {}) {
        val result = users.deleteOne(Filters.eq("id", id))
        onDelete(result)
    }

    fun save(user: ViberAccount, onSuccess: (user: ViberAccount) -> Unit = {}): ViberAccount {
        val account = users.find(Filters.eq("id", user.id), ViberAccount::class.java).first()
        if (account == null) {
            users.insertOne(user)
            onSuccess(user)
            return user
        } else {
            return account
        }
    }
}
