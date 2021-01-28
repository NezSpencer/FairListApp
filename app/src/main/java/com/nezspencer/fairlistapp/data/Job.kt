package com.nezspencer.fairlistapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Job(
    @PrimaryKey
    @Json(name = "id") val id: String,
    @Json(name = "type") val type: String,
    @Json(name = "url") val url: String,
    @Json(name = "created_at") val createdAt: String,
    @Json(name = "company") val company: String,
    @Json(name = "company_url") val companyUrl: String?,
    @Json(name = "location") val location: String,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "how_to_apply") val howToApply: String,
    @Json(name = "company_logo") val companyLogo: String?,
    var ttl: Long = 0L // this is the cache expiration time. will be set to 5 minutes from time of fetch
) : Parcelable

fun Job.hasSameContent(otherJob: Job): Boolean =
    id == otherJob.id &&
            type == otherJob.type
            && url == otherJob.url
            && createdAt == otherJob.createdAt
            && company == otherJob.company
            && companyUrl == otherJob.companyUrl
            && location == otherJob.location
            && title == otherJob.title
            && description == otherJob.description
            && howToApply == otherJob.howToApply
            && companyLogo == otherJob.companyLogo