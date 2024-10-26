    document.addEventListener("DOMContentLoaded", function() {
        const postId = window.location.pathname.split("/").pop(); // URL에서 post ID 추출
        document.getElementById("postId").value = postId;

        // 게시글 정보 가져오기
        fetch(`/api/post/${postId}`)
            .then(response => response.json())
            .then(data => {
                document.getElementById("title").value = data.title;
                document.getElementById("content").value = data.content;
            })
            .catch(error => {
                console.error("Error fetching post data:", error);
            });
    });

    document.getElementById('editForm').addEventListener('submit', function(event) {
        event.preventDefault(); // 기본 폼 제출 막기

        const postId = document.getElementById("postId").value;
        const title = document.getElementById('title').value;
        const content = document.getElementById('content').value;

        fetch(`/api/post/${postId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ title, content })
        })
        .then(response => {
            if (response.ok) {
                window.location.href = `/post/${postId}`; // 성공 시 게시글 상세 페이지로 이동
            } else {
                alert('게시글 수정에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('오류가 발생했습니다.');
        });
    });