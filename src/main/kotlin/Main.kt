package org.noblak

import com.mongodb.MongoException
import com.mongodb.kotlin.client.coroutine.MongoClient
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.BsonInt64
import org.bson.Document

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
suspend fun main() {
    val name = "Kotlin"
    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    println("Hello, " + name + "!")

    for (i in 1..5) {
        //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
        // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
        println("i = $i")
    }
    val connectionString = System.getenv("MONGODB_URI") ?: "mongodb://admin:admin@localhost:27017/"
    val client = MongoClient.create(connectionString = connectionString)
    val database = client.getDatabase(databaseName = "product_data")

    try {
        // Send a ping to confirm a successful connection
        val command = Document("ping", BsonInt64(1))
        database.runCommand(command)
        println("Pinged your deployment. You successfully connected to MongoDB!")

        // Try to store data
        database.createCollection("data_storage")
        val collection = database.getCollection<DataStorage>("data_storage")
        val record = DataStorage("tape", 5.0)
        collection.insertOne(record)
    } catch (me: MongoException) {
        System.err.println(me)
    }
    while (true) {

    }
}

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
    }
}