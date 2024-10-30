    function searchPosts() {
        const keyword = document.getElementById('searchInput').value;

        // Fetch API를 사용하여 검색 API 호출
        fetch(`/api/posts?keyword=${encodeURIComponent(keyword)}`)
            .then(response => response.json())
            .then(data => {
                // 기존 게시글 목록을 검색 결과로 대체
                const postList = document.getElementById('postList');
                postList.innerHTML = '';

                data.forEach(post => {
                    const row = document.createElement('tr');

                    row.innerHTML = `
                        <td>${post.id}</td>
                        <td><a href="/post/${post.id}">${post.title}</a></td>
                        <td>${post.memberDisplayName}</td>
                        <td>${new Date(post.created).toLocaleString()}</td>
                        <td>${post.viewCount}</td>
                    `;

                    postList.appendChild(row);
                });
            })
            .catch(error => console.error('Error fetching search results:', error));
    }