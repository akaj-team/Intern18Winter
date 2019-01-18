package asiantech.internship.summer.kotlin.recyclerview.model

data class TimelineItem1(
        val avatar: Int,
        val picture: Int,
        var countLike: Int,
        val username: String,
        val commenter: String,
        val comment: String,
        val isChecked: Boolean)