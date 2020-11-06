function onload(ev) {
    // 有用户点击 button，我们发起 ajax
    let xhr = new XMLHttpRequest();
    xhr.open("get", "/api/article-list.json");
    // 使用了一个匿名函数，定义一个函数，没有给名字（对比 java 中的匿名类来理解）
    xhr.onreadystatechange = function (ev) {
        if (xhr.readyState == XMLHttpRequest.DONE) {    // ajax 请求完成时
            if (xhr.status == 200) {                    // HTTP 响应 200 的时候

                let articleList = JSON.parse(xhr.responseText); // 把结果按照 JSON 格式解析
                console.log(articleList);

                // 把得到的结果，放入 html 中
                let articleListElement = document.querySelector("#article-list");    // html 输出的标签
                if (articleList.length > 0) {
                    articleListElement.innerHTML = "";
                    for (let i in articleList) {
                        let article = articleList[i];
                        let html = "<li>" +
                            // 中括号的用法，类似 java 中 map.get("id"); 的用法
                            "<a href=\"/article?id=" + article["id"] + "\">" +
                            article["title"] +
                            "</a>" + article["published-at"] + "</li>";
                        console.log(html);
                        articleListElement.innerHTML += html;    // 把拼接好的 html，放入标签中
                    }
                }
            }
        }
    };
    xhr.send();
}

window.onload(onload);