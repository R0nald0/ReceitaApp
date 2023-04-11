package com.example.receitas.data.local.model

import com.example.receitas.domain.model.Area
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId


class AreaDAO : RealmObject{
    @PrimaryKey
    var  _idArea : ObjectId = BsonObjectId()
    var nameArea: String = ""
}
fun AreaDAO.toArea() = Area(
    strArea = nameArea
)