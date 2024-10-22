    document.getElementById('postForm').addEventListener('submit', function(event) {
        event.preventDefault(); // 기본 폼 제출 막기

        const title = document.getElementById('title').value;
        const content = document.getElementById('content').value;

        fetch('/api/post', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ title, content })
        })
        .then(response => {
            if (response.ok) {
                window.location.href = '/home'; // 성공 시 홈 화면으로 이동
            } else {
                alert('게시글 작성에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('오류가 발생했습니다.');
        });
    });