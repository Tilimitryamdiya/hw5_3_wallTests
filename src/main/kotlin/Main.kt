data class Post(
    var id: Int = 0,
    val ownerId: Int = 0,
    val fromId: Int = 0,
    val createdBy: Int = 0,
    val date: Int,
    val text: String,
    val replyOwnerId: Int = 0,
    val replyPostId: Int = 0,
    val friendsOnly: Boolean = false,
    val comments: Comments = Comments(),
    val copyright: Copyright?,
    val likes: Likes = Likes(),
    val reposts: Reposts = Reposts(),
    val views: Views = Views(),
    val postType: String = "default",
    val postSource: PostSource?,
    val geo: Geo?,
    val signerId: Int = 0,
    val copyHistory: Array<Reposts> = emptyArray(),
    val canPin: Boolean = false,
    val canDelete: Int = 0,
    val canEdit: Int = 0,
    val isPinned: Int = 0,
    val markedAsAds: Boolean = false,
    val isFavorite: Boolean = false,
    val donut: Donut?,
    val postponedId: Int = 0
)

data class Comments(
    var count: Int = 0,
    var canPost: Boolean = true,
    var groupsCanPost: Boolean = false,
    var canClose: Boolean = false,
    var canOpen: Boolean = false
)

class Copyright(
    val id: Int = 0,
    val link: String,
    val name: String,
    val type: String
)

class Likes(
    count: Int = 0,
    var userLikes: Boolean = false,
    var canLike: Boolean = true,
    var canPublish: Boolean = false
) {
    var count: Int = count
        set(value) {
            if (value < 0) {
                return
            }
            field = value
        }
}

class Reposts(val count: Int = 0, val user_reposted: Boolean = false)

class Views(val count: Int = 0)

class PostSource(
    val type: String,
    val platform: String,
    val data: String,
    val url: String
)

class Geo(
    val type: String,
    val coordinates: String,
    val place: Place = Place()
)

class Place(
    val id: Int = 0,
    val title: String = "default",
    val latitude: Int = 0,
    val longitude: Int = 0,
    val created: Int = 0,
    val icon: String = "default",
    val checkins: Int = 0,
    val updated: Int = 0,
    val type: Int = 0,
    val country: Int = 0,
    val city: Int = 0,
    val address: String = "default"
)

class Donut(
    val isDonut: Boolean,
    val paidDuration: Int,
    val placeholder: String,
    val canPublishFreeCopy: Boolean,
    val edit_mode: String
)

object WallService {
    private var posts = emptyArray<Post>()
    private var postId = 1

    fun add(post: Post): Post {
        post.id = postId
        posts += post
        postId++
        return posts.last()
    }

    fun update(post: Post): Boolean {
        val (id) = post
        for ((index, post) in posts.withIndex()) {
            if (post.id == id) {
                posts[index] = post.copy(
                    fromId = post.fromId,
                    createdBy = post.createdBy,
                    text = post.text,
                    replyOwnerId = post.replyOwnerId,
                    replyPostId = post.replyPostId,
                    friendsOnly = post.friendsOnly,
                    comments = post.comments,
                    copyright = post.copyright,
                    likes = post.likes,
                    reposts = post.reposts,
                    views = post.views,
                    postType = post.postType,
                    postSource = post.postSource,
                    geo = post.geo,
                    signerId = post.signerId,
                    copyHistory = post.copyHistory,
                    canPin = post.canPin,
                    canDelete = post.canDelete,
                    canEdit = post.canEdit,
                    isPinned = post.isPinned,
                    markedAsAds = post.markedAsAds,
                    isFavorite = post.isFavorite,
                    donut = post.donut,
                    postponedId = post.postponedId
                )
                return true
            }
        }
        return false
    }

}

fun main() {
    val post1 = Post(
        ownerId = 1,
        date = 100922,
        text = "first post",
        friendsOnly = true,
        likes = Likes(count = 1),
        copyright = null,
        postSource = null,
        geo = Geo("Home", "12.34.56"),
        donut = null
    )

    val post2 = Post(
        text = "second post",
        date = 101022,
        likes = Likes(count = 10),
        copyright = null,
        postSource = PostSource("vk", "android", "profilePhoto", "url"),
        geo = null,
        donut = null
    )

    WallService.add(post1)
    WallService.add(post2)

    val newPost2 = Post(
        id = post2.id,
        text = "New text",
        date = 111022,
        copyright = null,
        postSource = PostSource("vk", "android", "profilePhoto", "url"),
        geo = null,
        donut = null
    )


    println(WallService.update(newPost2))
}
