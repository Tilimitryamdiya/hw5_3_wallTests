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
    val likes: Likes = Likes()
)

data class Comments(
    var count: Int = 0,
    var canPost: Boolean = true,
    var groupsCanPost: Boolean = false,
    var canClose: Boolean = false,
    var canOpen: Boolean = false
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
                    likes = post.likes
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
        likes = Likes(count = 1)
    )

    val post2 = Post(
        text = "second post",
        date = 101022,
        likes = Likes(count = 10)
    )

    val newPost2 = Post(
        id = post2.id,
        text = "New text",
        date = 111022
    )


    val service = WallService
    service.add(post1)
    service.add(post2)

    println(service.update(newPost2))
}
