    document.addEventListener("DOMContentLoaded", function() {
        const postId = window.location.pathname.split("/").pop(); // URL에서 post ID 추출
        fetchPostDetail(postId);
        fetchComments(postId);
    });

    function fetchPostDetail(postId) {
        fetch(`/api/post/${postId}`,{credentials: 'include'})
            .then(response => response.json())
            .then(data => {
                document.getElementById("postId").textContent = data.id;
                document.getElementById("postTitle").textContent = data.title;
                document.getElementById("postContent").textContent = data.content;
                document.getElementById("postAuthor").textContent = data.authorName;
                document.getElementById("postDate").textContent = new Date(data.created).toLocaleString();
                document.getElementById("postViewCount").textContent = data.viewCount;

                // 댓글 작성 폼에 postId 설정
                document.getElementById("postIdValue").value = data.id;

                if (data.checkAuth) {
                document.getElementById("editButton").style.display = "block";
                document.getElementById("editButton").addEventListener("click", function() {
                    window.location.href = `/post/edit/${data.id}`;
                    });

                document.getElementById("deleteButton").style.display = "block";
                document.getElementById("deleteButton").addEventListener("click", function() {
                    if (confirm("정말로 이 게시글을 삭제하시겠습니까?")) {
                        deletePost(data.id);
                    }
                });
                } else {
                document.getElementById("editButton").style.display = "none";
                document.getElementById("deleteButton").style.display = "none";
                }
            })
            .catch(error => {
                console.error("Error fetching post detail:", error);
            });
    }

    function fetchComments(postId) {
    fetch(`/api/comment/${postId}`)
        .then(response => response.json())
        .then(comments => {
            const commentsList = document.getElementById("commentsList");
            commentsList.innerHTML = "";

            comments.forEach(comment => {
                const li = document.createElement("li");

                const strong = document.createElement("strong");
                strong.textContent = `${comment.displayName}: `;

                const span = document.createElement("span");
                span.textContent = comment.content;

                li.appendChild(strong);
                li.appendChild(span);

                commentsList.appendChild(li);
            });
        })
        .catch(error => {
            console.error("Error fetching comments:", error);
        });
    }


    function submitComment() {
        const content = document.getElementById("commentContent").value;
        const postId = document.getElementById("postIdValue").value;

        fetch("/api/comment", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                content: content,
                postId: postId
            })
        })
        .then(response => response.json())
        .then(data => {
            location.reload(); // 댓글 작성 후 페이지 새로고침
        })
        .catch(error => {
            console.error("Error submitting comment:", error);
            alert("댓글 작성 중 오류가 발생했습니다.");
        });
    }

    function deletePost(postId) {
    fetch(`/api/post/${postId}`, {
        method: "DELETE",
        credentials: 'include'
    })
    .then(response => {
        if (response.ok) {
            alert("게시글이 삭제되었습니다.");
            window.location.href = "/home";
        } else if (response.status === 403) {
            alert("게시글 삭제 권한이 없습니다.");
        } else {
            alert("게시글 삭제에 실패했습니다.");
        }
    })
    .catch(error => {
        console.error("Error deleting post:", error);
        alert("오류가 발생했습니다.");
    });
}

