package io.legado.app.data.entities

import android.os.Parcelable
import android.text.TextUtils
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import io.legado.app.utils.GSON
import io.legado.app.utils.fromJsonObject
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "searchBooks", indices = [(Index(value = ["bookUrl"], unique = true))])
data class SearchBook(
    @PrimaryKey
    var bookUrl: String = "",
    var origin: String = "",                     // 书源规则id(默认-1,表示本地书籍)
    var name: String? = null,
    var author: String? = null,
    var tag: String? = null,
    var coverUrl: String? = null,
    var intro: String? = null,
    var latestChapterTitle: String? = null,
    var time: Long = 0L,
    var variable: String? = null,
    var bookInfoHtml: String? = null
) : Parcelable, BaseBook {

    @IgnoredOnParcel
    @Ignore
    override var variableMap: HashMap<String, String>? = null
        get() = run {
            initVariableMap()
            return field
        }

    private fun initVariableMap() {
        if (variableMap == null) {
            variableMap = if (TextUtils.isEmpty(variable)) {
                HashMap()
            } else {
                GSON.fromJsonObject<HashMap<String, String>>(variable!!)
            }
        }
    }

    override fun putVariable(key: String, value: String) {
        initVariableMap()
        variableMap?.put(key, value)
        variable = GSON.toJson(variableMap)
    }
}